var Db = require('../../db/db')

describe("Connect/save to mongoDB via Mongoose", function() {
    var mongoose = undefined
    mongoose = new Db('mongoose')
    mongoose.connect('mongoose_test')
    it("should return true if connection was successful", function() {
        //start mongoose ORM
        expect(mongoose).not.toBe(undefined)
    })

    it("should create a schema on the mongoose_test collection", function() {
        var schema = {
            name: {
                first: String,
                last: { type: String, trim: true }
            },
            age: { type: Number, min: 0}
        }
        var domainObj = mongoose.createDomain('test_coll',schema)
        console.log("domainObj: " + domainObj)
        var obj = new domainObj({name: {first:"Leon",last:"Siewert"},
                                age:63
        })
        console.log("obj = " + obj)
        mongoose.save(null,obj)
       // console.log("find: " + (JSON.stringify(domainObj.find({name:"Leon"}))))
    })
})

