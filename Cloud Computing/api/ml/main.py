# Initialize Firestore
from firebase_admin import credentials, initialize_app, firestore

cred = credentials.Certificate('credentials.json')
default_app = initialize_app(cred)

db = firestore.client()
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

# Get Dataset for Recommendation System
def getDataset():
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
    return data

def getRecommendation(categories):
    # Get all missions from mission collection
    missions = [doc.to_dict() for doc in mission_Ref.stream()]

    # Filter Active Mission
    from datetime import datetime
    temp = []
    for mission in missions:
        current = datetime.now().strftime("%Y/%m/%d")
        compare = datetime.strptime(
            mission["end_date"], "%d/%m/%Y").strftime("%Y/%m/%d")
        if current <= compare:
            temp.append(mission)
    missions = temp

    # Sort by Timestamp
    missions = sorted(missions, key=lambda mission : mission["timestamp"], reverse=True)
    
    order = {v:i for i,v in enumerate(categories)}
    recommendation = sorted(missions, key=lambda x: order[x["category"]])

    return recommendation

# Initialize Flask
from flask import Flask, request, jsonify

app = Flask(__name__)

# Routing
@app.route('/train')
def trainModel():
    try:
        import ml
        
        data = getDataset()
        
        return jsonify(message=ml.train(data)), 200
    
    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/<string:id>', methods=["GET"])
def predictModel(id):
    try:
        import ml
        
        data = getDataset()
        categories = ml.predict(data, id)

        missions = getRecommendation(list(categories.values()))
        
        title = request.args.get("title", default="", type=str)

        if title:
            missions = [mission for mission in missions if mission["title"].lower().find(title.lower()) != -1]

        length = len(missions)
        
        page = request.args.get("page", default=1, type=int)
        paginate = request.args.get("paginate", default=5, type=int)

        if page > 1:
            page_temp = page - 1
            missions = missions[((page_temp)*paginate):(page*paginate)]
        else:
            missions = missions[0:paginate]

        response = {
            "length": length,
            "data": missions,
            "page": page,
            "data_on_page": paginate
        }
        # HTTP Response Code: 200 OK
        return jsonify(response), 200

    except Exception as e:
        return f"An Error Occurred: {e}"

@app.route('/ml', methods=['GET'])
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

if __name__ == '__main__':
    import os
    
    # app.run(threaded=True)
    app.run(host="0.0.0.0", threaded=True, port=int(os.environ.get("PORT", 8080)))
