
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


app.get('/', function(req, res) {
	var data = {};
	client.keys('*', function(err, resp){
		var ids = resp;
		var readed = 0;
		for(var i = 0 ; i < ids.length ; i++) {
			client.get(ids[i], function (err, reply) {
			    if(reply != null){
			    data[ids[readed]] = reply.toString();
		    	    readed ++;
					if(readed == ids.length)
					    res.render('index.jade', {data:data});

}
		         });
		}
	});
});

app.listen(3000, function(){
  console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
});





