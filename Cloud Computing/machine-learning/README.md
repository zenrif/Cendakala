# Machine-Learning Backend

## Requirement (ignore if you already have it)
- Have a project on Google Cloud Platform to deploy the service. (Main step 1)
- Connect Firebase to Project and Enable firestore and Auth
- Install Google Cloud SDK
- Install Python with minimum version 3.11.1.

## Setup
1. Change your root directory to machine-learning folder
2. initialize a new npm package
3. Install dependencies using pip in terminal (for testing purposes) : **pip install --no-cache-dir -r requirements.txt**
  - Flask==2.3.2
  - Flask_Cors==3.0.10
  - keras==2.12.0
  - numpy==1.23.5
  - pandas==2.0.2
  - scikit_learn==1.2.2
  - tensorflow==2.12.0
  - tensorflow_recommenders==0.7.3
  - gunicorn
  - firebase-admin
  - flask[async]
  - Sastrawi
  - python-Levenshtein
  - fuzzywuzzy[speedup]
  - thefuzz
4. Copy the firebase service account to the machine-learning folder with the name **serviceaccountkey.json**
5. Build an image with and upload it to container registry with Google Cloud SDK in terminal: "gcloud builds submit --tag gcr.io/**your-project**/**name-of-the-image**"
6. Deploy the image to Cloud Run with Google Cloud SDK in terminal : "gcloud run deploy --image gcr.io/**your-project**/**name-of-the-image** --platform managed"
