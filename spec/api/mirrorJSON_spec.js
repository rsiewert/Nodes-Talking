/**
 * Created by rsie on 4/18/2014.
 */

var frisby = require('frisby')
    ,fs = require('fs');

var data1 = {message: {"json": {"data":"testMessage","type":"message","object":"myObject"}}}
frisby.create('Mirror JSON')
    .post('http://localhost:3000/json-mirror', {
        data : data1
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: data1
    })
.toss()
