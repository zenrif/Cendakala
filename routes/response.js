const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const {docID} = require('./surveys')

//router
router.get('/response', async (req, res) => {
try {
    const sID = docID;
   // const uid = 
} catch (error) {
    
}
})