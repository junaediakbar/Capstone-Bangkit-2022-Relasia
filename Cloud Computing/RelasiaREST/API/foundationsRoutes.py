import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
volunteers_Ref = db.collection('volunteers')
foundations_Ref = db.collection('foundations')

foundationsRoutes = Blueprint('foundationsRoutes', __name__)


@foundationsRoutes.route('/<string:id>', methods=["POST"])
def addVolunteersFoundations(id):
    try:
        Ref = volunteers_Ref.document(id)

        checkRef = Ref.get()
        if checkRef.exists:
            foundations_id = str(uuid.uuid4())[:8]
            data = request.get_json(force=True)

            foundations_Ref.document(foundations_id).set(data)
            data["volunteers_id"] = id
            Ref.collection("foundations").document(
                foundations_id).set(data)

            return jsonify({"success": True}), 200
        else:
            return f"User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationsRoutes.route('/', methods=["POST"])
def addFoundations():
    try:
        foundations_id = str(uuid.uuid4())[:8]
        data = request.get_json(force=True)
        foundations_Ref.document(foundations_id).set(data)
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationsRoutes.route('/', methods=["GET"])
def getFoundations():
    try:
        user_id = request.args.get('id')
        if user_id:
            foundations = foundations_Ref.document(
                user_id).collection("foundations")
            for doc in foundations.stream():
                data = doc.to_dict()
            return jsonify(data), 200
        else:
            all_foundations = [doc.to_dict()
                               for doc in foundations_Ref.stream()]
            return jsonify(all_foundations), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationsRoutes.route('/update', methods=['POST', 'PUT'])
def update():
    foundations_id = request.args.get('id')
    try:
        foundations_Ref.document(foundations_id).update(
            request.get_json(force=True))
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@foundationsRoutes.route('/delete', methods=['GET', 'DELETE'])
def delete():
    foundations_id = request.args.get('id')
    try:
        foundations_Ref.document(foundations_id).delete()
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
