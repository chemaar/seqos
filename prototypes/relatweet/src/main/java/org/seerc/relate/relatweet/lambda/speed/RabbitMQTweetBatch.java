package org.seerc.relate.relatweet.lambda.speed;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;
import backtype.storm.Config;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMQTweetBatch implements IBatchSpout {

	Fields fields;
	private QueueingConsumer consumer;
	private Channel channel;


	public RabbitMQTweetBatch(Fields fields) {
		this.fields = fields;
	}

	private void configure(Map conf) {
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
	}


	public void open(Map conf, TopologyContext context) {
		configure(conf);
	}


	public void emitBatch(long batchId, TridentCollector collector) {
		//Utils.sleep(2000);
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				List list = new LinkedList();
				list.add(message);
				collector.emit(list);
			} catch (ShutdownSignalException e) {
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}

	}


	public void ack(long batchId) {

	}


	public void close() {
	}


	public Map getComponentConfiguration() {
		Config conf = new Config();
		conf.setMaxTaskParallelism(1);
		return conf;
	}


	public Fields getOutputFields() {
		return fields;
	}

}
