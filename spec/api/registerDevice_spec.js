/**
 * Created by rsie on 4/14/2014.
 */

var frisby = require('frisby');

frisby.create('Register Device')
    .post('http://localhost:3000/register', {
        "data" : {
            "message": {"id":"629"}
        }
    }, {json:true})
    .expectStatus(200)
    .expectHeaderContains('Content-Type','json')
    .expectJSON({
        result: 'Ok'
    })
.toss()


