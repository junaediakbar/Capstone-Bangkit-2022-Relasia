from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
helpseeker_Ref = db.collection('helpseeker')
mission_Ref = db.collection('mission')

helpseekerRoutes = Blueprint('helpseekerRoutes', __name__)


@helpseekerRoutes.route('/', methods=['POST'])
def addHelpseeker():
    try:
        # Add new helpseeker to helpseeker collection
        helpseeker_id = request.json["id"]
        helpseeker_Ref.document(helpseeker_id).set(request.json["data"])

        # HTTP response code: 201 Created
        return jsonify(message="Successfully Created"), 201
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['PUT'])
def editDataHelpseeker():
    helpseeker_id = request.json["id"]
    try:
        helpseeker = helpseeker_Ref.document(helpseeker_id).get()

        if helpseeker.exists:
            helpseeker_Ref.document(helpseeker_id).update(request.json["data"])

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
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
            mission_Ref.document(mission).delete()

        helpseeker_Ref.document(helpseeker_id).delete()
        # HTTP response code: 200 OK
        return jsonify(message="Successfully Deleted"), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['GET'])
def getHelpseeker():
    try:
        try:
            helpseeker_id = request.json["id"]
        except:
            helpseeker_id = ""

        if helpseeker_id:
            helpseeker_data = helpseeker_Ref.document(
                helpseeker_id).get().to_dict()

            # Get all missions of helpseeker from mission collection
            missions = helpseeker_data["missions"]
            helpseeker_data["missions"] = {}
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                helpseeker_data["missions"][mission_id] = mission_data

            # HTTP response code: 200 OK
            return helpseeker_data, 200
        else:
            # Get all helpseekers from helpseeker collection
            all_helpseekers = [doc.to_dict()
                               for doc in helpseeker_Ref.stream()]

            # HTTP response code: 200 OK
            return jsonify(all_helpseekers), 200

    except Exception as e:
        return f"An Error Occurred: {e}"
