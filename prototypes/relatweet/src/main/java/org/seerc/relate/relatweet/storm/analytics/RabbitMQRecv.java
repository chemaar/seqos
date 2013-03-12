package org.seerc.relate.relatweet.storm.analytics;
import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQRecv {
	private final static String QUEUE_NAME = "tweet";
	 public static void main(String[] argv)
		      throws java.io.IOException,
		             java.lang.InterruptedException {

		    ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("localhost");
		    
		    Connection connection = factory.newConnection();
		    
		    Channel channel = connection.createChannel();
		    
		    QueueingConsumer consumer = new QueueingConsumer(channel);
		    channel.basicConsume(QUEUE_NAME, true, consumer);
		    while (true) {
		      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		      String message = new String(delivery.getBody());
		      System.out.println(" [x] Received '" + message + "'");
		    }
		  }
}
