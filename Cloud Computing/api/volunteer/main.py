# Initialize Firestore
from firebase_admin import credentials, initialize_app, firestore

cred = credentials.Certificate('credentials.json')
default_app = initialize_app(cred)

db = firestore.client()
volunteer_Ref = db.collection('volunteer')
foundation_Ref = db.collection('foundation')
mission_Ref = db.collection('mission')

# Initialize Flask
from flask import Flask, request, jsonify

app = Flask(__name__)

# Routing
@app.route('/<string:volunteer_id>', methods=['GET'])
def getVolunteer(volunteer_id):
    try:
        volunteer_data = volunteer_Ref.document(
            volunteer_id).get().to_dict()

        # Get all missions of volunteer from mission collection
        missions = volunteer_data["missions"]
        volunteer_data["missions"] = []
        for mission_id in missions:
            mission_data = mission_Ref.document(mission_id).get().to_dict()
            volunteer_data["missions"].append(mission_data)

        # Get all foundations of volunteer from mission collection
        foundations = volunteer_data["foundations"]
        volunteer_data["foundations"] = []
        for foundation_id in foundations:
            foundation_data = foundation_Ref.document(
                foundation_id).get().to_dict()
            volunteer_data["foundations"].append(foundation_data)

        # HTTP response code: 200 OK
        return volunteer_data, 200

    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/', methods=['POST'])
def addVolunteer():
    try:
        data = request.json
        data["foundations"] = []
        data["missions"] = []
        data["verified"] = "false"

        volunteer = volunteer_Ref.document(data["id"]).get()
        
        if volunteer.exists:
            # HTTP response code: 409 Conflict
            return jsonify(message="Volunteer Exists"), 409
        
        else:
            # Add new volunteer to volunteer collection
            volunteer_Ref.document(data["id"]).set(data)
            
            # HTTP Response Code: 201 Created
            return jsonify(message="Successfully Created", data=data), 201
    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/', methods=['PUT'])
def editVolunteer():
    try:
        volunteer_id = request.json["id"]
        volunteer = volunteer_Ref.document(volunteer_id).get()

        if volunteer.exists:
            # Update volunteer on volunteer collection
            volunteer_Ref.document(volunteer_id).update(request.json)
            data = volunteer_Ref.document(volunteer_id).get().to_dict()

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated", data=data), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400

    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/<string:volunteer_id>/foundation', methods=['PUT'])
def registerFoundation(volunteer_id):
    try:
        foundation_id = request.json['foundation']
        foundation = foundation_Ref.document(foundation_id).get()
        volunteer = volunteer_Ref.document(volunteer_id).get()

        if volunteer.exists and foundation.exists:
            foundation_data = foundation.to_dict()

            for data in foundation_data["volunteers"]:
                if volunteer_id == data["id"]:
                    # HTTP response code: 409 Conflict
                    return jsonify(message="Failed: Volunteer Exists"), 409
            else:
                # Add new volunteer to foundation collection
                foundation_data["volunteers"].append(
                    {"id": volunteer_id, "status": "pending"})
                foundation_Ref.document(foundation_id).update(foundation_data)

            # Add new
            volunteer_data = volunteer_Ref.document(
                volunteer_id).get().to_dict()
            volunteer_data["foundations"].append(foundation_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Registered"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/<string:volunteer_id>/mission', methods=['PUT'])
def applyMission(volunteer_id):
    try:
        volunteer = volunteer_Ref.document(volunteer_id).get()
        mission_id = request.json['mission']
        mission = mission_Ref.document(mission_id).get()

        if volunteer.exists and mission.exists:
            mission_data = mission.to_dict()

            for data in mission_data["volunteers"]:
                if volunteer_id == data["id"]:
                    # HTTP response code: 409 Conflict
                    return jsonify(message="Failed: Volunteer Exists"), 409

            # Update volunteer of a mission on mission collection
            mission_data["volunteers"].append(
                {"id": volunteer_id, "status": "pending"})
            mission_Ref.document(mission_id).update(mission_data)

            # Update mission of a volunteer on volunteer collection
            volunteer_data = volunteer.to_dict()
            volunteer_data["missions"].append(mission_id)
            volunteer_Ref.document(volunteer_id).update(volunteer_data)

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Applied"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occured: {e}"

@app.route('/', methods=['DELETE'])
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
                for data in mission_data["volunteers"]:
                    if volunteer_id == data["id"]:
                        mission_data["volunteers"].remove(data)
                mission_Ref.document(mission_id).update(mission_data)

            # Update volunteer from all registered foundations on foundation collection
            foundations = volunteer_data["foundations"]
            for foundation_id in foundations:
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                for data in foundation_data["volunteers"]:
                    if volunteer_id == data["id"]:
                        foundation_data["volunteers"].remove(data)
                foundation_Ref.document(foundation_id).update(mission_data)

            # Delete volunteer from volunteer collection
            volunteer_Ref.document(volunteer_id).delete()

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Deleted"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

if __name__ == '__main__':
    import os
    
    # app.run(threaded=True)
    app.run(host="0.0.0.0", threaded=True, port=int(os.environ.get("PORT", 8080)))