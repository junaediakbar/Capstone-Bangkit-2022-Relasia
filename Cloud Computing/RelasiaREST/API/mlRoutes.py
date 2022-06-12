from flask import Blueprint, request, jsonify
from firebase_admin import firestore

# Initialization Database Reference
db = firestore.client()
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

mlRoutes = Blueprint('mlRoutes', __name__)


@mlRoutes.route('/ml', methods=['GET'])
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
            mission_category = []
            for mission in volunteer["missions"]:
                mission_id = mission_Ref.document(mission).get()
                if mission_id.exists:
                    mission_data = mission_id.to_dict()
                mission_category.append(mission_data["category"])

            for i in range(8):
                data["volunteer_id"][count] = volunteer["id"]
                data["category"][count] = category[i]
                exp_count = 0
                for mission in mission_category:
                    if category[i] == mission:
                        exp_count += 1
                    else:
                        exp_count += 0
                data["experience"][count] = exp_count
                count += 1

        return jsonify(data), 200
    except Exception as e:
        return f"An Error Occurred: {e}"
