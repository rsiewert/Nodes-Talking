/**
 * Test to validate getting a document by its Id
 */
var frisby = require('frisby')
    ,Db = require('../../db/db')
    ,fs = require('fs');

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
var id = Math.floor(Math.random()*1000001)
var Id = 'server@'+id

describe('persist a document to the db and then run frisby getById',function() {
    var mongoose = new Db('mongoose')
    it("expect to connect to the database",function() {
        mongoose.connect(config.model.toLowerCase())
        expect(mongoose).not.toBe(undefined)
    })

    it('create a document and persist to the database',function() {
        var model = mongoose.getModel(config.model)
        var obj = new model({"data.message.node.nodeId": Id})
        mongoose.saveWithCallback(config.model,obj,function(err,result) {
            if(err) {
                throw err
            }
            console.log("Result: " + result)
            frisby.create("Get Device By Id from collection: "+config.collection.toLowerCase())
               .get('http://localhost:3000/getById/'+config.collection+'/'+Id)
               .expectStatus(200)
               .expectHeaderContains('Content-Type','json')
               .expectJSON({
                   Id:Id
               })
            .toss()
            mongoose.close()
        })
    })

 //      .expectJSON({
//            Id:Id
//        })
//    frisby.create("Get Device By Id from collection: "+config.collection.toLowerCase())
//        .get('http://localhost:3000/getById/'+config.collection+'/'+Id)
//        .expectStatus(200)
//        .expectHeaderContains('Content-Type','json')
//    .toss()
})
