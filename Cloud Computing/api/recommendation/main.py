# Initialize Firestore
from firebase_admin import credentials, initialize_app, firestore

cred = credentials.Certificate('credentials.json')
default_app = initialize_app(cred)

db = firestore.client()
volunteer_Ref = db.collection('volunteer')
mission_Ref = db.collection('mission')

# Get Dataset for Recommendation System
def getDataset():
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
    return data

def getRecommendation(categories):
    # Get all missions from mission collection
    missions = [doc.to_dict() for doc in mission_Ref.stream()]
    
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

# Routing
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

if __name__ == '__main__':
    # app.run(threaded=True)
    app.run(host="0.0.0.0", debug=True)