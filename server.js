var express     = require('express')
    ,http       = require('http')
    ,jade       = require('jade')
    ,Shred      = require('shred')
    ,Db         = require('./db/db')
    ,routes     = require('./routes') // Routes for the application
    ,MsgServer  = require('./msgServer/msgserver');

//-------------------INITIALIZATION BEGIN--------------------------
var app = express();
app.shred = new Shred({logCurl:true})

//startup db
var db = new Db('mongodb')
db.connect('register')

//start up msg server and sit on an exchange and routing key
var msgServer = new MsgServer('amqpnode')
msgServer.connect('amqp://localhost')
msgServer.receiveMessage("register",'reg-queue',["register.rk.*"])

app.configure(function() {
    app.set('port',process.env.PORT || 3000);
    app.use(express.bodyParser());
    //app.use(express.static(path.join(__dirname,'public')));
    app.set('views',__dirname + '/views');
    app.set('view engine','jade');
    app.set('view options', { "pretty": true });
    app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

// Application routes
routes(app, db, msgServer);

var server = http.createServer(app).listen(app.get('port'),function() {
    console.log("RabbitMQ + Node.js app running on " + app.get('port') + "!");
    app.connectionStatus = 'Connected'
});

//setup socket.io to listen to our app
var io = require('socket.io').listen(server);

console.log("The views path is: " + app.get('views'));

