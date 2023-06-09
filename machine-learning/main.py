from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import os
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore


# Get the path to the service account key file
current_dir = os.path.dirname(os.path.abspath(__file__))
key_path = os.path.join(current_dir, 'serviceaccountkey.json')

# Initialize Firebase Admin SDK
cred = credentials.Certificate(key_path)
firebase_admin.initialize_app(cred)

# Access Firestore database
DB = firestore.client()

app = Flask(__name__)
CORS(app)

async def getUsers():
    historyIndex = {
        0: "Kesehatan",
        1: "Pendidikan",
        2: "Hukum",
        3: "Keuangan",
        4: "Pariwisata",
        5: "Sosial dan Kemanusiaan",
        6: "Lingkungan dan Konversi",
        7: "Teknologi Informasi dan Komunikasi",
        8: "Olahraga dan Rekreasi",
        9: "Seni dan Budaya",
        10: "Agama dan Kepercayaan",
        11: "Bisnis dan Industri",
        12: "Politik dan Pemerintahan",
        13: "Transportasi dan Logistik",
        14: "Pertanian dan Logistik"
    }

    user_id = {}
    category = {}
    history = {}

    usersRef = DB.collection('users').get()
    count = 0
    for user in usersRef:
        userData = user.to_dict()
        for i in range(len(historyIndex)):
            if historyIndex[i] in userData['history'] and userData['history'][historyIndex[i]] > 0:
                user_id[count] = userData['uid']
                category[count] = historyIndex[i]
                history[count] = userData['history'][historyIndex[i]]
                count += 1

    res = {
        'user_id': user_id,
        'category': category,
        'history': history
    }

    return res



# Routing
@app.route("/", methods=["GET"])
def index():
    if(request.method == "GET"):
        return "Success get"
    
    
@app.route('/train', methods=["POST"])
async def trainModel():
    if(request.method == "POST"):
        from train_model import train
        train(await getUsers())
        return "complete"


@app.route("/collaborative", methods=["POST"])
async def collaborativeRecommend():
    if(request.method == "POST"):
        from collaborative import predict
        
        payload = request.json
        res = predict(await getUsers(), payload['uid'])
        return res


@app.route("/content", methods=['POST'])
def contentRecommend():
    if(request.method == "POST"):
        from content import survey_similarity_model
        
        # Read the JSON file
        # READ JSON
        data = request.json
        
        # Extract the surveys from the JSON data
        surveys = data['surveys']

        # Set the query
        query = data['survey_title']

        # Call the survey_similarity_model function
        matches = survey_similarity_model(query, surveys)

        # Create a dictionary with numbered keys
        output_dict = {str(i): survey_id for i, survey_id in enumerate(matches)}

        # Convert the dictionary to JSON
        output = jsonify(output_dict)

        # Print the output and save the JSON output to a file
        return output_dict
    
@app.route("/test", methods=['POST'])
def aa():
    if(request.method == "POST"):
        data = {'message': 'Hellow'}
        return jsonify(data)    




if __name__ == '__main__':
    
    # app.run(threaded=True)
    app.run(threaded=True, debug=True)
