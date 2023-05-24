const express = require('express')
const app = express()
const config = require('./config')
const usersRouter = require("./routes/users")
const surveysRouter = require("./routes/surveys")
const surveysRouter = require("./routes/response")

app.use(express.json())
app.use(express.urlencoded({extended: true}))
app.use('/users', usersRouter)
app.use('/surveys', surveysRouter)
app.use('/response', surveysRouter)
const port = process.env.PORT || 8080


app.get('/', (req, res) => {
    res.send('Hello World!!')
})

app.listen(port, () => {
    console.log(`App listening on port ${port}`)
})