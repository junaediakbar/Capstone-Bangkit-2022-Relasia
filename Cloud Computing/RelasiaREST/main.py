from API import create_app

app = create_app()


@app.route('/')
def index():
    return "<h1>Relasia API</h1>"


if __name__ == '__main__':
    app.run(debug=True)
