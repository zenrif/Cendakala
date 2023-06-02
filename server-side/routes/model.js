const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")

//router
router.post("/recommedation/content", verifyToken, async (req,res) => {

    // Get lastest surveys by uid
    try {
        const uid = req.body.uid
        let latestData = null
        const responseRef = await DB.collection('response').where("uid","==",uid).orderBy("timestamp", "desc").limit(1).get()

        responseRef.forEach((doc) => {
            const docData = doc.data();
            latestData = docData
        });

        if(latestData == null || latestData == undefined || latestData == ''){
            return res.status(404).json({
                status : "Failed",
                code : '',
                message : ""
            })
        }

        const surveysRef = DB.collection("surveys").doc(latestData.surveyID)
        const query = await surveysRef.get()
        const surveyName = query.data().title

        const filteredSurveys = DB.collection("surveys")
        const qSnap = await filteredSurveys.get()
        let surveys = []
        qSnap.forEach((survey) => {
            if(survey.data().quota > 0) {
                surveys.push({
                    survey:survey.data().title,
                    surveyID:survey.data().surveyID
                })
            }
        })

        res.json({
            survey_title:surveyName,
            surveys: surveys
        })

    } catch (error) {
        console.log(error)
        res.send(error)
    }
    
})


router.post("/recommedation/collaborative", async (req,res) => {
    const historyIndex = {
        0 : "Kesehatan",
        1 : "Pendidikan",
        2 : "Hukum",
        3 : "Keuangan",
        4 : "Pariwisata",
        5 : "Sosial dan Kemanusiaan",
        6 : "Lingkungan dan Konversi",
        7 : "Teknologi Informasi dan Komunikasi",
        8 : "Olahraga dan Rekreasi",
        9 : "Seni dan Budaya",
        10 : "Agama dan Kepercayaan",
        11 : "Bisnis dan Industri",
        12 : "Politik dan Pemerintahan",
        13 : "Transportasi dan Logistik",
        14 : "Pertanian dan Logistik"
    }

    // Get filtered users user_id, category, history for model
    let filteredUsers = []

    const usersRef = await DB.collection('users').get()

    usersRef.forEach((user) => {
        const userData = user.data();
        for(i =0; i < Object.keys(userData.history).length; i++){
            if(userData.history[historyIndex[i]] > 0) filteredUsers.push({
                user_id : userData.uid,
                category : historyIndex[i],
                history : userData.history[historyIndex[i]],
            })
        }
    });

    //Send data
    res.json({
        user_ui : req.body.uid,
        dummy: filteredUsers
    })
})

//fix
router.get("/recommedation/train/collaborative", async (req,res) => {
    const historyIndex = {
        0 : "Kesehatan",
        1 : "Pendidikan",
        2 : "Hukum",
        3 : "Keuangan",
        4 : "Pariwisata",
        5 : "Sosial dan Kemanusiaan",
        6 : "Lingkungan dan Konversi",
        7 : "Teknologi Informasi dan Komunikasi",
        8 : "Olahraga dan Rekreasi",
        9 : "Seni dan Budaya",
        10 : "Agama dan Kepercayaan",
        11 : "Bisnis dan Industri",
        12 : "Politik dan Pemerintahan",
        13 : "Transportasi dan Logistik",
        14 : "Pertanian dan Logistik"
    }

    // Get filtered users user_id, category, history for model
    let user_id = {}
    let category = {}
    let history = {}

    const usersRef = await DB.collection('users').get()
    let count = 0
    usersRef.forEach((user) => {
        const userData = user.data();
        for(i =0; i < Object.keys(userData.history).length; i++){
            if(userData.history[historyIndex[i]] > 0) {
                user_id[count] = userData.uid
                category[count] = historyIndex[i]
                history[count] = userData.history[historyIndex[i]]
                count++
            }
        }
    });

    //Send data
    res.json({
        user_id : user_id,
        category : category,
        history : history
    })
})

module.exports = router