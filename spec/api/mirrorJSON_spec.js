/**
 _                     ___ _____  _____ _   _
(_)                   |_  /  ___||  _  | \ | |
_ __ ___  _ _ __ _ __ ___  _ __  | \ `--. | | | |  \| |  ___ _ __   ___  ___
| '_ ` _ \| | '__| '__/ _ \| '__| | |`--. \| | | | . ` | / __| '_ \ / _ \/ __|
| | | | | | | |  | | | (_) | |/\__/ /\__/ /\ \_/ / |\  | \__ \ |_) |  __/ (__
|_| |_| |_|_|_|  |_|  \___/|_|\____/\____/  \___/\_| \_/ |___/ .__/ \___|\___|
                                           ______  | |
                                          |______| |_|
**/

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
