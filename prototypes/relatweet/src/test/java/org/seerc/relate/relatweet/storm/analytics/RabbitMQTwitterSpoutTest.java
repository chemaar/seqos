package org.seerc.relate.relatweet.storm.analytics;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import backtype.storm.spout.RawScheme;
import backtype.storm.spout.Scheme;

import com.rapportive.storm.amqp.QueueDeclaration;
import com.rapportive.storm.amqp.SharedQueueWithBinding;

public class RabbitMQTwitterSpoutTest {

	@Test
	public void test() {
		String host = "localhost";
		int port = 5672;
		String username = "guest";
		String password = "guest";
		String vhost = "";
		QueueDeclaration queueDeclaration = 
				new SharedQueueWithBinding("hello", "exchange", ""); 
		Scheme scheme = new RawScheme();
		RabbitMQTwitterSpout spout = 
				new RabbitMQTwitterSpout(host, port, 
						username, password, 
						vhost, queueDeclaration, scheme);
		
		spout.open(new HashMap(), null, null);
		spout.nextTuple();
		
		
	}

}
