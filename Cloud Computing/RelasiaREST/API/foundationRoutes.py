from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
foundation_Ref = db.collection('foundation')
volunteer_Ref = db.collection('volunteer')

foundationRoutes = Blueprint('foundationRoutes', __name__)

@foundationRoutes.route('/', methods=["POST"])
def addFoundation():
    try:
        foundation_id = request.json["id"]
        foundation_data = request.json["data"]
        foundation_Ref.document(foundation_id).set(foundation_data)

        # HTTP response code: 201 Created
        return jsonify(message="Successfully Created"), 201
<<<<<<< HEAD

    except Exception as e:
        return f"An Error Occurred: {e}"


=======
    
    except Exception as e:
        return f"An Error Occurred: {e}"

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@foundationRoutes.route('/<string:foundation_id>', methods=['PUT'])
def validateMember(foundation_id):
    try:
        foundation = foundation_Ref.document(foundation_id).get()
<<<<<<< HEAD
        print(foundation.to_dict())
        volunteer_id = request.json['volunteer']
        volunteer = volunteer_Ref.document(volunteer_id).get()
=======
        volunteer_id = request.json['member']
        volunteer = foundation_Ref.document(volunteer_id).get()
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
        status = request.json['status']

        if foundation.exists and volunteer.exists:
            # Update members status in foundation collection
            foundation_data = foundation.to_dict()
<<<<<<< HEAD
            foundation_data["volunteers"][volunteer_id] = status
            foundation_Ref.document(foundation_id).update(foundation_data)

            # Update volunteers verified status in volunteer collection
            if status == "accepted":
                volunteer_data = volunteer.to_dict()
                volunteer_data["verified"] = "true"
                volunteer_Ref.document(volunteer_id).update(volunteer_data)

=======
            foundation_data["members"][volunteer_id] = status
            foundation_Ref.document(foundation_id).update(foundation_data)

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
<<<<<<< HEAD

    except Exception as e:
        return f"An Error Occurred: {e}"


=======
    
    except Exception as e:
        return f"An Error Occurred: {e}"

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@foundationRoutes.route("/", methods=['PUT'])
def editFoundation():
    try:
        foundation_id = request.json["id"]
        foundation = foundation_Ref.document(foundation_id).get()
<<<<<<< HEAD

        if foundation.exists:
            foundation_Ref.document(foundation_id).update(request.json["data"])

=======
        
        if foundation.exists:
            foundation_Ref.document(foundation_id).update(request.json["data"])
            
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
<<<<<<< HEAD

    except Exception as e:
        return f"An Error Occurred: {e}"


=======
    
    except Exception as e:
        return f"An Error Occurred: {e}"

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
@foundationRoutes.route('/', methods=['DELETE'])
def deleteFoundation():
    try:
        foundations_id = request.json["id"]
        foundation = foundation_Ref.document(foundations_id).get()
<<<<<<< HEAD

        if foundations_id:
            foundation_data = foundation.to_dict()
            members = foundation_data["volunteers"]

            for volunteer_id in members.keys():
                volunteer = volunteer_Ref.document(volunteer_id).get()

                if volunteer.exists:
                    # Update foundation of volunteer on volunteer collection
                    volunteer_data = volunteer_Ref.document(
                        volunteer_id).get().to_dict()
=======
        
        if foundations_id:
            foundation_data = foundation.to_dict()
            members = foundation_data["member"]

            for volunteer_id in members.keys():
                volunteer = volunteer_Ref.document(volunteer_id).get()
                
                if volunteer.exists:
                    # Update foundation of volunteer on volunteer collection
                    volunteer_data = volunteer_Ref.document(volunteer_id).get().to_dict()
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
                    volunteer_data["foundations"].remove(foundations_id)
                    volunteer_Ref.document(volunteer_id).update(volunteer_data)

            foundation_Ref.document(foundations_id).delete()
<<<<<<< HEAD

=======
            
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

@foundationRoutes.route('/', methods=["GET"])
def getFoundations():
    try:
<<<<<<< HEAD
        try:
            foundation_id = request.json["id"]
        except:
            foundation_id = ""

        if foundation_id:
            foundation_data = foundation_Ref.document(
                foundation_id).get().to_dict()

            # Get all volunteer of foundation from volunteer collection
            volunteers = foundation_data["volunteers"]
            foundation_data["volunteers"] = {}

            for volunteer_id in volunteers:
                volunteer_data = volunteer_Ref.document(
                    volunteer_id).get().to_dict()
                foundation_data["volunteers"][volunteer_id] = volunteer_data

=======
        foundation_id = request.json["id"]
        
        if foundation_id:
            foundation_data = foundation_Ref.document(foundation_id).get().to_dict()
            
            # Get all volunteer of foundation from volunteer collection
            volunteers = foundation_data["volunteers"]
            foundation_data["volunteers"] = {}
            
            for volunteer_id in volunteers:
                volunteer_data = volunteer_Ref.document(volunteer_id).get().to_dict()
                foundation_data["volunteers"][volunteer_id] = volunteer_data

>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
            # HTTP response code: 200 OK
            return foundation_data, 200
        else:
            # Get all foundations from foundation collection
<<<<<<< HEAD
            all_foundations = [doc.to_dict()
                               for doc in foundation_Ref.stream()]

            # HTTP response code: 200 OK
            return jsonify(all_foundations), 200

=======
            all_foundations = [doc.to_dict() for doc in foundation_Ref.stream()]
            
            # HTTP response code: 200 OK
            return jsonify(all_foundations), 200
    
>>>>>>> ae4ac3c2e1bc5acf3de19c2f65e25864d20efd21
    except Exception as e:
        return f"An Error Occurred: {e}"
