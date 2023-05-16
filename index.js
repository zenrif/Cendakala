const express = require('express')
const app = express()
const config = require('./config')

app.use(express.json())
app.use(express.urlencoded({extended: true}))
const port = process.env.PORT || 8080


app.get('/', (req, res) => {
    res.send('Hello World!!')
})

app.listen(port, () => {
    console.log("App listening on port ${port}")
})