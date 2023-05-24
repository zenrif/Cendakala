const express = require("express")
const router  = express.Router()

//router
router.post('/response', async (req, res) => {
try {

    const dateTime = new Date(currect);
    const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
    const responseID =  crypto.randomUUID() + date + dateTime.getMilliseconds();
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