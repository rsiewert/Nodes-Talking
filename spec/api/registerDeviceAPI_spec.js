/**
 * Created by rsie on 4/14/2014.
 */

var frisby = require('frisby')
    ,fs = require('fs');

var theData = fs.readFileSync('./json/register.json','utf8')

frisby.create('Register Device')
    .post('http://localhost:3000/register', {
        data : {
            message: JSON.parse(theData)
        },
        say : function() {
            console.log("I'm just sayin")
        }
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: 'Ok'
    })
.toss()


