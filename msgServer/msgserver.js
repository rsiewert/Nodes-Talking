/**
 * Created by rsie on 3/30/2014.
 */

var amqp = require('amqp')
    ,amqpNode = require('amqplib')
    ,ampq   = require('amqp')
    ,when = require('when');

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
    sendMessage: function(msg) {
        if(this._impl) {
            this._impl.sendMessage(msg)
            return true
        } else
            return false
    },

    receiveMessage: function() {
        if(this._impl) {
            this._impl.receiveMessage()
            return true
        } else
            return false
    },
    createExchanges:  function(exchangeNames) {

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
//_________________________________/          Amqp             \__________________________

function Amqp() {
    this._amqp = null
}

Amqp.prototype = {
    createQueue: function() {
                //----------------create amqp queue(s)
//        queueNames.forEach(queueName,function () {
//            this._connection.queue(queueName,function(queue) {
//                console.log('Queue ' + queue.name + ' is open')
//                qStatus = 'The ' + queue.name + ' is ready for use!'
//                queue.bind(this._exchange,queueName.routingKey)
//                queue.subscribe(queueName.routingKey,function(msg,headers,deliveryInfo) {
//                    msg.message.timestamp = new Date().toJSON()
//                    console.log({'log':'the.routing.register','msg.message':msg.message})
//                    //insertReg(app.register,msg.message, msg.message.node.nodeId)
//                })
//            })
//        })
    },
    createExchange: function() {
        console.log("amqp: createExchange")
    }
}

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
    createExchange: function() {
        console.log("Amqp.node: createExchange")
    },
    sendMessage: function(msg) {
        this._amqpNode.then(function(conn) {
            return when(conn.createChannel().then(function(ch) {
                var q = 'hello';
                //var msg = 'Hello World!';

                var ok = ch.assertQueue(q, {durable: false});

                return ok.then(function(_qok) {
                    ch.sendToQueue(q, new Buffer(JSON.stringify(msg)),{'Content-Type': 'application/json'});
                    console.log(" [x] Sent '%s'", JSON.stringify(msg));
                    return ch.close();
                });
            })).ensure(function() { /*conn.close();*/ });
        }).then(null, console.warn);
    },
    receiveMessage: function() {
        this._amqpNode.then(function(conn) {
          process.once('SIGINT', function() { conn.close(); });
          return conn.createChannel().then(function(ch) {

            var ok = ch.assertQueue('hello', {durable: false});

            ok = ok.then(function(_qok) {
              return ch.consume('hello', function(msg) {
                console.log(" [x] Received '%s'", msg.content)
              }, {noAck: true});
            });

            return ok.then(function(_consumeOk) {
              console.log(' [*] Waiting for messages. To exit press CTRL+C');
            });
          });
        }).then(null, console.warn);
    }
}

//                             ___________________________
//____________________________/     Private Objects       \__________________________

var getSomething = function() {

}

module.exports = MsgServerModule
