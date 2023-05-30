const express = require('express')
const app = express()
const {DB, auth} = require('./config')
const {signInWithEmailAndPassword} = require('firebase/auth')
const authenticationRoute = require('./routes/authentication')
require('dotenv').config();

app.use(express.json())
app.use(express.urlencoded({extended: true}))
app.use("/authentication", authenticationRoute)


const port = process.env.PORT || 9080

app.post('/login', async(req, res) => {
    const email = "sample@gmail.com"
    const password = "sample"
 
   
})

app.get('/', (req, res) => {
    res.send('Hello World!!')
})

app.listen(port, () => {
    console.log(`Server running at ${port}`)
})