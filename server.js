var express = require('express'),
	http = require('http'),
	path = require('path'),
	jade = require('jade'),
	amqp = require('amqp'),
	Shred = require('shred'),
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

app.connectionStatus 	= 'No server connection'
app.exchangeStatus 		= 'No exchange established'
app.queueStatus 		= 'No queue established'
app.devices 			= nano.db.use('devices')
app.register			= nano.db.use('register')

var exchange
var q_register
var q_test

//------------------make connection to rabbitmq, create exchange and queues(s)/binding(s), set status, subscribe to queue(s)
rabbitMqConnection = amqp.createConnection({host:"localhost"})

rabbitMqConnection.addListener('error', function (e) {
	throw e;
})

rabbitMqConnection.on('ready',function() {
	app.connectionStatus = 'RabbitMQ is Connected';
	console.log("RabbitMq started....")
	
	//-----------------create rabbit exchange----------------------
	exchange = rabbitMqConnection.exchange('test-exchange',{},function(exchange) {
		console.log('Exchange ' + exchange.name + ' is open')
		app.exchangeStatus = 'An exchange has been established!'
	})	

	//----------------create rabbit queue(s)
	q_register = rabbitMqConnection.queue('register-queue',function(queue) {
		console.log('Queue ' + queue.name + ' is open')
		app.queueStatus = 'The queue is ready for use!'
		q_register.bind(exchange,'the.routing.register')
		q_register.subscribe('the.routing.register',function(msg,headers,deliveryInfo) {
			msg.message.timestamp = new Date().getTime()
			console.log({'log':'the.routing.register','msg.message':msg.message})
		    insertReg(app.register,msg.message, msg.message.node.nodeId)
			io.sockets.on('connection',function(socket) {
				console.log("Sockets.io is Connected!!")
				socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
				socket.on("my return event",function(data) {
					console.log(data)
				})	
			})
		})
	})
	q_test = rabbitMqConnection.queue('test-queue',function(queue) {
		console.log('Queue ' + queue.name + ' is open')
		app.queueStatus = 'The queue is ready for use!'
		q_test.bind(exchange,'test.routing.key')
		q_test.subscribe('the.routing.key',function(msg,headers,deliveryInfo) {
			console.log({'log':'the.routing.key','msg.message':msg.message})
			insertData(app.devices,msg.message)
			io.sockets.on('connection',function(socket) {
				console.log("Sockets.io is Connected!!")
				socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
				socket.on("my return event",function(data) {
					console.log(data)
				})	
			})
		})
	})
	q_ack = rabbitMqConnection.queue('ack-queue',function(queue) {
		console.log('Queue ' + queue.name + ' is open')
		app.queueStatus = 'The queue is ready for use!'
		q_test.bind(exchange,'ack.routing.key')
		q_test.subscribe('ack.routing.key',function(msg,headers,deliveryInfo) {
			console.log({'log':'ack.routing.key','msg.message':msg.message})
			//insertData(app.devices,msg.message)
			io.sockets.on('connection',function(socket) {
				console.log("Sockets.io is Connected!!")
				socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
				socket.on("my return event",function(data) {
					console.log(data)
				})	
			})
		})
	})
})

app.shred = new Shred({logCurl:true})
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

app.post('/newReg',function(req,res) {
	console.log("inside /newReg")
	var data = req.body.data
	console.log("new Reg = " + data)
	exchange.publish('the.routing.register', {message:data,status:"I am Ok"},{mandatory:true},function(result) {
		console.log('newReg: result of publish (false means success) = ' + result);
	});
})

app.post('/newData',function(req,res) {
	console.log("inside /newData")
	var data = req.body.data
	console.log("new Data = " + data)
	exchange.publish('*.routing.key', {message: data,status:"I am Ok"},{mandatory:true},function(result) {
		console.log('newData: result of publish (false means success) = ' + result);
	});
})
	
app.post('/newMessage',function(req,res) {
	console.log('Inside /newMessage');
	var newMessage = req.body.data;
	console.log('newMessage = ' + newMessage);
	exchange.publish('*.routing.key', {message: newMessage,status:"I am Ok"},{mandatory:true},function(result) {
		console.log('newMessage: result of publish (false means success) = ' + result);
	});
	res.redirect('/message-service');
});

app.post('/json-mirror',function(req,res) {
	var newMessage = req.body.data;
	res.json(newMessage);
});

app.get('/couchDBList',function(req,res) {
	//get the couchdb list via REST call
///*
	var req = app.shred.get({
		url: "http://localhost:5984/devices/_changes",//_design/listDB/_view/listDB",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		on: {
			// You can use response codes as events
			200: function(response) {
			  // Shred will automatically JSON-decode response bodies that have a
			  // JSON Content-Type
			  console.log(response.content.data);
			  res.json(response.content.data)
			},
			// Any other response means something's wrong
			response: function(response) {
			  console.log("Houston, we have a problem...");
			}
		}
	})
//	*/
	//res.render("")
})

app.get('/register',function(req,res) {
	console.log("Here in register: req ip = " + req.ip)
	res.send('Caller = ' + req.get('Host') + "\nreq ip = " + req.ip)
})

app.get('/listDB/:type',function(req,res) {
	getDBContentsById(app.devices,req.params.type,'other',function(err,results) {
		if(err) {
			console.log("error in /listDB/:type")
		} else {
			console.log("type = " + req.params.type)
			res.render('index',
			{
				title:'Listing CouchDB Documents By Type',
				connectionStatus:app.connectionStatus,
				exchangeStatus:app.exchangeStatus,
				queueStatus:app.queueStatus,
				"results":results
			})
		}
	})
})

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

app.get('/listRegisterDB',function(req,res) {

	getDBContents(app.register,"raw",function(err,results) {
		if(err) {
			console.log("error in listRawJson")
		} else {
			res.render('index',
			{
				title:'Listing CouchDB Register Documents',
				connectionStatus:app.connectionStatus,
				exchangeStatus:app.exchangeStatus,
				queueStatus:app.queueStatus,
				"results":results.data
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
//****************************END OF REST Apis***********************************


//****************************Private methods************************************
var insertData = function(db,data) {
	console.log("insertData: data = " + data.node.protocol.messenger.on_ack.exchange)
	db.insert({"data": {"message":data,"status":data.status}},function(err,body,header) {
		if(err) {
			console.log("err.insert = " + err.message)
			return
		}
		console.log("you have inserted a message: ")
		console.log(body)
		//publish back on the ack the message just inserted
	        var exchange = rabbitMqConnection.exchange(data.node.protocol.messenger.on_publish.exchange,{'passive':'true'})
		exchange.publish(data.node.protocol.messenger.on_ack.routing_key,{message: data},
				 {mandatory:true},function(result) {
			console.log('insertData: result of publish (false means success) = ' + result);
		})
	})
}


var insertReg = function(db,data,id) {
	console.log("insertReg: data = " + data.node.protocol.messenger.on_ack.exchange)
    db.insert({"data": {"message":data,"status":data.status}},id,function(err,body,header) {
		if(err) {
			console.log("err.insert = " + err.message)
			return
		}
		console.log("you have inserted a registration: ")
		console.log(body)
		//publish back on the ack the message just inserted
	        var exchange = rabbitMqConnection.exchange(data.node.protocol.messenger.on_publish.exchange,{'passive':'true'})
		exchange.publish(data.node.protocol.messenger.on_ack.routing_key,{message: data},
				 {mandatory:true},function(result) {
			console.log('insertReg: result of publish (false means success) = ' + result);
		})
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
					results.push(doc.doc.data.message)
				} else {
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
				}
			})
		} else {
			//console.log("err.getDBContents = " + err.message)
			callback({'status':err.message},[])
		}
		callback(0,results)
	})
}

var getDBContentsById = function(db,value,msgType,callback) {

	var req = app.shred.get({
		url: "http://localhost:5984/devices/_design/getById/_view/getById?data.message.type=['"+value+"']",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json"
		},
		on: {
			// You can use response codes as events
			200: function(response) {
				// Shred will automatically JSON-decode response bodies that have a
				// JSON Content-Type
				console.log(response.content.data);
				callback(0,response.content.data)
			},
			// Any other response means something's wrong
			response: function(response) {
				console.log("Houston, we have a problem...")
				callback("Houston we have a problem...",{})
			}
		}
	})
}


/*
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
*/


//*******************************END of Private Methods********************************



