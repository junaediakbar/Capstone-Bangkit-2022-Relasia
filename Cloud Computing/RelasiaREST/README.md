# Relasia-API

Relasia REST-API: App to Database

# How to Use
<<<<<<< HEAD

- Local Host: Run with python, local IP and Port:5000
  `http://127.0.0.1:5000/` or `http://localhost:5000/`
- Online Domain:
  `https://relasia-api.herokuapp.com/`

# Endpoint Route

- ### Helpseeker

=======
- Local Host: Run with python, local IP and Port:5000
  ```http://127.0.0.1:5000/``` or ```http://localhost:5000/```
- Online Domain:
  ```https://relasia-api.herokuapp.com/```
  
# Endpoint Route
- ### Helpseeker
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
  - **Get All Helpseeker**

    Method: **GET**

<<<<<<< HEAD
    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `https://relasia-api.herokuapp.com/helpseeker/`

    Example data that will be get:

    ```
    [
      {
=======
    URL Route: ```/helpseeker```

    Example:

    ```http://127.0.0.1:5000/helpseeker``` or ```https://relasia-api.herokuapp.com/helpseeker```

    Data that will be get:
    ```
    [
      { 
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
          "name": "Helpseeker Name",
          "city": "Helpseeker City",
          "mission": ["Mission 1", "Mission 2"]
      },
<<<<<<< HEAD
      {
=======
      { 
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
          "name": "Helpseeker Name 2",
          "city": "Helpseeker City 2",
          "mission": ["Mission 3", "Mission 4"]
      }
    ]
    ```
<<<<<<< HEAD

=======
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
  - **Get Specific Helpseeker**

    Method: **GET**

<<<<<<< HEAD
    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `https://relasia-api.herokuapp.com/helpseeker/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "helpseeker.id"
    }
    ```

    Example data that will be get:

    ```
    {
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "mission": [
                    Every Mission Detail
                 ]
    }
    ```

=======
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
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
  - **Add New Helpseeker**

    Method: **POST**

<<<<<<< HEAD
    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `https://relasia-api.herokuapp.com/helpseeker/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "helpseeker.id",
        "data": {
            "name": "Helpseeker Name",
            "city": "Helpseeker City",
            "missions": []
        }
    }
    ```

  - **Edit Helpseeker Data**

    Method: **PUT**

    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/`

    or

    `[https://relasia-api.herokuapp.com/helpseeker/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "helpseeker.id",
        "data": {
            Field of data that will be changed
        }
    }
    ```

  - **Delete Helpseeker Data**

    Method: **DELETE**

    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `[https://relasia-api.herokuapp.com/helpseeker/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "helpseeker.id"
    }
    ```

- ### Volunteer

  - **Get All Volunteer**

    Method: **GET**

    URL Route: `/volunteer/`

    Example:

    `http://127.0.0.1:5000/volunteer/` or `https://relasia-api.herokuapp.com/volunteer/`

    Example data that will be get:

    ```
    [
        {
            "address": "Volunteer Address",
            "birthyear": "Volunteer Birthyear",
            "city": "Volunteer City",
            "foundations": [
                "Volunteer Foundation Id Collection"
            ],
            "gender": "false",
            "missions": [
                "Volunteer Mission id Collection"
            ],
            "name": "Volunteer Name",
            "verified": "true"
        },
        {
            "address": "Volunteer Address 2",
            "birthyear": "Volunteer Birthyear 2",
            "city": "Volunteer City 2",
            "foundations": [
                "Volunteer Foundation Id Collection"
            ],
            "gender": "false",
            "missions": [
                "Volunteer Mission id Collection"
            ],
            "name": "Volunteer Name 2",
            "verified": "true"
        }
    ]
    ```

  - **Get Specific Volunteer**

    Method: **GET**

    URL Route: `/volunteer/`

    Example:

    `http://127.0.0.1:5000/volunteer/` or `https://relasia-api.herokuapp.com/volunteer/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "volunteer.id",
    }
    ```

    Example data that will be get:

    ```
    {
        "address": "Volunteer Address",
        "birthyear": "Volunteer Birthyear",
        "city": "Volunteer City",
        "foundations": {
            Every Foundation Collection Detail
        },
        "gender": "false",
        "missions": {
            Every Mission Collection Detail
        },
        "name": "Volunteer Name 2",
        "verified": "true"
    }
    ```

  - **Add New Volunteer**

    Method: **POST**

    URL Route: `/volunteer/`

    Example:

    `http://127.0.0.1:5000/volunteer/` or `https://relasia-api.herokuapp.com/volunteer/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "volunteer.id",
        "data": {
            "name": "Name Volunteer",
            "gender": "false",
            "birthyear": "Volunteer Birthyear",
            "address": "Volunteer Address",
            "city": "Volunteer City",
            "foundations": [],
            "missions": [],
            "verified": "false"
        }
    }
    ```

  - **Registering Volunteer to Foundation**

    Method: **PUT**

    URL Route: `/volunteer/<volunteer_id>/foundation`

    Example:

    `http://127.0.0.1:5000/volunteer/volunteer.id/foundation` or `https://relasia-api.herokuapp.com/volunteer.id/foundation`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "foundation": "foundation.id"
    }
    ```

  - **Applying Volunteer to Mission**

    Method: **PUT**

    URL Route: `/volunteer/<volunteer_id>/mission`

    Example:

    `http://127.0.0.1:5000/volunteer/volunteer.id/mission` or `https://relasia-api.herokuapp.com/volunteer.id/mission`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "mission": "mission.id"
    }
    ```

  - **Edit Volunteer Data**

    Method: **PUT**

    URL Route: `/volunteer/`

    Example:

    `http://127.0.0.1:5000/volunteer/` or `[https://relasia-api.herokuapp.com/volunteer`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "volunteer.id",
        "data": {
            Field of data that will be changed
        }
    }
    ```

  - **Delete Volunteer Data**

    Method: **DELETE**

    URL Route: `/volunteer/`

    Example:

    `http://127.0.0.1:5000/volunteer/` or `[https://relasia-api.herokuapp.com/volunteer/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "volunteer.id"
    }
    ```

- ### Foundation

  - **Get All Foundartion**

    Method: **GET**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `https://relasia-api.herokuapp.com/foundation/`

    Example data that will be get:

    ```
    [
        {
            "address": "Foundation Address,
            "call_center": "Foundation Call Center",
            "city": "Foundation City",
            "name": "Foundation Name",
            "volunteers": {
                Volunteer Status Collection
            }
        },
        {
            "address": "Foundation Address 2,
            "call_center": "Foundation Call Center 2",
            "city": "Foundation City 2",
            "name": "Foundation Name 2",
            "volunteers": {
                Volunteer Status Collection
            }
        }
    ]
    ```

  - **Get Specific Foundation**

    Method: **GET**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `https://relasia-api.herokuapp.com/foundation/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "foundation.id"
    }
    ```

    Example data that will be get:

    ```
    {
        "address": "Foundation Address,
        "call_center": "Foundation Call Center",
        "city": "Foundation City",
        "name": "Foundation Name",
        "volunteers": {
            Every Volunteers Detail
        }
    }
    ```

  - **Add New Foundation**

    Method: **POST**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `https://relasia-api.herokuapp.com/foundation/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "foundation.id",
        "data": {
            "address": "Foundation address",
            "call_center": "Foundation Call Center",
            "city": "Foundation City",
            "volunteers": {},
            "name": "Foundation Name"
        }
    }
    ```

  - **Validate Foundation Members**

    Method: **PUT**

    URL Route: `/foundation/<foundation_id>`

    Example:

    `http://127.0.0.1:5000/foundation/foundation.id` or `[https://relasia-api.herokuapp.com/foundation/foundation.id`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "volunteer": "volunteer.id",
        "status": "volunteer status"
    }
    ```

    **NOTE:**

    **If Volunteer status "accepted" in foundation one or more collection,
    Volunteer verified status will be "true"**

  - **Edit Foundation Data**

    Method: **PUT**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `[https://relasia-api.herokuapp.com/foundation/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "foundation id",
        "data": {
            Field of data that will be changed
        }
    }
    ```

  - **Delete Foundation Data**

    Method: **DELETE**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `[https://relasia-api.herokuapp.com/foundation/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "foundation id"
    }
    ```

- ### Mission

  - **Get All Mission**

  Method: **GET**

  URL Route: `/mission/`

  Example:

  `http://127.0.0.1:5000/mission/` or `https://relasia-api.herokuapp.com/mission/`

  Example data that will be get:

  ```
  [
      {
          "address": "Mission Address",
          "category": "Mission Category",
          "city": "Mission City",
          "end_date": "Mission End_date",
          "featured_image": [
              Mission Image Collection
          ],
          "id": "Mission Helpseeker ID",
          "note": "Mission Note",
          "number_of_needs": "Mission Needed",
          "province": "Mission Province",
          "requirement": "Mission Requirement",
          "start_date": "Mission Start_date",
          "timestamp": "Mission timestamp",
          "title": "Mission Title",
          "volunteers": {
              --Volunteer Apllied and their Status--
              "volunteer_id": "volunteer status"
          }
      },
      {
          "address": "Mission Address 2",
          "category": "Mission Category 2",
          "city": "Mission City 2",
          "end_date": "Mission End_date 2",
          "featured_image": [
              Mission Image Collection 2
          ],
          "id": "Mission Helpseeker ID 2",
          "note": "Mission Note 2",
          "number_of_needs": "Mission Needed 2",
          "province": "Mission Province 2",
          "requirement": "Mission Requirement 2",
          "start_date": "Mission Start_date 2",
          "timestamp": "Mission timestamp 2",
          "title": "Mission Title 2",
          "volunteers": {
              --Volunteer Apllied and their Status--
              "volunteer_id": "volunteer status 2"
          }
      }
  ]
  ```

  - **Get Specific Mission**

    Method: **GET**

    URL Route: `/mission/`

    Example:

    `http://127.0.0.1:5000/mission/` or `https://relasia-api.herokuapp.com/mission`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "mission.id"
    }
    ```

    Example data that will be get:

    ```
    {
        "address": "Mission Address",
        "category": "Mission Category",
        "city": "Mission City",
        "end_date": "Mission End_date",
        "featured_image": [
            Mission Image Collection
        ],
        "id": {
            Helpseeker Detailed Data who request the mission
        },
        "note": "Mission Note",
        "number_of_needs": "Mission Number Need",
        "province": "Mission Province",
        "requirement": "Mission Requirement",
        "start_date": "Mission Start_Date",
        "timestamp": "Mission Timestamp",
        "title": "Mission Title",
        "volunteers": {
            Mission Applied Volunteer Detailed data who join the mission
        }
    }
    ```

  - **Add New Mission by Helpseeker**

    Method: **POST**

    URL Route: `/mission/`

    Example:

    `http://127.0.0.1:5000/mission/` or `[https://relasia-api.herokuapp.com/mission/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "Helpseeker ID"
=======
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
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
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
<<<<<<< HEAD
        "volunteers": {}
    }
    ```

  - **Edit Mission Data**

    Method: **PUT**

    URL Route: `/mission/`

    Example:

    `http://127.0.0.1:5000/mission/` or `[https://relasia-api.herokuapp.com/mission/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "id": "mission.id",
        "data": {
            Field of data that will be changed
        }
    }
    ```

  - **Comfirm Volunteer Status on Mission**

    Method: **PUT**

    URL Route: `/mission/<mission_id>`

    Example:

    `http://127.0.0.1:5000/mission/mission.id` or `[https://relasia-api.herokuapp.com/mission/mission.id`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "volunteer": "volunteer.id",
        "status": "volunteer status"
    }
    ```

  - **Delete Foundation Data**

    Method: **DELETE**

    URL Route: `/mission/`

    Example:

    `http://127.0.0.1:5000/mission/` or `[https://relasia-api.herokuapp.com/mission/`

    Example data that **required** & in the form of a **JSON Body**:

    ```
    {
        "mission": "mission.id"
    }
    ```
=======
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
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
