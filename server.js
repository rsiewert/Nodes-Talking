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
	amqp = require('amqp');

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

app.connectionStatus = 'No server connection';
app.exchangeStatus = 'No exchange established';
app.queueStatus = 'No queue established';

app.get('/',function(req,res) {
	console.log("inside get /");
	
	res.render('index',
		{
			title:'Welcome to RabbitMQ and Node/Express',
			connectionStatus:app.connectionStatus,
			exchangeStatus:app.exchangeStatus,
			queueStatus:app.queueStatus
		});
	
	//res.send("Hello World");
});

app.post('/start-server',function(req,res) {
	console.log("Inside start-server");
	

	
	app.rabbitMqConnection = amqp.createConnection({host:'localhost'});
	app.rabbitMqConnection.on('ready',function() {
		app.connectionStatus = 'RabbitMQ is Connected';
		res.redirect('/');
	});
	
});

app.post('/new-exchange',function(req,res) {
	app.e = app.rabbitMqConnection.exchange('test-exchange',{confirm:true},function(exchange) {
		console.log('Exchange ' + exchange.name + ' is open');
		app.exchangeStatus = 'An exchange has been established!';
	});
	
	res.redirect('/');
});

app.post('/new-queue',function(req,res) {
	app.q = app.rabbitMqConnection.queue('test-queue',{},function(queue) {
		console.log('Queue ' + queue.name + ' is open');
		app.queueStatus = 'The queue is ready for use!';
		app.q.bind(app.e,'#');
	});
	
	res.redirect('/');
});

app.get('/message-service',function(req,res) {
	console.log('inside get message-service');
	//app.v = jade.compile(app.get('views') + '/message-server.jade',{debug:true,pretty:true});
	//console.log('html = ' + app.v({title:'Welcome',sentMessage:'Hello'}));
	/*
	var htmlout = app.v(
		{
			title: "Welcome to my new messaging service",
			sentMessage: 'Hello brave new world'
		}
	)
	res.render(htmlout);


	jade.renderFile(app.get('views') + '/message-service.jade',
			{debug:true,pretty:true,title:'Welcome to the messaging service',sentMessage:'Hello World'},
			function(err,html) {
				if(err) throw err;
				console.log('html = ' + html);
				//res.render(html);
			});
	*/			
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
	app.e.publish('routingKey', {message: newMessage,status:"I am Ok"},{mandatory:true},function(result) {
		console.log('result of publish (false means success) = ' + result);
	});
	console.log('after publish');

	app.q.subscribe(function(msg,headers,deliveryInfo) {
		console.log('inside subscribe: msg = ' + msg);
		//console.log('subscribe status = ' + msg.status);
		console.log('got a message with routing key = ' + deliveryInfo.routingKey);

/*
		
		jade.renderFile(app.get('views') + '/message-service.jade',
			{debug:true,pretty:true,title:'Welcome to the messaging service',sentMessage:msg.message},
			function(err,html) {
				if(err) throw err;
				console.log('html = ' + html);
				//res.send(html);
				console.log("after res.send");
			});

			
		res.render('message-service',
			{
				title: "You have new mail!",
				sentMessage: msg.message
			});
*/
		io.sockets.on('connection',function(socket) {
			console.log("Sockets.io is Connected!!");
			socket.emit('message',{"sentMessage":msg.message,"status":msg.status})
			socket.on("my return event",function(data) {
				console.log(data);
			});
			
		});

		/*
	var htmlout = app.v(
		{
			title: "Welcome to my new messaging service",
			sentMessage: msg.message
		}
	)
	res.render(htmlout);
	
		
		res.render('message-service',
			{
				title: "You have new mail!",
				sentMessage: msg.message
			});
	*/	
	
	});
	
	res.redirect('/message-service');
	console.log('after sockets.io');
});

app.post('/json-mirror',function(req,res) {
	var newMessage = req.body.newMessage;
	res.json(newMessage);
});


