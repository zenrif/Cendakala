const express = require("express")
const router  = express.Router()
const { DB } = require('../config')
const crypto = require('crypto')
const { verifyToken } = require("../middleware/verifyToken")
const { FieldValue } = require("firebase-admin/firestore")
const { Parser } = require('json2csv');
const fs = require('fs');

router.get('/results/download', async (req, res) => {
    const surveyId = req.query.surveyId; // ID survei yang akan diunduh  
    try {
        // Mengambil dokumen survei berdasarkan ID
        const surveyRef = db.collection('surveys').doc(surveyId);
        const surveyDoc = await surveyRef.get();
    
        if (!surveyDoc.exists) {
          return res.status(404).send('Survey not found');
        }
    
        const surveyData = surveyDoc.data();
        const userResponses = surveyData.responses;
    
        // Mengonversi respons ke dalam format CSV menggunakan json2csv
        const json2csvParser = new Parser();
        const csvData = json2csvParser.parse(userResponses);
    
        // Menyimpan data CSV ke file
        const filePath = './survey_responses.csv';
        fs.writeFileSync(filePath, csvData);
    
        // Mengirim file sebagai respons unduhan
        res.download(filePath, 'survey_responses.csv', (err) => {
          if (err) {
            console.error('Error downloading survey responses: ', err);
          }
    
          // Menghapus file setelah diunduh
          fs.unlinkSync(filePath);
        });
      } catch (error) {
        console.error('Error retrieving survey responses: ', error);
        res.status(500).send('Error retrieving survey responses');
      }
})