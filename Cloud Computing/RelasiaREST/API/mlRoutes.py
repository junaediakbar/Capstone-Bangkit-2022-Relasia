from flask import Blueprint, request, jsonify
from firebase_admin import firestore

# Initialization Database Reference
db = firestore.client()
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

mlRoutes = Blueprint('mlRoutes', __name__)


@mlRoutes.route('/', methods=['GET'])
def getDataForTraining():
    try:
        data = {
            "volunteer_id": {},
            "category": {},
            "experience": {}
        }
        category = [
            "Disabilitas",
            "Kesehatan",
            "Kesejahteraan Hewan",
            "Lingkungan",
            "Penanggulangan Bencana",
            "Pendidikan",
            "Pengembangan Masyarakat",
            "Pertanian"
        ]

        volunteers = [doc.to_dict() for doc in volunteer_Ref.stream()]
        count = 0
        for volunteer in volunteers:
            for mission in volunteer["missions"]:
                mission_id = mission_Ref.document(mission).get()
                if mission_id.exists:
                    mission_data = mission_id.to_dict()
                    for list in category:
                        exp_count = 0
                        if mission_data["category"] == list:
                            exp_count += 1
                        data["volunteer_id"][count] = volunteer["id"]
                        data["category"][count] = list
                        data["experience"][count] = exp_count
                        count += 1

        return jsonify(data), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
