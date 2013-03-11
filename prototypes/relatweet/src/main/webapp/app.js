
/**;
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , redis = require("redis")
  , client = redis.createClient()
  , uuid = require('node-uuid');


/**
 * Add the FIFO functionality to Array class.
 **/
Array.prototype.store = function (info) {
  this.push(info);
}

Array.prototype.fetch = function() {
  if (this.length <= 0) { return ''; }  // nothing in array
  return this.shift();
}

Array.prototype.display = function() {
  return this.join('\n');
}


var app = module.exports = express.createServer();

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.cookieParser());
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

app.configure('production', function(){
  app.use(express.errorHandler());
});

function get_all_data(fn) {
	console.log("Get all data");
	console.log(client.get("test"));
	var words = {};
    client.smembers('words', function(err, resp){
   		var ids = resp;
		var readed = 0;
		for(var i = 0 ; i < ids.length ; i++) {
			client.get(ids[i], function(err, resp) {
				words[ids[readed]] = JSON.parse(resp);
				readed ++;
				if(readed == ids.length)
					fn(words);
			});
		}
    });
}



app.get('/', function(req, res) {
	var data = {}
	
	client.hkeys("hash key", function (err, replies) {
	    if (err) {
	        return console.error("error response - " + err);
	    }

	    console.log(replies.length + " replies:");
	    replies.forEach(function (reply, i) {
	        console.log("    " + i + ": " + reply);
	    });
	});
	
	res.render('index.jade', {data:data});
	
});

app.listen(3000, function(){
  console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
});

console.log(client.get("test"));
