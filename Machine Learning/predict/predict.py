def predict(file_json,id):
    import pandas as pd 
    import numpy as np
    import tensorflow as tf
    import json
    from urllib.request import urlopen
    #df = pd.read_csv("dummy__.csv", sep=",", encoding='utf8', engine="python")
    #open file model
    model= tf.saved_model.load("saved_model")
    #Open file_json
    response = urlopen(file_json)
    data= json.load(response)
    dataframe=pd.DataFrame(data)
    dataframe['category'] = dataframe.category.astype(np.str)
    unique_category = np.unique(list(dataframe['category']))
    
    #make predictions based on id 
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
    print(dictionary)
    with open("predict.json", "w") as outfile:
        json.dump(dictionary, outfile)
predict(file_json='https://relasia-api.herokuapp.com/ml/', id=30)