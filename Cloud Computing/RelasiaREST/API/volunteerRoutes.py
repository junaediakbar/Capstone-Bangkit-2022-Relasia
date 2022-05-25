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
        id = request.args.get('id')
        if id:
            volunteer_Ref.document(id).set(request.get_json(force=True))
            return jsonify({"status": "success"}), 201
        else:
            return jsonify({"success": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteerRoutes.route('/<string:id>', methods=['POST', 'PUT'])
def addVolunteerFoundation(id):
    try:
        ref = volunteer_Ref.document(id)
        checkRef = ref.get()
        foundation_id = request.args.get('foundation')
        if checkRef.exists:
            checkFoundationId = foundation_Ref.document(foundation_id).get()
            if checkFoundationId.exists:
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                if id in foundation_data["member"]:
                    return jsonify({"status": "failed"}), 424
                else:
                    foundation_data["member"].append(id)
                    foundation_Ref.document(
                        foundation_id).update(foundation_data)

                volunteer_data = volunteer_Ref.document(id).get().to_dict()
                volunteer_data["foundation"].append(foundation_id)
                volunteer_Ref.document(id).update(volunteer_data)
                return jsonify({"status": "success"}), 201
            else:
                foundation_data = request.get_json(force=True)
                foundation_data["member"] = [id]
                foundation_Ref.document(foundation_id).set(foundation_data)

                volunteer_data = volunteer_Ref.document(id).get().to_dict()
                volunteer_data["foundation"].append(foundation_id)
                volunteer_Ref.document(id).update(volunteer_data)
                return jsonify({"status": "success"}), 201
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteerRoutes.route('/<string:id>', methods=['GET'])
def addVolunteerMission(id):
    try:
        ref = volunteer_Ref.document(id)
        checkRef = ref.get()
        mission_id = request.args.get('mission')
        if checkRef.exists:
            checkMissionId = mission_Ref.document(mission_id).get()
            if checkMissionId.exists:
                mission_data = mission_Ref.document(
                    mission_id).get().to_dict()
                if id in mission_data["applied_volunteer"]:
                    return jsonify({"status": "failed"}), 424
                else:
                    mission_data["applied_volunteer"].append(
                        {"status": "pending", "volunteer": id})
                    mission_Ref.document(mission_id).update(mission_data)

                volunteer_data = volunteer_Ref.document(id).get().to_dict()
                volunteer_data["mission"].append(mission_id)
                volunteer_Ref.document(id).update(volunteer_data)
                return jsonify({"status": "success"}), 201
            else:
                return jsonify({"status": "failed"}), 424
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occured: {e}"


@volunteerRoutes.route('/edit', methods=['POST', 'PUT'])
def editDataVolunteer():
    volunteer_id = request.args.get('id')
    try:
        if volunteer_id:
            volunteer_Ref.document(volunteer_id).update(
                request.get_json(force=True))
            return jsonify({"status": "success"}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteerRoutes.route('/delete', methods=['GET', 'DELETE'])
def deleteVolunteer():
    volunteer_id = request.args.get('id')
    try:
        if volunteer_id:
            user = volunteer_Ref.document(volunteer_id).get().to_dict()
            user_mission = user["mission"]
            user_foundation = user["foundation"]
            for i in range(0, len(user_mission)):
                mission_id = user_mission[i]
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                mission_data["applied_volunteer"].remove(volunteer_id)
                mission_Ref.document(mission_id).update(mission_data)

            for i in range(0, len(user_foundation)):
                foundation_id = user_foundation[i]
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                foundation_data["member"].remove(volunteer_id)
                foundation_Ref.document(foundation_id).update(foundation_data)

            volunteer_Ref.document(volunteer_id).delete()
            return jsonify({"status": "success"}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteerRoutes.route('/', methods=['GET'])
def getVolunteer():
    try:
        volunteer_id = request.args.get('id')
        if volunteer_id:
            user = volunteer_Ref.document(volunteer_id).get().to_dict()
            user_mission = user["mission"]
            user_foundation = user["foundation"]
            for i in range(0, len(user_mission)):
                mission_id = user_mission[i]
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                user["mission"][i] = mission_data
            for i in range(0, len(user_foundation)):
                foundation_id = user_foundation[i]
                foundation_data = foundation_Ref.document(
                    foundation_id).get().to_dict()
                user["foundation"][i] = foundation_data
            return user, 200
        else:
            all_users = [doc.to_dict() for doc in volunteer_Ref.stream()]
            return jsonify(all_users), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
