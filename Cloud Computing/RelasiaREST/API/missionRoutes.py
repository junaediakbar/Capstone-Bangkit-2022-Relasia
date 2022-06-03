import enum
from os import remove
from flask import Blueprint, request, jsonify
from firebase_admin import firestore
from datetime import datetime
import hashlib

# Initialization Database Reference
db = firestore.client()
volunteer_Ref = db.collection('volunteer')
helpseeker_Ref = db.collection('helpseeker')
mission_Ref = db.collection('mission')

missionRoutes = Blueprint('missionRoutes', __name__)

@missionRoutes.route('/', methods=['GET'])
def getAllMission():
    try:
        page            = request.args.get("page",          default=1,  type=int)
        data_on_page    = request.args.get("data_on_page",  default=5,  type=int)
        volunteer_id    = request.args.get("volunteer",     default="", type=str)
        helpseeker_id   = request.args.get("helpseeker",    default="", type=str)
        city            = request.args.get("city",          default="", type=str)
        province        = request.args.get("province",      default="", type=str)
        status          = request.args.get("status",        default="", type=str)
        active          = request.args.get("active",        default="", type=str)

        missions = []

        if volunteer_id:
            volunteer_data = volunteer_Ref.document(volunteer_id).get().to_dict()
            mission_id = volunteer_data["missions"]
            
            for data in mission_id:
                missions.append(mission_Ref.document(data).get().to_dict())

            if status:
                temp = []
                for mission in missions:
                    for volunteer in mission["volunteers"]:
                        if volunteer["id"] == volunteer_id and volunteer["status"] == status:
                            temp.append(mission)
                missions = temp

        elif helpseeker_id:
            helpseeker_data = helpseeker_Ref.document(helpseeker_id).get().to_dict()
            mission_id = helpseeker_data["missions"]
            
            for data in mission_id:
                missions.append(mission_Ref.document(data).get().to_dict())

        else:
            # Get all missions from mission collection
            missions = [doc.to_dict() for doc in mission_Ref.stream()]

        if city:
            missions = [mission for mission in missions if mission["city"] == city]
        
        if province:
            missions = [mission for mission in missions if mission["province"] == province]

        if active:
            temp = []
            for mission in missions:
                current = datetime.now().strftime("%Y/%m/%d")
                compare = datetime.strptime(mission["end_date"], "%d/%m/%Y").strftime("%Y/%m/%d")
                if active == "active" and current <= compare:
                    temp.append(mission)
                elif active == "inactive" and current > compare:
                    temp.append(mission)
        
        length = len(missions)
        
        if page > 1:
            page_temp = page - 1
            if (length % data_on_page) == 0:
                missions = missions[(
                    (page_temp)*data_on_page):(page_temp*data_on_page + 5)]
            else:
                missions = missions[(
                    (page_temp)*data_on_page):(page_temp*data_on_page + (length % 5))]
        else:
            missions = missions[0:data_on_page]

        response = {
            "length": length,
            "page": page,
            "data_on_page": data_on_page,
            "data": missions
        }
        
        # HTTP Response Code: 200 OK
        return jsonify(response), 200
    except Exception as e:
        return f"An Error Occurred: {e}"

@missionRoutes.route('/<string:mission_id>', methods=['GET'])
def getMission(mission_id):
    try:
        mission_data = mission_Ref.document(mission_id).get().to_dict()

        # Get Helpseeker who request the mission from helpseeker collection
        helpseeker_id = mission_data["helpseeker"]
        helpseeker = helpseeker_Ref.document(helpseeker_id).get().to_dict()
        mission_data["helpseeker"] = helpseeker

        # Get all volunteers who join a mission from volunteer collection
        volunteers = mission_data["volunteers"]
        mission_data["volunteers"] = []
        for data in volunteers:
            volunteer_id = data["id"]
            volunteer = volunteer_Ref.document(volunteer_id).get()
            if volunteer.exists:
                volunteer_data = volunteer.to_dict()
                volunteer_data['status'] = data["status"]
                mission_data["volunteers"].append(volunteer_data)

        # HTTP Response Code: 200 OK
        return mission_data, 200
    except Exception as e:
        return f"An Error Occurred: {e}"

@missionRoutes.route('/', methods=['POST'])
def addMission():
    try:
        # Composite mission_id from hash(helpseeker_id$title)
        title = request.json['title']
        helpseeker_id = request.json['helpseeker']
        mission_id = hashlib.sha1(
            (helpseeker_id + "$" + title).encode("utf-8")).hexdigest()

        helpseeker = helpseeker_Ref.document(helpseeker_id).get()
        if helpseeker.exists:
            mission = mission_Ref.document(mission_id).get()
            if mission.exists:
                # HTTP response code: 409 Conflict
                return jsonify(message="Mission Exists"), 409
            else:
                # Add new mission to mission collection
                mission_data = request.json
                mission_data["timestamp"] = datetime.now()
                mission_data["id"] = mission_id
                mission_data["volunteers"] = []
                mission_Ref.document(mission_id).set(mission_data)

                # Add new mission to helpseeker collection
                helpseeker_data = helpseeker.to_dict()
                helpseeker_data["missions"].append(mission_id)
                helpseeker_Ref.document(helpseeker_id).update(helpseeker_data)

                # HTTP response code: 201 Created
                return jsonify(message="Successfully Created", data=mission_data), 201
        else:
            return f"Something error or user doesn't exist"
    except Exception as e:
        return f"An Error Occurred: {e}"

@missionRoutes.route('/', methods=['PUT'])
def editMission():
    try:
        mission_id = request.json["id"]
        mission = mission_Ref.document(mission_id).get()

        if mission.exists:
            # Update a mission
            mission_Ref.document(mission_id).update(request.json)
            data = mission_Ref.document(mission_id).get().to_dict()

            # HTTP Response Code: 200 OK
            return jsonify(message="Successfully Updated", data=data), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

@missionRoutes.route('/<string:mission_id>', methods=['PUT'])
def confirmVolunteers(mission_id):
    try:
        mission = mission_Ref.document(mission_id).get()
        volunteer_id = request.json['id']
        volunteer = volunteer_Ref.document(volunteer_id).get()
        status = request.json['status']

        if mission.exists and volunteer.exists:
            # Update Volunteers Status
            mission_data = mission.to_dict()
            for data in mission_data["volunteers"]:
                if volunteer_id == data["id"]:
                    data["status"] = status
                    break

            mission_Ref.document(mission_id).update(mission_data)

            # HTTP Response Code: 200 OK
            return jsonify(message="Successfully Updated"), 200
        else:
            # HTTP response code: 400 Bad Request
            return jsonify(message="Bad Request"), 400
    except Exception as e:
        return f"An Error Occurred: {e}"

@missionRoutes.route('/', methods=['DELETE'])
def deleteMission():
    try:
        # Update mission on volunteer collection
        mission_id = request.json["mission"]
        mission_data = mission_Ref.document(mission_id).get().to_dict()
        volunteers = mission_data["volunteers"]
        for volunteer_id in volunteers:
            volunteer = volunteer_Ref.document(volunteer_id["id"]).get()
            if volunteer.exists:
                volunteer_data = volunteer.to_dict()
                volunteer_data["missions"].remove(mission_id)
                volunteer_Ref.document(
                    volunteer_id["id"]).update(volunteer_data)

        # Update mission on helpseeker collection
        helpseeker_id = mission_data["helpseeker"]
        helpseeker = helpseeker_Ref.document(helpseeker_id).get()
        if helpseeker.exists:
            helpseeker_data = helpseeker.to_dict()
            helpseeker_data["missions"].remove(mission_id)
            helpseeker_Ref.document(helpseeker_id).update(helpseeker_data)

        mission_Ref.document(mission_id).delete()

        # HTTP Response Code: 200 OK
        return jsonify(message="Successfully Deleted"), 200
    except Exception as e:
        return f"An Error Occurred: {e}"