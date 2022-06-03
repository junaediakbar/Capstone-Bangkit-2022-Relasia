# Relasia-API

Relasia REST-API: App to Database

# How to Use

- Local Host: Run with python, local IP and Port:5000
  `http://127.0.0.1:5000/` or `http://localhost:5000/`
- Online Domain:
  `https://relasia-api.herokuapp.com/`

# Endpoint Route

- ### Helpseeker

  - **Get All Helpseeker**

    Method: **GET**

    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `https://relasia-api.herokuapp.com/helpseeker/`

    Request:
    ```
    (Null)
    ```
    
    Response:

    ```
    {
        "length": 2,
        "data": [
            {
                "id": "helpseeker.pertama",
                "name": "Helpseeker Name",
                "city": "Helpseeker City",
                "mission": [
                    {
                        "id" : "Mission Pertama",
                        "helpseeker" : {
                            "id" : "helpseeker.pertama",
                            "name" : "Helpseeker Name",
                            "phone" : "0821123456789"
                        },
                        "title" : "Mission Title",
                        "requirement" : "Mission Requirement",
                        "note" : "Mission Note",
                        "address" : "Mission Address",
                        "category" : "Mission Category",
                        "city" : "Mission City",
                        "province" : "Mission Province",
                        "start_date" : "01/01/2022",
                        "end_date" : "02/02/2022",
                        "featured_image" : [
                            "link image 1",
                            "link image 2"
                        ],
                        "number_of_needs" : "5",
                        "volunteers" : [
                            {
                                "id" : "volunteer.pertama",
                                "status" : "pending"
                            },
                            {
                                "id" : "volunteer.kedua",
                                "status" : "pending"
                            },
                        ],
                    },
                    {
                        "id" : "Mission Kedua",
                        "helpseeker" : {
                            "id" : "helpseeker.pertama",
                            "name" : "Helpseeker Name",
                            "phone" : "0821123456789"
                        },
                        "title" : "Mission Title",
                        "requirement" : "Mission Requirement",
                        "note" : "Mission Note",
                        "address" : "Mission Address",
                        "category" : "Mission Category",
                        "city" : "Mission City",
                        "province" : "Mission Province",
                        "start_date" : "01/01/2022",
                        "end_date" : "02/02/2022",
                        "featured_image" : [
                            "link image 1",
                            "link image 2"
                        ],
                        "number_of_needs" : "5",
                        "volunteers" : [
                            {
                                "id" : "volunteer.pertama",
                                "status" : "pending"
                            },
                            {
                                "id" : "volunteer.kedua",
                                "status" : "pending"
                            },
                        ],
                    }
                ]
            },
            {
                "id": "helpseeker.kedua",
                "name": "Helpseeker Name",
                "city": "Helpseeker City",
                "mission": [
                    {
                        "id" : "Mission Ketiga",
                        "helpseeker" : {
                            "id" : "helpseeker.kedua",
                            "name" : "Helpseeker Name",
                            "phone" : "0821123456789"
                        },
                        "title" : "Mission Title",
                        "requirement" : "Mission Requirement",
                        "note" : "Mission Note",
                        "address" : "Mission Address",
                        "category" : "Mission Category",
                        "city" : "Mission City",
                        "province" : "Mission Province",
                        "start_date" : "01/01/2022",
                        "end_date" : "02/02/2022",
                        "featured_image" : [
                            "link image 1",
                            "link image 2"
                        ],
                        "number_of_needs" : "5",
                        "volunteers" : [
                            {
                                "id" : "volunteer.pertama",
                                "status" : "pending"
                            },
                            {
                                "id" : "volunteer.kedua",
                                "status" : "pending"
                            },
                        ],
                    }
                ]
            }
        ]
    }
    ```

  - **Get Specific Helpseeker**

    Method: **GET**

    URL Route: `/helpseeker/`

    Example:

    `http://127.0.0.1:5000/helpseeker/` or `https://relasia-api.herokuapp.com/helpseeker/`

    Request:

    ```
    {
        "id": "helpseeker.pertama"
    }
    ```

    Response:

    ```
    {
        "id": "helpseeker.pertama",
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "mission": [
            {
                "id" : "Mission Pertama",
                "helpseeker" : {
                    "id" : "helpseeker.pertama",
                    "name" : "Helpseeker Name",
                    "phone" : "0821123456789"
                },
                "title" : "Mission Title",
                "requirement" : "Mission Requirement",
                "note" : "Mission Note",
                "address" : "Mission Address",
                "category" : "Mission Category",
                "city" : "Mission City",
                "province" : "Mission Province",
                "start_date" : "01/01/2022",
                "end_date" : "02/02/2022",
                "featured_image" : [
                    "link image 1",
                    "link image 2"
                ],
                "number_of_needs" : "5",
                "volunteers" : [
                    {
                        "id" : "volunteer.pertama",
                        "status" : "pending"
                    },
                    {
                        "id" : "volunteer.kedua",
                        "status" : "pending"
                    },
                ],
            },
            {
                "id" : "Mission Kedua",
                "helpseeker" : {
                    "id" : "helpseeker.pertama",
                    "name" : "Helpseeker Name",
                    "phone" : "0821123456789"
                },
                "title" : "Mission Title",
                "requirement" : "Mission Requirement",
                "note" : "Mission Note",
                "address" : "Mission Address",
                "category" : "Mission Category",
                "city" : "Mission City",
                "province" : "Mission Province",
                "start_date" : "01/01/2022",
                "end_date" : "02/02/2022",
                "featured_image" : [
                    "link image 1",
                    "link image 2"
                ],
                "number_of_needs" : "5",
                "volunteers" : [
                    {
                        "id" : "volunteer.pertama",
                        "status" : "pending"
                    },
                    {
                        "id" : "volunteer.kedua",
                        "status" : "pending"
                    },
                ],
            }
        ]
    }
    ```

  - **Add New Helpseeker**

    Method: **POST**

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
    {
        "length": 2,
        "data": [
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
    }
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

  - **Get All Foundation**

    Method: **GET**

    URL Route: `/foundation/`

    Example:

    `http://127.0.0.1:5000/foundation/` or `https://relasia-api.herokuapp.com/foundation/`

    Example data that will be get:

    ```
    {
        "length": 2,
        "data": [
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
    }
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
    {
          "length": 2,
          "data": [
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
    }
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
    - **Get Volunteer Mission History Filtered**

        Method: **GET**

        URL Route: `/mission/filtered`

        Example:

        `http://127.0.0.1:5000/mission/filtered` or `https://relasia-api.herokuapp.com/mission/filtered`

        Example data that **required** & in the form of a **JSON Body**:

        ```
        {
            "id": "volunteer.id",
            "filter": {
              "city": "City",
              "province": "Province",
              "status": "Status Volunteer"
              "active": "Active/Inactive" (Case Sensitive)
        }
        ```

        **Note:**
        Only four types of filters work for now there are "city", "province", "status", and "active".

        You can use this filter by using 1 or more filter types like:

        ```
        {
            "id": "volunteer.id",
            "filter": {
              "city": "City"
        }
        ```

        or

        ```
        {
            "id": "volunteer.id",
            "filter": {
              "city": "City",
              "status": "Status"
        }
        ```

        or 
        ```
        {
            "id": "volunteer.id",
            "filter": {
              "city": "City",
              "province": "Province",
        }
        ```
        etc.

        Example data that will be get is all mission which has been filtered according to the parameters:
        ```
        {
          "length": n,
          "data": [
              { Filtered Mission 1 Data },
              { Filtered Mission 2 Data}, 
              .
              .
              { Filtered Mission n Data}
            ]
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
