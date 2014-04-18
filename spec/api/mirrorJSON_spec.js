/**
 * Created by rsie on 4/18/2014.
 */

var frisby = require('frisby')
    ,fs = require('fs');

var json = {"data": 'I am Here: YESSSSS'}
frisby.create('Mirror JSON')
    .post('http://localhost:3000/json-mirror', {
        data : {
            message: json
        }
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: json.message
    })
.toss()
