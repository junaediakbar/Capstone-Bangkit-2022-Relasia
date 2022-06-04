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

  - **[GET]** *Get Specific Helpseeker*
    
    Additional Route: `<string:id>`
    
    Request:

    ```
    (Null)
    ```

    Response:

    ```
    {
        "city": "Helpseeker City",
        "id": "helpseeker.id",
        "missions": [_List of All Mission Data_],
        "name": "Helpseeker Name",
        "phone": "083876012340",
        "picture": ""
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
            "city": "Helpseeker City",
            "id": "helpseeker.id",
            "missions": [],
            "name": "Helpseeker Name",
            "phone": "083876012340",
            "picture": ""
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
        "picture": "image.jpg" (optional)
    }
    ```
    
    Response:
    ```
    {
        "message" : "Successfully Updated",
        "data" : {
            "city": "Helpseeker City",
            "id": "helpseeker.id",
            "missions": [],
            "name": "Helpseeker Name",
            "phone": "083876012340",
            "picture": ""
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

  - **[GET]** *Get Specific Volunteer*
  
    Additional Route: `<string:id>`
    
    Request:

    ```
    (Null)
    ```

    Response:

    ```
    {
    "address": "Volunteer Address",
    "birthyear": "2000",
    "city": "Volunteer City",
    "foundations": [_All Foundation Data_],
    "gender": "male",
    "id": "volunteer.deh",
    "missions": [_All Mission Data that Applied_],
    "name": "Volunteer baru",
    "phone": "0821123456789",
    "picture": "image.jpg",
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
            "id": "volunteer.deh",
            "missions": [],
            "name": "Volunteer baru",
            "phone": "0821123456789",
            "picture": "",
            "verified": "false"
        }
    }
    ```
  
  - **[PUT]** *Edit Volunteer*
  
    Request:

    ```
    {
        "id": "volunteer.id",
        "address": "Volunteer Address", (optional)
        "birthyear": "2000", (optional)
        "city": "Volunteer City", (optional)
        "gender": "male", (optional)
        "name": "Volunteer Name", (optional)
        "phone": "0821123456787", (optional)
        "picture": "image.jpg" (optional)
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
  
    Additional Route: `<string:id>`
    
    Request:

    ```
    (Null)
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
    
  - **[GET]** *Get Foundation Data filtered by City** (for nearest foundations)
    
    Additional Parameter: `city`
    
    Request:

    ```
    (Null)
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
            "city": "Foundation City",
            "volunteers": []
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
            "volunteers": [
                _List of volunteers id and status_
            ]
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

  - **[GET]** *Get All Mission or Filtered Mission*

    Default page: 1
    Default data on page (paginate): 5
    
    Query Request Parameter (all Optional):
    - page: 3 (Default Page: 1)
    - paginate: 5 (Default data on page: 5)
    - volunteer: volunteer.id (for filter by specific volunteer mission)
    - helpseeker: helpseker.id (for filter by helpseeker who requested)
    - city: mission.city (for filter by mission city)
    - province: mission.province (for filter by province)
    - status: "accepted" or "pending" or "rejected" (for filter by volunteers applied status)
    - active: "active" or "inactive" (for filter by mission active or inactive)
    
    Request:
    ```
    (Null)
    ```
    
    Response:

    ```
    {
        "page": "1",
        "data_on_page": "5",
        "length": "10",
        "data": [
            _List of All Mission Data or Filtered Mission_
        ]
    }
    ```

  - **[GET]** *Get Spesific Missions*
    
    Additional Route: `<string:id>`
    
    Request:

    ```
    (Null)
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
            _List of Volunteers Detailed Data_
        ]
    }
    ```
    
  - **[POST]** *Add New Mission*

    Request:

    ```
    {
        "helpseeker": "helpseeker.id",
        "title": "Mission Title Deh",
        "category": "Mission Category",
        "requirement": "Mission Requirement",
        "note": "Mission Note",
        "address": "Mission Address",
        "city": "Mission City",
        "province": "Mission Province",
        "start_date": "Mission Start_date",
        "end_date": "Mission End_date",
        "featured_image": [
            "gambar.jpg"
        ],
        "number_of_needs": "Mission Needed"
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Created",
        "data": {
            "address": "Mission Address",
            "category": "Mission Category",
            "city": "Depok",
            "end_date": "Mission End_date",
            "featured_image": [
                "gambar.jpg"
            ],
            "helpseeker": "helpseeker.id",
            "id": "mission.id",
            "note": "Mission Note",
            "number_of_needs": "Mission Needed",
            "province": "Mission Province",
            "requirement": "Mission Requirement",
            "start_date": "Mission Start_date",
            "timestamp": "Sat, 04 Jun 2022 12:09:38 GMT",
            "title": "Mission Title Deh",
            "volunteers": [
                _List of Volunteers id and Status_
            ]
        }
    }
    ```

  - **[PUT]** *Edit Mission*

    Request:

    ```
    {
        "id": "mission.id"
        "helpseeker": "helpseeker.id", (optional)
        "title": "Mission Title Deh", (optional)
        "category": "Mission Category", (optional)
        "requirement": "Mission Requirement", (optional)
        "note": "Mission Note", (optional)
        "address": "Mission Address", (optional)
        "city": "Mission City", (optional)
        "province": "Mission Province", (optional)
        "start_date": "Mission Start_date", (optional)
        "end_date": "Mission End_date", (optional)
        "featured_image": [ (optional)
            "gambar.jpg"
        ],
        "number_of_needs": "Mission Needed" (optional)
    }
    ```
    
    Response:

    ```
    {
        "message": "Successfully Updated",
        "data": {
            "address": "Mission Address",
            "category": "Mission Category",
            "city": "Depok",
            "end_date": "Mission End_date",
            "featured_image": [
                "gambar.jpg"
            ],
            "helpseeker": "helpseeker.deh",
            "id": "ffed635516693f45965c0afe91a98fff10e02e0b",
            "note": "Mission Note",
            "number_of_needs": "Mission Needed",
            "province": "Mission Province",
            "requirement": "Mission Requirement",
            "start_date": "Mission Start_date",
            "timestamp": "Sat, 04 Jun 2022 12:09:38 GMT",
            "title": "Mission Title Deh",
            "volunteers": [
                _List of Volunteers id and Status_
            ]
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
