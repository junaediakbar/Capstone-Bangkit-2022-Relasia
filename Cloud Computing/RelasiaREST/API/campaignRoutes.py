import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
user_Ref = db.collection('users')
campaign_Ref = db.collection('campaign')

campaignRoutes = Blueprint('campaignRoutes', __name__)


@campaignRoutes.route('/<string:id>', methods=["POST"])
def addCampaign(id):
    try:
        ref = user_Ref.document(id)
        checkRef = ref.get()
        if checkRef.exists:
            campaign_id = str(uuid.uuid4())[:8]
            data = request.get_json(force=True)
            user_Ref.document(id).collection(
                "campaign").document(campaign_id).set(data)

            data["user_id"] = id
            campaign_Ref.document(campaign_id).set(data)
            return jsonify({"success": True}), 200
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@campaignRoutes.route('/<string:id>', methods=['GET'])
def getCampaignByUser(id):
    try:
        ref = user_Ref.document(id)
        checkRef = ref.get()
        if checkRef.exists:
            campaigns = user_Ref.document(id).collection("campaign")
            for doc in campaigns.stream():
                data = doc.to_dict()

            return jsonify(data), 200
        else:
            return f"Something error or User doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@campaignRoutes.route('/', methods=['GET'])
def getCampaign():
    try:
        campaign_id = request.args.get('id')
        if campaign_id:
            campaigns = campaign_Ref.document(campaign_id).get()
            return jsonify(campaigns.to_dict()), 200
        else:
            all_campaigns = [doc.to_dict() for doc in campaign_Ref.stream()]
            return jsonify(all_campaigns), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@campaignRoutes.route('/<string:id>/update', methods=['POST', 'PUT'])
def editCampaign(id):
    try:
        campaign_id = request.args.get('id')
        ref = user_Ref.document(id)
        checkRef = ref.get()

        if checkRef.exists:
            data = request.get_json(force=True)
            user_Ref.document(id).collection(
                "campaign").document(campaign_id).update(data)

            data["user_id"] = id
            campaign_Ref.document(campaign_id).update(data)
            return jsonify({"success": True}), 200
        else:
            return f"Something error or campaigns/users doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"


@campaignRoutes.route('/<string:id>/delete', methods=['GET', 'DELETE'])
def deleteCampaign(id):
    try:
        campaign_id = request.args.get('id')
        ref = user_Ref.document(id)
        checkRef = ref.get()

        if checkRef.exists:
            user_Ref.document(id).collection(
                "campaign").document(campaign_id).delete()
            campaign_Ref.document(campaign_id).delete()
            return jsonify({"success": True}), 200
        else:
            return f"Something error or campaigns/users doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"
