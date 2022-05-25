from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
helpseeker_Ref = db.collection('helpseeker')
mission_Ref = db.collection('mission')

helpseekerRoutes = Blueprint('helpseekerRoutes', __name__)


@helpseekerRoutes.route('/', methods=['POST'])
def addHelpSeeker():
    try:
        id = request.args.get('id')
        if id:
            helpseeker_Ref.document(id).set(request.get_json(force=True))
            return jsonify({"status": "success"}), 201
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/<string:id>', methods=['POST', 'PUT'])
def addHelpSeekerMission(id):
    try:
        ref = helpseeker_Ref.document(id)
        checkRef = ref.get()
        mission_id = request.args.get('mission')
        if checkRef.exists:
            if mission_id:
                checkMissionId = mission_Ref.document(mission_id).get()
                if checkMissionId.exists:
                    return jsonify({"status": "failed"}), 424
                else:
                    mission_data = request.get_json(force=True)
                    mission_data["helpseeker"] = id
                    mission_Ref.document(mission_id).set(mission_data)

                    helpseeker_data = helpseeker_Ref.document(
                        id).get().to_dict()
                    helpseeker_data["mission"].append(mission_id)
                    helpseeker_Ref.document(id).update(helpseeker_data)
                    return jsonify({"status": "success"}), 201
            else:
                return jsonify({"status": "failed"}), 424
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/edit', methods=['POST', 'PUT'])
def editDataHelpseeker():
    helpseeker_id = request.args.get('id')
    try:
        if helpseeker_id:
            helpseeker_Ref.document(helpseeker_id).update(
                request.get_json(force=True))
            return jsonify({"status": "success"}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/<string:id>/edit', methods=['POST', 'PUT'])
def editMissionByHelpseeker(id):
    try:
        ref = helpseeker_Ref.document(id)
        checkRef = ref.get()
        mission_id = request.args.get('mission')
        if checkRef.exists:
            if mission_id:
                checkMissionId = mission_Ref.document(mission_id).get()
                if checkMissionId.exists:
                    mission_data = request.get_json(force=True)
                    mission_Ref.document(mission_id).update(mission_data)
                    return jsonify({"status": "success"}), 201
                else:
                    return jsonify({"status": "failed"}), 424
            else:
                return jsonify({"status": "failed"}), 424
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/delete', methods=['GET', 'DELETE'])
def deleteHelpSeeker():
    helpseeker_id = request.args.get('id')
    try:
        if helpseeker_id:
            user = helpseeker_Ref.document(helpseeker_id).get().to_dict()
            user_mission = user["mission"]
            for i in range(0, len(user_mission)):
                mission_Ref.document(user_mission[i]).delete()

            helpseeker_Ref.document(helpseeker_id).delete()
            return jsonify({"status": "success"}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['GET'])
def getHelpSeeker():
    try:
        helpseeker_id = request.args.get('id')
        if helpseeker_id:
            user = helpseeker_Ref.document(helpseeker_id).get().to_dict()
            user_mission = user["mission"]
            for i in range(0, len(user_mission)):
                mission_data = mission_Ref.document(
                    user_mission[i]).get().to_dict()
                user["mission"][i] = mission_data
            return user, 200
        else:
            all_users = [doc.to_dict() for doc in helpseeker_Ref.stream()]
            return jsonify(all_users), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
