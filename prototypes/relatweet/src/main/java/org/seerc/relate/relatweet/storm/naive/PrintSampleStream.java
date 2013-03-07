package org.seerc.relate.relatweet.storm.naive;

import org.seerc.relate.relatweet.storm.TweetSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

/**
 * From examples of Nathan Marz in storm-starter
 * @author chema
 *
 */
public class PrintSampleStream {        
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TweetSpout());
        builder.setBolt("print", new PrinterBolt())
                .shuffleGrouping("spout");
                
        
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
