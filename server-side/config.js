//Firebase Firestore and Auth Configuration 
var admin = require('firebase-admin')
var { getFirestore } = require('firebase-admin/firestore');
var { getAuth} = require('firebase-admin/auth');

const serviceAccount = require("./serviceaccountkey.json"); // Put your firebase service account file here in json format Ex. path/to/serviceaccountkey.json
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const DB = getFirestore();
const auth= getAuth();

module.exports = {DB, auth}