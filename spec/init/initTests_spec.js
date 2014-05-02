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
    console.log("collection = " + config.register)
    json.data.message.node.nodeId = config.nodeId

    console.log("json = " + json.data.message.node.nodeId)
    it("should connect to mongodb using native driver and save a document needed for getById test",function() {
        MongoClient.connect('mongodb://localhost:27017/'+config.register, function(err, db) {
            if(err) throw err
            db.createCollection(config.register,function(err,collection) {
                collection.remove({"data.message.node.nodeId":json.data.message.node.nodeId},function(err,removed) {
                    console.log("removed: " + removed)
                })
                collection.save(json,function(err,document) {
                    console.log("wrote this doc: " + JSON.stringify(document) + " on collection: " + config.register)
                    db.close()
                })
            })
            expect(json.data.message.node.nodeId).toEqual(config.nodeId)
        })
    })
})
