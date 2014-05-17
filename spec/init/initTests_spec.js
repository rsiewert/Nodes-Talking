/**
 * Created by rsie on 4/24/2014.
 */
var Db = require('../../db/db')
    ,fs = require('fs')
    ,domainFactory = require('./domain/domainfactory')


describe("Initialize the db to allow other tests to run", function() {
    var boot = fs.readFileSync('./json/bootstrap.json','utf8')
    var config = JSON.parse(boot)
    console.log("collection = " + config.Registration.toLowerCase())
    json.data.message.node.nodeId = config.nodeId
    var mongoose = new Db('mongoose')
    mongoose.connect(config.Registration.toLowerCase())

    console.log("json = " + json.data.message.node.nodeId)

    it("should connect to mongoose and save a document needed for getById test",function() {
        MongoClient.connect('mongodb://localhost:27017/'+config.Registration, function(err, db) {
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
