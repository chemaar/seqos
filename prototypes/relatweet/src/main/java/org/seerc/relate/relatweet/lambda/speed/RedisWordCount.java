package org.seerc.relate.relatweet.lambda.speed;

import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class RedisWordCount extends BaseBasicBolt {
	protected static Logger logger = Logger.getLogger(RedisWordCount.class);
    Jedis jedis;
    
    
    
    @Override
	public void prepare(Map stormConf, TopologyContext context) {
		super.prepare(stormConf, context);
		this.jedis = new Jedis((String) stormConf.get("REDIS_HOST"));
		//FIXME: Remove previous
	}


	@Override
	public void cleanup() {
		super.cleanup();				
		logger.info(jedis.get("test"));
		logger.info(jedis.get("is"));
	}


	public void execute(Tuple tuple, BasicOutputCollector collector) {
        String word = tuple.getString(0);
        String value = jedis.get(word);
        int count = 0;
        if (value != null) count = Integer.valueOf(value);
        count++;
        jedis.set(word, String.valueOf(count));
        collector.emit(new Values(word, count));
    }


	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	    declarer.declare(new Fields("word", "count"));
		
	}

    
    

}
