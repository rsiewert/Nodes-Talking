/* Nodejs Test Rig for Central Server. */


var http = require('http'),
	assert = require('assert')	

var opts = {
	host: 'localhost',
	port: 3000,
	path: '/json-mirror',
	method:'POST',
	headers:{'Content-Type':'application/json'}
} 

/* Some test data sent from the system */
var sensor_data = {
	data: {
		type: 'temperature',
		value: 100,
		time: '12:36:05 August 12 2018'
	}
}
var myjson = 
	{"data": 
		{"message":"Let the games begin.","status":"I woke up this morning...","array":[{"myMessage1":"Here I am"}]}}

/* Test 1: Check if we can echo a JSON message containing test sensor data */
 

var req = http.request(opts, function(res) {
	res.setEncoding('utf8')
	
    /* Load up returned data as it comes back */
	var data = ""
	res.on('data', function(d) {
		console.log("inside res.on")
		data += d
	})
		
    /* At the end of the reply check the fields to make sure they match */
	res.on('end', function() {
		var ret_sensor_data = JSON.parse(data)    
		console.log('type = ' + ret_sensor_data.type)
		console.log('value = ' + ret_sensor_data.value)
		console.log('time = ' + ret_sensor_data.time)
		
		assert.equal(ret_sensor_data.type, sensor_data.data.type)
		assert.equal(ret_sensor_data.value, sensor_data.data.value)
		assert.equal(ret_sensor_data.time, sensor_data.data.time)
		
	})
})


/* The sensor data and send to the server */	 
req.write(JSON.stringify(sensor_data))
req.end()
