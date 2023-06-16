# Server-Side Backend

## Requirement (ignore if you already have it)
- Have a project on Google Cloud Platform to deploy the service. (Main step 1)
- Connect Firebase to Project and Enable firestore and Auth
- Install Google Cloud SDK
- Install nodejs with minimum version 18.0.0.
- Deploy machine-learning backend service to Cloud Run ([machine-learning](https://github.com/zenrif/Cendakala/tree/backend/machine-learning))
- Deploy client-side backend service to App Engine ([client-side](https://github.com/zenrif/Cendakala/tree/backend/client-side))

## Setup
1. Change your root directory to server-side folder
2. initialize a new npm package
3. Install dependencies using npm in terminal : **npm install**
  - "axios": "^1.4.0",
  - "cors": "^2.8.5",
  - "express": "^4.18.2",
  - "firebase": "^9.22.0",
  - "firebase-admin": "^11.8.0",
  - "fuzzball": "^2.1.2",
  - "jsonwebtoken": "^9.0.0",
  - "node-fetch": "^3.3.1",
  - "nodemon": "^2.0.2- 
4. Copy the firebase service account to the server-side folder with the name **serviceaccountkey.json**
5. Change url :
  - Go to "/middleware/verifyToken.js", change **clientSideUrl** value with your client-side backend url  
  - Go to "/routes/surveys.js", in router.post "/recommendation/home"
    - Change **urlCollaborative** with "https://**this-is-your-url**/collaborative"
    - Change **urlContent** with "https://**this-is-your-url**/content"
  - Go to "/routes/surveys.js", in router.get "/search/:input" 
    - Change **urlSearch** with "https://**this-is-your-url**/search"
6. Deploy the backend to App Engine with Google Cloud SDK in terminal: "gcloud app deploy --version **your-app-version** "
