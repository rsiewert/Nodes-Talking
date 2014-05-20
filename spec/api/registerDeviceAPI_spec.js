/**
 _     _           ______           _           ___  ______ _____
(_)   | |          |  _  \         (_)         / _ \ | ___ \_   _|
_ __ ___  __ _ _ ___| |_ ___ _ __| | | |_____   ___  ___ ___/ /_\ \| |_/ / | |   ___ _ __   ___  ___
| '__/ _ \/ _` | / __| __/ _ \ '__| | | / _ \ \ / / |/ __/ _ \  _  ||  __/  | |  / __| '_ \ / _ \/ __|
| | |  __/ (_| | \__ \ ||  __/ |  | |/ /  __/\ V /| | (_|  __/ | | || |    _| |_ \__ \ |_) |  __/ (__
|_|  \___|\__, |_|___/\__\___|_|  |___/ \___| \_/ |_|\___\___\_| |_/\_|    \___/ |___/ .__/ \___|\___|
__/ |                                                             ______  | |
|___/                                                             |______| |_|

**/

var frisby = require('frisby')
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




