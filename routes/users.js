const express = require("express")
const router  = express.Router()
const { verifyToken } = require('../middleware/verifyToken')
const { DB } = require('../config')

//Router
router.post('/create', verifyToken, async (req, res) => {
    try {
        const docID =  req.body.uid;
        const userData = {
            "uid" : docID,
            "name" : req.body.name,
            "gender" : req.body.gender,
            "birthday" : req.body.birthday,
            "job" : req.body.job,
            "interest" : req.body.interest,
            "history" : req.body.history,
            "balance" : 0
        }
        const docRef = await DB.collection("users").doc(docID);

        const docSnapshot = await docRef.get();

        if(docSnapshot.exists){
            res.status(409).json({
                uid : docID,
                status : "Failed",
                code : "users/existed",
                message : "User is already existed in database"
            })
        }
        else{
            await docRef.set(userData);
            const response = {
                status : "Success",
                Message : "Success adding user data",
                uid : docID
            }
            res.status(200).json(response)
        }
    } catch (error) {
        console.error(error);
        res.status(500).send('Server error');
    }
})


router.get('/read/all', verifyToken, async (req, res) => {
    let responseArr = [];

    try {
        const usersCollection = await DB.collection('users')
        const querySnapshot = await usersCollection.get();
        querySnapshot.forEach( (user) => {
            responseArr.push(user.data())
        } )

        res.status(200).json({
            status : "Success",
            Message : "Success get all users",
            users : responseArr
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read', verifyToken, async (req, res) => {
    try {
        const userRef = await DB.collection('users').doc(req.body.uid);
        const selectedUser = await userRef.get()
        if(selectedUser.exists){
            res.status(200).json({
                status : "Success",
                Message : "Success get user",
                user : selectedUser.data()
            });
        }
        else{
            res.status(404).json({ 
                Status : "Failed",
                code : "users/notFound",
                "Message" : "User does not exist" 
            })
        }
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
        const userRef = DB.collection('users').doc(req.body.uid)
        const docSnapshot = await userRef.get()

        if(docSnapshot.exists){
            await userRef.set(updatedData, {merge : true})
            res.status(200).json({
                status: "Update Success",
                message : "User information updated"
            })
        }
        else{
            res.status(404).json({
                status : "Failed",
                code : "users/notFound",
                message : "User not found"
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
    const uid = req.body.uid;

    try {
        const userRef = DB.collection('users').doc(uid)
        const docSnapshot = await userRef.get()

        if(docSnapshot.exists){
            await userRef.delete();
            res.status(200).json({
                status: "Success",
                message : "Delete user success"
            })
        }
        else{
            res.status(404).json({
                status : "Failed",
                code : "users/notFound",
                message : "User not found"
            })
        }

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

// router.post('/login', async(req, res) => {
//     try {
//         console.log('a')
//         res.send('test')
//     } catch (error) {
//         console.error(error)
//         res.status(500).json({
//             message : error
//         })
//     }
// })

// router.post('/verify', verifyToken, async(req, res) => {
//     try{
//         res.json(req.body.uid)
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

