var Db = require('../../db/db')


describe("Connect to mongoDB via Mongoose", function() {
    var mongooseDB = new Db('mongoose')
    var conn = undefined
    it("should return true if connection was successful", function() {
        //start mongoose ORM
        conn = mongooseDB.connect('mongoose_test')
        expect(conn).not.toBe(undefined)
    })

    it("should create a schema on the mongoose_test collection", function() {
        //create the schema
        if(conn != undefined) {
            var userSchema = new conn.Schema({
                name: {
                    first: String,
                    last: { type: String, trim: true }
                },
                age: { type: Number, min: 0}
            })
            // Compiles the schema into a model, opening (or creating, if
            // nonexistent) the 'PowerUsers' collection in the MongoDB database
            var PUser = conn.model('PowerUsers', userSchema)

            // Clear out old data
            PUser.remove({}, function(err) {
                if (err) {
                    console.log ('error deleting old data.')
                }
            })
        }
    })
})

