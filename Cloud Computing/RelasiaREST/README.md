# Relasia-API

Relasia REST-API: App to Database

# How to Use
- Local Host: Run with python, local IP and Port:5000
  ```http://127.0.0.1:5000/``` or ```http://localhost:5000/```
- Online Domain:
  ```https://relasia-api.herokuapp.com/```
  
# Endpoint Route
- ### Helpseeker
  - **Get All Helpseeker**

    Method: **GET**

    URL Route: ```/helpseeker```

    Example:

    ```http://127.0.0.1:5000/helpseeker``` or ```https://relasia-api.herokuapp.com/helpseeker```

    Data that will be get:
    ```
    [
      { 
          "name": "Helpseeker Name",
          "city": "Helpseeker City",
          "mission": ["Mission 1", "Mission 2"]
      },
      { 
          "name": "Helpseeker Name 2",
          "city": "Helpseeker City 2",
          "mission": ["Mission 3", "Mission 4"]
      }
    ]
    ```
  - **Get Specific Helpseeker**

    Method: **GET**

    URL Route: ```/helpseeker?id=<id>```
    
    Need Query Value: id

    Example:

    ```http://127.0.0.1:5000/helpseeker?id=helpseeker1``` or ```https://relasia-api.herokuapp.com/helpseeker?id=helpseeker1```

    Data that will be get:
    ```
    { 
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "mission": [
                    {
                       "address": "Mission Address",
                       "applied_volunteer": [
                                              {
                                                  "status": "status"
                                                  "volunteer": "volunteer_id"
                                              },
                                              {
                                                  "status": "status"
                                                  "volunteer": "volunteer_id"
                                              }
                                            ],
                       "category": "category_name",
                       "city": "City Name",
                       "end_date": "end_date",
                       "featured_image": [
                                          "image1.jpg",
                                          "image2.jpg"
                                         ],
                       "helpseeker": "helpseeker_id",
                       "note": "mission_note",
                       "number_of_needs": number_volunteer+need,
                       "province": "mission province",
                       "requirement": "mission_requirement",
                       "start_date": "Start date",
                       "title": "Mission title"
                    },
                    {"Mission 2 Detail"}
                 ]
    }
    ```
  - **Add New Helpseeker**

    Method: **POST**

    URL Route: ```/helpseeker```

    Example:

    ```http://127.0.0.1:5000/helpseeker``` or ```https://relasia-api.herokuapp.com/helpseeker```

    Data that required & in the form of a **JSON Body**:
    ```
    {
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "mission": []
    }
    ```
  - **Add Mission Data by Helpseeker**

    Method: **POST**

    URL Route: ```/helpseeker/<helpseeker_id>?mission=<mission_id>```

    Example:

    ```http://127.0.0.1:5000/helpseeker/helpseeker.2?mission=mission.1```
    
    or
    
    ```[https://relasia-api.herokuapp.com/helpseeker/helpseeker.2?mission=mission.1```

    Data that required & in the form of a **JSON Body**:
    ```
    {
        "title": "Mission Title",
        "address": "Mission Address",
        "city": "Mission City",
        "province": "Mission Province",
        "number_of_needs": "Mission numbers of Volunter needed",
        "start_date": "Mission Start Date",
        "end_date": "Mission End Date",
        "featured_image": ["MissionImage1.jpg", "MissionImage2.jpg"],
        "category": "Mission Category",
        "requirement": "Mission Requirement",
        "note": "Mission Note",
        "applied_volunteer": []
    }
    ```
  - **Edit Mission Data by Helpseeker**

    Method: **PUT**

    URL Route: ```/helpseeker/edit?id=<helpseeker_id>```

    Example:

    ```http://127.0.0.1:5000/helpseeker/edit?id=helpseeker1``` or ```[https://relasia-api.herokuapp.com/helpseeker/edit?id=helpseeker1```

    Data that required & in the form of a **JSON Body**:
    ```
    Field of data that will be changed
  - **Edit Helpseeker Data**

    Method: **PUT**

    URL Route: ```/helpseeker/<helpseeker_id>/edit?mission=<mission_id>```

    Example:

    ```http://127.0.0.1:5000/helpseeker/helpseeker1/edit?mission=mission1```
    
    or
    
    ```[https://relasia-api.herokuapp.com/helpseeker/helpseeker1/edit?mission=mission1```

    Data that required & in the form of a **JSON Body**:
    ```
    Field of data that will be changed
    ```
  - **Delete Helpseeker Data**

    Method: **DELETE**

    URL Route: ```/helpseeker/delete?id=<helpseeker_id>```

    Example:

    ```http://127.0.0.1:5000/helpseeker/delete?id=helpseeker1``` or ```[https://relasia-api.herokuapp.com/helpseeker/delete?id=helpseeker1```
  
- ### Volunteer

- ### Foundation

- ### Mission
