from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import os

app = Flask(__name__)
CORS(app)


# Routing
@app.route("/", methods=["GET"])
def index():
    if(request.method == "GET"):
        return "Success get"
    
    
@app.route('/train', methods=["POST"])
def trainModel():
    if(request.method == "POST"):
        from train_model import train
        train("https://server-side-dot-cendakala.et.r.appspot.com/models/recommedation/train/collaborative")
        return "complete"


@app.route("/collaborative", methods=["POST"])
def collaborativeRecommend():
    if(request.method == "POST"):
        from collaborative import predict
        
        payload = request.json
        res = predict("https://server-side-dot-cendakala.et.r.appspot.com/models/recommedation/train/collaborative", payload['uid'])
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
        print(output_dict)

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
