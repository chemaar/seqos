package org.seerc.relate.relatweet.lambda.speed;



import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.MemoryMapState;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TridentTopologyMain {
	

	  public static class MySplitFunction extends BaseFunction {
	        public void execute(TridentTuple tuple, TridentCollector collector) {
	            String sentence = tuple.getString(0);
	            for(String word: sentence.split(" ")) {
	              collector.emit(new Values(word));    
	            }
	        }
	    }
	  	  
    public static StormTopology buildTopology(LocalDRPC drpc) {
    	FixedTweetBatch spout = new FixedTweetBatch(new Fields("tweet"), 3);
    	//RabbitMQTweetBatch spout = new RabbitMQTweetBatch(new Fields("tweet"));
    	TridentTopology topology = new TridentTopology();              
        MemoryMapState.Factory stateFactory = new MemoryMapState.Factory();
		TridentState wordCounts =
              topology.newStream("spout1", spout)
                .parallelismHint(16)
                .each(new Fields("tweet"), new MySplitFunction(), new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(stateFactory, new Count(), new Fields("count"))         
                .parallelismHint(16);
                
        
        topology.newDRPCStream("words", drpc)
                .each(new Fields("args"), new MySplitFunction(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));
                return topology.build();
    }
    
    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        conf.put("QUEUE_NAME", LambdaArchitectureConstants.QUEUE_NAME);
        conf.put("REDIS_HOST", LambdaArchitectureConstants.REDIS_HOST);
        if(args.length==0) {
            LocalDRPC drpc = new LocalDRPC();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordCounter", conf, buildTopology(drpc));
            for(int i=0; i<100; i++) {
                System.out.println("DRPC RESULT: " + drpc.execute("words", "services"));
                Thread.sleep(1000);
            }
        } else {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, buildTopology(null));        
        }
    }

}
