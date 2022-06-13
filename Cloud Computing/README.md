# Cloud Computing Documentation

- ## 1. Write the Flask server app using Python & Install prequisite

  Source Code for Flask Server:

  https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/tree/cc/Cloud%20Computing

- ## 2. Setup Google Cloud Platform

  - Enable Cloud Run and Cloud Build API

- ## 3. Run cloud shell in Google Cloud Platform

- ## 4. Dockerfile, requirements.txt, .dockerignore

  - https://cloud.google.com/run/docs/quickstarts/build-and-deploy#containerizing

- ## 5. Cloud build & deploy

  ```
  gcloud builds submit --tag gcr.io/<your_project>/relasia
  ```

  ```
  gcloud run deploy --image gcr.io/<your_project>/relasia --platform managed
  ```

- ## 6. Configuring Databases using CLoud Firestore

- ## 7. Connect all service to 1 domain using API Gateway

  ### REST API Documentation

  For API documentation, see the following link

  <a href="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/tree/cc/Cloud%20Computing/api">REST API Documentation
