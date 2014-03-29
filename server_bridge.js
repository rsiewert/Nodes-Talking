/**
 * Created by rsie on 3/27/2014.
 */

// DevicesTalking - the object you usually create
//               in your "end-user" scripts:
//
// <SCRIPT type="text/javascript">

//
//    var dt = new DevicesTalking();
//    ...
//
// </SCRIPT>
//
function DbModule()
{
// Implementation reference:

  this._impl = null;

// Setup procedure:

  this._SetImplementation(this._EstablishImplementer('mongodb'));

  return this;
}

DbModule.prototype = {

    _SetImplementation: function(implementer)
    {
        this._impl = null;
        if(implementer) this._impl = implementer;
    },

    // EstablishImplementer - function that creates
    // the Concrete Implementer and binds it to DevicesTalking.
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

  // Functions "exported" by the DevicesTalking abstraction:
  //                                 __________________
  //________________________________/   Client API     \___________________________________

    getAll : function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.getAll();     // Forward request to implementer
    },

    getByIds : function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.getByIds();     // Forward request to implementer
    },

    update : function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.update();     // Forward request to implementer
    },

    create : function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.create();     // Forward request to implementer
    },

    remove : function()
    {
        // Check if any implementor is bound and has the required method:
        if(this._impl)
            this._impl.remove();     // Forward request to implementer
    }
};

// This is the first in the set of concrete implementers:
//                             ___________________________
//____________________________/     Implementations       \__________________________

function MongoDB()
{
}

MongoDB.prototype = {

  // This "public" function is directly called by DevicesTalking:
    getAll: function()
    {
      console.log("MongoDB.getAll")
    },
    getByIds: function()
    {
      console.log("MongoDB.getByIds")
    }


}

// This is the second implementer:
function CouchDB()
{
}

CouchDB.prototype = {

    getAll: function()
    {
        console.log("CouchDB.getAll")
    },
    getByIds: function()
    {
        console.log("MongoDB.getByIds")
    }
}

module.exports.dbmodule = new DbModule()