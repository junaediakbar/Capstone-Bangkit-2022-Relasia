from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
foundation_Ref = db.collection('foundation')
volunteer_Ref = db.collection('volunteer')

foundationRoutes = Blueprint('foundationRoutes', __name__)


@foundationRoutes.route('/', methods=["POST"])
def addFoundations():
    try:
        foundation_id = request.args.get('id')
        if foundation_id:
            data = request.get_json(force=True)
            foundation_Ref.document(foundation_id).set(data)
            return jsonify({"status": "success"}), 201
        else:
            return jsonify({"success": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationRoutes.route('/<string:id>', methods=['POST'])
def addFoundationsMember(id):
    try:
        ref = foundation_Ref.document(id)
        checkRef = ref.get()
        member_id = request.args.get('member')
        if checkRef.exists:
            foundation_data = foundation_Ref.document(id).get().to_dict()
            if member_id in foundation_data["member"]:
                return jsonify({"status": "failed"}), 424
            else:
                foundation_data["member"].append(member_id)
                foundation_Ref.document(id).update(foundation_data)

            return jsonify({"status": "success"}), 201
        else:
            return jsonify({"success": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationRoutes.route('/edit', methods=['POST', 'PUT'])
def update():
    foundation_id = request.args.get('id')
    try:
        if foundation_id:
            foundation_Ref.document(foundation_id).update(
                request.get_json(force=True))
            return jsonify({"success": True}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationRoutes.route('/delete', methods=['GET', 'DELETE'])
def deleteFoundation():
    foundations_id = request.args.get('id')
    try:
        if foundations_id:
            foundation = foundation_Ref.document(
                foundations_id).get().to_dict()
            member = foundation["member"]

            for i in range(0, len(member)):
                member_id = member[i]
                checkRef = volunteer_Ref.document(member_id).get()
                if checkRef.exists:
                    member_data = volunteer_Ref.document(
                        member_id).get().to_dict()
                    member_data["foundation"].remove(foundations_id)
                    member_data.document(member_id).update(member_data)

            foundation_Ref.document(foundations_id).delete()
            return jsonify({"success": True}), 200
        else:
            return jsonify({"status": "failed"}), 424
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationRoutes.route('/', methods=["GET"])
def getFoundations():
    try:
        foundation_id = request.args.get('id')
        if foundation_id:
            foundation = foundation_Ref.document(foundation_id).get().to_dict()
            member = foundation["member"]
            for i in range(0, len(member)):
                member_id = member[i]
                checkRef = volunteer_Ref.document(member_id).get()
                if checkRef.exists:
                    member_data = volunteer_Ref.document(
                        member_id).get().to_dict()
                    foundation["member"][i] = member_data

            return foundation, 200
        else:
            all_foundations = [doc.to_dict()
                               for doc in foundation_Ref.stream()]
            return jsonify(all_foundations), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
