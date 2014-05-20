/**
            _    ___  _ _______           _                 ___  ______ _____
           | |  / _ \| | |  _  \         (_)               / _ \ | ___ \_   _|
  __ _  ___| |_/ /_\ \ | | | | |_____   ___  ___ ___  ___ / /_\ \| |_/ / | |   ___ _ __   ___  ___
 / _` |/ _ \ __|  _  | | | | | / _ \ \ / / |/ __/ _ \/ __||  _  ||  __/  | |  / __| '_ \ / _ \/ __|
| (_| |  __/ |_| | | | | | |/ /  __/\ V /| | (_|  __/\__ \| | | || |    _| |_ \__ \ |_) |  __/ (__
 \__, |\___|\__\_| |_/_|_|___/ \___| \_/ |_|\___\___||___/\_| |_/\_|    \___/ |___/ .__/ \___|\___|
  __/ |                                                                   ______  | |
 |___/                                                                   |______| |_|

**/

var frisby = require('frisby')
    ,fs = require('fs')

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)

frisby.create('Get All Devices')
    .get('http://localhost:3000/getAll/'+config.model)
    .expectStatus(200)
    .expectHeaderContains('content-type', 'application/json')
.toss()
