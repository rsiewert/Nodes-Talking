/**
                     _____                        _____         _
                    /  ___|                      |_   _|       | |
 _ __ ___  ___  __ _\ `--.  ___ _ ____   _____ _ __| | ___  ___| |_   ___ _ __   ___  ___
| '_ ` _ \/ __|/ _` |`--. \/ _ \ '__\ \ / / _ \ '__| |/ _ \/ __| __| / __| '_ \ / _ \/ __|
| | | | | \__ \ (_| /\__/ /  __/ |   \ V /  __/ |  | |  __/\__ \ |_  \__ \ |_) |  __/ (__
|_| |_| |_|___/\__, \____/ \___|_|    \_/ \___|_|  \_/\___||___/\__| |___/ .__/ \___|\___|
                __/ |                                            ______  | |
               |___/                                            |______| |_|
**/

    var MsgServer   = require('../../msgServer/msgserver')
    ,Db             = require('../../db/db')
    ,fs             = require('fs')
    ,Registration = require('../../models/registration')

var boot    = fs.readFileSync('./json/bootstrap.json','utf8')
var config  = JSON.parse(boot)

describe("send and receive messages from a RabbitMQ message node", function() {
//start up msg server and sit on an exchange and routing key
    var msgServer   = undefined

    var mongoose    = new Db('mongoose')
    console.log("collection = " + config.model)
    mongoose.connect(config.model.toLowerCase())

    it("should assert not undefined for connection to msg server", function() {
        msgServer = new MsgServer('amqpnode')
        msgServer.connect('amqp://localhost')

        function regMsg (msg) {
            console.log(" [x]: Routing Key: '%s' -- Received Msg: '%s'", msg.fields.routingKey, msg.content)
            mongoose.save(config.model,JSON.parse(msg.content))
        }
        msgServer.receiveMessage("tests"/*the exchange*/,'register-queue',["ack.rk.register"],regMsg)
        expect(msgServer).not.toBe(undefined)
    })

    it("should send a message and get an acknowledgement",function() {
        setTimeout(sendMsg,1500)
        //sit on the ack q
        function sendMsg() {
            var id = Math.floor(Math.random()*1000001)
            var reg = new Registration({'data.message.node.nodeId':'server@'+id})
            console.log("msgServerTest: reg = " + reg)
            //send a message to the well-known reg-queue
            msgServer.sendMessage(reg,'tests'/*the exchange*/,'ack.rk.register')
            setTimeout(sendMsg,1500)
        }
    })
})

