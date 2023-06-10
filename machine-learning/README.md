# Client-Side Backend

## Requirement (ignore if you already have it)
- Have a project on Google Cloud Platform to deploy the service. (Main step 1)
- Connect Firebase to Project and Enable firestore and Auth
- Install Google Cloud SDK
- Install nodejs with minimum version 18.0.0.

## Setup
1. Change your root directory to client-side folder
2. initialize a new npm package
3. Install dependencies using npm in terminal : **npm install**
  - "axios": "^1.4.0",
  - "dotenv": "^16.0.3",
  - "express": "^4.18.2",
  - "firebase": "^9.22.0",
  - "nodemon": "^2.0.22"
4. Copy the firebase service account to the client-side folder with the name **serviceaccountkey.json**
5. Deploy the backend to App Engine with Google Cloud SDK in terminal: "gcloud app deploy --version **your-app-version** "
