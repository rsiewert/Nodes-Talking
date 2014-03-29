/**
 * Created by rsie on 3/27/2014.
 */

// Abstraction - the object you usually create
//               in your "end-user" scripts:
//
// <SCRIPT type="text/javascript">

//
//    var abstr = new Abstraction();
//    ...
//
// </SCRIPT>
//
function Abstraction()
{
// Implementation reference:

  this._impl = null;

// Setup procedure:

  this._SetImplementation(this._EstablishImplementor('mongodb'));

  return this;
}

Abstraction.prototype = {

  _SetImplementation: function(implementor)
  {
    this._impl = null;
    if(implementor) this._impl = implementor;
  },

  // EstablishImplementor - function that creates
  // the Concrete Implementor and binds it to Abstraction.
  // This is the very method to place your
  // browser/feature/object detection code.
  _EstablishImplementor: function(container)
  {
    if(container === 'mongodb')
      return new ImplementationOne();

    else if(container === 'couchdb')
      return new ImplementationTwo();

    // ...

    return null;
  },

  // Function "exported" by the Abstraction:
  FuncOne: function()
  {
    // Check if any implementor is bound and has the required method:
    if(this._impl && this._impl.FuncOne)
       this._impl.FuncOne();     // Forward request to implementor
  }
};

// ...

// This is the first in the set of concrete implementors:
function ImplementationOne()
{
// ...
}

ImplementationOne.prototype = {

  // This "public" function is directly called by Abstraction:
  FuncOne: function()
  {
    // ...
  }

// ...
}

// This is the second implementor:
function ImplementationTwo()
{
// ...
}

ImplementationTwo.prototype = {

  FuncOne: function()
  {
    // ...
  }

// ...
}