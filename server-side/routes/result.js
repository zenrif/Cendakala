const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")
const { FieldValue } = require("firebase-admin/firestore")

router.post('/result', verifyToken, async (req, res) => {
    try {
    
    } catch (error) {
        
    }
})