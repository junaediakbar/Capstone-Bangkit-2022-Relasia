# Machine Learning

# Recommendation System Model 
Recommendation System or Recommender System is a information filtering system based on machine learning which is made to predict user preference. The recommendation system is based on collaborative filtering techniques, which tried to predict user behaviour or preference based on collaborative data from all the user in the application

# Datasets 
=======
The project is based from Google Colab and laptop/PC. Using Machine Learning with Tensorflow as framework to recommendation system Collaborative Filtering . 

# Collaborative Filtering illustration
![Collaborative Filtering](https://user-images.githubusercontent.com/92794664/173233343-f585e8bd-693f-45cd-b71c-7021dc98b77d.png)

## 1. Indorelawan
Indorelawan dataset consists of data scrapped from indorelawan.org website. The dataset consist of four columns, which is nomor, nama, keahlian, and lokasi. Nomor is the index of the dataset, nama is the foundation's name, keahlian is the category of the mission (i.e. health, law, etc.), and lokasi is the location of the foundation. This data is further used to create dummy dataset for backend database and machine learning model building.

## 2. Dummydataset
Dummydataset is a dataset that is formed from the Indorelawan dataset. It consists of three columns, volunteer_id, keahlian, and pengalaman. Volunteer_id is the user identification in the database. Keahlian is the volunteer's mission category (i.e. health, law, etc.). Pengalaman describes how many did the volunteer helped in the same mission category. The data in the dummydataset dataset is generated with Python library, Faker.

# Training
The model is a rankings model consists of 2 submodels; retreival model and ranker model. 

## Retrieval Model
Retrieval model is used to map the keahlian or the volunteer's mission category and volunteer_id into embeddings. Therefore, a TensorFlow embedding algorithm is used for each of the 2 variables. Then at the final layers, a sequential model is used which consists of:
     -	`Dense(units=16, activation='relu')` layer
     -	`Dense(units=32, activation='relu')` layer
     -	`Dense(1)` layer

## Rankings Model
The rankings model is then used as the second submodels to rank the possible recommendation to the user based on the most to the least recommended.

## Dataset used
Dummydataset dataset is used to try and train the model. For the deployment a different dummy dataset which is already on the database will be used.

## Results
    - `mean_squared_error: 11,65%`
    - `loos : 1,35%`
    - `regularization_loss: 0%`
    - `total_loss: 1,35%`

# Deployment
The model architecture then deployed to backend service / google cloud for then the model will get the data and process it and finally send the recommendation to the application

## train
The train file is used to constantly train the model so that it can adapt to the updated dataset from the user. After training the model with the updated data, the file will then save the model to the google cloud storage, which then be used by the predict file to send the recommendation to the Android application

## predict
Predict is used to get the model that is saved for predictions. The file will get the updated data based on a specific volunteer id. The predict will return the recommendations prediction in a JSON format.

# Prerequisites
Function dependencies used in this project:
- keras==2.7.0
- numpy==1.20.3
- pandas==1.3.4
- tensorflow==2.7.0
- tensorflow_recommenders==0.6.0
