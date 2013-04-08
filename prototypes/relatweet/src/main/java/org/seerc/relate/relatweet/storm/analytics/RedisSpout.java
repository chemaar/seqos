package org.seerc.relate.relatweet.storm.analytics;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import redis.clients.jedis.Jedis;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class RedisSpout extends BaseRichSpout {
	Jedis jedis;
	String host; 
	int port;
	SpoutOutputCollector collector;

	@SuppressWarnings("rawtypes")
	public void open(Map stormConf, TopologyContext context,
			SpoutOutputCollector collector) {
		host = stormConf.get("redis-host").toString();
		port = Integer.valueOf(stormConf.get("redis-port").toString());
		this.collector = collector;
		reconnect();
	}

	private void reconnect() {
		jedis = new Jedis(host, port);
	}

	public void nextTuple() {
		String content = jedis.rpop("navigation");
		if(content==null || "nil".equals(content)) {
			try { Thread.sleep(300); } catch (InterruptedException e) {}
		} else {
			JSONObject obj=(JSONObject)JSONValue.parse(content);
			String user = obj.get("user").toString();
			String product = obj.get("product").toString();
			String type = obj.get("type").toString();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("product", product);		      
			collector.emit(new Values(user, null));
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("user", "otherdata"));
	}

}
