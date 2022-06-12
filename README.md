<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia">
    <img src="https://www.linkpicture.com/q/Logo_105.png" width='250dp' alt="Logo" >
  </a>

  <h1 align="center">Relasia</h1>
  <h2 align="center">
  Application which helps you to find volunteers. Ask for Help Now!</h2>
  
  <p align="center">
  This is a project to fulfill the  <a href="https://grow.google/intl/id_id/bangkit/"><strong>Bangkit Academy led by Google, Tokopedia, Gojek, & Traveloka</strong></a>
   Program.
    <br />
    <a href="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="">Volunteers APK Link</a>
    ·
    <a href="">Help Seekers APK Link</a>
    ·
    <a href="">View Demo</a>
    ·
    <a href="">Presentation Slide</a>
    ·
    <a href="">Go To Market Slide</a>
    <br />
    © C22 - PS099 Bangkit Capstone Team
  </p>
</p>

# Team Members

## Team ID : C22 - PS099

<br>

| Name                   | Student ID | Path                |
| ---------------------- | ---------- | ------------------- |
| Fransiskus Ricardo     | M2008G0842 | Machine Learning    |
| Gentur Rizky Arganta   | M7180F1752 | Machine Learning    |
| Junaedi Akbar          | A2004F0299 | Android Development |
| Nur Ahmad Khatim       | A7004F0309 | Android Development |
| Fathurrahman Irwansa   | C2010F1132 | Cloud Computing     |
| Chandra Halim Nuruddin | C2010F1136 | Cloud Computing     |

<br>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#app-overview">App Overview</a></li>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#machine-learning-development-documentation">Machine Learning Development Documentation</a></li>
        <li><a href="#mobile-development-documentation">Mobile Development Documentation</a></li>
        <li><a href="#cloud-computing-documentation">Cloud Computing Documentation</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project

Indonesia has many social foundations/institutions that operate in each sector that aims to help the local community in need. In DKI Jakarta alone there are 376 registered social foundations/institutions.

There are many major cities in Indonesia, which means that there could be dozens of thousand social foundations/institutions which have many active members that are ready to help the society.

Our project aims to solve the effectiveness problem by building an application that connects these parties where the help seekers can be recommended to volunteers who are trusted in their fields according to the area they need in an effective way.

# App Overview

- **Prerequisites**

  1.  Android
  2.  Internet connection

- **Installation**

  1.  Download the APK
  2.  Install the APK

- **Register**

  1.  Open Volunteer or Helpseeker Applications
  2.  Register your email address

- **Screenshot**

<!-- Machine Learning Development Documentation -->

## Machine Learning Development Documentation

### Recommendation system Model

The project is based from Google Colab and laptop/PC. Using Machine Learning with Tensorflow as framework to recommendation system.

### Prerequisites

Function dependencies used in this project:

- keras==2.7.0
- numpy==1.20.3
- pandas==1.3.4
- tensorflow==2.7.0
- tensorflow_recommenders==0.6.0

### 1. Datasets

- Create a dummy dataset
- Dataset is inspired by the dataset on the Indorelawan web https://www.indorelawan.org/o/organization/search
- Dataset that will be used is based on volunteer's applied missions that appear in our databases

### 2. Train

- Added more layer to make model accuracy more better:
  - Added `Dense(units=16, activation='relu')` layer
  - Added `Dense(units=32, activation='relu')` layer
  - Added `Dense(1)` layer
- Training with 150 epochs
- From the result, got:
  - `mean_squared_error: 11,65%`
  - `loos : 1,35%`
  - `regularization_loss: 0%`
  - `total_loss: 1,35%`

### 3. Saved the Model

- Then, saved the model

### 4 Predict

- Load model
- Make recommendations for `volunteer_id` based on the created model
- Recommendation result is saved in json file

<!-- Mobile Development Documentation -->

## Mobile Development Documentation

- ### Feature
- #### Dependencies :

  - [kotlinx-coroutines](https://developer.android.com/kotlin/coroutines)
  - [Retrofit 2](https://square.github.io/retrofit/)
  - [Google Play services Maps](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

## Cloud Computing Documentation

- ### 1. Write the Flask server app using Python & Install prequisite

  Source Code for Flask Server:

  https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/tree/cc/Cloud%20Computing

- ### 2. Setup Google Cloud Platform

  - Enable Cloud Run and Cloud Build API

- ### 3. Run cloud shell in Google Cloud Platform

- ### 4. Dockerfile, requirements.txt, .dockerignore

  - https://cloud.google.com/run/docs/quickstarts/build-and-deploy#containerizing

- ### 5. Cloud build & deploy

  ```
  gcloud builds submit --tag gcr.io/<your_project>/relasia
  ```

  ```
  gcloud run deploy --image gcr.io/<your_project>/relasia --platform managed
  ```

- ### 6. Configuring Databases using CLoud Firestore

- ### 7. Connect all service to 1 domain using API Gateway

  ### REST API Documentation

  For API documentation, see the following link

  https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/tree/master/Cloud%20Computing/RelasiaREST

# Contact

| Name                   | Contact                                                                                                                                                                                                                                                                                         |
| ---------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Fransiskus Ricardo     | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:fransiskus.ricardo@mail.ugm.ac.id"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a>         |
| Gentur Rizky Arganta   | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:gentur.rizky.arganta-2019@fst.unair.ac.id"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a> |
| Junaedi Akbar          | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:juned.akb@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a>                       |
| Nur Ahmad Khatim       | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:naimackerman@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a>                    |
| Fathurrahman Irwansa   | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:fathurrahman.irw@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a>                |
| Chandra Halim Nuruddin | <a href=""><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> <a href="mailto:chalim181@gmail.com"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"></a>                       |
