/**

 /$$$$$$$                        /$$
| $$__  $$                      | $$
| $$  \ $$  /$$$$$$  /$$   /$$ /$$$$$$    /$$$$$$   /$$$$$$$
| $$$$$$$/ /$$__  $$| $$  | $$|_  $$_/   /$$__  $$ /$$_____/
| $$__  $$| $$  \ $$| $$  | $$  | $$    | $$$$$$$$|  $$$$$$
| $$  \ $$| $$  | $$| $$  | $$  | $$ /$$| $$_____/ \____  $$
| $$  | $$|  $$$$$$/|  $$$$$$/  |  $$$$/|  $$$$$$$ /$$$$$$$/
|__/  |__/ \______/  \______/    \___/   \_______/|_______/


**/

var fs = require('fs')
    ,log4js     = require('log4js')
    ,logger     = log4js.getLogger('stout')

module.exports = function(app, db) {


    // REST: Endpoints defined by the Routes abstraction:
    //                                 __________________
    //________________________________/   Client API     \___________________________________

    // GET:
    //                                     __________________
    //____________________________________/   GET API        \___________________________________

    app.get('/', function (req, res) {
        logger.debug("inside get /");
        var coll = req.params.collection

        //this is now using the DbModule
        //db.getAll('register')
        //db.coll.find()

        res.render('index',
            {
                title: 'Welcome to a NodesTalking Server: ' + app.connectionStatus
            })
    })

    app.get('/getAll/:model',function(req,res) {
        var model = req.params.model
        //var logger = log4js.getLogger('stdout')
        db.getAll(model,function(err,docs) {
            if(err)
                throw err
            logger.debug("Count = " + docs.length)
            if(!docs.length)
                docs = {}
            for(var i=0;i<docs.length;i++) {
                logger.debug("doc = " + JSON.stringify(docs[i]))
            }
            res.json(docs)
        })
    })

    app.get('/getById/:model/:id',function(req,res) {
        var model = req.params.model
        var id = req.params.id
        logger.debug("\ngetById: id: %s",id)
        logger.debug("\ngetById: model: %s",model)
        db.getById(model,id,function(err,doc) {
            if(doc == null) {
                logger.debug("doc = null")
            } else {
                logger.debug("\n\napp.get: json = " + JSON.stringify(doc) + "\n\n")
                // logger.debug("\nresult = " + doc.data.message.node.nodeId)
                res.json({Id: doc.data.message.node.nodeId})
            }
        })
    })

    // POST:
    //                                     __________________
    //____________________________________/   POST API       \___________________________________

    app.post('/register',function(req,res) {
        //this rest api needs to save this registration to the db
        logger.debug("/register : model:  " + JSON.stringify(req.body.data))
        var instance = req.body.data
        db.save('Registration',instance)
        res.json({"result": "Ok"})
    })

    app.post('/json-mirror',function(req,res) {
        logger.debug("json-mirror: req = "+ JSON.stringify(req.body.data))
        res.json({"result": req.body.data})
    })


//tests are good up to here... 0000000000000000000000000000000000000000000


    app.get('/devices/docs/:nodeType',function(req,res) {
        logger.debug("inside routes: get by node type...")
        logger.debug("node type = " + req.params.nodeType)
        db.getNodeByType('register',req.params.nodeType)
        res.send("Ok")
    })


    // PUT:
    //                                     __________________
    //____________________________________/   PUT API        \___________________________________

    app.post('/newReg',function(req,res) {
        logger.debug("inside /newReg")
    })

    app.post('/newData',function(req,res) {
        logger.debug("inside /newData")
    })

    app.post('/newMessage',function(req,res) {
        logger.debug('Inside /newMessage');
    });

//    app.post('/json-mirror',function(req,res) {
////    	var newMessage = req.body.data;
////    	res.json(newMessage);
//    });

    app.get('/message-service',function(req,res) {
        logger.debug('inside get message-service');
        res.render('message-service',
            {
                title:'Welcome to the messaging service',
                sentMessage: ''
            })

    })

    app.get('/listDB/:type',function(req,res) {
    })

    app.get('/listDB',function(req,res) {
    })

    app.get('/listRegisterDB',function(req,res) {
    })

    app.get('/listRawJson', function(req,res) {
    })

    app.get('/getDbByView', function(req,res) {
        logger.debug('Inside getDbByView')
    })

    // Deletes:
    //                                     __________________
    //____________________________________/   DELETE API     \___________________________________









//****************************Private methods************************************

    var insertData = function(db,data) {
        logger.debug("insertData: data = " + data.node.protocol.messenger.on_ack.exchange)
        db.insert({"data": {"message":data,"status":data.status}},function(err,body,header) {
            if(err) {
                logger.debug("err.insert = " + err.message)
                return
            }
            logger.debug("you have inserted a message: ")
            logger.debug(body)
            //publish back on the ack exchange the message just inserted...might want to revise in the future to have rabbit
            //do the ack/error processing
            var options = {'passive':false}
            options['passive'] = data.node.protocol.messenger.on_ack.manageExchange
            var exchange = app.rabbitMqConnection.exchange(data.node.message.protocol.messenger.on_publish.exchange,options)
            exchange.publish(data.node.protocol.messenger.on_ack.routing_key,{message: data},{mandatory:true},function(result) {
                logger.debug('insertData: result of publish (false means success) = ' + result);

                //TODO: this might be the place to clean up the just created exchange?
            })
        })
    }


    var insertReg = function(db,data,id) {
        logger.debug("insertReg: data = " + data.node.protocol.messenger.on_ack.exchange)
        db.insert({"data": {"message":data,"status":data.status}},id,function(err,body,header) {
            if(err) {
                logger.debug("err.insert = " + err.message)
                return
            }
            logger.debug("you have inserted a registration: ")
            logger.debug(body)
            //publish back on the ack the registration received
            var exchange = app.rabbitMqConnection.exchange(data.node.protocol.messenger.on_publish.exchange,{'passive':'true'})
            exchange.publish(data.node.protocol.messenger.on_ack.routing_key,{message: data},
                {mandatory:true},function(result) {
                    logger.debug('insertReg: result of publish (false means success) = ' + result);
                })
        })
    }

    var getDBContents = function(db,type,callback) {
        var params = {include_docs:true}

        db.list(params,function(err,body,headers) {
            var results = []
            if(!err) {
                body.rows.forEach(function(doc) {
                    logger.debug(doc)
                    if(type == 'raw') {
                        results.push(doc.doc.data.message)
                    } else {
                        if(doc.doc.message != undefined || doc.doc.data != undefined) {
                            if(doc.doc.message == undefined) {
                                logger.debug(doc.doc.data.message)
                                if(type != 'raw') {
                                    results.push(doc.doc.data.message)
                                }
                            }
                            else {
                                logger.debug(doc.doc.message)
                                results.push(doc.doc.message)
                            }
                        }
                    }
                })
            } else {
                //logger.debug("err.getDBContents = " + err.message)
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
                    logger.debug(doc.value)
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
                    logger.debug(response.content.data);
                    callback(0,response.content.data)
                },
                // Any other response means something's wrong
                response: function(response) {
                    logger.debug("Houston, we have a problem...")
                    callback("Houston we have a problem...",{})
                }
            }
        })
    }


}
