/**
 * Created by rsie on 5/2/2014.
 */

var mongoose = require('mongoose')
    ,fs = require('fs')

function DomainCompiler(db,collection,schemaName) {
    this.schemaName = schemaName
    this.coll       = collection
    this._mongoose  = db
}

DomainCompiler.prototype = {

    createModel: function (collection,schemaName) {
        var schema = fs.readFileSync('./'+this.schemaName+".schema','utf8')
        //create the schema
        console.log("createModel: coll = " + collection)
        console.log("createModel: schema = " + JSON.stringify(schema))
        var modelSchema = mongoose.Schema(schema)
        console.log("createModel: modelSchema = " + modelSchema)
        var model = this._mongoose.model(collection, modelSchema)
        console.log("createModel: res = " + model)
        return model
    }
}

module.exports = DomainCompiler

