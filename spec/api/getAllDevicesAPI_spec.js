/**
 * Created by rsie on 4/18/2014.
 */

var frisby = require('frisby')
    ,fs = require('fs');

frisby.create('Get All Devices')
    .get('http://localhost:3000/getAll/register')
    .expectStatus(200)
.toss()
