/**
 * Created by rsie on 3/30/2014.
 */

var amqp = require('amqp')
    ,amqpNode = require('amqplib')
    ,ampq   = require('amqp')
    ,when = require('when')
    ,all = when.all;

//                                 _______________________________
//________________________________/    Client Side Abstraction    \___________________________________

function MsgServerModule(msgServerType) {

// Implementation reference:

    this._impl = null;

// Setup procedure:

    this._SetImplementation(this._EstablishImplementer(msgServerType));

    return this;
}

MsgServerModule.prototype = {

    _SetImplementation: function(implementer)
    {
        this._impl = null;
        if(implementer)
            this._impl = implementer;
    },

    // EstablishImplementer - function that creates
    // the Concrete Implementer and binds it to MsgModule.
    // This is the very method to place your
    // browser/feature/object detection code.
    _EstablishImplementer: function(container)
    {
        if(container === 'amqp')
            return new Amqp();

        else if(container === 'amqpnode')
            return new AmqpNode();

        return null;
    },

  // Functions "exported" by the MsgServerModule abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________
    connect:  function(host)
    {
        if(this._impl) {
            this._impl.connect(host)
            return true
        } else
            return false
    },
    sendMessage: function(msg,exchangeName,routingKey) {
        if(this._impl) {
            this._impl.sendMessage(msg,exchangeName,routingKey)
            return true
        } else
            return false
    },
    receiveMessage: function(exchangeName,q,routingKey) {
        if(this._impl) {
            this._impl.receiveMessage(exchangeName,q,routingKey)
            return true
        } else
            return false
    },
    createExchanges:  function(exchangeNames) {
        if(this._impl) {
            this._impl.createExchanges(exchangeNames)
            return true
        } else
            return false
    },
    createQueues: function(queueNames) {
    },
    removeExchanges: function(exchangeNames) {
        console.log("remove these exchanges: " + exchangeNames)
    },
    removeQueues: function(queueNames) {
        console.log("remove thesse queues: " + queueNames)
    }
}
//                             ___________________________
//____________________________/     Implementations       \__________________________

//                                  ___________________________
//_________________________________/         AmqpNode          \__________________________

function AmqpNode() {
    this._amqpNode = null
}

AmqpNode.prototype = {
    connect: function(host) {
        this._amqpNode = amqpNode.connect(host)
            .then(function(connection) {
                console.log("AmqpNode connected")
                return connection
            })
    },
//    createExchanges: function(exchangesNames) {
//        this._amqpNode.then(function(conn) {
//            return when(conn.createChannel().then(function(ch) {
//                for(var exchange in exchangesNames) {
//                    var ok = ch.assertExchange(exchange, 'topic', {durable:true})
//                    ch.publish(exchange, 'i.am.up', new Buffer(JSON.stringify({"data": {"message":"i am here"}}),{'Content-Type': 'application/json'}))
//                    console.log(" [x] Sent on exchange '%s'", JSON.stringify({"data": {"message":"i am here"}}));
//                }
//            })).ensure(function() { /*conn.close()*/})
//        }).then(null, console.warn)
//    },
    sendMessage: function(msg,exchangeName,routingKey) {
        this._amqpNode.then(function(conn) {
            return when(conn.createChannel().then(function(ch) {

                var ok = ch.assertExchange(exchangeName, 'topic', {durable: true});

                return ok.then(function() {
                    ch.publish(exchangeName, routingKey, new Buffer(JSON.stringify(msg)),{'Content-Type': 'application/json'});
                    console.log(" [x] Sent '%s'", JSON.stringify(msg));
                    return ch.close();
                });
            })).ensure(function() { /*conn.close();*/ });
        }).then(null, console.warn);
    },
    receiveMessage: function(exchangeName,q,key) {
        this._amqpNode.then(function(conn) {
            process.once('SIGINT', function() { conn.close(); });
            return conn.createChannel().then(function(ch) {

                var ok = ch.assertExchange(exchangeName, 'topic', {durable: true});

                ok = ok.then(function() {
                    return ch.assertQueue(q, {exclusive: true});
                });

                ok = ok.then(function(qok) {
                    var queue = qok.queue;
                    return all(key.map(function(rk) {
                        ch.bindQueue(queue, exchangeName, rk);
                    })).then(function() { return queue; });
                });

                ok = ok.then(function(queue) {
                    return ch.consume(queue, rMsg, {noAck: true});
                });

                return ok.then(function() {
                    console.log(' [*] Waiting for messages. To exit press CTRL+C');
                });

                function rMsg(msg) {
                    var struct = JSON.parse(msg.content)
                    console.log(" [x] %s:'%s'",
                        msg.fields.routingKey,
                        struct.msg);
                }
            });
        }).then(null, console.warn);
    }
}

//                                  ___________________________
//_________________________________/          Amqp             \__________________________

function Amqp() {
    this._amqp = null
}

Amqp.prototype = {
    createQueue: function() {
        console.log("Amqp: createQueue")
    },
    createExchange: function() {
        console.log("Amqp: createExchange")
    },
    receiveMessage: function() {
        console.log("Amqp: receiveMessage")
    }
}

//                             ___________________________
//____________________________/     Private Objects       \__________________________

var getSomething = function() {

}

module.exports = MsgServerModule



//JUST MEANT FOR DOC PURPOSES
    //------------------make connection to rabbitmq, create exchange and queues(s)/binding(s),
    //------------------set status, subscribe to queue(s)
//    app.rabbitMqConnection = amqp.createConnection({host:"localhost"})
//
//    app.rabbitMqConnection.addListener('error', function (e) {
//        throw e;
//    })
//
//    app.rabbitMqConnection.on('ready',function() {
//        app.connectionStatus = 'RabbitMQ is Connected';
//        console.log("RabbitMq started....")
//
//        //-----------------create rabbit exchange----------------------
//        exchange = app.rabbitMqConnection.exchange('test-exchange',{},function(exchange) {
//            console.log('Exchange ' + exchange.name + ' is open')
//            app.exchangeStatus = 'An exchange has been established!'
//        })
//
//        //----------------create rabbit queue(s)
//        q_register = app.rabbitMqConnection.queue('register-queue',function(queue) {
//            console.log('Queue ' + queue.name + ' is open')
//            app.queueStatus = 'The ' + queue.name + ' is ready for use!'
//            queue.bind(exchange,'the.routing.register')
//            queue.subscribe('the.routing.register',function(msg,headers,deliveryInfo) {
//                msg.message.timestamp = new Date().toJSON()
//                console.log({'log':'the.routing.register','msg.message':msg.message})
//                insertReg(app.register,msg.message, msg.message.node.nodeId)
//                io.sockets.on('connection',function(socket) {
//                    console.log("Sockets.io is Connected!!")
//                    socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
//                    socket.on("my return event",function(data) {
//                        console.log(data)
//                    })
//                })
//            })
//        })
//        q_test = app.rabbitMqConnection.queue('test-queue',function(queue) {
//            console.log('Queue ' + queue.name + ' is open')
//            app.queueStatus = 'The ' + queue.name + ' is ready for use!'
//            queue.bind(exchange,'test.routing.key')
//            queue.subscribe('the.routing.key',function(msg,headers,deliveryInfo) {
//                console.log({'log':'the.routing.key','msg.message':msg.message})
//                insertData(app.devices,msg.message)
//                io.sockets.on('connection',function(socket) {
//                    console.log("Sockets.io is Connected!!")
//                    socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
//                    socket.on("my return event",function(data) {
//                        console.log(data)
//                    })
//                })
//            })
//        })
//        q_ack = app.rabbitMqConnection.queue('ack-queue',function(queue) {
//            console.log('Queue ' + queue.name + ' is open')
//            app.queueStatus = 'The ' + queue.name + ' is ready for use!'
//            queue.bind(exchange,'ack.routing.key')
//            queue.subscribe('ack.routing.key',function(msg,headers,deliveryInfo) {
//                console.log({'log':'ack.routing.key','msg.message':msg.message})
//                //insertData(app.devices,msg.message)
//                io.sockets.on('connection',function(socket) {
//                    console.log("Sockets.io is Connected!!")
//                    socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
//                    socket.on("my return event",function(data) {
//                        console.log(data)
//                    })
//                })
//            })
//        })
//    })
   // console.log('Express server listening on port 3000');

