import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
volunteers_Ref = db.collection('volunteers')
user_Ref = db.collection('users')
campaign_Ref = db.collection('campaign')

historyRoutes = Blueprint('historyRoutes', __name__)


@historyRoutes.route('/<string:id>/select', methods=["POST", "PUT"])
def volunteerCampaign(id):
    try:
        campaign_id = request.args.get('id')
        ref = volunteers_Ref.document(id)
        checkRef = ref.get()

        if checkRef.exists:
            data = campaign_Ref.document(campaign_id).get()
            data["history_id"] = str(uuid.uuid4())[:8]
            volunteers_Ref.document(id).collection(
                "history").document(campaign_id).set(data)
            data["volunteers_Registered"].push(request.get_json(force=True))
            campaign_Ref.document(campaign_id).update(data)
            return jsonify({"success": True}), 200
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@historyRoutes.route('/<string:id>', methods=["GET"])
def volunteerHistory(id):
    try:
        ref = volunteers_Ref.document(id)
        checkRef = ref.get()

        if checkRef.exists:
            history = volunteers_Ref.document(id).collection(
                "history")
            for doc in history.stream():
                data = doc.to_dic
            return jsonify(data), 200
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"
