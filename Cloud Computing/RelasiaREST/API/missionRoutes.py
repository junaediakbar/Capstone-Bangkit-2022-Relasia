from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

missionRoutes = Blueprint('missionRoutes', __name__)


@missionRoutes.route('/edit', methods=['POST', 'PUT'])
def editMission():
    try:
        mission_id = request.args.get('id')
        if mission_id:
            mission_data = request.get_json(force=True)
            mission_Ref.document(mission_id).update(mission_data)
            return jsonify({"success": True}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@missionRoutes.route('/<string:id>/', methods=['POST', 'PUT'])
def editVolunteerStatus(id):
    try:
        ref = mission_Ref.document(id)
        checkRef = ref.get()
        volunteer_id = request.args.get('volunteer')
        if checkRef.exists:
            if volunteer_id:
                mission_data = mission_Ref.document(id).get().to_dict()
                applied_volunteer = mission_data["applied_volunteer"]
                for i in range(0, len(applied_volunteer)):
                    if volunteer_id in applied_volunteer[i]["volunteer"]:
                        applied_volunteer[i] = request.get_json(force=True)
                        applied_volunteer[i]["volunteer"] = volunteer_id
                        break
                    else:
                        return jsonify({"status": "failed"}), 424
                mission_Ref.document(id).update(mission_data)
                return jsonify({"status": "success"}), 201
            else:
                return jsonify({"status": "failed"}), 424
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@missionRoutes.route('/delete', methods=['GET', 'DELETE'])
def deleteMission():
    try:
        mission_id = request.args.get('id')
        if mission_id:
            mission_data = mission_Ref.document(mission_id).get().to_dict()
            applied_volunteer = mission_data["applied_volunteer"]
            for i in range(0, len(applied_volunteer)):
                volunteer_id = applied_volunteer[i]["volunteer"]
                checkVolunteerId = volunteer_Ref.document(volunteer_id).get()
                if checkVolunteerId.exists:
                    volunteer_data = checkVolunteerId.to_dict()
                    volunteer_data["mission"].remove(mission_id)
                    volunteer_Ref.document(volunteer_id).update(volunteer_data)
                else:
                    return jsonify({"status": "failed"}), 424
            mission_Ref.document(mission_id).delete()
            return jsonify({"status": "success"}), 200
        else:
            return f"Something error or campaigns/users doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@missionRoutes.route('/', methods=['GET'])
def getMission():
    try:
        mission_id = request.args.get('id')
        if mission_id:
            mission = mission_Ref.document(mission_id).get().to_dict()
            applied_volunteer = mission["applied_volunteer"]
            for i in range(0, len(applied_volunteer)):
                volunteer_id = applied_volunteer[i]["volunteer"]
                checkVolunteerId = volunteer_Ref.document(volunteer_id).get()
                if checkVolunteerId.exists:
                    volunter_data = checkVolunteerId.to_dict()
                    applied_volunteer[i]["volunteer"] = volunter_data
            return mission, 200
        else:
            all_mission = [doc.to_dict() for doc in mission_Ref.stream()]
            return jsonify(all_mission), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@missionRoutes.route('/image', methods=['GET'])
def getMissionImage():
    try:
        mission_id = request.args.get('id')
        if mission_id:
            mission = mission_Ref.document(mission_id).get().to_dict()
            image = mission["featured_image"]
            return jsonify(image), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"
