# Machine Learning
Product Based Capstone Bangkit 2022

# Recommendation system Model 

The project is based from Google Colab and laptop/PC. Using Machine Learning with Tensorflow as framework to recommendation system Collaborative Filtering . 

# Collaborative Filtering illustration
![Collaborative Filtering](https://user-images.githubusercontent.com/92794664/173233343-f585e8bd-693f-45cd-b71c-7021dc98b77d.png)


# Prerequisites
Function dependencies used in this project:
- keras==2.7.0
- numpy==1.20.3
- pandas==1.3.4
- tensorflow==2.7.0
- tensorflow_recommenders==0.6.0


## 1. Datasets 
  - Create a dummy dataset
  - Dataset is inspired by the dataset on the Indorelawan web https://www.indorelawan.org/o/organization/search

## 2. Train 
   - Added more layer to make model accuracy more better:
     -  Added `Dense(units=16, activation='relu')` layer
     -  Added `Dense(units=32, activation='relu')` layer
     -  Added `Dense(1)` layer
  - Training with 150 epochs
  - From the result, got:
    - `mean_squared_error: 11,65%`
    - `loos : 1,35%`
    - `regularization_loss: 0%`
    - `total_loss: 1,35%`

## 3. Saved the Model 
  - Then, saved the model 

## 4 Predict 
  - Load model 
  - Make recommendations for `volunteer_id` based on the created model
  - Recommendation result is saved in json file
