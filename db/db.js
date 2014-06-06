/**

 /$$$$$$$              /$$               /$$                                            /$$$$$$
| $$__  $$            | $$              | $$                                           /$$__  $$
| $$  \ $$  /$$$$$$  /$$$$$$    /$$$$$$ | $$$$$$$   /$$$$$$   /$$$$$$$  /$$$$$$       | $$  \__/  /$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$   /$$$$$$
| $$  | $$ |____  $$|_  $$_/   |____  $$| $$__  $$ |____  $$ /$$_____/ /$$__  $$      |  $$$$$$  /$$__  $$ /$$__  $$|  $$  /$$//$$__  $$ /$$__  $$
| $$  | $$  /$$$$$$$  | $$      /$$$$$$$| $$  \ $$  /$$$$$$$|  $$$$$$ | $$$$$$$$       \____  $$| $$$$$$$$| $$  \__/ \  $$/$$/| $$$$$$$$| $$  \__/
| $$  | $$ /$$__  $$  | $$ /$$ /$$__  $$| $$  | $$ /$$__  $$ \____  $$| $$_____/       /$$  \ $$| $$_____/| $$        \  $$$/ | $$_____/| $$
| $$$$$$$/|  $$$$$$$  |  $$$$/|  $$$$$$$| $$$$$$$/|  $$$$$$$ /$$$$$$$/|  $$$$$$$      |  $$$$$$/|  $$$$$$$| $$         \  $/  |  $$$$$$$| $$
|_______/  \_______/   \___/   \_______/|_______/  \_______/|_______/  \_______/       \______/  \_______/|__/          \_/    \_______/|__/


**/

var mongojs = require('mongojs')
    ,nano = require('nano')('http://localhost:5984')
    ,mongoose = require('mongoose')
    ,Registration = require('../models/registration')
    ,log4js     = require('log4js')
    ,logger     = log4js.getLogger('stout')

//                                 _______________________________
//________________________________/    Client Side Abstraction    \___________________________________

function DbModule(dbType)
{
// Implementation reference:

  this._impl = null;

// Setup procedure:

  this._SetImplementation(this._EstablishImplementer(dbType));

  return this;
}

DbModule.prototype = {

    _SetImplementation: function(implementer)
    {
        this._impl = null;
        if(implementer)
            this._impl = implementer;
    },

    // EstablishImplementer - function that creates
    // the Concrete Implementer and binds it to DbModule.
    // This is the very method to place your
    // browser/feature/object detection code.
    _EstablishImplementer: function(container)
    {
        if(container === 'mongojs_db')
            return new MongoJS_DB()
        else if(container === 'mongoose')
            return new Mongoose()
        else if(container === 'mongodb')
            return new MongoDB()
        else if(container === 'couchdb')
            return new CouchDB()

        return null;
    },

  // Functions "exported" by the DbModule abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________
    initialize: function(db) {
        if(this._impl) {
            this._impl.initialize(db)
        }
    },
    connect: function(collection)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl) {
            this._impl.connect(collection);     // Forward request to implementer
            return true
        } else
            return false
    },
    close: function() {
        if(this._impl) {
            this._impl.close()
        }
    },
    getAll : function(model,callback)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getAll(model,callback);     // Forward request to implementer
    },
    getById : function(collection,id,callback)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getById(collection,id,callback);     // Forward request to implementer
    },
    getByIds : function(collection,ids)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getByIds(collection,ids);     // Forward request to implementer
    },
    getNodeByType: function(collection,type)
    {
        if(this._impl)
            this._impl.getNodeByType(collection,type)
    },
    save : function(collection,doc)
    {
        if(this._impl)
            this._impl.save(collection,doc)
    },
    saveWithCallback : function(model,doc,callback) {
        if(this._impl)
            this._impl.saveWithCallback(model,doc,callback)
    },
    update : function(collection,doc)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.update(doc);     // Forward request to implementer
    },
    create : function()
    {
        // Check if any implementor is bound
        if(this._impl) {
            return this._impl.create();     // Forward request to implementer
        }
        return null
    },
    getModel: function(modelName) {
        if(this._impl) {
            return this._impl.getModel(modelName)
        }
    },
    createModel: function(coll,obj) {
        if(this._impl) {
            return this._impl.createModel(coll, obj)
        }
        return null
    },
    remove : function(collection,id)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.remove(collection,id);     // Forward request to implementer
    }
}

//                             ___________________________
//____________________________/     Implementations       \__________________________

//                                  ________________________
//_________________________________/       Mongoose         \__________________________

function Mongoose()
{
    this._mongoose = null
}

Mongoose.prototype = {
    connect: function (collection) {
        if(this._mongoose)
            return this._mongoose

        var uri = 'mongodb://localhost/' + collection
        this._mongoose = mongoose.connect(uri, function (err, res) {
            if (err) {
                logger.debug('ERROR connecting to: ' + uri + '. ' + err);
                return null
            } else {
                logger.debug('Succeeded connected to: ' + uri);

                return this._mongoose
            }
        })
    },
    getAll: function (modelName, callback) {
        var model = this._mongoose.model(modelName)
        model.find({},function(err,docs) {
            if(err) callback(err,null)
            callback(null,docs)
        })
    },
    getById: function (modelName, Id, callback) {
        var model = this._mongoose.model(modelName)
        if(model) {
            model.findOne({"data.message.node.nodeId": Id}, function (err, obj) {
                logger.debug(JSON.stringify(obj))
                callback(null,obj)
            })
        }
        else {
            callback(err,null)
        }
    },
    getModel: function(modelName) {
        var model = this._mongoose.model(modelName)
        if(model)
            return model
        else
            throw err
    },
    createModel: function (collection, schema) {
        //create the schema
        logger.debug("createModel: coll = " + collection)
        logger.debug("createModel: schema = " + JSON.stringify(schema))
        var userSchema = this._mongoose.Schema(schema)
        logger.debug("createModel: userSchema = " + userSchema)
        var model = this._mongoose.model(collection, userSchema)
        logger.debug("createModel: res = " + model)
        return model
    },
    save: function (modelName,doc) {
        if (doc != undefined) {
           logger.debug("save: doc = " + JSON.stringify(doc))
            //doc is the incoming data, so we have to retrieve a model and instantiate an instance with the incoming json doc
            var model = this._mongoose.model(modelName)
            logger.debug("save: model = " + model)
            var reg = new model(doc)
            logger.debug("save: reg = " + JSON.stringify(reg))
            reg.save(function (err,item,numberAffected) {
                if (err)
                    logger.debug("err in the save: " + err.message)
                else {
                    logger.debug("save: numberAffected: " + numberAffected)
                    logger.debug("save: saved item: " + item)
                }
            })
        }
    },
    saveWithCallback: function (modelName,doc,callback) {
        logger.debug("saveWithCallback: model doc = " + doc)
        var model = this._mongoose.model(modelName)
        logger.debug("saveWithCallback: model = " + model)
        var reg = new model(doc)
        logger.debug("saveWithCallback: reg = " + JSON.stringify(reg) )
        if (doc != undefined) {
            doc.save(function (err,item,numberAffected) {
                if (err) {
                    logger.debug("saveWithCallback: err in the save: " + err.message)
                    callback(err, null)
                }
                else {
                    logger.debug("numberAffected: " + numberAffected)
                    logger.debug("saved item: " + item)
                    callback(null,{"results.numberAffected":numberAffected,"Saved": item})
                }
            })
        }
    },
    close: function() {
        this._mongoose.disconnect(function() {
            logger.debug("Disconnected...")
        })
    }
}
//                                  ___________________________
//_________________________________/       Native MongoDB      \__________________________

function MongoDB()
{
    this._mongodb  = null
}

MongoDB.prototype = {

    //Native MongoDB has the db connection object in its callback(giving the db connection at the app level) so we need to just pass
    //that to this initialize function. All the other interfaces allow the db to be connected from anywhere in the code hierarchy
    initialize: function(db) {
        this._mongodb = db
    },
    connect: function(collection) {
        var coll = this._mongodb.collection(collection)
    },
    getAll: function(model,callback) {
        var model = this._mongodb.collection(model)
        model.find().toArray(function(err,docs) {
            if(err)
                callback(err,null)
            callback(null,docs)
        })
    },
    getById: function(collection,Id,callback) {
        var coll = this._mongodb.collection(collection)
        coll.findOne({"data.message.node.nodeId":Id},function(err,document) {
            if(err)
                callback(err,null)
            logger.debug("Db: document = " + JSON.stringify(document))
            callback(null,document)
         })
     },
    save: function(collection,doc) {
        logger.debug("In Save Method")
        var coll = this._mongodb.collection(collection)
        coll.save(doc,function(err,document) {
            logger.debug("wrote this doc: " + JSON.stringify(document))
        })
    }
}
//                                  ___________________________
//_________________________________/          MongoJS_DB       \__________________________

function MongoJS_DB()
{
    this._mongojs_db  = null
}

MongoJS_DB.prototype = {

    connect: function(collection) {
        this._mongojs_db = mongojs(collection);
        logger.debug("MongoDB.connect")
    },
    getAll: function(collection)
    {
        var coll = this._mongojs_db.collection(collection);

        coll.find().sort({name:1},function(err, docs) {
            // docs is an array of all the documents in mycollection
            for(var i=0;i<docs.length;i++)
                logger.debug("doc = " + docs[i]._id + " name = " + docs[i].name)
        })
        logger.debug("MongoJS_DB.getAll")
    },
    getById: function(collection,id)
    {
        logger.debug("MongoDB: getById method")
        var coll = this._mongojs_db.collection(collection)
        logger.debug("getById: id = " + id)
        logger.debug("coll = " + collection)
        coll.find({_id:this._mongojs_db.ObjectId(id)},function(err,docs) {
            logger.debug("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                logger.debug("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        logger.debug("MongoJS_DB.getById")
    },
    getNodeByType: function(collection,type) {
        logger.debug("MongoDB: getNodeByType")
        var coll = this._mongojs_db.collection(collection)
        coll.find({type:type},function(err,docs) {
            logger.debug("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                logger.debug("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        logger.debug("MongoJS_DB.getNodeByType")
    },
    getByIds: function(collection,ids)
    {
        //should return an array of json objects that match the ids
        logger.debug("MongoDB: getByIds method")
        var coll = this._mongojs_db.collection(collection)
        logger.debug("getByIds: ids = " + ids)
        logger.debug("coll = " + collection)

        coll.find({_id:this._mongojs_db.ObjectId(ids)},function(err,docs) {
            logger.debug("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                logger.debug("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        logger.debug("MongoJS_DB.getByIds")
    },
    remove: function(collection,id) {
        logger.debug("MongoJS_DB.remove")
    },
    create: function(collection) {
        logger.debug("MongoJS_DB.create")
    },
    save: function(collection,doc) {
        var coll = this._mongojs_db.collection(collection)
        coll.save(doc,{"myname":"Leon Siewert"},function(err,document) {
            if(err)
                throw err
            logger.debug("Saved: doc = " + JSON.stringify(document))
        })
    }
}

//                                  ___________________________
//_________________________________/          CouchDB          \__________________________

function CouchDB()
{
    this._couchdb = null
}

CouchDB.prototype = {

    getAll: function(collection)
    {
        logger.debug("CouchDB.getAll")
    },
    getByIds: function(collection,ids)
    {
        logger.debug("CouchDB.getByIds")
    }
}

// This is the set of private objects:
//                             ___________________________
//____________________________/     Private Objects       \__________________________

var getSomething = function() {

}

module.exports = DbModule