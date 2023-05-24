const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")

//router
router.post('/create', async (req, res) => {
    
    try {
        const {answers, surveyID, reward} = req.body;
        const uid = req.body.uid;

        const current = Date.now()
        const dateTime = new Date(current);
        const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
        const responseID =  crypto.randomUUID() + date + dateTime.getMilliseconds();

        const responseData = {
            surveyID : surveyID,
            uid : uid,
            answers : answers,
            responseID : responseID,
            reward : reward
        }
        
        //Survey
        const surveyRef = DB.collection('surveys').doc(req.body.surveyID)
        const docSnapshot = await surveyRef.get() 

        if(!docSnapshot.exists){
            return res.status(404).json({
                status : "Failed",
                code : 'surveys/notFound',
                message : "Survey not found"
            })
        }

        const surveyQuota = docSnapshot.data().quota
        const newQuota = surveyQuota - 1;
        

        //User
        const userRef = DB.collection('users').doc(req.body.uid)
        const docSnapshotU = await userRef.get()

        if(!docSnapshotU.exists){
            return res.status(404).json({
                status : "Failed",
                code : "users/notFound",
                message : "User not found"
            })
        }

        const userBalance = docSnapshotU.data().balance;
        const newBalance = userBalance + req.body.reward;
        

        

        //Response
        const responseRef = DB.collection('response')
        const resSnap = await responseRef.get();
        const resDoc = resSnap.docs

        let isDouble = false;
        for(const respon of resDoc){
            if(respon.data().uid === req.body.uid && respon.data().surveyID === surveyID){
                isDouble = true;
                break;
            }
        }

        if(isDouble){
            return res.status(409).json({
                status : "Failed",
                code : "response/doubleSubmit",
                message : "Cannot submit response twice at same survey"
            })
        }
        //Eksekusi 
        await surveyRef.update({quota: newQuota})
        await userRef.update({balance: newBalance})
        await responseRef.doc(responseID).set(responseData);
        const response = {
            status : "Success",
            Message : "Success adding response",
            response : responseID
        }
        res.status(200).json(response)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/response/:responseID', async(req, res) =>{
try {

    const sID = req.body.surveyID;
    const uID = req.body.uid;
    const aID = req.body.answer;

    const surveyresponse = {
        "surveyID" : sID,
        "uid" : uID,
        "answer" : aID
    }
    
} catch (error) {
    console.error(error)
    res.status(500).json({
        message : error
    })
}
})

module.exports = router