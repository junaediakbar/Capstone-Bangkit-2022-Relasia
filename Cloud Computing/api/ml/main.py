# Initialize Flask
from flask import Flask, request, jsonify

app = Flask(__name__)

# Routing
@app.route('/train', methods=["GET"])
def trainModel():
    try:
        import train
        url_json = ""
        train.train(url_json)
        return "Successfully Trained", 200
    
    except Exception as e:
        return f"An Error Occurred: {e}"

# Routing
@app.route('/predict', methods=["GET"])
def predictModel():
    try:
        import predict
        # Fetch Data si Volunteer
        data = {}
        return jsonify(predict.predict(data)), 200
    
    except Exception as e:
        return f"An Error Occurred: {e}"

if __name__ == '__main__':
    app.run(threaded=True)
    # app.run(host="0.0.0.0", debug=True)