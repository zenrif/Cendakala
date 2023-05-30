// Import the functions you need from the SDKs you need
const { initializeApp } = require("firebase/app");
const { getFirestore }  = require("firebase/firestore");
const { getAuth }  = require("firebase/auth");
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCkRKKE_8TcEGmzFRwOwmjwtDtXpQHTx_M",
  authDomain: "cendakala.firebaseapp.com",
  projectId: "cendakala",
  storageBucket: "cendakala.appspot.com",
  messagingSenderId: "668197949674",
  appId: "1:668197949674:web:afebe7224a714561b44793"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const DB = getFirestore(app)
const auth = getAuth(app)

module.exports = {DB, auth }