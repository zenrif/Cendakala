const express = require("express")
const router  = express.Router()
const { DB } = require('../config')

//router
router.get('/response', async (req, res) => {
try {
    const dateTime = new Date(currect);
    const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
    const responseID =  crypto.randomUUID() + date + dateTime.getMilliseconds();

} catch (error) {
    
}
})

router.get('/response/:responseID', async(req, res) =>{
try {
    const sID = req.body.surveyID;
    const uID = req.body.uid;
    const aID = req.body.answer;

    surveyresponse = {
        "surveyID" : sID,
        "uid" : uID,
        "answer" : aID
    }
} catch (error) {
    
}
})