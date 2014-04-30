/**
 * Created by rsie on 4/29/2014.
 */
MsgServer  = require('../../msgServer/msgserver')

describe("send and receive messages from a RabbitMQ message node", function() {
//start up msg server and sit on an exchange and routing key
    var msgServer = undefined

    it("should assert not undefined for connection to msg server", function() {
        msgServer = new MsgServer('amqpnode')
        msgServer.connect('amqp://localhost')
        msgServer.receiveMessage("tests",'register-queue',["ack.rk.register"])
        expect(msgServer).not.toBe(undefined)
    })

    it("should send a message and get an acknowledgement",function() {
        setTimeout(sendMsg,1500)
        //sit on the ack q
        function sendMsg() {
            //send a message to the well-known reg-queue
            msgServer.sendMessage({"name":"Ron Siewert","Occupation":"Grifter"},'tests','ack.rk.register')
        }
    })
})
