# Relasia-API

Relasia REST-API: App to Database

# How to Use

- Local Host: Run with python, local IP and Port:5000
  `http://127.0.0.1:5000/` or `http://localhost:5000/`
- Online Domain:
  `https://relasia-api.herokuapp.com/`

# Endpoint Route

- ### Helpseeker

  URL Route:
    `http://127.0.0.1:5000/helpseeker/`
  or
    `https://relasia-api.herokuapp.com/helpseeker/`

  - **[GET]** *Get Helpseeker*
    
    Request:

    ```
    {
        "id": "kijang.satu"
    }
    ```

    Response:

    ```
    {
        "id": "kijang.satu",
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "phone": "0821123456789",
        "missions": []
    }
    ```

  - **[POST]** *Add New Helpseeker*

    Request:

    ```
    {
        "id": "helpseeker.id",
        "name": "Helpseeker Name",
        "city": "Helpseeker City",
        "phone": "082123456789"
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Created",
        "data" : {
            "id": "helpseeker.id",
            "name": "Helpseeker Name",
            "city": "Helpseeker City",
            "phone": "082123456789",
            "missions": []
        }
    }
    ```

  - **[PUT]** *Edit Helpseeker*
    
    Request:
    
    ```
    {
        "id": "helpseeker.id",
        "name": "Helpseeker Name", (optional)
        "city": "Helpseeker City", (optional)
        "phone": "082123456789" (optional)
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Updated",
        "data" : {
            "id": "helpseeker.id",
            "name": "Helpseeker Name",
            "city": "Helpseeker City",
            "phone": "0821123456789"
        }
    }
    ```

  - **[DELETE]** *Delete Helpseeker*

    Request:
    
    ```
    {
        "id": "helpseeker.id"
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Deleted"
    }
    ```

- ### Volunteer

  URL Route: `/volunteer/`

  Example:

  `http://127.0.0.1:5000/volunteer/` or `https://relasia-api.herokuapp.com/volunteer/`

  - **[GET]** *Get Volunteer*
  
    Request:

    ```
    {
        "id": "volunteer.id"
    }
    ```

    Response:

    ```
    {
       "address": "Volunteer Address",
       "birthyear": "2000",
       "city": "Volunteer City",
       "foundations": [],
       "gender": "male",
       "id": "volunteer.baru",
       "missions": [],
       "name": "Volunteer baru",
       "phone": "0821123456789",
       "verified": "false"
    }
    ```

  - **[POST]** *Add New Volunteer*
  
    Request:

    ```
    {
        "id": "volunteer.id",
        "name": "Volunteer Name 2",
        "gender": "male",
        "birthyear": "2000",
        "phone" : "0821123456789",
        "address": "Volunteer Address",
        "city": "Volunteer City" 
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Created",
        "data": {
            "address": "Volunteer Address",
            "birthyear": "2000",
            "city": "Volunteer City",
            "foundations": [],
            "gender": "male",
            "id": "volunteer.baru",
            "missions": [],
            "name": "Volunteer baru",
            "phone": "0821123456789",
            "verified": "false"
        }
    }
    ```
  
  - **[PUT]** *Edit Volunteer*
  
    Request:

    ```
    {
        "id": "volunteer.id",
        "address": "Volunteer Address",
        "birthyear": "2000",
        "city": "Volunteer City",
        "gender": "male",
        "name": "Volunteer Name 2",
        "phone": "0821123456787"        
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Updated",
        "data": {
            "id": "volunteer.id",
            "name": "Volunteer Name 2",
            "gender": "male",
            "birthyear": "2000",
            "address": "Volunteer Address",
            "city": "Volunteer City",
            "verified": "true"
        }
    }
    ```
    
  - **[PUT]** *Register Foundation*
    
    Additional Route: `/<string:volunteer_id>/foundation`
    
    Request:

    ```
    {
        "foundation": "foundation.id"       
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Registered"
    }
    ```

  - **[PUT]** *Apply Mission*
    
    Additional Route: `/<string:volunteer_id>/mission`
    
    Request:

    ```
    {
        "mission": "mission.id"       
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Applied"
    }
    ```

  - **[DELETE]** *Delete Volunteer*

    Request:
    
    ```
    {
        "id": "volunteer.id"
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Deleted"
    }
    ```

- ### Foundation

  URL Route: `/foundation/`

  Example:

  `http://127.0.0.1:5000/foundation/` or `https://relasia-api.herokuapp.com/foundation/`

  - **[GET]** *Get Foundation*
  
    Request:

    ```
    {
        "id": "foundation.id"
    }
    ```

    Response:

    ```
    {
        "id": "foundation.id",
        "name": "Foundation Name",
        "phone": "082199998888",
        "address": "Foundation Address",
        "city": "Foundation City",
        "volunteers": [
            {
                "id": "volunteer.id",
                "status": "pending"
            },
            {
                "id": "volunteer.id",
                "status": "pending"
            }
        ]
    }
    ```

  - **[POST]** *Add New Foundation*
  
    Request:

    ```
    {
        "id": "foundation.id",
        "name": "Foundation Name",
        "phone": "082199998888",
        "address": "Foundation Address",
        "city": "Foundation City"
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Created",
        "data": {
            "id": "foundation.id",
            "name": "Foundation Name",
            "phone": "082199998888",
            "address": "Foundation Address",
            "city": "Foundation City"
        }
    }
    ```
    
  - **[PUT]** *Edit Foundation*
  
    Request:

    ```
    {
        "id": "foundation.id",
        "name": "Foundation Name", (optional)
        "phone": "082199998888", (optional)
        "address": "Foundation Address", (optional)
        "city": "Foundation City" (optional)
    }
    ```

    Response:

    ```
    {
        "message": "Successfully Updated",
        "data": {
            "id": "foundation.id",
            "name": "Foundation Name",
            "phone": "082199998888",
            "address": "Foundation Address",
            "city": "Foundation City"
        }
    }
    ```

  - **[PUT]** *Validate Members*

    Additional Route: `/<foundation_id>`

    Request:

    ```
    {
        "id": "volunteer.id",
        "status": "accepted"
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Updated"
    }
    ```

    **NOTE:**

    **If Volunteer status "accepted" in foundation one or more collection,
    Volunteer verified status will be "true"**

  - **[DELETE]** *Delete Foundation*

    Request:
    
    ```
    {
        "id": "foundation.id"
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Deleted"
    }
    ```

- ### Mission

  URL Route: `/mission/`

  Example:

  `http://127.0.0.1:5000/mission/` or `https://relasia-api.herokuapp.com/mission/`

  - **[GET]** *Get All Mission*
    Default page: 1
    Default data on page: 5
    
    Response:

    ```
    {
        "page": "1",
        "data_on_page": "5",
        "length": "10",
        "volunteer": "volunteer.id",
        "helpseeker": "helpseeker.id",
        "city": "City",
        "province": "Province",
        "status": "Status Volunteer"
        "active": "Active/Inactive"
        "data": [
            {
                "id": "Mission Helpseeker ID",
                "helpseeker": "helpseeker.id",
                "title": "Mission Title",
                "category": "Mission Category",
                "requirement": "Mission Requirement",
                "note": "Mission Note",
                "address": "Mission Address",
                "city": "Mission City",
                "province": "Mission Province",
                "start_date": "Mission Start_date",
                "end_date": "Mission End_date",
                "featured_image": [
                    (List of string for image links)
                ],
                "number_of_needs": "Mission Needed",
                "timestamp": "Mission timestamp",
                "volunteers": [
                    {
                        "id": "volunteer.pertama",
                        "status": "pending"
                    },
                    {
                        "id": "volunteer.kedua",
                        "status": "accepted"
                    },
                ]
            },
            {
                "id": "Mission Helpseeker ID",
                "title": "Mission Title",
                "category": "Mission Category",
                "requirement": "Mission Requirement",
                "note": "Mission Note",
                "address": "Mission Address",
                "city": "Mission City",
                "province": "Mission Province",
                "start_date": "Mission Start_date",
                "end_date": "Mission End_date",
                "featured_image": [
                    (List of string for image links)
                ],
                "number_of_needs": "Mission Needed",
                "timestamp": "Mission timestamp",
                "volunteers": [
                    {
                        "id": "volunteer.pertama",
                        "status": "pending"
                    },
                    {
                        "id": "volunteer.kedua",
                        "status": "accepted"
                    },
                ]
            },
        ]
    }
    ```

  - **[GET]** *Get Spesific Missions*
    
    Additional Route: `/`

    Request:
    
    ```
    {
        "id": "mission.id"
    }
    ```
    
    Response:

    ```
    {
        "id": "Mission Helpseeker ID",
        "helpseeker": "helpseeker.id",
        "title": "Mission Title",
        "category": "Mission Category",
        "requirement": "Mission Requirement",
        "note": "Mission Note",
        "address": "Mission Address",
        "city": "Mission City",
        "province": "Mission Province",
        "start_date": "Mission Start_date",
        "end_date": "Mission End_date",
        "featured_image": [
            (List of string for image links)
        ],
        "number_of_needs": "Mission Needed",
        "timestamp": "Mission timestamp",
        "volunteers": [
            {
                "id": "volunteer.pertama",
                "status": "pending"
            },
            {
                "id": "volunteer.kedua",
                "status": "accepted"
            },
        ]
    }
    ```
    
  - **[POST]** *Add New Mission*

    Request:

    ```
    {
        "id": "Mission Helpseeker ID",
        "title": "Mission Title",
        "category": "Mission Category",
        "requirement": "Mission Requirement",
        "note": "Mission Note",
        "address": "Mission Address",
        "city": "Mission City",
        "province": "Mission Province",
        "start_date": "Mission Start_date",
        "end_date": "Mission End_date",
        "featured_image": [
            (List of string for image links)
        ],
        "number_of_needs": "Mission Needed"
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Created",
        "data": {
            "timestamp": "Mission timestamp",
            "id": "Mission Helpseeker ID",
            "title": "Mission Title",
            "category": "Mission Category",
            "requirement": "Mission Requirement",
            "note": "Mission Note",
            "address": "Mission Address",
            "city": "Mission City",
            "province": "Mission Province",
            "start_date": "Mission Start_date",
            "end_date": "Mission End_date",
            "featured_image": [
                (List of string for image links)
            ],
            "number_of_needs": "Mission Needed"
        }
    }
    ```

  - **[PUT]** *Edit Mission*

    Request:

    ```
    {
        "id": "Mission Helpseeker ID",
        "title": "Mission Title", (optional)
        "category": "Mission Category", (optional)
        "requirement": "Mission Requirement", (optional)
        "note": "Mission Note", (optional)
        "address": "Mission Address", (optional)
        "city": "Mission City", (optional)
        "province": "Mission Province", (optional)
        "start_date": "Mission Start_date", (optional)
        "end_date": "Mission End_date", (optional)
        "featured_image": [ (optional)
            (List of string for image links)
        ],
        "number_of_needs": "Mission Needed" (optional)
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Updated",
        "data": {
            "timestamp": "Mission timestamp",
            "id": "Mission Helpseeker ID",
            "title": "Mission Title",
            "category": "Mission Category",
            "requirement": "Mission Requirement",
            "note": "Mission Note",
            "address": "Mission Address",
            "city": "Mission City",
            "province": "Mission Province",
            "start_date": "Mission Start_date",
            "end_date": "Mission End_date",
            "featured_image": [
                (List of string for image links)
            ],
            "number_of_needs": "Mission Needed"
        }
    }
    ```

  - **[PUT]** *Confirm Volunteers*

    Additional Route: `/<mission_id>`

    Request:

    ```
    {
        "id": "volunteer.id",
        "status": "accepted"
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Updated"
    }
    ```

  - **[DELETE]** *Delete Mission*

    Request:

    ```
    {
        "mission": "mission.id"
    }
    ```
    
    Response:
    
    ```
    {
        "message": "Successfully Deleted
    }
    ```
