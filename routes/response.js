const express = require("express")
const router  = express.Router()
const { DB } = require('../config')

//router
router.get('/response', async (req, res) => {
try {

} catch (error) {
    
}

router.get('/response/:responseID')
try {
    const sID = req.body.surveyID;
    const uID = req.body.uid;
    const aID = req.body.answer;
} catch (error) {
    
}
})