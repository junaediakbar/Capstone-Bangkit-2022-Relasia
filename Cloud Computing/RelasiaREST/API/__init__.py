from flask import Flask
from firebase_admin import credentials, initialize_app

# Initialize Firestore DB
cred = credentials.Certificate('API/key.json')
default_app = initialize_app(cred)

# Initialize Flask App


def create_app():
    app = Flask(__name__)
    app.config['SECRET_KEY'] = 'relasiaApp'

    from .usersRoutes import usersRoutes
    from .volunteersRoutes import volunteersRoutes
    from .foundationsRoutes import foundationsRoutes
    from .historyRoutes import historyRoutes
    from .campaignRoutes import campaignRoutes

    app.register_blueprint(usersRoutes, url_prefix='/helpseekers')
    app.register_blueprint(volunteersRoutes, url_prefix='/volunteers')
    app.register_blueprint(foundationsRoutes, url_prefix='/foundations')
    app.register_blueprint(historyRoutes, url_prefix='/history')
    app.register_blueprint(campaignRoutes, url_prefix='/campaign')

    return app
