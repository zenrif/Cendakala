const express = require("express")
const router  = express.Router()
const { verifyToken } = require('../middleware/verifyToken')
const { DB } = require('../config')

//Router
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
                message : "Success get user",
                user : selectedUser.data()
            });
        }
        else{
            res.status(404).json({ 
                Status : "Failed",
                code : "users/notFound",
                "message" : "User does not exist" 
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

module.exports = router;

