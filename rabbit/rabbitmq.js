/**
 * Created by rsie on 3/30/2014.
 */

var amqp = require('amqp')
    ,amqplib = require('amqplib')
    ,when = require('when');

function RabbitMQ() {
    this._connection = null
    this._exchange = []
    return this
}

RabbitMQ.prototype = {

  // Functions "exported" by the RabbitMQ abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________

    connect:  function(host)
    {
        this._connection = amqplib.connect(host);

       //        console.log("Inside connect : myhost: " + myhost.host)
//        this._connection = amqp.createConnection(myhost)
//        this._connection.addListener('error', function (e) {
//            throw e;
//        })
//        if(this._connection == undefined)
//            console.log("before connection.on: connection undefined????")
//        this._connection.on('ready',function() {
//            if(this._connection == undefined)
//                console.log("connection undefined????")
//            console.log('Rabbit Module: RabbitMQ is Connected')
//            for(var i=0;i<exchangeNames.length;i++) {
//                console.log("exchange: " + exchangeNames[i])
//                //-----------------create rabbit exchange----------------------
//                //this._connection.on('ready',function() {
//                if(this._connection != undefined) {
//                    this._exchange = this._connection.exchange(exchangeNames[i], {}, function (exchange) {
//                        console.log('RabbitMQ: Exchange ' + exchange.name + ' is open')
//                    })
//                }
//               // })
//          // }
//        })
    },
    sendMessage: function(msg) {
        this._connection.then(function(conn) {
            //this._connection = conn
            return when(conn.createChannel().then(function(ch) {
                var q = 'hello';
                //var msg = 'Hello World!';

                var ok = ch.assertQueue(q, {durable: false});

                return ok.then(function(_qok) {
                    ch.sendToQueue(q, new Buffer(msg));
                    console.log(" [x] Sent '%s'", msg);
                    return ch.close();
                });
            })).ensure(function() { conn.close(); });;
        }).then(null, console.warn);
    },
    receiveMessage: function() {
        this._connection.then(function(conn) {
          process.once('SIGINT', function() { conn.close(); });
          return conn.createChannel().then(function(ch) {

            var ok = ch.assertQueue('hello', {durable: false});

            ok = ok.then(function(_qok) {
              return ch.consume('hello', function(msg) {
                console.log(" [x] Received '%s'", msg.content);
              }, {noAck: true});
            });

            return ok.then(function(_consumeOk) {
              console.log(' [*] Waiting for messages. To exit press CTRL+C');
            });
          });
        }).then(null, console.warn);
    },
    createExchanges:  function(exchangeNames) {
//        this._connection.on('ready', function () {
//            for(var i=0;i<exchangeNames.length;i++) {
//                console.log("exchange: " + exchangeNames[i])
//                //-----------------create rabbit exchange----------------------
//                //this._connection.on('ready',function() {
//                if(this._connection != undefined) {
//                    this._connection.exchange(exchangeNames[i], {}, function (exchange) {
//                        console.log('RabbitMQ: Exchange ' + exchange.name + ' is open')
//                        this._exchange.push(exchange)
//                    })
//                }
//               // })
//           }
//      //  })
    },
    createQueues: function(queueNames) {
        //----------------create rabbit queue(s)
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
    removeExchanges: function(exchangeNames) {
        console.log("remove these exchanges: " + exchangeNames)
    },
    removeQueues: function(queueNames) {
        console.log("remove thesse queues: " + queueNames)
    }
}

module.exports = RabbitMQ
