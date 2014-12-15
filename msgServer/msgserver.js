/**
 /$$      /$$                                                                   /$$$$$$
| $$$    /$$$                                                                  /$$__  $$
| $$$$  /$$$$  /$$$$$$   /$$$$$$$ /$$$$$$$  /$$$$$$   /$$$$$$   /$$$$$$       | $$  \__/  /$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$   /$$$$$$
| $$ $$/$$ $$ /$$__  $$ /$$_____//$$_____/ |____  $$ /$$__  $$ /$$__  $$      |  $$$$$$  /$$__  $$ /$$__  $$|  $$  /$$//$$__  $$ /$$__  $$
| $$  $$$| $$| $$$$$$$$|  $$$$$$|  $$$$$$   /$$$$$$$| $$  \ $$| $$$$$$$$       \____  $$| $$$$$$$$| $$  \__/ \  $$/$$/| $$$$$$$$| $$  \__/
| $$\  $ | $$| $$_____/ \____  $$\____  $$ /$$__  $$| $$  | $$| $$_____/       /$$  \ $$| $$_____/| $$        \  $$$/ | $$_____/| $$
| $$ \/  | $$|  $$$$$$$ /$$$$$$$//$$$$$$$/|  $$$$$$$|  $$$$$$$|  $$$$$$$      |  $$$$$$/|  $$$$$$$| $$         \  $/  |  $$$$$$$| $$
|__/     |__/ \_______/|_______/|_______/  \_______/ \____  $$ \_______/       \______/  \_______/|__/          \_/    \_______/|__/
                                                     /$$  \ $$
                                                    |  $$$$$$/
                                                     \______/
**/

var amqp        = require('amqp')
    ,amqpNode   = require('amqplib')
    ,ampq       = require('amqp')
    ,when       = require('when')
    ,all        = when.all
    ,log4js     = require('log4js')
    ,logger     = log4js.getLogger('stout')

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
    receiveMessage: function(exchangeName,q,routingKey,callback) {
        if(this._impl) {
            this._impl.receiveMessage(exchangeName,q,routingKey,callback)
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
        logger.debug("remove these exchanges: " + exchangeNames)
    },
    removeQueues: function(queueNames) {
        logger.debug("remove thesse queues: " + queueNames)
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
                logger.debug("AmqpNode connected")
                return connection
            })
    },
    sendMessage: function(msg,exchangeName,routingKey) {
        this._amqpNode.then(function(conn) {
            return when(conn.createChannel().then(function(ch) {

                var ok = ch.assertExchange(exchangeName, 'topic', {durable: true})

                return ok.then(function() {
                    ch.publish(exchangeName, routingKey, new Buffer(JSON.stringify(msg)),{'Content-Type': 'application/json'})
                    logger.debug(" [x] Sent '%s'", JSON.stringify(msg))
                    return ch.close()
                })
            })).ensure(function() { /*conn.close();*/ })
        }).then(null, console.warn)
    },
    receiveMessage: function(exchangeName,q,key,callback) {
        this._amqpNode.then(function(conn) {
            process.once('SIGINT', function() { conn.close(); })
            return conn.createChannel().then(function(ch) {

                var ok = ch.assertExchange(exchangeName, 'topic', {durable: true})

                var exchangeOk = ok.then(function() {
                    return ch.assertQueue(q, {exclusive: true, autoDelete: true})
                });

                queueOk = exchangeOk.then(function(qok) {
                    var queue = qok.queue
                    return all(key.map(function(rk) {
                        ch.bindQueue(queue, exchangeName, rk)
                    })).then(function() { return queue; })
                });

                consumerOk = queueOk.then(function(queue) {
                    return ch.consume(queue, callback, {ack: true})
                });

                return consumerOk.then(function() {
                    logger.debug(' [*] Waiting for messages. To exit press CTRL+C')
                });
            });
        }).then(null, console.warn)
    }
}

//                                  ___________________________
//_________________________________/          Amqp             \__________________________

function Amqp() {
    this._amqp = null
}

Amqp.prototype = {
    createQueue: function() {
        logger.debug("Amqp: createQueue")
    },
    createExchange: function() {
        logger.debug("Amqp: createExchange")
    },
    receiveMessage: function() {
        logger.debug("Amqp: receiveMessage")
    }
}

//                             ___________________________
//____________________________/     Private Objects       \__________________________

var getSomething = function() {

}

module.exports = MsgServerModule

