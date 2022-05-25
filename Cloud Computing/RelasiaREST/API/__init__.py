from flask import Flask
from firebase_admin import credentials, initialize_app

# Initialize Firestore DB
cred = credentials.Certificate('API/key.json')
default_app = initialize_app(cred)

# Initialize Flask App


def create_app():
    app = Flask(__name__)
    app.config['SECRET_KEY'] = 'relasiaApp'

    from .helpseekerRoutes import helpseekerRoutes
    from .volunteerRoutes import volunteerRoutes
    from .foundationRoutes import foundationRoutes
    from .missionRoutes import missionRoutes

    app.register_blueprint(helpseekerRoutes, url_prefix='/helpseeker')
    app.register_blueprint(volunteerRoutes, url_prefix='/volunteer')
    app.register_blueprint(foundationRoutes, url_prefix='/foundation')
    app.register_blueprint(missionRoutes, url_prefix='/mission')

    return app
