var Db = require('../../db/db')
    ,fs = require('fs');

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
var theSchema = fs.readFileSync('./json/register.schema','utf8')

var result = {}

describe("Connect/save to mongoDB via Mongoose -- create a schema object and persist to the db", function() {
    var mongoose = undefined
    mongoose = new Db('mongoose')
    console.log("collection = " + config.collection)
    mongoose.connect(config.collection)

    it("should assert not undefined if connection was successful", function () {
        //start mongoose ORM
        expect(mongoose).not.toBe(undefined)
    })

    it("should create a schema on the " + config.collection + " collection", function () {
        domainObj = mongoose.createModel(config.collection, JSON.parse(theSchema))
        console.log("domainObj: " + domainObj)
        // var obj = new domainObj({name: {first: "Leon", last: "Siewert"}, age: 63})
        obj = new domainObj({"data.message.node.nodeId": "server@LeonSiewert"})
        obj.title = 'My Title'
        obj.description = "I hope this helps...take my wife, please Hennie Youngman"
        obj.data.message.node.actsAs = "SERVER"
        obj.data.message.node.location.latitude = "52"
        obj.data.message.updated = Date.now()
        expect(domainObj).not.toBe(undefined)
    })

    it("should persist the result of the schema obj in the previous it section, then retrieve and verify",function() {
        mongoose.save(null, obj)
        // console.log("find: " + (JSON.stringify(domainObj.find({name:"Leon"}))))
        domainObj.find({"data.message.node.nodeId": "server@LeonSiewert"}, function (err, docs) {
            console.log("len = " + docs.length)
            docs.forEach(function (doc) {
                console.log("doc: " + JSON.stringify(doc))
                result = doc.node.nodeId
                console.log("result = " + result)
            })
            mongoose.close()
            expect(result).toEqual({"data.nodeId": "server@LeonSiewert"})
        })
    })
})

