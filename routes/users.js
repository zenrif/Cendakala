const express = require("express")
const router  = express.Router()
const {DB, auth} = require('../config')

//Router
router.post('/create', async (req, res) => {
    try {
        const docID =  req.body.uid;
        const userData = {
            "name" : req.body.name,
            "gender" : req.body.gender,
            "birthday" : req.body.birthday,
            "job" : req.body.job,
            "interest" : req.body.interest,
            "history" : req.body.history,
            "balance" : req.body.balance
        }
        const docRef = await DB.collection("users").doc(docID);

        const docSnapshot = await docRef.get();

        if(docSnapshot.exists){
            res.json({
                uid : docID,
                status : "existed",
                message : "User is already existed in database"
            })
        }
        else{
            await docRef.set(userData);
            const response = {
                Message : "Success adding user data",
                uid : docID
            }
            res.json(response)
        }
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
})


router.get('/read/all', async (req, res) => {
    let responseArr = [];

    try {
        const usersCollection = await DB.collection('users')
        const querySnapshot = await usersCollection.get();
        querySnapshot.forEach( (user) => {
            responseArr.push(user.data())
        } )

        res.json(responseArr)
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read/:uid', async (req, res) => {
    try {
        const userRef = await DB.collection('users').doc(req.params.uid);
        const selectedUser = await userRef.get()
        if(selectedUser.exists){
            res.send(selectedUser.data());
        }
        else{
            res.send({ "Message" : "User does not exist" })
        }
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.put('/update/:uid', async (req, res) => {
    
    const updatedData = req.body;

    try {
        const userRef = DB.collection('users').doc(req.params.uid)
        const docSnapshot = await userRef.get()

        if(docSnapshot.exists){
            await userRef.set(updatedData, {merge : true})
            res.status(200).json({
                status: "Update Success",
                message : "User updated"
            })
        }
        else{
            res.status(404).json({
                status : "User Not Found"
            })
        }
        
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})


router.delete('/delete/:uid', async (req, res) => {
    const uid = req.params.uid;

    try {
        const userRef = DB.collection('users').doc(uid)
        const docSnapshot = await userRef.get()

        if(docSnapshot.exists){
            await userRef.delete();
            res.status(200).json({
                status: "Delete Success"
            })
        }
        else{
            res.status(404).json({
                status : "User Not Found"
            })
        }

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.post('/login', async(req, res) => {
    try {
        console.log('a')
        res.send('test')
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

// router.post('/verify', async(req, res) => {
//     try{
//         const token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFiYjI2MzY4YTNkMWExNDg1YmNhNTJiNGY4M2JkYjQ5YjY0ZWM2MmYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vY2VuZGFrYWxhIiwiYXVkIjoiY2VuZGFrYWxhIiwiYXV0aF90aW1lIjoxNjg0MzM0Mzc4LCJ1c2VyX2lkIjoicFJaMnoyckdYVFJxQ2wyaGFHbGZ6em4wT3lYMiIsInN1YiI6InBSWjJ6MnJHWFRScUNsMmhhR2xmenpuME95WDIiLCJpYXQiOjE2ODQzMzQzNzgsImV4cCI6MTY4NDMzNzk3OCwiZW1haWwiOiJzYW1wbGVAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInNhbXBsZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.N3vlozmOyjhK-yPaoMqTHi-LOWRkwdBNNr7WAoRe0h08I--3-jOcVKce18-jl5Fi3GBUzLy_AAWpctgHw6aOc0J-W2gWrqRSm2miaWnG_TUcgUy08XMtYy9o_acyP6kW6gOrubKqBvXxkgn-Mn3OXp9cjHMrJmR4USMKNuDN2HyP6Ux3M3OWL7oCNv2gIt2RmbVGhQ_nuuWf5pvk4OM6zsI03Z1A9bIRtvgQGiD1Vj4TODfda4M9ettSME3qZYf5bY027xcozWgRwvMGiqAkusX2J5ur6TiU4Ek3whFtS1e_tVBQIhzdIkCCaTyD2nwCuV2SQO1fuetY4Sn7Agl5e";
//         const decoded = await auth.verifyIdToken(token)

//         res.send(decoded)
//     } catch (error) {
//         console.error(error)
//         const spesificErr = error.code;

//         if(error.code === "auth/argument-error"){
//             res.status(403).json({
//                 status : "Unauthorized Accesss"
//             })
//         }
//         res.status(500).json({
//             message : error
//         })
//     }
    
// })


module.exports = router;

