# Initialize Firestore
from firebase_admin import credentials, initialize_app

cred = credentials.Certificate('credentials.json')
default_app = initialize_app(cred)

from flask import Flask, render_template, request, flash, jsonify
import requests

app = Flask(__name__)

# Class for Volunteer
class Volunteer:
    # Constructor
    def __init__(self, id, name, gender, birthyear, phone, city, address, status):
        self.id = id
        self.name = name
        self.gender = gender
        self.birthyear = birthyear
        self.phone = phone
        self.city = city
        self.address = address
        self.status = status

@app.route("/<string:id>", methods=["GET"])
def index(id):
    from urllib.request import urlopen
    import json

    url = "https://relasia-api.herokuapp.com/foundation/" + id

    response = urlopen(url)

    foundation = json.load(response)
    print(foundation)

    members = []

    for member in foundation["volunteers"]:
        url = "https://relasia-api.herokuapp.com/volunteer/" + member["id"]

        response = urlopen(url)

        volunteer = json.load(response)

        # flash(volunteer)
        volunteer = Volunteer(
            id=member["id"],
            name=volunteer["name"],
            gender=volunteer["gender"],
            birthyear=volunteer["birthyear"],
            phone=volunteer["phone"],
            city=volunteer["city"],
            address=volunteer["address"],
            status=member["status"]
        )
        members.append(volunteer)

    return render_template('                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            .html', title='Foundation Portal', members=members)

@app.route('/')
@app.route('/index', methods=['GET', 'POST'])
def index():
    if (request.method == 'POST'):
            email = request.form['name']
            password = request.form['password']
            try:
                auth.sign_in_with_email_and_password(email, password)
                #user_id = auth.get_account_info(user['idToken'])
                #session['usr'] = user_id
                return render_template('home.html')
            except:
                unsuccessful = 'Please check your credentials'
                return render_template('index.html', umessage=unsuccessful)
    return render_template('index.html')

@app.route('/create_account', methods=['GET', 'POST'])
def create_account():
    if (request.method == 'POST'):
            email = request.form['name']
            password = request.form['password']
            auth.create_user_with_email_and_password(email, password)
            return render_template('index.html')
    return render_template('create_account.html')

@app.route('/forgot_password', methods=['GET', 'POST'])
def forgot_password():
    if (request.method == 'POST'):
            email = request.form['name']
            auth.send_password_reset_email(email)
            return render_template('index.html')
    return render_template('forgot_password.html')

@app.route('/home', methods=['GET', 'POST'])
def home():
    return render_template('home.html')

if __name__ == '__main__':
    app.run(port=8080, debug=True)