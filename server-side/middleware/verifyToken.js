const { auth } = require('../config')
const jwt = require("jsonwebtoken")
const axios = require("axios")

exports.verifyToken = async (req, res, next) => {
    const original = req.headers.authtoken
    if(original == "" || original == undefined || original == null){
        return res.status(403).json({
            status : "Failed",
            code : "auth/no-token",
            message : "Please provide token"
        })
    }
    const arrOriginal = original.split(" ");
    const token = arrOriginal[1];
    try {
        const verify = await auth.verifyIdToken(token);
        req.body.uid = verify.uid
        next()
    } catch (error) {
        if(error.code === "auth/argument-error" || original === undefined){
            return res.status(403).json({
                status : "Failed",
                code : "auth/argument-error",
                message : "Unauthorized Access"
            })
        }
        else if(error.code === "auth/id-token-expired"){
            const decodedPayload = jwt.decode(token)
            const uid = decodedPayload.sub

            const newToken = await auth.createCustomToken(uid)
            const config = { 
                headers: {newtoken :`${newToken}`} // Replace 'YOUR_TOKEN_VALUE' with the actual token value
            };

            const data ={}
        
            const getUser = await axios.post('https://client-side-dot-cendakala.et.r.appspot.com/authentication/newAccess', data, config);

            return res.status(403).json({
                status : "Failed",
                code : "auth/id-token-expired",
                message : "Your token expired, please replace the token",
                newToken : getUser.data
            })
        }
        return res.status(500).json({
            message : error
        })
    }
}
// router.post('/verify', async(req, res) => {
//     try{
//         const token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFiYjI2MzY4YTNkMWExNDg1YmNhNTJiNGY4M2JkYjQ5YjY0ZWM2MmYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vY2VuZGFrYWxhIiwiYXVkIjoiY2VuZGFrYWxhIiwiYXV0aF90aW1lIjoxNjg0MzM0Mzc4LCJ1c2VyX2lkIjoicFJaMnoyckdYVFJxQ2wyaGFHbGZ6em4wT3lYMiIsInN1YiI6InBSWjJ6MnJHWFRScUNsMmhhR2xmenpuME95WDIiLCJpYXQiOjE2ODQzMzQzNzgsImV4cCI6MTY4NDMzNzk3OCwiZW1haWwiOiJzYW1wbGVAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInNhbXBsZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.N3vlozmOyjhK-yPaoMqTHi-LOWRkwdBNNr7WAoRe0h08I--3-jOcVKce18-jl5Fi3GBUzLy_AAWpctgHw6aOc0J-W2gWrqRSm2miaWnG_TUcgUy08XMtYy9o_acyP6kW6gOrubKqBvXxkgn-Mn3OXp9cjHMrJmR4USMKNuDN2HyP6Ux3M3OWL7oCNv2gIt2RmbVGhQ_nuuWf5pvk4OM6zsI03Z1A9bIRtvgQGiD1Vj4TODfda4M9ettSME3qZYf5bY027xcozWgRwvMGiqAkusX2J5ur6TiU4Ek3whFtS1e_tVBQIhzdIkCCaTyD2nwCuV2SQO1fuetY4Sn7Agl5e";
//         const decoded = await auth.verifyIdToken(token)

//         res.send(decoded)
//     } catch (error) {
//         console.error(error)
//         const spesificErr = error.code;

        
//     }
    
// })