import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
user_Ref = db.collection('users')

usersRoutes = Blueprint('usersRoutes', __name__)


@usersRoutes.route('/', methods=['POST'])
def create():
    try:
        id = str(uuid.uuid4())[:8]
        user_Ref.document(id).set(request.get_json(force=True))
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@usersRoutes.route('/', methods=['GET'])
def getData():
    try:
        user_id = request.args.get('id')
        if user_id:
            user = user_Ref.document(user_id).get()
            return jsonify(user.to_dict()), 200
        else:
            all_users = [doc.to_dict() for doc in user_Ref.stream()]
            return jsonify(all_users), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@usersRoutes.route('/edit', methods=['POST', 'PUT'])
def update():
    user_id = request.args.get('id')
    try:
        user_Ref.document(user_id).update(request.get_json(force=True))
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@usersRoutes.route('/delete', methods=['GET', 'DELETE'])
def delete():
    user_id = request.args.get('id')
    try:
        user_Ref.document(user_id).delete()
        return jsonify({"success": True}), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
