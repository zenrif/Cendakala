const express = require("express")
const router  = express.Router()
const { DB, auth } = require('../config')
const {signInWithEmailAndPassword, createUserWithEmailAndPassword, signInWithCustomToken} = require('firebase/auth')
const axios = require('axios')
const { collection, doc, setDoc, getFirestore } = require("firebase/firestore")

router.post('/signin', async (req, res) => {
    const {email, password} = req.body
    try {
        signInWithEmailAndPassword(auth, email, password).then((userCredential) => {
            res.status(200).json({
                status: "Success",
                token : `Bearer ${userCredential.user.stsTokenManager.accessToken}`,
                expirationTime : userCredential.user.stsTokenManager.expirationTime
            });
            }).catch((error) => {
                const errorCode = error.code;
                const errorMessage = error.message;
        
                if(errorCode === 'auth/invalid-credential'){
                    return res.status(401).json({
                        status : "Failed",
                        code : "auth/invalid-credential",
                        message : "Your E-Mail or Password is incorrect"
                    })
                }
            })
        } catch (error) {
            console.error(error);
            res.status(500).send('Server error');
    }
})

router.post('/signup', async (req, res) => {
    const {email, password, name, gender, birthday, job, interest} = req.body;

    try {
        const userCredential = await createUserWithEmailAndPassword(auth, email, password);
        const docID =  userCredential.user.uid;
        const userData = {
            "uid" : docID,
            "name" : name,
            "gender" : gender,
            "birthday" : birthday,
            "job" : job,
            "interest" : interest,
            "history" : {
                "Kesehatan" : 0,
                "Pendidikan" : 0,
                "Hukum" : 0,
                "Pariwisata" : 0,
                "Sosial dan Kemanusiaan" : 0,
                "Lingkungan dan Konversi" : 0,
                "Teknologi informasi dan Komunikasi" : 0,
                "Olahraga dan Rekreasi" : 0,
                "Seni dan Budaya" : 0,
                "Agama dan Kepercayaan" : 0,
                "Bisnis dan Industri" : 0,
                "Politik dan Pemerintahan"  : 0,
                "Transportasi dan Logistik" : 0,
                "Pertanian dan Logistik" : 0 
            },
            "balance" : 0
        }
        const docRef = await doc(collection(DB, "users"), docID)

        if(docRef.exists){
            return res.status(409).json({
                uid : docID,
                status : "Failed",
                code : "users/existed",
                message : "User is already existed in database"
            })
        }
        else{
            await setDoc(docRef, userData);
        }

        res.status(200).json({
            status : "Success",
            token : `Bearer ${userCredential.user.stsTokenManager.accessToken}`,
            expirationTime : userCredential.user.stsTokenManager.expirationTime
        });
    } catch (error) {

        if(error.code === "auth/email-already-in-use"){
            return res.status(409).json({ 
                status : "Failed",
                code : "auth/email-already-in-use",
                "message" : "User already exist in application" 
            })
        }
        else if(error.code === "auth/weak-password"){
            return res.status(400).json({ 
                status : "Failed",
                code : "auth/invalid-password",
                "message" : "The password must at least six characters" 
            })
        } 
        else if(error.code === "auth/invalid-email"){
            return res.status(400).json({
                status : "Failed",
                code : "auth/invalid-email",
                "message" : "Please provide correct email" 
            })
        }
        console.error(error);
        res.status(500).send('Server error');
    }
})

router.post("/newAccess", async (req, res) => {
    try {
        const userCredential = await signInWithCustomToken(auth, req.headers.newtoken);
        return res.json({ 
            token : `Bearer ${userCredential.user.stsTokenManager.accessToken}`,
            expirationTime : userCredential.user.stsTokenManager.expirationTime}
        );
    } catch (error) {
        console.error(error);
        return res.status(500).json({ error: 'An error occurred' });
    }
});

module.exports = router