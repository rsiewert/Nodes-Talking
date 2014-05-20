/**
______           _     _             _   _
| ___ \         (_)   | |           | | (_)
| |_/ /___  __ _ _ ___| |_ _ __ __ _| |_ _  ___  _ __
|    // _ \/ _` | / __| __| '__/ _` | __| |/ _ \| '_ \
| |\ \  __/ (_| | \__ \ |_| | | (_| | |_| | (_) | | | |
\_| \_\___|\__, |_|___/\__|_|  \__,_|\__|_|\___/|_| |_|
            __/ |
           |___/
**/


var mongoose = require('mongoose')
, Schema = mongoose.Schema
, ObjectId = Schema.ObjectId


var RegistrationSchema = new Schema ({
    "title" : String,
    "description" : String,
    "domainType" : String,
    "data": {
        "message": {
            "node": {
                "actsAs": String,
                "nodeId": String,
                "location": {
                    "latitude": String,
                    "longitude": String,
                    "altitude": String
                },
                "status": String,
                "protocol": {
                    "rest": {
                        "Mirror": {
                            "domain": String,
                            "port": String,
                            "endpoint": String
                        }
                    },
                    "messenger": {
                        "on_publish": {
                            "exchange": String,
                            "routing_key": String,
                            "manageExchange": Boolean
                        },
                        "heartbeat": {
                            "exchange": String,
                            "routing_key": String,
                            "manageExchange": String
                        },
                        "on_ack": {
                            "exchange": String,
                            "routing_key": String,
                            "manageExchange": Boolean
                        },
                        "command_server": {
                            "exchange": String,
                            "routing_key": String,
                            "manageExchange": Boolean
                        }
                    }
                },
                "description": String
            },
            "originTimestamp": String,
            "seqId": String,
            "requestAck": Boolean,
            "timestamp": Date,
            "updated": Date
        }
    }
})

module.exports = mongoose.model('Registration',RegistrationSchema)