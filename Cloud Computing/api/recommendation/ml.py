#Ambil File buat Retraining
def train(data):
  try:
    import pandas as pd
    from tensorflow.saved_model import load
    # from urllib.request import urlopen
    import tensorflow_recommenders as tfrs
    import numpy as np
    import tensorflow as tf
    import keras
    from datetime import datetime 
    from typing import Dict, Text
    import json

    # response = urlopen(url_json)

    # data = json.load(response)

    dummy_subset = pd.DataFrame(data)

    dummy_subset

    dummy_subset['volunteer_id'] = dummy_subset.volunteer_id.astype(np.str)
    dummy_subset['category'] = dummy_subset.category.astype(np.str)
    dummy_subset['experience'] = dummy_subset.experience.astype(np.float32)

    dataset = tf.data.Dataset.from_tensor_slices((tf.cast(dummy_subset['volunteer_id'].values.reshape(-1,1), tf.string), tf.cast(dummy_subset['category'].values.reshape(-1,1), tf.string),
    tf.cast(dummy_subset['experience'].values.reshape(-1,1),tf.float32)))

    @tf.function
    def rename(x0,x1,x2):
        y = {}
        y["volunteer_id"] = x0
        y['category'] = x1
        y['experience'] = x2
        return y

    dataset = dataset.map(rename)

    category = dummy_subset.category.values
    volunteer = dummy_subset.volunteer_id.values

    unique_category = np.unique(list(category))
    unique_volunteer_ids = np.unique(list(volunteer))

    #Training to create new model

    class RankingModel(tf.keras.Model):

      def __init__(self):
        super().__init__()
        embedding_dimension = 5

        self.volunteer_embeddings = tf.keras.Sequential([
          tf.keras.layers.experimental.preprocessing.StringLookup(
            vocabulary=unique_volunteer_ids, mask_token=None),
          tf.keras.layers.Embedding(len(unique_volunteer_ids) + 1, embedding_dimension)
        ])

        self.category_embeddings = tf.keras.Sequential([
          tf.keras.layers.experimental.preprocessing.StringLookup(
            vocabulary=unique_category, mask_token=None),
          tf.keras.layers.Embedding(len(unique_category) + 1, embedding_dimension)
        ])

        self.ratings = tf.keras.Sequential([
          tf.keras.layers.Dense(16, activation="relu"),
          tf.keras.layers.Dense(32, activation="relu"),
          tf.keras.layers.Dense(1)
      ])

      def call(self, x, training=True, mask=None):
        
        volunteer_id, category = x
        volunteer_embedding = self.volunteer_embeddings(volunteer_id)
        category_embedding = self.category_embeddings(category)

        return self.ratings(tf.concat([volunteer_embedding, category_embedding], axis=1))

    class volunteersModel(tfrs.models.Model):

      def __init__(self):
        super().__init__()
        self.ranking_model: tf.keras.Model = RankingModel()
        self.task: tf.keras.layers.Layer = tfrs.tasks.Ranking(
          loss = tf.keras.losses.MeanSquaredError(),
          metrics=[tf.keras.metrics.RootMeanSquaredError()]
        )

      def call(self, features: Dict[str, tf.Tensor]) -> tf.Tensor:
        return self.ranking_model(
            (features["volunteer_id"], features["category"]))

      def compute_loss(self, features: Dict[Text, tf.Tensor], training=False) -> tf.Tensor:
        labels = features.pop("experience")

        mission_predictions = self(features)

        # The task computes the loss and the metrics.
        return self.task(labels=labels, predictions=mission_predictions)

    model = volunteersModel()
    model.compile(optimizer=tf.keras.optimizers.Adagrad(learning_rate=0.1))

    # Training 
    model.fit(dataset, epochs=150, verbose=1)
    
    tf.saved_model.save(model, "saved_model")
    
    return "Successfully Trained"
  
  except Exception as e:
    return f"An Error Occurred: {e}"

# train("https://relasia-api.herokuapp.com/ml/")

def predict(data, id):
  try:
    import pandas as pd 
    import numpy as np
    import tensorflow as tf
    import json
    # from urllib.request import urlopen
    # df = pd.read_csv("dummy__.csv", sep=",", encoding='utf8', engine="python")
    # open file model
    model= tf.saved_model.load("saved_model")
    # Open file_json
    # response = urlopen(file_json)
    # data= json.load(response)
    dataframe=pd.DataFrame(data)
    dataframe['category'] = dataframe.category.astype(np.str)
    unique_category = np.unique(list(dataframe['category']))
    
    # make predictions based on id 
    user = np.array(["{}".format(id) for i in range(len(unique_category))])
    test_data = tf.data.Dataset.from_tensor_slices((tf.cast(user.reshape(-1,1), tf.string), tf.cast(unique_category.reshape(-1,1), tf.string)))

    # Name the columns 
    @tf.function
    def rename_test(x0,x1):
        y = {}
        y["volunteer_id"] = x0
        y['category'] = x1
        return y
    test_data = test_data.map(rename_test)

    # Make predictions and store them in to dictionary
    test_mission = {}
    dictionary = {}
    for b in test_data:
        test_mission[b['category'].numpy()[0]] = model.ranking_model((b['volunteer_id'],b['category']))
    label = 0
    for b in sorted(test_mission, key=test_mission.get, reverse=True):
        dictionary[label] = b.decode("utf-8")
        label +=1
    
    return dictionary
    
    # print(dictionary)
    # with open("predict.json", "w") as outfile:
    #     json.dump(dictionary, outfile)
  
  except Exception as e:
        return f"An Error Occurred: {e}"
# # predict(file_json='https://relasia-api.herokuapp.com/ml/', id=30)