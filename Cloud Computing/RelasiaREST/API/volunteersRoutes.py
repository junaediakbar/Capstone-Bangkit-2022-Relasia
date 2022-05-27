import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
volunteers_Ref = db.collection('volunteers')

volunteersRoutes = Blueprint('volunteersRoutes', __name__)


@volunteersRoutes.route('/', methods=['POST'])
def create():
    try:
        id = str(uuid.uuid4())[:8]
        volunteers_Ref.document(
            id).set(request.get_json(force=True))
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteersRoutes.route('/', methods=['GET'])
def read():
    try:
        user_id = request.args.get('id')
        if user_id:
            user = volunteers_Ref.document(user_id).get()
            return jsonify(user.to_dict()), 200
        else:
            all_users = [doc.to_dict() for doc in volunteers_Ref.stream()]
            return jsonify(all_users), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteersRoutes.route('/update', methods=['POST', 'PUT'])
def update():
    user_id = request.args.get('id')
    try:
        volunteers_Ref.document(user_id).update(request.get_json(force=True))
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@volunteersRoutes.route('/delete', methods=['GET', 'DELETE'])
def delete():
    user_id = request.args.get('id')
    try:
        volunteers_Ref.document(user_id).delete()
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
