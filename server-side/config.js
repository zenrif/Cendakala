var admin = require('firebase-admin')
var { getFirestore } = require('firebase-admin/firestore');
var { getAuth} = require('firebase-admin/auth');

const serviceAccount = require("./serviceaccountkey.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const DB = getFirestore();
const auth= getAuth();

module.exports = {DB, auth}