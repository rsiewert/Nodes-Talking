/**
 ___  ____  ____  ____  _  _  ____  ____       ___  ____  ____  ___
 /$$$$$$              /$$           /$$$$$$$                  /$$$$$$       /$$       /$$$$$$$$                    /$$
/$$__  $$            | $$          | $$__  $$                |_  $$_/      | $$      |__  $$__/                   | $$
| $$  \__/  /$$$$$$  /$$$$$$        | $$  \ $$ /$$   /$$        | $$    /$$$$$$$         | $$  /$$$$$$   /$$$$$$$ /$$$$$$
| $$ /$$$$ /$$__  $$|_  $$_/        | $$$$$$$ | $$  | $$        | $$   /$$__  $$         | $$ /$$__  $$ /$$_____/|_  $$_/
| $$|_  $$| $$$$$$$$  | $$          | $$__  $$| $$  | $$        | $$  | $$  | $$         | $$| $$$$$$$$|  $$$$$$   | $$
| $$  \ $$| $$_____/  | $$ /$$      | $$  \ $$| $$  | $$        | $$  | $$  | $$         | $$| $$_____/ \____  $$  | $$ /$$
|  $$$$$$/|  $$$$$$$  |  $$$$/      | $$$$$$$/|  $$$$$$$       /$$$$$$|  $$$$$$$         | $$|  $$$$$$$ /$$$$$$$/  |  $$$$/
\______/  \_______/   \___/        |_______/  \____  $$      |______/ \_______/         |__/ \_______/|_______/    \___/
                                              /$$  | $$
                                             |  $$$$$$/
                                              \______/
**/
var frisby      = require('frisby')
    ,Db         = require('../../db/db')
    ,fs         = require('fs')
    ,log4js     = require('log4js')
    ,logger     = log4js.getLogger('stout')
    ,uuid       = require('node-uuid')

var boot = fs.readFileSync('./json/bootstrap.json','utf8')
var config = JSON.parse(boot)
var id = uuid.v4()   // Math.floor(Math.random()*1000001)
var Id = id

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
            logger.debug("Result: " + JSON.stringify(result))
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
})
