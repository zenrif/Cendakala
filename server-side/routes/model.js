const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const { verifyToken } = require("../middleware/verifyToken")
const axios = require("axios")

//Function
function randomize(array){
    for(let i = array.length - 1; i > 0 ; i--){
        const j = Math.floor(Math.random() * (i+1));
        [array[i], array[j]] = [array[j], array[i]]
    }
    return array
}

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
        allSur.forEach((survey) =>{
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
            if(check["3"] == "" && category1 == cat1 && category2 == cat3){
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
            if(check["6"] == "" && category1 == cat2 && category2 == cat3){
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
            if(check["8"] == "" && category1 == cat1 && category2 == "null"){
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
        })
    }
    else if(number == 5){
        allSur.forEach((survey) =>{
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
            if(check["2"] == "" && category1 == cat1 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["2"] = false
                counter++
            }

            //Pattern BC
            if(check["3"] == "" && category1 == cat2 && category2 == cat3){
                recommededSurvey[counter] = survey.surveyID
                check["3"] = false
                counter++
            }

            //Pattern AA
            if(check["4"] == "" && category1 == cat1 && category2 == "null"){
                recommededSurvey[counter] = survey.surveyID
                check["4"] = false
                counter++
            }
        })
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

async function getSurveyData(surveyIDs, db) {
    const docRefs = surveyIDs.map((id) => db.collection("surveys").doc(id));
    const snapshots = await Promise.all(docRefs.map((docRef) => docRef.get()));
    const surveyData = [];
    snapshots.forEach((snap) => {
        surveyData.push(snap.data());
    });
    return surveyData;
}
  
async function getSurveyName(surveyID, db) {
    const surveyRef = db.collection("surveys").doc(surveyID);
    const query = await surveyRef.get();
    return query.data().title;
}

//router
router.post("/recommedation/home", verifyToken, async (req,res) => {
    try {
        // Get lastest surveys by uid
        const uid = req.body.uid
        let latestData = null
        const responseRef = await DB.collection('response').where("uid","==",uid).orderBy("timestamp", "desc").limit(1).get()

        responseRef.forEach((doc) => {
            const docData = doc.data();
            latestData = docData
        });

        //Get Filtered Survey
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


        //If user is new and never response any survey
        if(latestData == null || latestData == undefined || latestData == ''){
            const reqUID = {uid : uid}
            // Get Recommendation with collaborative-filtering
            const colRecom = await axios.post('https://machine-learning-v4-5ojaxkbdyq-et.a.run.app/collaborative', reqUID); // Insert your request domain

            //Get recommended category
            const kat1 = colRecom.data['0']
            const kat2 = colRecom.data['1']
            const kat3 = colRecom.data['2']

            //Get Recommedation Survey ID using pattern
            let arrRecom = getRecommendedSurvey(kat1,kat2,kat3,allSur,10)
            const arrRecoms = Object.values(arrRecom)
            const surveyData = await getSurveyData(arrRecoms, DB)

            const randomSurvey = randomize(surveyData)

            return res.status(200).json({
                status : "Success",
                message : "Success get recommendation",
                surveys : randomSurvey
            });
        }
        //If user already filled out a survey
        else{
            const reqUID = {uid : uid}
            // Get Recommendation with collaborative-filtering
            const colRecom = await axios.post('https://machine-learning-v4-5ojaxkbdyq-et.a.run.app/collaborative', reqUID); // Insert your request domain

            //Get recommended category
            const kat1 = colRecom.data['0']
            const kat2 = colRecom.data['1']
            const kat3 = colRecom.data['2']

            //Get Recommedation Survey ID using pattern
            let arrRecom = getRecommendedSurvey(kat1,kat2,kat3,allSur,5)

            //Get Lastest Survey ID that User response
            const surveyName = await getSurveyName(latestData.surveyID, DB)

            const data ={
                survey_title:surveyName,
                surveys: surveys
            }

            //Get recommendation survey by content filtering
            const getContentRecom = await axios.post('https://machine-learning-v4-5ojaxkbdyq-et.a.run.app/content', data); // Insert your request domain
            const allSurveyRecom = []

            for(let i=0; i<10; i++){
                if(i <= 4){
                    allSurveyRecom.push(arrRecom[i])
                }
                else{
                    const a = i-5
                    allSurveyRecom.push(getContentRecom.data[a])
                }
            }
            
            let surveyTemp = await getSurveyData(allSurveyRecom, DB)
            const randomizeRecomSurveys = randomize(surveyTemp)

            return res.status(200).json({
                status : "Success",
                message : "Success get recommendation",
                surveys : randomizeRecomSurveys
            });
        }
    } catch (error) {
        console.log(error)
        res.send(error)
    }
    
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