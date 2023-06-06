const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")

const month = {
    1:"Jan",
    2:"Feb",
    3:"Mar",
    4:"Apr",
    5:"May",
    6:"Jun",
    7:"Jul",
    8:"Aug",
    9:"Sep",
    10:"Oct",
    11:"Nov",
    12:"Dec"
}

//router
router.post('/topup', verifyToken, async (req, res) => {
    
    try {
        const {total, method} = req.body;
        const uid = req.body.uid;

        if(total < 10000){
            return res.status(409).json({
                status : "Failed",
                code: "transaction/minimumTotal",
                message : "Minimum amount for top up is Rp10,000"
            })
        }
        const current = Date.now()
        const dateTime = new Date(current);
        const date = dateTime.getDate() + " " + month[dateTime.getMonth().toString()] + " " + dateTime.getFullYear();
        const transactionID =  crypto.randomUUID() + dateTime.getMilliseconds();

        const transactionRef = DB.collection('transaction').doc(transactionID)
        const userRef = DB.collection('users').doc(uid)
        const userBalance = (await userRef.get()).data().balance
        const newBalance = userBalance + total


        const transactionData = {
            type : "topUp",
            uid : uid,
            total : total,
            date : date,
            method : method,
            transactionID : transactionID
        }

        await userRef.update({balance : newBalance})
        await transactionRef.set(transactionData)
        
        const response = {
            status : "Success",
            Message : "Success TopUp",
            transactionID : transactionID
        }
        res.status(200).json(response)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.post('/withdrawal', verifyToken, async (req, res) => {
    
    try {
        const {total, method} = req.body;
        const uid = req.body.uid;
    
        const userRef = DB.collection('users').doc(uid)
        const userBalance = (await userRef.get()).data().balance
        const newBalance = userBalance - total
        if(userBalance < total){
            return res.status(409).json({
                status : "Failed",
                code: "transaction/notEnoughBalance",
                message : `Your balance is not enough to make a withdrawal`
            })
        }
        else if(total < 100000){
            return res.status(409).json({
                status : "Failed",
                code: "transaction/minimumWithdrawal",
                message : `The minimum withdrawal balance is Rp100,000 `
            })
        }

        const current = Date.now()
        const dateTime = new Date(current);
        const date = dateTime.getDate() + " " + month[dateTime.getMonth().toString()] + " " + dateTime.getFullYear();
        const transactionID =  crypto.randomUUID() + dateTime.getMilliseconds();

        const transactionRef = DB.collection('transaction').doc(transactionID)
        
        const transactionData = {
            type : "withdrawal",
            uid : uid,
            total : total,
            date : date,
            method : method,
            transactionID : transactionID
        }

        await userRef.update({balance : newBalance})
        await transactionRef.set(transactionData)
        
        const response = {
            status : "Success",
            Message : "Success Withdrawal",
            transactionID : transactionID
        }
        res.status(200).json(response)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.get('/read', verifyToken, async (req, res) => {
    let history = []

    try {
        const uid = req.body.uid;

        const userRef = DB.collection('users').doc(uid)
        const userBalance = (await userRef.get()).data().balance


        const transactionRef = DB.collection('transaction')
        const querySnapshot = await transactionRef.get()

        querySnapshot.forEach( (transactionData) => {
            if(transactionData.data().uid === uid && transactionData.data().type === "topUp" || transactionData.data().type === "withdrawal") history.push(transactionData.data())
        })

        if(history.length == 0){
            history = "empty"
        }
        
        const response = {
            status : "Success",
            Message : "Success Withdrawal",
            balance : userBalance,
            history : history
        }
        res.status(200).json(response)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})

router.post('/buy', verifyToken, async (req, res) => {
    
    try {
        const {surveyID, price} = req.body;
        const uid = req.body.uid;
    
        const userRef = DB.collection('users').doc(uid)
        const userBalance = (await userRef.get()).data().balance
        const transaction = DB.collection('transaction')
        const allTransactions = await transaction.get()
        const newBalance = userBalance - price

        // Owned checker
        let alreadyPurchased = false

        allTransactions.forEach((trans) => {
            if(trans.data().surveyID == surveyID && trans.data().uid == uid){
                alreadyPurchased = true
            }
        })

        if(alreadyPurchased){
            return res.status(409).json({
                status : "Failed",
                code: "transaction/alreadyPurchased",
                message : `You have purchased this survey`
            })
        }
        if(userBalance < price){
            return res.status(409).json({
                status : "Failed",
                code: "transaction/notEnoughBalance",
                message : `Your balance is not enough to make a purchase`
            })
        }

        const current = Date.now()
        const dateTime = new Date(current);
        const date = dateTime.getDate() + " " + month[dateTime.getMonth().toString()] + " " + dateTime.getFullYear();
        const transactionID =  crypto.randomUUID() + dateTime.getMilliseconds();

        const transactionRef = transaction.doc(transactionID)
        
        const transactionData = {
            type : "buySurvey",
            uid : uid,
            total : price,
            date : date,
            method : "wallet",
            transactionID : transactionID
        }

        

        // Add money to seller
        const surveyRef = await DB.collection('surveys').doc(surveyID).get()
        const sellerUID = surveyRef.uid
        const sellerRef = await DB.collection('users').doc(sellerUID).get()
        const newSellerBalance = sellerRef.balance + price



        await userRef.update({
            balance : newBalance
        })

        await sellerRef.update({
            balance : newSellerBalance
        })
        await transactionRef.set(transactionData)
        
        const response = {
            status : "Success",
            Message : "Success buy survey",
            transactionID : transactionID
        }
        res.status(200).json(response)

    } catch (error) {
        console.error(error)
        res.status(500).json({
            message : error
        })
    }
})


module.exports = router