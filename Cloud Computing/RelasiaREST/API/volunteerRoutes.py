from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
volunteer_Ref = db.collection('volunteer')
foundation_Ref = db.collection('foundation')
mission_Ref = db.collection('mission')

volunteerRoutes = Blueprint('volunteerRoutes', __name__)

@volunteerRoutes.route('/', methods=['POST'])
def addVolunteer():
    try:
        volunteer_id = request.json['id']
        volunteer_data = request.json['data']

        volunteer = volunteer_Ref.document(volunteer_id).get()
        if volunteer.exists:
            # HTTP response code: 409 Conflict
            return jsonify(message="Volunteer Exists"), 409
        else:
            # Add new volunteer to volunteer collection
            volunteer_Ref.document(volunteer_id).set(volunteer_data)
<<<<<<< HEAD

=======
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP Response Code: 201 Created
            return jsonify(message="Successfully Created"), 201
    except Exception as e:
        return f"An Error Occurred: {e}"

<<<<<<< HEAD

=======
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@volunteerRoutes.route('/<string:volunteer_id>/foundation', methods=['PUT'])
def registerFoundation(volunteer_id):
    try:
        foundation_id = request.json['foundation']
        foundation = foundation_Ref.document(foundation_id).get()
        volunteer = volunteer_Ref.document(volunteer_id).get()
<<<<<<< HEAD

        if volunteer.exists and foundation.exists:
            foundation_data = foundation.to_dict()

=======
        
        if volunteer.exists and foundation.exists:
            foundation_data = foundation.to_dict()
        
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            if volunteer_id in foundation_data["volunteers"].keys():
                # HTTP response code: 409 Conflict
                return jsonify(message="Volunteer Exists"), 409
            else:
                # Add new volunteer to foundation collection
                foundation_data["volunteers"][volunteer_id] = 'pending'
                foundation_Ref.document(foundation_id).update(foundation_data)

<<<<<<< HEAD
            # Add new
            volunteer_data = volunteer_Ref.document(
                volunteer_id).get().to_dict()
            volunteer_data["foundations"].append(foundation_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)

=======
            # Add new 
            volunteer_data = volunteer_Ref.document(id).get().to_dict()
            volunteer_data["foundations"].append(foundation_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

<<<<<<< HEAD

=======
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@volunteerRoutes.route('/<string:volunteer_id>/mission', methods=['PUT'])
def applyMission(volunteer_id):
    try:
        volunteer = volunteer_Ref.document(volunteer_id).get()
        mission_id = request.json['mission']
        mission = mission_Ref.document(mission_id).get()
<<<<<<< HEAD

        if volunteer.exists and mission.exists:
            mission_data = mission.to_dict()

=======
        
        if volunteer.exists and mission.exists:
            mission_data = mission.to_dict()
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            if volunteer_id in mission_data["volunteers"].keys():
                # HTTP response code: 409 Conflict
                return jsonify(message="Volunteer Exists"), 409
            else:
                # Update volunteer of a mission on mission collection
<<<<<<< HEAD
                mission_data["volunteers"].update({volunteer_id: "pending"})
=======
                mission_data["volunteers"].update({volunteer_id : "pending"})
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
                mission_Ref.document(mission_id).update(mission_data)

            # Update mission of a volunteer on volunteer collection
            volunteer_data = volunteer.to_dict()
<<<<<<< HEAD
            volunteer_data["missions"].append(mission_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)

=======
            volunteer_data["mission"].append(mission_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Applied"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occured: {e}"

<<<<<<< HEAD

=======
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@volunteerRoutes.route('/', methods=['PUT'])
def editVolunteer():
    try:
        volunteer_id = request.json["id"]
        volunteer = volunteer_Ref.document(volunteer_id).get()
<<<<<<< HEAD

=======
        
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
        if volunteer.exists:
            # Update volunteer on volunteer collection
            volunteer_data = request.json["data"]
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
<<<<<<< HEAD

=======
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
<<<<<<< HEAD

    except Exception as e:
        return f"An Error Occurred: {e}"


=======
    
    except Exception as e:
        return f"An Error Occurred: {e}"

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@volunteerRoutes.route('/', methods=['DELETE'])
def deleteVolunteer():
    volunteer_id = request.json['id']
    try:
        volunteer = volunteer_Ref.document(volunteer_id).get()
<<<<<<< HEAD

        if volunteer.exists:
            volunteer_data = volunteer.to_dict()

=======
        
        if volunteer.exists:
            volunteer_data = volunteer.to_dict()
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # Update volunteer from all applied missions on mission collection
            missions = volunteer_data["missions"]
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
<<<<<<< HEAD
                mission_data["volunteers"].pop(volunteer_id)
=======
                mission_data["volunteers"].remove(volunteer_id)
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
                mission_Ref.document(mission_id).update(mission_data)

            # Update volunteer from all registered foundations on foundation collection
            foundations = volunteer_data["foundations"]
            for foundation_id in foundations:
<<<<<<< HEAD
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                print(foundation_data)
                foundation_data["volunteers"].pop(volunteer_id)
                foundation_Ref.document(foundation_id).update(foundation_data)

            # Delete volunteer from volunteer collection
            volunteer_Ref.document(volunteer_id).delete()

=======
                foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
                foundation_data["members"].remove(volunteer_id)
                foundation_Ref.document(foundation_id).update(foundation_data)
            
            # Delete volunteer from volunteer collection
            volunteer_Ref.document(volunteer_id).delete()
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
<<<<<<< HEAD

=======
    
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
    except Exception as e:
        return f"An Error Occurred: {e}"

@volunteerRoutes.route('/', methods=['GET'])
def getVolunteer():
    try:
<<<<<<< HEAD
        try:
            volunteer_id = request.json["id"]
        except:
            volunteer_id = ""

        if volunteer_id:
            volunteer_data = volunteer_Ref.document(
                volunteer_id).get().to_dict()

            # Get all missions of volunteer from mission collection
            missions = volunteer_data["missions"]
            volunteer_data["missions"] = {}
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                volunteer_data["missions"][mission_id] = mission_data

            # Get all foundations of volunteer from mission collection
            foundations = volunteer_data["foundations"]
            volunteer_data["foundations"] = {}
            for foundation_id in foundations:
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                volunteer_data["foundations"][foundation_id] = foundation_data

=======
        volunteer_id = request.json["id"]
        
        if volunteer_id:
            volunteer_data = volunteer_Ref.document(volunteer_id).get().to_dict()
            
            # Get all missions of volunteer from mission collection
            missions = volunteer_data["mission"]
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                volunteer_data["mission"][mission_id] = mission_data

            # Get all foundations of volunteer from mission collection
            foundations = volunteer_data["foundation"]
            volunteer_data["foundation"] = {}
            for foundation_id in foundations:
                foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
                volunteer_data["foundation"][foundation_id] = foundation_data
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return volunteer_data, 200
        else:
            all_volunteers = [doc.to_dict() for doc in volunteer_Ref.stream()]
<<<<<<< HEAD

            # HTTP response code: 200 OK
            return jsonify(all_volunteers), 200

=======
            
            # HTTP response code: 200 OK
            return jsonify(all_volunteers), 200
    
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
    except Exception as e:
        return f"An Error Occurred: {e}"
