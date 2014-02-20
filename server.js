/*
var app = require('express')()
  , server = require('http').createServer(app)
  , io = require('socket.io').listen(server);

server.listen(3000);

app.get('/', function (req, res) {
  res.sendfile(__dirname + '/index.html');
io.sockets.on('connection', function (socket) {
	console.log("socket connected....");
	socket.emit('news', { hello: 'world' });
	
	socket.on('my other event', function (data) {
		console.log(data);
	});

	socket.on('my test event', function (data) {
		console.log(data);
	});

});
});
*/



var express = require('express'),
	http = require('http'),
	path = require('path'),
	jade = require('jade'),
	amqp = require('amqp'),
	nano = require('nano')('http://localhost:5984');

//-------------------INITIALIZATION BEGIN--------------------------
var app = express();

app.configure(function() {
	app.set('port',process.env.PORT || 3000);
	app.use(express.bodyParser());
	//app.use(express.static(path.join(__dirname,'public')));
	app.set('views',__dirname + '/views');
	app.set('view engine','jade');
	app.set('view options', { pretty: true });
	app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));

});

var server = http.createServer(app).listen(app.get('port'),function() {
	console.log("RabbitMQ + Node.js app running on " + app.get('port') + "!");
});

//setup socket.io to listen to our app
var io = require('socket.io').listen(server);

console.log("The views path is: " + app.get('views'));

app.connectionStatus 	= 'No server connection';
app.exchangeStatus 		= 'No exchange established';
app.queueStatus 		= 'No queue established';
app.devices 			= nano.db.use('devices');


//------------------make connection to rabbitmq, create exchange and queue, set status
app.rabbitMqConnection = amqp.createConnection({host:'localhost'});
app.rabbitMqConnection.on('ready',function() {
	app.connectionStatus = 'RabbitMQ is Connected';
	//-----------------create rabbit exchange----------------------
	app.e = app.rabbitMqConnection.exchange('test-exchange',{confirm:true},function(exchange) {
		console.log('Exchange ' + exchange.name + ' is open');
		app.exchangeStatus = 'An exchange has been established!';
		//----------------create rabbit queue and bind to exchange--------
		app.q = app.rabbitMqConnection.queue('test-queue',{},function(queue) {
			console.log('Queue ' + queue.name + ' is open');
			app.queueStatus = 'The queue is ready for use!';
			queue.bind(app.e,'*.routing.*')

			//this needs to be abstracted out
			queue.subscribe('the.routing.one',function(msg,headers,deliveryInfo) {
				console.log({'log':'1','msg.message':msg.message})
				insertData(app.devices,msg)
				io.sockets.on('connection',function(socket) {
					console.log("Sockets.io is Connected!!")
					socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
					socket.on("my return event",function(data) {
						console.log(data)
					})	
				})
			})
			return queue
		})
	})
})
//---------------------INITIALIZATION END---------------------------------

//**********************************REST API'S***********************************
app.get('/',function(req,res) {
	console.log("inside get /");
	
	res.render('index',
		{
			title:'Welcome to RabbitMQ and Node/Express',
			connectionStatus:app.connectionStatus,
			exchangeStatus:app.exchangeStatus,
			queueStatus:app.queueStatus
		});
})

app.get('/message-service',function(req,res) {
	console.log('inside get message-service');
	res.render('message-service',
	   {
			title:'Welcome to the messaging service',
	     	sentMessage: ''
	   });
	 
});
	
app.post('/newMessage',function(req,res) {
	console.log('Inside /newMessage');
	var newMessage = req.body.newMessage;
	console.log('newMessage = ' + newMessage);
	app.e.publish('*.routing.key', {message: newMessage,status:"I am Ok"},{mandatory:true},function(result) {
		console.log('result of publish (false means success) = ' + result);
	});
	res.redirect('/message-service');
});

app.post('/json-mirror',function(req,res) {
	var newMessage = req.body.data;
	res.json(newMessage);
});

app.get('/listDB',function(req,res) {

	getDBContents(app.devices,"other",function(err,results) {
		if(err) {
			console.log("error in listRawJson")
		} else {
			res.render('index',
			{
				title:'Listing CouchDB Documents',
				connectionStatus:app.connectionStatus,
				exchangeStatus:app.exchangeStatus,
				queueStatus:app.queueStatus,
				"results":results
			})
		}
	})
})

app.get('/listRawJson', function(req,res) {

	getDBContents(app.devices,"raw",function(err,results) {
		if(err) {
			console.log("error in listRawJson")
		} else {
			res.json({"results":results})
		}
	})
})
//****************************END OF REST APIs***********************************


//****************************Private methods************************************
var insertData = function(db,data) {
	db.insert({"data": {"message":data.message,"status":data.status}},function(err,body,header) {
		if(err) {
			console.log("err.insert = " + err.message)
			return
		}
		console.log("you have inserted a message")
		console.log(body)
	})
}

var getDBContents = function(db,type,callback) {
	var params = {include_docs:true}

	db.list(params,function(err,body,headers) {
		var results = []
		if(!err) {
			body.rows.forEach(function(doc) {
				console.log(doc)
				if(type == 'raw') {
					results.push(doc)
				}
				if(doc.doc.message != undefined || doc.doc.data != undefined) {
					if(doc.doc.message == undefined) {
						console.log(doc.doc.data.message)
						if(type != 'raw') {
							results.push(doc.doc.data.message)
						}
					}
					else {
						console.log(doc.doc.message)
						results.push(doc.doc.message)
					}
				}
			})
		} else {
			//console.log("err.getDBContents = " + err.message)
			callback({'status':err.message},[])
		}
		callback(0,results)
	})
}
//*******************************END of Private Methods********************************



