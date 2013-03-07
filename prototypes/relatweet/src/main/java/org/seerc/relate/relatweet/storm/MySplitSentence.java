package org.seerc.relate.relatweet.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class MySplitSentence implements IRichBolt {

	private OutputCollector collector;

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		  this.collector = collector;	   

	}

	
	public void execute(Tuple tuple) {
		String sentence = tuple.getString(0);
        for(String word: sentence.split(" ")) {
            this.collector.emit(new Values(word));                
        }

	}

	
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	     declarer.declare(new Fields("word"));
	}

	
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}


	  
}
