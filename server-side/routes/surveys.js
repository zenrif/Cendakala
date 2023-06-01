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
            Message : "Success adding survey",
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
            Message : "Success get all surveys",
            surveys : responseArr
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read',verifyToken, async (req, res) => {
    try {
        const surveyRef = await DB.collection('surveys').doc(req.body.surveyID);
        const selectedSurvey = await surveyRef.get()
        if(selectedSurvey.exists){
            res.status(200).json({
                status : "Success",
                Message : "Success get survey",
                survey : selectedSurvey.data()
            });
        }
        else{
            res.status(404).json({ 
                status : "Failed",
                code : "surveys/notFound",
                "Message" : "Survey does not exist" 
            })
        }
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/search/category', verifyToken, async (req, res) => {
    const category = req.body.category || null

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
            Message : "Success get the spesific category surveys",
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

module.exports = router


