package org.seerc.relate.relatweet.storm.analytics;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class TwitterStatusNaiveReader {
	public static void main(String []args) throws IOException, InterruptedException{
		  String [] messages = {
			"This is a first",
			"message to ",
			"test the whole architecture."
		  };
		  ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("localhost");
		  Connection connection = factory.newConnection();
		  Channel channel = connection.createChannel();
		  channel.queueDeclare(TopologyStarter.QUEUE_NAME, false, false, false, null);
		  for (int i = 0;i<messages.length;i++){
			  channel.basicPublish("", TopologyStarter.QUEUE_NAME, null, messages[i].getBytes());  
			  System.out.println(" Sent '" + messages[i] + "'");
			  Thread.currentThread().sleep(5000);
		  }
		  channel.close();
		  connection.close();
	}
}
