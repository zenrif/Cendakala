const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")
const { FieldValue } = require("firebase-admin/firestore")

//router
router.post('/create', verifyToken, async (req, res) => {
    
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
        const surveyCategory1 = docSnapshot.data().surveyCategory1
        const surveyCategory2 = docSnapshot.data().surveyCategory2

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
        const increment = FieldValue.increment(1)
        const surveyData = {
            quota: newQuota,
            finished: true,
            interest:{}
        }

        if(surveyData.interest.surveyCategory1 != "none"){surveyData.interest.surveyCategory1 = increment}
        if(surveyData.interest.surveyCategory2 != "none"){surveyData.interest.surveyCategory2 = increment}

        //Eksekusi 
        if(newQuota == 0){
            await surveyRef.update(surveyData)
        }else{
            await surveyRef.update({quota: newQuota})
        }
    
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

router.get('/read/all', verifyToken, async(req, res) =>{
try {
    let responseArr = []

    const responseRef = DB.collection('response')
    const resSnap = await responseRef.get();

    resSnap.forEach( (response) => {
        responseArr.push(response.data())
    } )

    res.status(200).json({
        status : "Success",
        Message : "Success get all responses",
        responses : responseArr
    })
    
} catch (error) {
    console.error(error)
    res.status(500).json({
        message : error
    })
}
})

router.get('/read/surveys/:surveyID',verifyToken, async(req, res) =>{
    try {
        const surveyID = req.params.surveyID
        let responseArr = []
        console.log(typeof(responseArr))
        const responseRef = DB.collection('response')
        const resSnap = await responseRef.get();

        resSnap.forEach( (response) => {
            if(response.data().surveyID === surveyID) responseArr.push(response.data())
        } )

        if(Object.keys(responseArr).length===0){
            return res.status(404).json({
                status : "Failed",
                code: "response/surveyIDNotFound",
                Message : "SurveyID Invalid"
            })
        }
    
        res.status(200).json({
            status : "Success",
            Message : "Success get response by surveyID",
            responses : responseArr
        })
        
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read/uid',verifyToken, async(req, res) =>{
    try {
        let responseArr = []
    
        const responseRef = DB.collection('response')
        const resSnap = await responseRef.get();
    
        resSnap.forEach( (response) => {
            if(response.data().uid === req.body.uid) responseArr.push(response.data())
        } )
    
        res.status(200).json({
            status : "Success",
            Message : "Success get all responses by uid",
            responses : responseArr
        })
        
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get("/read/:responseID",verifyToken, async (req, res) => {
    try {
        const responseID = req.params.responseID
        const responseRef = DB.collection('response').doc(responseID)
        const resSnap = await responseRef.get();

        if(!resSnap.exists){
            return res.status(404).json({ 
                Status : "Failed",
                code : "response/notFound",
                "message" : "Response does not exist" 
            })
        }
        
        res.status(200).json({
            status : "Success",
            message : "Success get response",
            detail : resSnap.data()
        });


    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        }) 
    }
})



module.exports = router