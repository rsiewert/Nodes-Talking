/**
 * Created by rsie on 4/18/2014.
 */

var frisby = require('frisby')
    ,fs = require('fs')

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)

frisby.create('Get All Devices')
    .get('http://localhost:3000/getAll/'+config.Registration)
    .expectStatus(200)
    .expectHeaderContains('content-type', 'application/json')
.toss()
