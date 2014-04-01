var mongojs = require('mongojs')
    ,nano = require('nano')('http://localhost:5984');


// DbModule - the object you usually create
//               in your "end-user" scripts:
//
// <SCRIPT type="text/javascript">

//
//    var db = new DbModule();
//    ...
//
// </SCRIPT>
//
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
        if(container === 'mongodb')
            return new MongoDB();

        else if(container === 'couchdb')
            return new CouchDB();

        return null;
    },

  // Functions "exported" by the DbModule abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________
    connect: function(collection)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl) {
            this._impl.connect(collection);     // Forward request to implementer
            return true
        } else
            return false
    },
    getAll : function(collection)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getAll(collection);     // Forward request to implementer
    },
    getByIds : function(collection,ids)
    {
        // Check if any implementor is bound
        if(this._impl)
            this._impl.getByIds(err,ids);     // Forward request to implementer
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
            var result = this._impl.create();     // Forward request to implementer
            return result
        }
        return null
    },
    remove : function(collection,id)
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.remove();     // Forward request to implementer
    }
};

// This is the set of concrete implementers:
//                             ___________________________
//____________________________/     Implementations       \__________________________

//this is the first implementer
function MongoDB()
{
    this._mongodb  = null
}

MongoDB.prototype = {

    getAll: function(collection)
    {
        var coll = this._mongodb.collection(collection);

        coll.find().sort({name:1},function(err, docs) {
            // docs is an array of all the documents in mycollection
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i]._id + " name = " + docs[i].name)
        })
        console.log("MongoDB.getAll")
    },
    getByIds: function(collection,ids)
    {
        console.log("MongoDB: create method")
        var coll = this._mongodb.collection(collection)
        coll.find({_id:id}),function(err,docs) {
            for(var i=0;i<docs.length;i++)
                console.log("doc = " + docs[i])
        }
        console.log("MongoDB.getByIds")
    },
    connect: function(collection) {
        this._mongodb = mongojs(collection);
    },
    remove: function(collection,id) {
        console.log("MongoDB: remove method")
    },
    create: function(collection) {
    }
}

// This is the second implementer:
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