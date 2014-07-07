

var mongoose = require('mongoose')
, Schema = mongoose.Schema
, ObjectId = Schema.ObjectId

var HeartBeatSchema = new Schema ({
    "message": {
        "heartbeat": {
            "status": String
        },
        "originTimestamp": Date,
        "nodeId": String,
        "seqId": Number,
        "requestAck": Boolean
    }
})

module.exports = mongoose.model('HeartBeat',HeartBeatSchema)
