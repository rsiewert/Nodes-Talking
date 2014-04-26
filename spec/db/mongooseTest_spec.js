var Db = require('../../db/db')
    ,fs = require('fs');

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
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
        var schema = {
            name: {
                first: String,
                last: { type: String, trim: true }
            },
            age: { type: Number, min: 0}
        }
        var domainObj = mongoose.createModel(config.collection, schema)
        console.log("domainObj: " + domainObj)
        var obj = new domainObj({name: {first: "Leon", last: "Siewert"}, age: 63})
        console.log("obj = " + obj)
        mongoose.save(null, obj)
        // console.log("find: " + (JSON.stringify(domainObj.find({name:"Leon"}))))
        domainObj.find({"name.first": "Leon", "name.last": "Siewert"}, function (err, docs) {
            console.log("len = " + docs.length)
            docs.forEach(function (doc) {
                console.log("doc: " + JSON.stringify(doc))
                result = doc.name
                console.log("result = " + result)
            })
            mongoose.close()
            expect(result).toEqual({last: 'Siewert', first: 'Leon'})
        })
    })
})

