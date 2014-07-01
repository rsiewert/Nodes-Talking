/**

 /$$$$$$                                                           /$$$$$$
/$$__  $$                                                         /$$__  $$
| $$  \__/  /$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$   /$$$$$$       | $$  \ $$  /$$$$$$   /$$$$$$
|  $$$$$$  /$$__  $$ /$$__  $$|  $$  /$$//$$__  $$ /$$__  $$      | $$$$$$$$ /$$__  $$ /$$__  $$
\____  $$| $$$$$$$$| $$  \__/ \  $$/$$/| $$$$$$$$| $$  \__/      | $$__  $$| $$  \ $$| $$  \ $$
/$$  \ $$| $$_____/| $$        \  $$$/ | $$_____/| $$            | $$  | $$| $$  | $$| $$  | $$
|  $$$$$$/|  $$$$$$$| $$         \  $/  |  $$$$$$$| $$            | $$  | $$| $$$$$$$/| $$$$$$$/
\______/  \_______/|__/          \_/    \_______/|__/            |__/  |__/| $$____/ | $$____/
                                                                           | $$      | $$
                                                                           | $$      | $$
                                                                           |__/      |__/
**/
global.app_require = function(name) {
    return require(__dirname + '/' + name);
}
var express         = require('express')
    ,http           = require('http')
    ,jade           = require('jade')
    ,Shred          = require('shred')
    ,Db             = app_require('db/db')
    ,routes         = require('./routes') // Routes for the application
    ,MsgServer      = require('./msgServer/msgserver')
    ,fs             = require('fs')
    ,log4js         = require('log4js')
    ,logger         = log4js.getLogger('stout')


//-------------------INITIALIZATION BEGIN--------------------------
"use strict";
var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)

var app = express();
app.shred = new Shred({logCurl: true})

//startup native mongodb driver
var db = new Db('mongoose')
db.connect(config.collection.toLowerCase())

//logger.setLevel(log4js.Level.INFO)
//logger.addAppender( new ConsoleAppender(true) )
logger.debug("just checking in.....")

function regMsg (msg) {
    logger.debug(" [x]: Routing Key: '%s' -- Received Msg: '%s'", msg.fields.routingKey, msg.content)
    db.save(config.model,JSON.parse(msg.content))
}

//start up msg server and sit on an exchange and routing key
var msgServer = new MsgServer('amqpnode')
msgServer.connect('amqp://localhost')
msgServer.receiveMessage("register", 'reg-queue', ["register.rk.*"],regMsg)

app.configure(function () {
    app.use(express.errorHandler({ dumpExceptions: true, showStack: true }))
    app.use(express.json())
    app.set('port', '3000')
    app.use('/static',__dirname + '/public')
    app.set('view engine', 'jade')
    app.set('views', __dirname + '/views')
    app.set('view options', { "pretty": true })
})

// Application routes
routes(app, db)

var server = http.createServer(app).listen(app.get('port'), function () {
    logger.debug("RabbitMQ + Node.js app running on " + app.get('port') + "!");
    app.connectionStatus = 'Connected'
});

//setup socket.io to listen to our app
var io = require('socket.io').listen(server);

logger.debug("The views path is: " + app.get('views'));


