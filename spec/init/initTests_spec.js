/**
 * Created by rsie on 4/24/2014.
 */
var fs = require('fs')
    ,MongoClient = require('mongodb').MongoClient // Driver for connecting to MongoDB;


describe("Initialize the db to allow other tests to run", function() {
    var boot = fs.readFileSync('./json/bootstrap.json','utf8')
    var config = JSON.parse(boot)
    var theData = fs.readFileSync('./json/register.json','utf8')
    var json = JSON.parse(theData)
    json.nodeId = config.nodeId
    //json.myId = "0.23dkdjdj"

    console.log("json = " + json.nodeId)

    MongoClient.connect('mongodb://localhost:27017/'+config.collection, function(err, db) {
        var coll = db.collection(config.collection)
        //coll.remove({data: {nodeId:json.nodeId}})
        coll.remove({data:{nodeId:json.nodeId}})
        console.log("json = " + JSON.stringify(json))
        coll.save(json,function(err,document) {
            console.log("wrote this doc: " + JSON.stringify(document))
            db.close()
        })
    })
})
