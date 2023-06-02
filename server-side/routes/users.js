const express = require("express")
const router  = express.Router()
const { verifyToken } = require('../middleware/verifyToken')
const { DB } = require('../config')
const  axios = require("axios")

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

router.post("/bulk", async(req, res)=>{
    const datas = req.body

    try {
        for (let index = 0; index < 50; index++) {
            const arr = datas[index]
            await axios.post('https://client-side-dot-cendakala.et.r.appspot.com/authentication/signup', arr);
        }
    } catch (error) {
        console.log(error)
        res.send(error)
    }

    res.send("yes")
})















function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
  
  // Object with the keys and initial values
  
  
  // Function to update the "history" object with random values
function updateHistoryObject() {
    const updatedHistory = {};
    const historyData = {
        "Kesehatan": 0,
        "Pendidikan": 0,
        "Hukum": 0,
        "Pariwisata": 0,
        "Sosial dan Kemanusiaan": 0,
        "Lingkungan dan Konversi": 0,
        "Teknologi informasi dan Komunikasi": 0,
        "Olahraga dan Rekreasi": 0,
        "Seni dan Budaya": 0,
        "Agama dan Kepercayaan": 0,
        "Bisnis dan Industri": 0,
        "Politik dan Pemerintahan": 0,
        "Transportasi dan Logistik": 0,
        "Pertanian dan Logistik": 0
    };

    for (const key in historyData) {
      updatedHistory[key] = getRandomInt(0, 20);
    }
  
    return updatedHistory;
}

router.put("/userHistory", async (req, res)=>{

    try {
        const usersCollection = await DB.collection('users')
        const querySnapshot = await usersCollection.get();
        const batch = DB.batch()

        querySnapshot.forEach( (user) => {
            const users = usersCollection.doc(user.data().uid)
            console.log()
            users.update( {history: updateHistoryObject()})
            
        })

        res.status(200).json({
            status : "Success",
            Message : "Success update all users history",
        })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})


module.exports = router;

