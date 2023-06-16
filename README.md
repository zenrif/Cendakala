# Cloud Computing Documentation
The Cendakala application uses Google Cloud Platform Services to share and store data. The google cloud project is connected to firebase because the application also uses firebase auth for authentication and firebase firestore for the database.

## Requirement
- Git
- Code editor (Visual studio code)
- Google Cloud Account

## Cloud Architecture
<p align="center"><img src="https://github.com/zenrif/Cendakala/assets/101646114/0c1b1db1-0bf1-473a-aa48-658ce9ef3c5a"></p>
<p align="center">Architecture Illustration</p>

## 1. Setup Google Cloud Platform
- Create Project & Configure Identity and Access Management
- Enable the following API :
  -  App Engine API
  -  Cloud Run Admin API
  -  Compute Engine API
  -  Cloud Logging API
  -  Cloud Firestore API
  -  Cloud Pub/Sub API
  -  Cloud Build API
  -  Cloud Schduler API

## 2. Download Google Cloud SDK and Run Cloud Shell in Your Code Editor
- Download Google Cloud SDK & Install

## 3. Setup Firebase 
- Open [Firebase](https://firebase.google.com/), go to console & connect it To Google Cloud Project
- Active Firebase Auth & Firebase Firestore
- Make a **Service Account** and download the file

## 4. Clone Project and Set Google Cloud account
- Open your code editor (we recommend using Visual Studio Code)
- open [Cendakala-Project-Backend](https://github.com/zenrif/Cendakala/tree/backend) and clone it to your local folder or use "git clone .https://github.com/zenrif/Cendakala/tree/backend" in terminal
- In terminal use "**git init**" and connect your google cloud account

## 5. Set Project and Deploy Application to App Engine
- Set your project in the terminal by execute "gcloud config set project **your-project**"

## 6. REST-API
- For API Documentation, see the following link : [API Documentation](https://docs.google.com/document/d/1y4ClCsz6hy0ygxJ8HvCwDNeSdZL8LUDqe-rE3bVlZBY/edit?usp=sharing)
<p align="center"><img src="https://github.com/zenrif/Cendakala/assets/101646114/734b51fd-211f-49ef-b934-ea3c9caf24ad"></p>