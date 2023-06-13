const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")
const axios = require('axios')


//Function

// Randomize all survey inside array
function randomize(array){
    for(let i = array.length - 1; i > 0 ; i--){
        const j = Math.floor(Math.random() * (i+1));
        [array[i], array[j]] = [array[j], array[i]]
    }
    return array
}

// Get recommendation using special pattern from category
function getRecommendedSurvey(cat1, cat2, cat3, allSur, number = 10){
    let recommededSurvey = {}
    let counter = 0
    let check = {
        "0": "",
        "1": "",
        "2":"",
        "3":"",
        "4":"",
        "5":"",
        "6":"",
        "7":"",
        "8":"",
        "9":""
    }

    if(number == 10){

        for(let i = 0 ; i < allSur.length ; i++){
            const survey = allSur[i]
            const category1 = survey.category1
            const category2 = survey.category2
    
            //Pattern AB
            if(check["0"] == "" && category1 == cat1 && category2 == cat2){
                recommededSurvey[counter] = survey.surveyID
                check["0"] = survey.surveyID
                counter++
            }
            //Pattern AB 2
            else if(check["1"] == "" && category1 == cat1 && category2 == cat2){
                recommededSurvey[counter] = survey.surveyID
                check["1"] = false
                counter++
            }
            //Pattern AB 3
            else if(check["2"] == "" && category1 == cat1 && category2 == cat2){
                recommededSurvey[counter] = survey.surveyID
                check["2"] = false
                counter++
            }
            //Pattern AC
            else if(check["3"] == "" && category1 == cat1 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["3"] = false
                counter++
            }
            //Pattern AC 2
            else if(check["4"] == "" && category1 == cat1 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["4"] = false
                counter++
            }
            //Pattern AC 3
            else if(check["5"] == "" && category1 == cat1 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["5"] = false
                counter++
            }
            //Pattern BC
            else if(check["6"] == "" && category1 == cat2 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["6"] = false
                counter++
            }
            //Pattern AC
            else if(check["7"] == "" && category1 == cat2 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["7"] = false
                counter++
            }
            //Pattern AA
            else if(check["8"] == "" && category1 == cat1 && category2 == "null"){
                recommededSurvey[counter] = survey.surveyID
                check["8"] = false
                counter++
            }
            //Pattern AA 2
            else if(check["9"] == "" && category1 == cat1 && category2 == "null"){
                recommededSurvey[counter] = survey.surveyID
                check["9"] = false
                counter++
            }

            if(counter == 10) break;
        }
    }
    else if(number == 5){
        for(let i = 0 ; i < allSur.length ; i++){
            const survey = allSur[i]
            const category1 = survey.category1
            const category2 = survey.category2
        
            //Pattern AB
            if(check["0"] == "" && category1 == cat1 && category2 == cat2){
                recommededSurvey[counter] = survey.surveyID
                check["0"] = survey.surveyID
                counter++
            }
            //Pattern AB 2
            else if(check["1"] == "" && category1 == cat1 && category2 == cat2){
                recommededSurvey[counter] = survey.surveyID
                check["1"] = false
                counter++
            }
            //Pattern AC
            else if(check["2"] == "" && category1 == cat1 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["2"] = false
                counter++
            }
            //Pattern BC
            else if(check["3"] == "" && category1 == cat2 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["3"] = false
                counter++
            }
            //Pattern AA
            else if(check["4"] == "" && category1 == cat1 && category2 == "null"){
                recommededSurvey[counter] = survey.surveyID
                check["4"] = false
                counter++
            }

            if(counter == 5) break
        }
    }
    if(counter != number){
        for(let i = 0; i < allSur.length; i++){
            if(allSur[i].category1 == cat1 || allSur[i].category1 == cat2 || allSur[i].category1 == cat3){
                const id = allSur[i].surveyID 
                if( id != check["0"] && id != check["1"] && id != check["2"] && id != check["3"] && id != check["4"] && id != check["5"] && id != check["6"] && id != check["7"] && id != check["8"] && id != check["9"]){
                    recommededSurvey[counter] = id
                    counter++

                    if(counter == number) break;
                }
            }
        }
    }

    return recommededSurvey
}

// Get selected surveys data from database
async function getSurveyData(surveyIDs, db) {
    const docRefs = surveyIDs.map((id) => db.collection("surveys").doc(id));
    const snapshots = await Promise.all(docRefs.map((docRef) => docRef.get()));
    const surveyData = [];
    snapshots.forEach((snap) => {
        surveyData.push(snap.data());
    });
    return surveyData;
}
 
// Get survey title from database
async function getSurveyName(surveyID, db) {
    const surveyRef = await db.collection("surveys").doc(surveyID).get();
    return surveyRef.data().title;
}

//Router
router.post('/create',verifyToken, async (req, res) => {
    try {
        // Get current date
        const currect = Date.now();
        const dateTime = new Date(currect);
        const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
        
        // Generate custom docID
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
        // Get all current user surveys
        const surveysCollection = await DB.collection('surveys').where("uid", "==", req.body.uid)
        const querySnapshot = await surveysCollection.get();
        querySnapshot.forEach( (survey) => {
            responseArr.push(survey.data())
        } )

        res.status(200).json({
            status : "Success",
            message : "Success get all user surveys",
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
        // Get Survey
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

            // Filter ownership of the survey
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
        //Get Surveys
        const surveysCollection = await DB.collection('surveys')
        const querySnapshot = await surveysCollection.get();

        if(category === null) {
            res.status(409).json({
                status : "Failed",
                code : 'surveys/noCategory',
                message : "Please provide 1 category"
            })
        }

        //Filter surveys by category
        querySnapshot.forEach( (survey) => {
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
        // Get survey by surveyID
        const surveyRef = DB.collection('surveys').doc(updatedData.surveyID)
        const docSnapshot = await surveyRef.get()

        if(docSnapshot.exists){
            // Update survey data
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
        // Get survey by surveyID
        const surveyRef = DB.collection('surveys').doc(surveyID)
        const docSnapshot = await surveyRef.get()

        if(docSnapshot.exists){
            // Delete the survey
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
        //Get survey by surveyID and Check, is survey sellable
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

router.post("/recommedation/home", verifyToken, async (req, res) => {
    try {
      // Get latest surveys by uid
      const uid = req.body.uid;
      let latestData = null;

      // Change the url with your machine learning backend url (Ex. https://this-is-your-url/collaborative)
      const urlCollaborative = 'https://machine-learning-api-search-5ojaxkbdyq-et.a.run.app/collaborative'

      // Change the url with your machine learning backend url (Ex. https://this-is-your-url/content)
      const urlContent = 'https://machine-learning-api-search-5ojaxkbdyq-et.a.run.app/content'

      // Get lastest user response
      const responseRef = await DB.collection('response').where("uid", "==", uid).orderBy("timestamp", "desc").limit(1).get();
  
      responseRef.forEach((doc) => {
        const docData = doc.data();
        latestData = docData;
      });
  
      // Get Filtered Survey
      const filteredSurveysSnapshot = await DB.collection("surveys").where("quota", ">", 0).get();
      const surveys = [];
      const allSur = [];
  
      filteredSurveysSnapshot.forEach((survey) => {
        const surveyData = survey.data();
        surveys.push({
          survey: surveyData.title,
          surveyID: surveyData.surveyID,
        });
        allSur.push({
          survey: surveyData.title,
          surveyID: surveyData.surveyID,
          category1: surveyData.category1,
          category2: surveyData.category2,
        });
      });
  
      // If user is new and never responded to any survey
      if (latestData == null || latestData == undefined || latestData == '') {
        const reqUID = { uid: uid };
        
        // Get Recommendation with collaborative-filtering
        const colRecom = await axios.post(urlCollaborative, reqUID); // Insert your request domain

        // Get recommended category
        const kat1 = colRecom.data['0'];
        const kat2 = colRecom.data['1'];
        const kat3 = colRecom.data['2'];
  
        // Get Recommendation Survey ID using pattern
        let arrRecom = getRecommendedSurvey(kat1, kat2, kat3, allSur, 10);
        const arrRecoms = Object.values(arrRecom);
        const surveyData = await getSurveyData(arrRecoms, DB);
  
        const randomSurvey = randomize(surveyData);
  
        return res.status(200).json({
          status: "Success",
          message: "Success get recommendation",
          surveys: randomSurvey
        });
      }
      // If user already filled out a survey
      else {
        const reqUID = { uid: uid };
        
        // Get Recommendation with collaborative-filtering
        const colRecom = await axios.post(urlCollaborative, reqUID); 

        // Get recommended category
        const kat1 = colRecom.data['0'];
        const kat2 = colRecom.data['1'];
        const kat3 = colRecom.data['2'];
  
        // Get Recommendation Survey ID using pattern
        let arrRecom = getRecommendedSurvey(kat1, kat2, kat3, allSur, 5);
  
        // Get Lastest Survey ID that User responded to
        const surveyName = await getSurveyName(latestData.surveyID, DB);
  
        const data = {
          survey_title: surveyName,
          surveys: surveys
        };
  
        // Get recommendation survey by content filtering
        const getContentRecom = await axios.post(urlContent, data); // Insert your request domain
        const allSurveyRecom = [];
  
        for (let i = 0; i < 10; i++) {
          if (i <= 4) {
            allSurveyRecom.push(arrRecom[i]);
          } else {
            const a = i - 5;
            allSurveyRecom.push(getContentRecom.data[a]);
          }
        }
  
        let surveyTemp = await getSurveyData(allSurveyRecom, DB);
        const randomizeRecomSurveys = randomize(surveyTemp);
  
        return res.status(200).json({
          status: "Success",
          message: "Success get recommendation",
          surveys: randomizeRecomSurveys
        });
      }
    } catch (error) {
      console.error(error);
      res.send(error);
    }
});

router.get("/purchaseAble", verifyToken, async(req,res)=>{
    try {
        // Get sellAble surveys
        const surveyRef = DB.collection("surveys").where("sell", "==", true).limit(10)
        const selectedSurvey = await surveyRef.get()
        
        let data = []
        selectedSurvey.forEach((doc) => {
            const docData = doc.data();
            data.push(docData)
        });
        res.status(200).json({
            status : "Success",
            message : "Success get survey",
            surveys : data 
        });

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }

})

router.get('/search/:input', verifyToken, async (req, res) => {
    const input = req.params.input || null

    let responseArr = [];
    try {
        // Change the url with your machine learning backend url (Ex. https://this-is-your-url/search/)
        const urlSearch = 'https://machine-learning-api-search-5ojaxkbdyq-et.a.run.app/search/' + input
        const survey_list = await axios.get(urlSearch);
        surveyData = survey_list.data.data.results

        surveyData.forEach((survey) => {
            responseArr.push(survey)
        })
        res.status(200).json({
            status : "Success",
            message : "Success get the surveys",
            surveys : responseArr
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
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


