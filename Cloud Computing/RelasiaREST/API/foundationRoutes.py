from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
foundation_Ref = db.collection('foundation')
volunteer_Ref = db.collection('volunteer')

foundationRoutes = Blueprint('foundationRoutes', __name__)

@foundationRoutes.route('/<string:foundation_id>', methods=["GET"])
def getFoundation(foundation_id):
    try:
        foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
        
        # Get all volunteer of foundation from volunteer collection
        volunteers = foundation_data["volunteers"]
        foundation_data["volunteers"] = []

        for volunteer in volunteers:
            volunteer_data = volunteer_Ref.document(volunteer["id"]).get().to_dict()

            foundation_data["volunteers"].append({
                "id": volunteer_data["id"],
                "name": volunteer_data["name"],
                "gender": volunteer_data["gender"],
                "missions": volunteer_data["missions"],
                "city": volunteer_data["city"],
                "address": volunteer_data["address"],
                "status": volunteer["status"]
            })

        # HTTP response code: 200 OK
        return foundation_data, 200
    except Exception as e:
        return f"An Error Occurred: {e}"

@foundationRoutes.route('/', methods=["POST"])
def addFoundation():
    try:
        data = request.json
        data["volunteers"] = []

        foundation = foundation_Ref.document(data["id"]).get()
        if foundation.exists:
            # HTTP response code: 409 Conflict
            return jsonify(message="Foundation Exists"), 409

        foundation_Ref.document(data["id"]).set(data)
        data = foundation_Ref.document(data["id"]).get().to_dict()

        # HTTP response code: 200 OK
        return jsonify(message="Successfully Created", data=data), 200
    except Exception as e:
        return f"An Error Occurred: {e}"

@foundationRoutes.route("/", methods=['PUT'])
def editFoundation():
    try:
        foundation_id = request.json["id"]
        foundation = foundation_Ref.document(foundation_id).get()

        if foundation.exists:
            foundation_Ref.document(foundation_id).update(request.json)

            data = foundation_Ref.document(foundation_id).get().to_dict()

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated", data=data), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400

    except Exception as e:
        return f"An Error Occurred: {e}"

@foundationRoutes.route('/<string:foundation_id>', methods=['PUT'])
def validateMember(foundation_id):
    try:
        foundation = foundation_Ref.document(foundation_id).get()
        volunteer_id = request.json['id']
        volunteer = volunteer_Ref.document(volunteer_id).get()
        status = request.json['status']

        if foundation.exists and volunteer.exists:
            # Update members status in foundation collection
            foundation_data = foundation.to_dict()

            for member in foundation_data["volunteers"]:
                if member["id"] == volunteer_id:
                    member["status"] = status
                    break

            foundation_Ref.document(foundation_id).update(foundation_data)

            # Update volunteers verified status in volunteer collection
            if status == "accepted":
                volunteer_data = volunteer.to_dict()
                volunteer_data["verified"] = "true"
                volunteer_Ref.document(volunteer_id).update(volunteer_data)

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400

    except Exception as e:
        return f"An Error Occurred: {e}"

@foundationRoutes.route('/', methods=['DELETE'])
def deleteFoundation():
    try:
        foundations_id = request.json["id"]
        foundation = foundation_Ref.document(foundations_id).get()

        if foundations_id:
            foundation_data = foundation.to_dict()
            members = foundation_data["volunteers"]

            for member in members:
                volunteer = volunteer_Ref.document(member["id"]).get()

                if volunteer.exists:
                    # Update foundation of volunteer on volunteer collection
                    volunteer_data = volunteer_Ref.document(
                        member["id"]).get().to_dict()
                    volunteer_data["foundations"].remove(foundations_id)
                    volunteer_Ref.document(member["id"]).update(volunteer_data)

            foundation_Ref.document(foundations_id).delete()

            # HTTP response code: 200 OK
            return jsonify(message="Successfully Deleted"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"
