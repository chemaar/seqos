package org.seerc.relate.relatweet.storm;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class TweetSpout extends BaseRichSpout {
	SpoutOutputCollector collector;
	LinkedBlockingQueue<String> queue = null;
	TwitterStream twitterStream;


	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		queue = new LinkedBlockingQueue<String>(1000);
		this.collector = collector;
		StatusListener listener = new TweetListener(this);
		TwitterStreamFactory fact = new TwitterStreamFactory();
		twitterStream = fact.getInstance();
		twitterStream.addListener(listener);
		twitterStream.sample();
	}


	public void nextTuple() {
		String ret = queue.poll();
		if(ret==null) {
			Utils.sleep(50);
		} else {
			collector.emit(new Values(ret));
		}
	}


	public void close() {
		twitterStream.shutdown();
	}


	public Map<String, Object> getComponentConfiguration() {
		Config ret = new Config();
		ret.setMaxTaskParallelism(1);
		return ret;
	}    


	public void ack(Object id) {
	}


	public void fail(Object id) {
	}


	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tweet"));
	}

	public LinkedBlockingQueue<String> getQueue() {
		return queue;
	}


	public void setQueue(LinkedBlockingQueue<String> queue) {
		this.queue = queue;
	}

}
