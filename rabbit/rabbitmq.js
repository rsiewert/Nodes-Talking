/**
 * Created by rsie on 3/30/2014.
 */

var amqp = require('amqp')

function RabbitMQ() {
    this._connection = null
    this._connectionStatus = null
    this._exchange = null
    this._exchangeStatus = null
    this._q_register  = null
    this._q_test = null
    this._q_ack = null
}

RabbitMQ.prototype = {

  // Functions "exported" by the RabbitMQ abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________

    setConnection:  function(myhost)
    {
        this._connection = amqp.createConnection(myhost)
        this._connection.addListener('error', function (e) {
            throw e;
        })
        this._connection.on('ready',function() {
            this._connectionStatus = 'Rabbit Module: RabbitMQ is Connected';
            console.log(this._connectionStatus)
        })
    },

    createExchange:  function(exchange)
    {
        this._connection.on('ready',function() {
            //-----------------create rabbit exchange----------------------
            this._exchange = this._connection.exchange('test-exchange',{},function(exchange) {
                console.log('Exchange ' + exchange.name + ' is open')
                this._exchangeStatus = 'An exchange has been established!'
            })
            console.log("createExchange")
        })
    }
}

module.exports = RabbitMQ
