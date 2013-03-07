package org.seerc.relate.relatweet.storm;

import java.util.HashMap;
import java.util.Map;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.task.ShellBolt;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class TweeterWordCount {    
    public static class Split extends BaseFunction {
        
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for(String word: sentence.split(" ")) {
                collector.emit(new Values(word));                
            }
        }
    }
    
    public static class SplitSentence extends ShellBolt implements IRichBolt {        
        public SplitSentence() {           
        }        
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }        
        public Map<String, Object> getComponentConfiguration() {
            return null;
        }
    }  
    
    public static class WordCount extends BaseBasicBolt {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        
        public void execute(Tuple tuple, BasicOutputCollector collector) {
            String word = tuple.getString(0);
            Integer count = counts.get(word);
            if(count==null) count = 0;
            count++;
            counts.put(word, count);
            collector.emit(new Values(word, count));
        }
        
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }
    
// public static void main(String[] args) throws Exception {        
//        TopologyBuilder builder = new TopologyBuilder();        
//        builder.setSpout("spout", new TweetSpout(), 5);        
//        builder.setBolt("split", new SplitSentence(), 8)
//                 .shuffleGrouping("spout");
//        builder.setBolt("count", new WordCount(), 12)
//                 .fieldsGrouping("split", new Fields("word"));
//
//        Config conf = new Config();
//        conf.setDebug(true);
//        
//        if(args!=null && args.length > 0) {
//            conf.setNumWorkers(3);            
//            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
//        } else {        
//        	conf.setMaxTaskParallelism(3);
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology("word-count", conf, builder.createTopology());        
//            Thread.sleep(10000);
//            cluster.shutdown();
//        }
//	
// }
 
 public static StormTopology buildTopology(LocalDRPC drpc) {
     
     TridentTopology topology = new TridentTopology();        
     TridentState wordCounts =
           topology.newStream("spout1", new RandomSentenceSpout())
             .parallelismHint(16)
             .each(new Fields("tweet"), new Split(), new Fields("word"))
             .groupBy(new Fields("word"))
             .persistentAggregate(new MemoryMapState.Factory(),
                                  new Count(), new Fields("count"))         
             .parallelismHint(16);
             
     topology.newDRPCStream("words", drpc)
             .each(new Fields("args"), new Split(), new Fields("word"))
             .groupBy(new Fields("word"))
             .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
             .each(new Fields("count"), new FilterNull())
             .aggregate(new Fields("count"), new Sum(), new Fields("sum"))
             ;
     return topology.build();
 }
 
 public static void main(String[] args) throws Exception {
	 Config conf = new Config();
     conf.setMaxSpoutPending(20);
     if(args.length==0) {
         LocalDRPC drpc = new LocalDRPC();
         LocalCluster cluster = new LocalCluster();
         cluster.submitTopology("wordCounter", conf, buildTopology(drpc));
         for(int i=0; i<1; i++) {
             System.out.println("DRPC RESULT: " + drpc.execute("words", "cow"));
             Thread.sleep(1000);
         }
     } else {
         conf.setNumWorkers(3);
         StormSubmitter.submitTopology(args[0], conf, buildTopology(null));        
     }
 }
 
 
}
