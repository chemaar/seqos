package org.seerc.relate.relatweet.lambda.speed;

import org.seerc.relate.relatweet.storm.MySplitSentence;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;


public class TopologyWordCount {    
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RabbitMQTweetSpout(), 5);
        builder.setBolt("split", new MySplitSentence(), 8)
                 .shuffleGrouping("spout");
        builder.setBolt("count", new RedisWordCount(), 12)
                 .fieldsGrouping("split", new Fields("word"));
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("QUEUE_NAME", LambdaArchitectureConstants.QUEUE_NAME);
        conf.put("REDIS_HOST", LambdaArchitectureConstants.REDIS_HOST);
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {        
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
           // Thread.sleep(40000);
           // cluster.shutdown();
        }
    }
 
}
