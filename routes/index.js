/**
 * Created by rsie on 4/5/2014.
 */
module.exports = exports = function(app, db) {

    app.get('/', function (req, res) {
        console.log("inside get /");

        //this is now using the DbModule
        db.getAll('register')
        //msgServer.sendMessage({msg: "Hello Cruel World"}, "myexchange", "my.routing.key")
        //msgServer.sendMessage({msg: 'This will make it...'}, 'myexchange', 'my.routing.key')

        res.render('index',
            {
                title: 'Welcome to RabbitMQ and Node/Express',
                connectionStatus: app.connectionStatus,
                exchangeStatus: app.exchangeStatus,
                queueStatus: app.queueStatus
            });
    });

    app.post('/json-mirror',function(req,res) {
        var newMessage = req.body.data;
        res.json(newMessage);
    });

    app.get('/devices/doc/:id',function(req,res) {
    })

    app.get('/message-service',function(req,res) {
        console.log('inside get message-service');
        res.render('message-service',
            {
                title:'Welcome to the messaging service',
                sentMessage: ''
            });

    })

    app.post('/newReg',function(req,res) {
        console.log("inside /newReg")
        var data = req.body.data
        console.log("new Reg = " + data)
        exchange.publish('the.routing.register', {message:data,status:"I am Ok"},{mandatory:true},function(result) {
            console.log('newReg: result of publish (false means success) = ' + result);
        })
    })

    app.post('/newData',function(req,res) {
        console.log("inside /newData")
        var data = req.body.data
        console.log("new Data = " + data)
        exchange.publish('*.routing.key', {message: data,status:"I am Ok"},{mandatory:true},function(result) {
            console.log('newData: result of publish (false means success) = ' + result);
        })
    })

    app.post('/newMessage',function(req,res) {
        console.log('Inside /newMessage');
        var newMessage = req.body.data;
        console.log('newMessage = ' + newMessage);
        exchange.publish('*.routing.key', {message: newMessage,status:"I am Ok"},{mandatory:true},function(result) {
            console.log('newMessage: result of publish (false means success) = ' + result);
        })
        res.redirect('/message-service');
    });

    //app.post('/json-mirror',function(req,res) {
    //	var newMessage = req.body.data;
    //	res.json(newMessage);
    //});

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

    app.get('/getDbByView', function(req,res) {
        console.log('Inside getDbByView')
        getDBContentsByView(app.register,'listDB','listDB',function(err,results) {
            //console.log(results)
        })
    })


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
            //publish back on the ack exchange the message just inserted...might want to revise in the future to have rabbit
            //do the ack/error processing
            var options = {'passive':false}
            options['passive'] = data.node.protocol.messenger.on_ack.manageExchange
            var exchange = app.rabbitMqConnection.exchange(data.node.message.protocol.messenger.on_publish.exchange,options)
            exchange.publish(data.node.protocol.messenger.on_ack.routing_key,{message: data},{mandatory:true},function(result) {
                console.log('insertData: result of publish (false means success) = ' + result);

                //TODO: this might be the place to clean up the just created exchange?
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
            //publish back on the ack the registration received
            var exchange = app.rabbitMqConnection.exchange(data.node.protocol.messenger.on_publish.exchange,{'passive':'true'})
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

    var getDBContentsByView = function(db,id,view,callback) {

        db.view('_design/'+view, view, function(err, body) {
            var results = new Array()
            if (!err) {
                body.rows.forEach(function(doc) {
                    console.log(doc.value)
                    results.push(doc.value)
                })
                callback(0,results)
            } else {
                callback(err,0)
            }
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


}
