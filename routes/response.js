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

        const surveyRef = DB.collection('surveys').doc(req.body.surveyID)
        const docSnapshot = await surveyRef.get() 

        if(!docSnapshot.exist){
            return res.status(404).json({
                status : "Failed",
                code : 'surveys/notFound',
                message : "Survey not found"
            })
        }

        const surveyQuota = docSnapshot.data().quota



        res.status(200).json(surveyQuota)

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