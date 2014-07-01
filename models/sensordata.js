/*
/$$$$$$                                                          /$$$$$$$              /$$
/$$__  $$                                                        | $$__  $$            | $$
| $$  \__/  /$$$$$$  /$$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$       | $$  \ $$  /$$$$$$  /$$$$$$    /$$$$$$
|  $$$$$$  /$$__  $$| $$__  $$ /$$_____/ /$$__  $$ /$$__  $$      | $$  | $$ |____  $$|_  $$_/   |____  $$
\____  $$| $$$$$$$$| $$  \ $$|  $$$$$$ | $$  \ $$| $$  \__/      | $$  | $$  /$$$$$$$  | $$      /$$$$$$$
/$$  \ $$| $$_____/| $$  | $$ \____  $$| $$  | $$| $$            | $$  | $$ /$$__  $$  | $$ /$$ /$$__  $$
|  $$$$$$/|  $$$$$$$| $$  | $$ /$$$$$$$/|  $$$$$$/| $$            | $$$$$$$/|  $$$$$$$  |  $$$$/|  $$$$$$$
\______/  \_______/|__/  |__/|_______/  \______/ |__/            |_______/  \_______/   \___/   \_______/

*/

var mongoose = require('mongoose')
, Schema = mongoose.Schema
, ObjectId = Schema.ObjectId


var SensorDataSchema = new Schema ({
    "title" : String,
    "description" : String,
    "nodeId" : Number,
    "sensorType": [String],
    "data": {
        "type": {
            "measurement": [{"key":"value"}]
        }
    }
})

module.exports = mongoose.model('SensorData',SensorDataSchema)


