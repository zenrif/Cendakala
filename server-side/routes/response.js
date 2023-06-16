const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")
const { FieldValue } = require("firebase-admin/firestore")
const excelJs = require("exceljs")

//router
router.post('/create', verifyToken, async (req, res) => {
    
    try {
        const {answers, surveyID, reward} = req.body;
        const uid = req.body.uid;

        //Get Date and generate custom responseID
        const current = Date.now()
        const dateTime = new Date(current);
        const date = dateTime.getDate() + "-" + dateTime.getMonth() + "-" + dateTime.getFullYear();
        const responseID =  crypto.randomUUID() + date + dateTime.getMilliseconds();

        const responseData = {
            surveyID : surveyID,
            uid : uid,
            answers : answers,
            responseID : responseID,
            reward : reward,
            timestamp : FieldValue.serverTimestamp()
        }
        
        //Get survey by surveyID
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

        //Get User by uid
        const userRef = DB.collection('users').doc(req.body.uid)
        const docSnapshotU = await userRef.get()

        if(!docSnapshotU.exists){
            return res.status(404).json({
                status : "Failed",
                code : "users/notFound",
                message : "User not found"
            })
        }

        // Get balance
        const userBalance = docSnapshotU.data().balance;
        const newBalance = userBalance + req.body.reward;
        
        //Get all response
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

        if(surveyData.interest.surveyCategory1 != "null" || surveyData.interest.surveyCategory1 != null ){surveyData.interest.surveyCategory1 = increment}
        if(surveyData.interest.surveyCategory2 != "null" || surveyData.interest.surveyCategory2 != null){surveyData.interest.surveyCategory2 = increment}

        // Update 
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

    // Get current user responses
    const responseRef = DB.collection('response').where("uid", "==", req.body.uid)
    const resSnap = await responseRef.get();

    resSnap.forEach( (response) => {
        responseArr.push(response.data())
    })

    let surveys = []

    const responseTitle = await DB.collection('surveys').get()

    let count = 0


    responseTitle.forEach((resp) => {
        surveys.push(resp.data())
    })

    responseArr.forEach((respo)=>{
        const survey = surveys.find( survey => {
            return survey.surveyID == respo.surveyID
        })
        const surveyTitle = survey.title 
        respo["title"] = surveyTitle
    })

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

        // Get responses by surveyID
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
    
        // Get responses by uid
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

        // Get response by responseID
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

router.get('/download/:surveyID', async (req, res) => {
    const surveyID = req.params.surveyID
    const data = []
    const users = []

    try {
        //Check response is already saved or no
        const check = await DB.collection('download').doc(surveyID).get()

        if(check.exists){
            // Excel create
            let workbook = new excelJs.Workbook();
            const sheet = workbook.addWorksheet("answers")
            let answers = check.data().data
            let questions = check.data().questions
            let title = check.data().title

            //Columns
            sheet.columns = [
                {header : "number", key: "number", width: 25},
                {header : "name", key: "name", width: 25},
                {header : "gender", key: "gender", width: 25},
                {header : "job", key: "job", width: 25},
                {header : "type", key: "type", width: 25},
                {header : "answer", key: "answer", width: 100}
            ]

            //Rows
            await answers.forEach((ans)=>{
                sheet.addRow({
                    number : ans.number,
                    name : ans.name,
                    gender : ans.gender,
                    job : ans.job,
                    type : ans.type,
                    answer : ans.answer
                })
            })

            // Questions
            const sheet2 = workbook.addWorksheet("questions")

            //Columns
            sheet2.columns = [
                {header : "number", key: "number", width: 25},
                {header : "question", key: "question", width: 100},
                {header : "type", key: "type", width: 25},
                {header : "choices", key: "choices", width: 100}
            ]

            let row = 2;

            for (const key in questions) {
                if (questions.hasOwnProperty(key)) {
                    const question = questions[key];
                    sheet2.getCell(`A${row}`).value = key;
                    sheet2.getCell(`B${row}`).value = question.question;
                    sheet2.getCell(`C${row}`).value = question.type;
                    sheet2.getCell(`D${row}`).value = '';

                    if (question.choices) {
                    const choiceKeys = Object.keys(question.choices);
                    const numChoices = Math.min(choiceKeys.length, 4);

                    for (let i = 0; i < numChoices; i++) {
                        const choiceKey = choiceKeys[i];
                        const choice = question.choices[choiceKey];
                        sheet2.getCell(`D${row}`).value += `Choice ${choiceKey}: ${choice}\n`;
                    }
                }

                row++;
            }
            }

            res.setHeader(
                "Content-Type",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            )

            res.setHeader(
                "Content-Disposition",
                "attachment;filename=" + title + ".xlsx"
            )

            return workbook.xlsx.write(res)

        }
        // Get survey questions
        const surveySnap = await DB.collection('surveys').doc(surveyID).get()
        const surveyQuest = surveySnap.data().questions

        // Get survey responses
        const resSnap = await DB.collection('response').where("surveyID", "==", surveyID).get()

        // Get users data
        const userSnap = await DB.collection('users').get()
        userSnap.forEach((user) => {
            users.push(user.data())
        })

        resSnap.forEach((resp) => {
            const result = users.find(obj => obj.uid == resp.data().uid)
            const respData = resp.data()
            const ansArr = respData.answers
            for (let index = 1; index < Object.keys(ansArr).length +1; index++) {
                
                if(ansArr[index].type == "multiple"){
                    const merge = ansArr[index].choice + "." + ansArr[index].answer
                    const tmp = {
                        number: index,
                        name : result.name,
                        gender : result.gender,
                        job : result.job,
                        type : ansArr[index].type,
                        answer : merge
                    }
                    data.push(tmp)
                }
                else{

                    const tmp = {
                        number: index,
                        name : result.name,
                        gender : result.gender,
                        job : result.job,
                        type : ansArr[index].type,
                        answer : ansArr[index].answer
                        
                    }
                    data.push(tmp)
                }
            }
            
        })
        const template = {
            title : surveySnap.data().title,
            data : data,
            questions : surveyQuest
        }

        await DB.collection('download').doc(surveyID).set(template);
        
        // Excel create
        let workbook = new excelJs.Workbook();
        const sheet = workbook.addWorksheet("answers")
        let answers = data
        let questions = surveySnap.data().questions
        let title = surveySnap.data().title

        //Columns
        sheet.columns = [
            {header : "number", key: "number", width: 25},
            {header : "name", key: "name", width: 25},
            {header : "gender", key: "gender", width: 25},
            {header : "job", key: "job", width: 25},
            {header : "type", key: "type", width: 25},
            {header : "answer", key: "answer", width: 100}
        ]

        //Rows
        await answers.forEach((ans)=>{
            sheet.addRow({
                number : ans.number,
                name : ans.name,
                gender : ans.gender,
                job : ans.job,
                type : ans.type,
                answer : ans.answer
            })
        })

        // Questions
        const sheet2 = workbook.addWorksheet("questions")

        //Columns
        sheet2.columns = [
            {header : "number", key: "number", width: 25},
            {header : "question", key: "question", width: 100},
            {header : "type", key: "type", width: 25},
            {header : "choices", key: "choices", width: 100}
        ]

        let row = 2;

        for (const key in questions) {
            if (questions.hasOwnProperty(key)) {
                const question = questions[key];
                sheet2.getCell(`A${row}`).value = key;
                sheet2.getCell(`B${row}`).value = question.question;
                sheet2.getCell(`C${row}`).value = question.type;
                sheet2.getCell(`D${row}`).value = '';

                if (question.choices) {
                const choiceKeys = Object.keys(question.choices);
                const numChoices = Math.min(choiceKeys.length, 4);

                for (let i = 0; i < numChoices; i++) {
                    const choiceKey = choiceKeys[i];
                    const choice = question.choices[choiceKey];
                    sheet2.getCell(`D${row}`).value += `Choice ${choiceKey}: ${choice}\n`;
                }
            }

            row++;
        }
        }

        res.setHeader(
            "Content-Type",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )

        res.setHeader(
            "Content-Disposition",
            "attachment;filename=" + title + ".xlsx"
        )

        return workbook.xlsx.write(res)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }

})

module.exports = router