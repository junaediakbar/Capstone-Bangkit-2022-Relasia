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
            
            # HTTP Response Code: 201 Created
            return jsonify(message="Successfully Created"), 201
    except Exception as e:
        return f"An Error Occurred: {e}"

@volunteerRoutes.route('/<string:volunteer_id>/foundation', methods=['PUT'])
def assignFoundation(volunteer_id):
    try:
        foundation_id = request.json['foundation']
        foundation = foundation_Ref.document(foundation_id).get()
        volunteer = volunteer_Ref.document(volunteer_id).get()
        
        if volunteer.exists and foundation.exists:
            foundation_data = foundation.to_dict()
        
            if volunteer_id in foundation_data["members"].keys():
                # HTTP response code: 409 Conflict
                return jsonify(message="Volunteer Exists"), 409
        
            else:
                # Add new volunteer to foundation collection
                foundation_data["members"][volunteer_id] = 'pending'
                foundation_Ref.document(foundation_id).update(foundation_data)

            # Add new 
            volunteer_data = volunteer_Ref.document(id).get().to_dict()
            volunteer_data["foundations"][foundation_id] = 'pending'
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
            
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

@volunteerRoutes.route('/<string:volunteer_id>/mission', methods=['PUT'])
def applyMission(volunteer_id):
    try:
        volunteer = volunteer_Ref.document(volunteer_id).get()
        mission_id = request.json['mission']
        mission = mission_Ref.document(mission_id).get()
        
        if volunteer.exists and mission.exists:
            mission_data = mission.to_dict()
            
            if volunteer_id in mission_data["volunteers"].keys():
                # HTTP response code: 409 Conflict
                return jsonify(message="Volunteer Exists"), 409
            else:
                # Update volunteer of a mission on mission collection
                mission_data["volunteers"].update({volunteer_id : "pending"})
                mission_Ref.document(mission_id).update(mission_data)

            # Update mission of a volunteer on volunteer collection
            volunteer_data = volunteer.to_dict()
            volunteer_data["mission"].append(mission_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
            
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Applied"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occured: {e}"

@volunteerRoutes.route('/', methods=['PUT'])
def editVolunteer():
    try:
        volunteer_id = request.json["id"]
        volunteer = volunteer_Ref.document(volunteer_id).get()
        
        if volunteer.exists:
            # Update volunteer on volunteer collection
            volunteer_data = request.json["data"]
            volunteer_Ref.document(volunteer_id).update(volunteer_data)
            
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    
    except Exception as e:
        return f"An Error Occurred: {e}"

@volunteerRoutes.route('/', methods=['DELETE'])
def deleteVolunteer():
    volunteer_id = request.json['id']
    try:
        volunteer = volunteer_Ref.document(volunteer_id).get()
        
        if volunteer.exists:
            volunteer_data = volunteer.to_dict()
            
            # Update volunteer from all applied missions on mission collection
            missions = volunteer_data["missions"]
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                mission_data["volunteers"].remove(volunteer_id)
                mission_Ref.document(mission_id).update(mission_data)

            # Update volunteer from all registered foundations on foundation collection
            foundations = volunteer_data["foundations"]
            for foundation_id in foundations:
                foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
                foundation_data["members"].remove(volunteer_id)
                foundation_Ref.document(foundation_id).update(foundation_data)
            
            # Delete volunteer from volunteer collection
            volunteer_Ref.document(volunteer_id).delete()
            
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    
    except Exception as e:
        return f"An Error Occurred: {e}"

@volunteerRoutes.route('/', methods=['GET'])
def getVolunteer():
    try:
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
            for foundation_id in foundations:
                foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
                volunteer_data["foundation"][foundation_id] = foundation_data
            
            # HTTP response code: 200 OK
            return volunteer_data, 200
        else:
            all_volunteers = [doc.to_dict() for doc in volunteer_Ref.stream()]
            
            # HTTP response code: 200 OK
            return jsonify(all_volunteers), 200
    
    except Exception as e:
        return f"An Error Occurred: {e}"
