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
        "phone": "0821123456789"
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
            "phone": "082123456789"
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

  - **[DELETE]** *Delete Helpseeker**

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
        "foundations": [
            (List of string for each registered foundation id)
        ],
        "gender": "male",
        "name": "Volunteer Name 2",
        "verified": "true"
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
            "id": "volunteer.id",
            "name": "Volunteer Name 2",
            "gender": "male",
            "birthyear": "2000",
            "phone" : "0821123456789",
            "address": "Volunteer Address",
            "city": "Volunteer City",
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

  - **[DELETE]** *Delete Volunteer**

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

  - **[DELETE]** *Delete Foundation**

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
