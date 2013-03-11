package org.seerc.relate.relatweet.storm.analytics;

import java.io.IOException;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMQTweetSpout extends BaseRichSpout {
	SpoutOutputCollector collector;
	private QueueingConsumer consumer;
	private Channel channel;


	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			this.channel = connection.createChannel();
			this.consumer = new QueueingConsumer(this.channel);
			this.channel.basicConsume((String) conf.get("QUEUE_NAME"), true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.collector = collector;
	}


	//FIXME: Use spout for amq
	public void nextTuple() {		    
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				collector.emit(new Values(message));
			} catch (ShutdownSignalException e) {
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}

	}


	public void close() {

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


}
