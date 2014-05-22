/**

 /$$      /$$                                                                             /$$$$$$$$                    /$$
| $$$    /$$$                                                                            |__  $$__/                   | $$
| $$$$  /$$$$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$$  /$$$$$$          | $$  /$$$$$$   /$$$$$$$ /$$$$$$
| $$ $$/$$ $$ /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$ /$$_____/ /$$__  $$         | $$ /$$__  $$ /$$_____/|_  $$_/
| $$  $$$| $$| $$  \ $$| $$  \ $$| $$  \ $$| $$  \ $$| $$  \ $$|  $$$$$$ | $$$$$$$$         | $$| $$$$$$$$|  $$$$$$   | $$
| $$\  $ | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$ \____  $$| $$_____/         | $$| $$_____/ \____  $$  | $$ /$$
| $$ \/  | $$|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$/|  $$$$$$/ /$$$$$$$/|  $$$$$$$         | $$|  $$$$$$$ /$$$$$$$/  |  $$$$/
|__/     |__/ \______/ |__/  |__/ \____  $$ \______/  \______/ |_______/  \_______/         |__/ \_______/|_______/    \___/
                                  /$$  \ $$
                                 |  $$$$$$/
                                  \______/

 **/



var Db = require('../../db/db')
    ,fs = require('fs')

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
var result = {}

describe("Connect/save to mongoDB via Mongoose -- create a schema object and persist to the db", function() {
    var mongoose = undefined
    var domainModel = undefined
    var domainInstance = undefined
    mongoose = new Db('mongoose')
    console.log("collection = " + config.collection)
    mongoose.connect(config.collection.toLowerCase())
    var id = undefined

    it("should assert not undefined if connection was successful", function () {
        //start mongoose ORM
        expect(mongoose).not.toBe(undefined)
    })

    it("should create an instance on the " + config.collection + " collection", function () {
        domainModel = mongoose.getModel(config.collection)
        console.log("domainModel: " + domainModel)
        id = Math.floor(Math.random()*1000001)
        var theId = 'server@' + id
        domainInstance = new domainModel({"data.message.node.nodeId": theId})
        domainInstance.title = 'My Title'
        domainInstance.description = "I hope this helps...take my wife, please Hennie Youngman"
        domainInstance.data.message.node.actsAs = "SERVER"
        domainInstance.data.message.node.location.latitude = "58"
        domainInstance.data.message.updated = Date.now()
        expect(domainInstance).not.toBe(undefined)
    })

    it("should persist the result of the schema obj in the previous it section",function() {
        console.log("domainInstance = " + JSON.stringify(domainInstance))
        console.log("find: data.message.node.nodeId = " + domainInstance.data.message.node.nodeId)
        mongoose.saveWithCallback(config.collection,domainInstance,function(err,result) {
            if(err) {
                throw err
            }
            console.log("Result: " + result)
            domainModel.find({"data.message.node.nodeId":domainInstance.data.message.node.nodeId}, function (err, docs) {
                console.log("len = " + docs.length)
                docs.forEach(function (doc) {
                    console.log("doc: " + JSON.stringify(doc))
                    result = doc.data.message.node.nodeId
                    console.log("result nodeId = " + JSON.stringify(result))
                })
                mongoose.close()
                expect(result.Saved.data.message.node.nodeId).toEqual({"data.message.node.nodeId": 'server@' + id})
            })
        })
    })
})

