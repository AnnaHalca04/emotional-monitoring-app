from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

# Incarca modelele
ridge = joblib.load('model_ridge.pkl')
nb = joblib.load('model_naive.pkl')
le = joblib.load('label_encoder.pkl')

@app.route('/predictie', methods=['POST'])
def predictie():
    try:
        date = request.get_json()

        features = np.array([[
            date['ore_somn'],
            date['minute_somn'],
            date['stres'],
            date['activitate_fizica'],
            date['activitate_sociala'],
            date['mancare']
        ]])

        # Ridge Regression - scor numeric
        scor = round(float(ridge.predict(features)[0]), 2)
        scor = max(0.0, min(10.0, scor))

        # Naive Bayes - tip zi
        tip_encoded = nb.predict(features)[0]
        tip_zi = le.inverse_transform([tip_encoded])[0]

        return jsonify({
            'scor': scor,
            'tip_zi': tip_zi,
            'status': 'ok'
        })

    except Exception as e:
        return jsonify({'error': str(e), 'status': 'error'}), 400

@app.route('/health', methods=['GET'])
def health():
    return jsonify({'status': 'ok', 'message': 'Flask ML service running'})

if __name__ == '__main__':
    app.run(port=5000, debug=True)
