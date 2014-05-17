/**
 * Created by rsie on 4/29/2014.
 */
MsgServer   = require('../../msgServer/msgserver')
,Db         = require('../../db/db')
,fs         = require('fs')

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
            console.log(" [x]: Routing Key: '%s' -- Received Msg: '%s'", msg.fields.routingKey, msg.content.toString())
            mongoose.save(config.model,msg)
        }
        msgServer.receiveMessage("tests"/*the exchange*/,'register-queue',["ack.rk.register"],regMsg)
        expect(msgServer).not.toBe(undefined)
    })

    it("should send a message and get an acknowledgement",function() {
        setTimeout(sendMsg,1500)
        //sit on the ack q
        function sendMsg() {
            //send a message to the well-known reg-queue
            msgServer.sendMessage({"name":"Ron Siewert","Occupation":"Grifter"},'tests'/*the exchange*/,'ack.rk.register')
            setTimeout(sendMsg,1500)
        }
    })
})

