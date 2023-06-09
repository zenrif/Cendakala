const express = require('express')
const app = express()
const usersRouter = require("./routes/users")
const surveysRouter = require("./routes/surveys")
const responseRouter = require("./routes/response")
const transactionRouter = require("./routes/transaction")
const cors = require("cors")

//Middleware
app.use(cors())
app.use(express.json())
app.use(express.urlencoded({extended: true}))
app.use('/users', usersRouter)
app.use('/surveys', surveysRouter)
app.use('/response', responseRouter)
app.use('/transaction', transactionRouter)

const port = process.env.PORT || 8080


app.get('/', (req, res) => {
    res.send('Hello World!!')
})

//App Listen
app.listen(port, () => {
    console.log(`App listening on port ${port}`)
})