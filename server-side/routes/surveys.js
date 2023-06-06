const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")

//Router
router.post('/create',verifyToken, async (req, res) => {
    try {
        const currect = Date.now();
        const dateTime = new Date(currect);
        const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
        
        const docID =  crypto.randomUUID() + date + dateTime.getMilliseconds();
        const surveyData = {
            "surveyID" : docID,
            "title" : req.body.title,
            "createdAt" : date,
            "uid" : req.body.uid,
            "questionNum" : req.body.questionNum,
            "quota" : req.body.quota,
            "reward" : req.body.reward,
            "category1" : req.body.category1,
            "category2" : req.body.category2,
            "finished" : false,
            "sell" : false,
            "questions" : req.body.questions
        }

        //filtering
        const reward = req.body.moneyDepo / req.body.quota
        if(reward < 5000){
            res.status(409).json({
                status : "Failed",
                code : "surveys/minimumReward",
                message : "Minimum rewards for each respondent is Rp5.000"
            })
        }
        else if(req.body.questionNum < 1){
            res.status(409).json({
                status : "Failed",
                code : "surveys/minimumQuestion",
                message : "Minimum question for survey is 1 and cannot be empty"
            })
        }
        else if(req.body.category1 === "null" && req.body.category2 === "null"){
            res.status(409).json({
                status : "Failed",
                code : "surveys/noCategory",
                message : "Please choose minimum 1 category"
            })
        }

        const docRef = await DB.collection("surveys").doc(docID);

        await docRef.set(surveyData);
        const response = {
            status : "Success",
            message : "Success adding survey",
            surveyID : docID
        }
        res.status(200).json(response)
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
})

router.get('/read/all',verifyToken, async (req, res) => {
    let responseArr = [];

    try {
        const surveysCollection = await DB.collection('surveys')
        const querySnapshot = await surveysCollection.get();
        querySnapshot.forEach( (survey) => {
            responseArr.push(survey.data())
        } )

        res.status(200).json({
            status : "Success",
            message : "Success get all surveys",
            surveys : responseArr
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read/:surveyID',verifyToken, async (req, res) => {
    try {
        const surveyID = req.params.surveyID
        const uid = req.body.uid
        const surveyRef = await DB.collection('surveys').doc(surveyID);
        const selectedSurvey = await surveyRef.get() 
    
        if(selectedSurvey.exists){
            const transactionRef = await DB.collection('transaction').where("uid", "==", uid).get()
            let owned = false
            transactionRef.forEach((trans) => {
                if(trans.data().surveyID == surveyID && trans.data().uid == uid) owned = true
            })

            let ownership = null

            if(selectedSurvey.uid == uid){ ownership = "creator" }
            else if(owned){ ownership = "buyer" }
            else{ ownership = "public" }
            res.status(200).json({
                status : "Success",
                message : "Success get survey",
                ownership : ownership,
                survey : selectedSurvey.data()
            });
        }
        else{
            res.status(404).json({ 
                status : "Failed",
                code : "surveys/notFound",
                "message" : "Survey does not exist" 
            })
        }
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/search/category/:category', verifyToken, async (req, res) => {
    const category = req.params.category || null

    let responseArr = [];
    try {
        const surveysCollection = await DB.collection('surveys')
        const querySnapshot = await surveysCollection.get();

        if(category === null) {
            res.status(409).json({
                status : "Failed",
                code : 'surveys/noCategory',
                message : "Please provide 1 category"
            })
        }
        querySnapshot.forEach( (survey) => {
            console.log(survey.data().category1)
            if(survey.data().category1 === category){

                responseArr.push(survey.data())
            }
        } )

        querySnapshot.forEach( (survey) => {
            if(survey.data().category2 === category){
                responseArr.push(survey.data())
            }
        })
        res.status(200).json({
            status : "Success",
            message : "Success get the spesific category surveys",
            surveys : responseArr
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.put('/update', verifyToken, async (req, res) => {
    
    const updatedData = req.body;

    try {
        const surveyRef = DB.collection('surveys').doc(updatedData.surveyID)
        const docSnapshot = await surveyRef.get()

        if(docSnapshot.exists){
            await surveyRef.set(updatedData, {merge : true})
            res.status(200).json({
                status: "Update Success",
                message : "Survey information updated"
            })
        }
        else{
            res.status(404).json({
                status : "Failed",
                code : 'surveys/notFound',
                message : "Survey not found"
            })
        }
        
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.delete('/delete', verifyToken, async (req, res) => {
    const surveyID = req.body.surveyID;

    try {
        const surveyRef = DB.collection('surveys').doc(surveyID)
        const docSnapshot = await surveyRef.get()

        if(docSnapshot.exists){
            await surveyRef.delete();
            res.status(200).json({
                status: "Success",
                message : "Delete survey success"
            })
        }
        else{
            res.status(404).json({
                status : "Failed",
                code : "surveys/notFound",
                message : "Survey not found"
            })
        }

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.put('/sell', verifyToken, async (req, res) => {
    const {surveyID, price, uid} = req.body

    try {
        //Check, is survey sellable
        const surveyRef = DB.collection('surveys').doc(surveyID)
        const survey = await surveyRef.get()
        
        if(uid != survey.uid){
            return res.status(403).json({
                status : "Failed",
                code : "surveys/notSurveyOwner",
                message : "Only the owner of the survey can sell the survey"
            })
        }
        if(!survey.finished){
            return res.status(409).json({
                status : "Failed",
                code : "surveys/notFinished",
                message : "Surveys must meet the quota of respondents to be sold (finished first)"
            })
        }
        if(price < 0){
            return res.status(409).json({
                status : "Failed",
                code : "surveys/invalidPrice",
                message : "Price must be 0 or more than 0 to be sold"
            })
        }
            
        // Yes
        await surveyRef.update({sell : true})
        
        const response = {
            status : "Success",
            message : "Success add sell status to the survey"
        }
        res.status(200).json(response)
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
})

router.post("/home", verifyToken, async(req,res)=>{
    const getRecommendationColla = null
    const getRecommendationContent = null

    const recommedationSurvey = []

    //randomize Surveys
    //const keys = Object.keys,

})





















router.post("/bulkInsert",verifyToken, async (req, res) => {
    const data = req.body

    try {
        

        //filtering
        const reward = req.body.moneyDepo / req.body.quota
        if(reward < 5000){
            res.status(409).json({
                status : "Failed",
                code : "surveys/minimumReward",
                message : "Minimum rewards for each respondent is Rp5.000"
            })
        }
        else if(req.body.questionNum < 1){
            res.status(409).json({
                status : "Failed",
                code : "surveys/minimumQuestion",
                message : "Minimum question for survey is 1 and cannot be empty"
            })
        }
        else if(req.body.category1 === "null" && req.body.category2 === "null"){
            res.status(409).json({
                status : "Failed",
                code : "surveys/noCategory",
                message : "Please choose minimum 1 category"
            })
        }

        for (let i = 0; i < 123; i++) {
            const currect = Date.now();
            const dateTime = new Date(currect);
            const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
            const docID =  crypto.randomUUID() + date + dateTime.getMilliseconds();
            const surveyData = {
                "surveyID" : docID,
                "title" : data[i]['survey'],
                "createdAt" : date,
                "uid" : req.body.uid,
                "questionNum" :5,
                "quota" : 100,
                "reward" : 2000,
                "category1" : data[i]['category_1'],
                "category2" : data[i]['category_2'],
                "finished" : false,
                "sell" : false,
                "price" : 0,
                "questions" : {
                    "1": {
                        "question": "Siapa yang lebih baik menjadi president ditahun 2024?",
                        "choiceNum": 3,
                        "type": "multiple",
                        "choices": {
                            "1": "Bukan saya donkk",
                            "2": "Yang pasti bukan saya hehe",
                            "3": "Bolehlah kalau saya dipilih"
                        }
                    },
                    "2": {
                        "question": "mengapa ibu puan maharani selalu menjadi kontroversi setiap ngomong sesuatu?",
                        "type": "essay"
                    },
                    "3": {
                        "question": "Siapa yang lebih baik menjadi president ditahun 2024?",
                        "choiceNum": 3,
                        "type": "multiple",
                        "choices": {
                            "1": "Bukan saya donkk",
                            "2": "Yang pasti bukan saya hehe",
                            "3": "Bolehlah kalau saya dipilih"
                        }
                    },
                    "4": {
                        "question": "Siapa yang lebih baik menjadi president ditahun 2024?",
                        "choiceNum": 3,
                        "type": "multiple",
                        "choices": {
                            "1": "Bukan saya donkk",
                            "2": "Yang pasti bukan saya hehe",
                            "3": "Bolehlah kalau saya dipilih"
                        }
                    },
                    "5": {
                        "question": "mengapa ibu puan maharani selalu menjadi kontroversi setiap ngomong sesuatu?",
                        "type": "essay"
                    }
                }
            }
            
            const docRef = await DB.collection("surveys").doc(docID);
            await docRef.set(surveyData);
        }
        
        const response = {
            status : "Success",
            message : "Success adding bulk survey",
        }
        res.status(200).json(response)
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
    
    
})

router.delete("/deleteAll", async (req, res) => {

    try {
        const docRef = await DB.collection("surveys").get()

        const deletePromises = docRef.docs.map((doc) => doc.ref.delete());

        // Wait for all delete operations to complete
        await Promise.all(deletePromises);
        const response = {
            status : "Success",
            message : "Success delete bulk survey",
        }
        res.status(200).json(response)
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
    
    
})

module.exports = router


