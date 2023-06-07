const express = require('express')
const app = express()
const config = require('./config')
const usersRouter = require("./routes/users")
const surveysRouter = require("./routes/surveys")
const responseRouter = require("./routes/response")
const transactionRouter = require("./routes/transaction")
const modelRouter = require("./routes/model")
const cors = require("cors")

app.use(cors())
app.use(express.json())
app.use(express.urlencoded({extended: true}))
app.use('/users', usersRouter)
app.use('/surveys', surveysRouter)
app.use('/response', responseRouter)
app.use('/transaction', transactionRouter)
app.use('/models', modelRouter)
const port = process.env.PORT || 8080


app.get('/', (req, res) => {
    res.send('Hello World!!')
})

app.listen(port, () => {
    console.log(`App listening on port ${port}`)
})