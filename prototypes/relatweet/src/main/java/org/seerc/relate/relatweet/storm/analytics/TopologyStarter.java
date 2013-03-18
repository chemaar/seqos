package org.seerc.relate.relatweet.storm.analytics;

import org.apache.log4j.Logger;
import org.seerc.relate.relatweet.storm.naive.PrinterBolt;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.RawScheme;
import backtype.storm.spout.Scheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import com.rapportive.storm.amqp.QueueDeclaration;
import com.rapportive.storm.amqp.SharedQueueWithBinding;

public class TopologyStarter implements LAMBDA_ARCHITECTURE_CONSTANTS {
	public final static String REDIS_HOST = "localhost";
	public final static int REDIS_PORT = 6379;
	public static boolean testing = false;
	public final static String QUEUE_NAME = "tweet";
	
	public static RabbitMQTwitterSpout createSpout(){
		String host = "localhost";
		int port = 5672;
		String username = "";
		String password = "";
		String vhost = "";
		QueueDeclaration queueDeclaration = new SharedQueueWithBinding(
				QUEUE_NAME, "", ""); 
		Scheme scheme = new RawScheme();
		return	new RabbitMQTwitterSpout(host, port, 
						username, password, 
						vhost, queueDeclaration, scheme);
	}
	
	public static void main(String[] args) {
        Logger.getRootLogger().removeAllAppenders();

     
		TopologyBuilder builder = new TopologyBuilder();        
        builder.setSpout("spout", new RabbitMQTweetSpout(), 3);            
        builder.setBolt("print", new PrinterBolt())
        .shuffleGrouping("spout");        
		
		Config conf = new Config();		
		conf.put("QUEUE_NAME", QUEUE_NAME);
		
		LocalCluster cluster = new LocalCluster();		
		cluster.submitTopology("test", conf, builder.createTopology());
		
		Utils.sleep(1000000);
		cluster.shutdown();

	}
}