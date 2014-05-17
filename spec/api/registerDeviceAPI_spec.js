/**
 * This test exercises the register endpoint. Reads in a json message from the filesystem and saves it to the db.
 */

var frisby = require('frisby')
    ,mongoose = require('mongoose')
//    ,RegistrationSchema = mongoose.model('Registration')
    ,Registration = require('../../models/registration')

var id = Math.floor(Math.random()*1000001)
var regInstance = new Registration({'data.message.node.nodeId':'server@'+id})
if(regInstance == undefined) console.log('reg is undefined')

console.log('regInstance: ' + JSON.stringify(regInstance))

frisby.create('Register Device')
    .post('http://localhost:3000/register', {
        data : regInstance
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: 'Ok'
    })
.toss()




