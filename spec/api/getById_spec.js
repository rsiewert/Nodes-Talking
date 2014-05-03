/**
 * Test to validate getting a document by its Id
 */
var frisby = require('frisby')
    ,fs = require('fs');

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
var Id = 'server@779359'

frisby.create("Get Device By Id from collection: "+config.register)
    .get('http://localhost:3000/getById/:'+config.register+'/:'+Id)
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        Id:Id
    })
.toss()
