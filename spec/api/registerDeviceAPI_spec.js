/**
 * This test exercises the register endpoint. Reads in a json message from the filesystem and saves it to the db.
 */

var frisby = require('frisby')
    ,fs = require('fs');

var theData = fs.readFileSync('./json/register.json','utf8')
var json = JSON.parse(theData)
var id = Math.floor(Math.random()*1000001)
json.nodeId = 'server@' + id
console.log('jason.id: ' + json.data.message.node.nodeId)
frisby.create('Register Device')
    .post('http://localhost:3000/register', {
        data: {
            data: json.data
        }
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: 'Ok'
    })
.toss()




