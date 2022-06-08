from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
helpseeker_Ref = db.collection('helpseeker')
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

helpseekerRoutes = Blueprint('helpseekerRoutes', __name__)


@helpseekerRoutes.route('/', methods=['POST'])
def addHelpseeker():
    try:
        # Add new helpseeker to helpseeker collection
        data = request.json
        try:
            data["picture"] = request.json["picture"]
        except:
            data["picture"] = ""
        data["missions"] = []

        helpseeker = helpseeker_Ref.document(data["id"]).get()
        if helpseeker.exists:
            # HTTP response code: 409 Conflict
            return jsonify(message="Helpseeker Exists"), 409
        else:
            helpseeker_Ref.document(data["id"]).set(data)
            # HTTP response code: 201 Created
            return jsonify(message="Successfully Created", data=data), 201
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['PUT'])
def editDataHelpseeker():
    try:
        helpseeker_id = request.json["id"]
        helpseeker = helpseeker_Ref.document(helpseeker_id).get()

        if helpseeker.exists:
            helpseeker_Ref.document(helpseeker_id).update(request.json)
            data = helpseeker_Ref.document(helpseeker_id).get().to_dict()

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated", data=data), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['DELETE'])
def deleteHelpseeker():
    helpseeker_id = request.json["id"]
    try:
        helpseeker_data = helpseeker_Ref.document(
            helpseeker_id).get().to_dict()
        missions = helpseeker_data["missions"]

        for mission in missions:
            mission_data = mission_Ref.document(mission).get().to_dict()
            volunteers = mission_data["volunteers"]
            for volunteer_id in volunteers:
                volunteer = volunteer_Ref.document(volunteer_id["id"]).get()
                if volunteer.exists:
                    volunteer_data = volunteer.to_dict()
                    volunteer_data["missions"].remove(mission)
                    volunteer_Ref.document(
                        volunteer_id["id"]).update(volunteer_data)
            mission_Ref.document(mission).delete()

        helpseeker_Ref.document(helpseeker_id).delete()
        # HTTP response code: 200 OK
        return jsonify(message="Successfully Deleted"), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/<string:id>', methods=['GET'])
def getHelpseeker(id):
    try:
        try:
            helpseeker_id = id
        except:
            helpseeker_id = ""

        if helpseeker_id:
            helpseeker_data = helpseeker_Ref.document(
                helpseeker_id).get().to_dict()

            # Get all missions of helpseeker from mission collection
            missions = helpseeker_data["missions"]
            helpseeker_data["missions"] = []
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                helpseeker_data["missions"].append(mission_data)

            # HTTP response code: 200 OK
            return helpseeker_data, 200
        else:
            # Get all helpseekers from helpseeker collection
            # all_helpseekers = [doc.to_dict()
            #                    for doc in helpseeker_Ref.stream()]

            # response = {
            #     "length": len(all_helpseekers),
            #     "data": all_helpseekers,
            # }

            # HTTP response code: 409 Bad Request
            return jsonify(message="Bad Request"), 409

    except Exception as e:
        return f"An Error Occurred: {e}"
