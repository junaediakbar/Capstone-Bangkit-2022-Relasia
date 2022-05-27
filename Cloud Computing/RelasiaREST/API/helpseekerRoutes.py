from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
helpseeker_Ref = db.collection('helpseeker')
mission_Ref = db.collection('mission')

helpseekerRoutes = Blueprint('helpseekerRoutes', __name__)

@helpseekerRoutes.route('/', methods=['POST'])
def addHelpseeker():
    try:
        # Add new helpseeker to helpseeker collection
        helpseeker_id = request.json["id"]
        helpseeker_Ref.document(helpseeker_id).set(request.json["data"])

        # HTTP response code: 201 Created
        return jsonify(message="Successfully Created"), 201
    except Exception as e:
        return f"An Error Occurred: {e}"

<<<<<<< HEAD

=======
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@helpseekerRoutes.route('/', methods=['PUT'])
def editDataHelpseeker():
    helpseeker_id = request.json["id"]
    try:
        helpseeker = helpseeker_Ref.document(helpseeker_id).get()
<<<<<<< HEAD

        if helpseeker.exists:
            helpseeker_Ref.document(helpseeker_id).update(request.json["data"])

=======
        
        if helpseeker.exists:
            helpseeker_Ref.document(helpseeker_id).update(request.json["data"])
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
<<<<<<< HEAD

=======
    
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
    except Exception as e:
        return f"An Error Occurred: {e}"

# I think it is not necessary, it has been covered by editMission on missionRoutes
# @helpseekerRoutes.route('/<string:id>', methods=['POST', 'PUT'])
# def editMissionByHelpseeker(id):
#     try:
#         ref = helpseeker_Ref.document(id)
#         checkRef = ref.get()
#         mission_id = request.args.get('mission')
#         if checkRef.exists:
#             if mission_id:
#                 checkMissionId = mission_Ref.document(mission_id).get()
#                 if checkMissionId.exists:
#                     mission_data = request.get_json(force=True)
#                     mission_Ref.document(mission_id).update(mission_data)
#                     return jsonify({"status": "success"}), 201
#                 else:
#                     return jsonify({"status": "failed"}), 424
#             else:
#                 return jsonify({"status": "failed"}), 424
#         else:
#             return jsonify({"status": "failed"}), 424
#     except Exception as e:
#         return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['DELETE'])
def deleteHelpseeker():
    helpseeker_id = request.json["id"]
    try:
<<<<<<< HEAD
        helpseeker_data = helpseeker_Ref.document(
            helpseeker_id).get().to_dict()
        missions = helpseeker_data["missions"]

        for mission in missions:
            mission_Ref.document(mission).delete()

=======
        helpseeker_data = helpseeker_Ref.document(helpseeker_id).get().to_dict()
        missions = helpseeker_data["missions"]
        
        for mission in missions:
            mission_Ref.document(mission).delete()

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
        helpseeker_Ref.document(helpseeker_id).delete()
        # HTTP response code: 200 OK
        return jsonify(message="Successfully Deleted"), 200
    except Exception as e:
        return f"An Error Occurred: {e}"


@helpseekerRoutes.route('/', methods=['GET'])
def getHelpseeker():
    try:
<<<<<<< HEAD
        try:
            helpseeker_id = request.json["id"]
        except:
            helpseeker_id = ""

        if helpseeker_id:
            helpseeker_data = helpseeker_Ref.document(
                helpseeker_id).get().to_dict()

            # Get all missions of helpseeker from mission collection
            missions = helpseeker_data["missions"]
            helpseeker_data["missions"] = {}
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                helpseeker_data["missions"][mission_id] = mission_data
=======
        helpseeker_id = request.json["id"]
        
        if helpseeker_id:
            helpseeker_data = helpseeker_Ref.document(helpseeker_id).get().to_dict()
            
            # Get all missions of helpseeker from mission collection
            missions = helpseeker_data["mission"]
            helpseeker_data["mission"] = {}
            for mission_id in missions:
                mission_data = mission_Ref.document(mission_id).get().to_dict()
                helpseeker_data["mission"][mission_id] = mission_data
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21

            # HTTP response code: 200 OK
            return helpseeker_data, 200
        else:
            # Get all helpseekers from helpseeker collection
<<<<<<< HEAD
            all_helpseekers = [doc.to_dict()
                               for doc in helpseeker_Ref.stream()]

            # HTTP response code: 200 OK
            return jsonify(all_helpseekers), 200

=======
            all_helpseekers = [doc.to_dict() for doc in helpseeker_Ref.stream()]
            
            # HTTP response code: 200 OK
            return jsonify(all_helpseekers), 200
    
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
    except Exception as e:
        return f"An Error Occurred: {e}"
