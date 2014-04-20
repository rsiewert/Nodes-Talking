var mongojs = require('mongojs')
    ,nano = require('nano')('http://localhost:5984');

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
    getAll : function(collection,callback)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getAll(collection,callback);     // Forward request to implementer
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
    remove : function(collection,id)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.remove();     // Forward request to implementer
    }
}

//                             ___________________________
//____________________________/     Implementations       \__________________________

//                                  ___________________________
//_________________________________/       Raw MongoDB         \__________________________

function MongoDB()
{
    this._mongodb  = null
}

MongoDB.prototype = {

    initialize: function(db) {
        this._mongodb = db
    },
    connect: function(collection) {
        var coll = this._mongodb.collection(collection)
    },
    getAll: function(collection,callback) {
        var coll = this._mongodb.collection(collection)
        coll.find().toArray(function(err,docs) {
            if(err)
                callback(err,null)
            callback(null,docs)
        })
    },
    getById: function(collection,Id,callback) {
        var coll = this._mongodb.collection(collection)
        coll.findOne({"message.nodeId":Id},function(err,document) {
            if(err)
                callback(err,null)
            console.log("Db: document = " + JSON.stringify(document))
            callback(null,document)
         })
     },
    save: function(collection,doc) {
        console.log("In Save Method")
        var coll = this._mongodb.collection(collection)
        coll.save(doc,function(err,document) {
            console.log("wrote this doc: " + JSON.stringify(document))
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
        console.log("MongoDB.connect")
    },
    getAll: function(collection)
    {
        var coll = this._mongojs_db.collection(collection);

        coll.find().sort({name:1},function(err, docs) {
            // docs is an array of all the documents in mycollection
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i]._id + " name = " + docs[i].name)
        })
        console.log("MongoJS_DB.getAll")
    },
    getById: function(collection,id)
    {
        console.log("MongoDB: getById method")
        var coll = this._mongojs_db.collection(collection)
        console.log("getById: id = " + id)
        console.log("coll = " + collection)
        coll.find({_id:this._mongojs_db.ObjectId(id)},function(err,docs) {
            console.log("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        console.log("MongoJS_DB.getById")
    },
    getNodeByType: function(collection,type) {
        console.log("MongoDB: getNodeByType")
        var coll = this._mongojs_db.collection(collection)
        coll.find({type:type},function(err,docs) {
            console.log("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        console.log("MongoJS_DB.getNodeByType")
    },
    getByIds: function(collection,ids)
    {
        //should return an array of json objects that match the ids
        console.log("MongoDB: getByIds method")
        var coll = this._mongojs_db.collection(collection)
        console.log("getByIds: ids = " + ids)
        console.log("coll = " + collection)

        coll.find({_id:this._mongojs_db.ObjectId(ids)},function(err,docs) {
            console.log("docs.length = " +docs.length)
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i]._id + " name = " +docs[i].name)
        })
        console.log("MongoJS_DB.getByIds")
    },
    remove: function(collection,id) {
        console.log("MongoJS_DB.remove")
    },
    create: function(collection) {
        console.log("MongoJS_DB.create")
    },
    save: function(collection,doc) {
        var coll = this._mongojs_db.collection(collection)
        coll.save(doc,{"myname":"Leon Siewert"},function(err,document) {
            if(err)
                throw err
            console.log("Saved: doc = " + JSON.stringify(document))
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
        console.log("CouchDB.getAll")
    },
    getByIds: function(collection,ids)
    {
        console.log("CouchDB.getByIds")
    }
}

// This is the set of private objects:
//                             ___________________________
//____________________________/     Private Objects       \__________________________

var getSomething = function() {

}

module.exports = DbModule