package org.seerc.relate.relatweet.storm.analytics;

import org.apache.log4j.Logger;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class TopologyStarter {
	public final static String REDIS_HOST = "localhost";
	public final static int REDIS_PORT = 6379;
	public static boolean testing = false;

	public static void main(String[] args) {
        Logger.getRootLogger().removeAllAppenders();

		TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("read-feed", new RedisSpout(), 3);
            
        Config conf = new Config();
        conf.setDebug(true);

        conf.put("redis-host", REDIS_HOST);
        conf.put("redis-port", REDIS_PORT);
        
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("analytics", conf, builder.createTopology());
	}
}