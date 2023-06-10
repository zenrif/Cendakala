# Cloud Computing Documentation
The Cendakala application uses Google Cloud Platform Services to share and store data. The google cloud project is connected to firebase because the application also uses firebase auth for authentication and firebase firestore for the database.

## Requirement
- Git.
- Code editor (Visual studio code).
- Google Cloud Account.

## 1. Setup Google Cloud Platform
- Create Project & Configure Identity and Access Management.
- Enable App Engine API & configure App Engine services.

## 2. Download Google Cloud SDK and Run Cloud Shell in Your Code Editor
- Download Google Cloud SDK & Install.

## 3. Setup Firebase 
- Open [Firebase](https://firebase.google.com/), go to console & connect it To Google Cloud Project.
- Active Firebase Auth & Firebase Firestore.
- Make a **Service Account** and download the file.

## 4. Clone Project and Set Google Cloud account
- Open your code editor. (i recommend using Visual Studio Code)
- open [Cendakala-Project-Backend](https://github.com/zenrif/Cendakala/tree/backend) and clone it to your local folder or use "git clone .https://github.com/zenrif/Cendakala/tree/backend" in terminal.
- In terminal use "**git init**" and connect your google cloud account.

## 5. Set Project and Deploy Application to App Engine
- Set your project in the terminal by execute "gcloud config set project **your-project**".

## 6. REST-API
- For API Documentation, see the following link : [API Documentation](https://docs.google.com/document/d/1y4ClCsz6hy0ygxJ8HvCwDNeSdZL8LUDqe-rE3bVlZBY/edit?usp=sharing)
