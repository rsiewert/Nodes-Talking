/**
 * Created by rsie on 5/2/2014.
 */

var fs = require('fs')
    ,generator = require('mongoose-gen')
    ,Registration = require('../models/registration')

var DomainFactory = (function() {
    var _domainMap = {}

    console.log("DomainFactory is Alive!")

    var _createModel = function (collection,schema,db) {
        //create the model
        console.log("DomainFactory:createModel: coll = " + collection)
        var model = db.createModel(collection, schema)
        console.log("createModel: model = " + model)
        return model
    }

    return { // public interface
        getModel: function (model,db) {   /*model = string, e.g. Registration*/
            if (_domainMap[model] == undefined) {
               // var theSchema = fs.readFileSync('./models/' + model.toLowerCase() + '.schema', 'utf8')
                var collection = model.toLowerCase()
                console.log("here I am")
                var dModel = _createModel(collection, JSON.parse(theSchema),db)
                _domainMap[model] = dModel
            }
            return _domainMap[model]
        }
    }
})()








//function DomainFactory(db) {
//    this._db = db
//    this._domainMap = {}
//}
//
//DomainFactory.prototype = {
//    getModel: function (model /*string, e.g. Registration*/) {
//        if(this._domainMap[model] == undefined) {
//            var theSchema = fs.readFileSync('./domain/'+model+'.schema','utf8')
//            var collection = model.toLowerCase()
//            var dModel = this.createModel(collection,JSON.parse(theSchema))
//            this._domainMap[model] = dModel
//        }
//        return this._domainMap[model]
//    },
//    createModel: function (collection,schema) {
//        //create the model
//        console.log("DomainFactory:createModel: coll = " + collection)
//        console.log("DomainFactory:createModel: schema = " + JSON.stringify(schema))
//        var model = this._db.createModel(collection,schema)
//        console.log("createModel: model = " + model)
//        return model
//    }
//}

module.exports = DomainFactory

