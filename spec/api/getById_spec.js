/**
 * This test is to validate getting a document by its Id
 */
var frisby = require('frisby')
    ,fs = require('fs');

var Id = 'server@779359'
frisby.create("Get Device By Id from collection: 'register'")
    .get('http://localhost:3000/getById/register/'+Id)
    .expectStatus(200)
.toss()
