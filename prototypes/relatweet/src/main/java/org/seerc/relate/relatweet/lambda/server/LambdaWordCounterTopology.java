package org.seerc.relate.relatweet.lambda.server;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;
import org.seerc.relate.relatweet.lambda.speed.FixedTweetBatch;
import org.seerc.relate.relatweet.lambda.speed.TridentTopologyMain.MySplitFunction;

import redis.clients.jedis.Jedis;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.CombinerAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.MapGet;
import storm.trident.state.StateFactory;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.splout.db.qnode.beans.QueryStatus;

/**
 * Example inspired (with custom adaptations and different business logic) in https://github.com/pereferrera/trident-lambda-splout/
 */

public class LambdaWordCounterTopology {


	private static final int MIN_RANGE = 600;
	private static final int MAX_RANGE = 700;

	public static class LambdaMerge extends BaseFunction {

		public void execute(TridentTuple tuple, TridentCollector collector) {
			//Comment: Merge:[services, {services=83}, {result={count=460, word=services}]
			Map<String, Long> resultRealTime = (Map<String, Long>) tuple.get(1);
			QueryStatus resultBatch = (QueryStatus) tuple.get(2);
			TreeMap<String, Long> consolidatedResult;

			if(resultRealTime != null) {
				consolidatedResult = new TreeMap<String, Long>(resultRealTime);
			} else {
				consolidatedResult = new TreeMap<String, Long>();
			}

			if(resultBatch != null) {
				if(resultBatch.getResult() != null) {
					for(Object rowBatch : resultBatch.getResult()) {
						Map<String, Object> mapRow = (Map<String, Object>) rowBatch;
						String word = (String) mapRow.get("word");
						// we do this since Splout may return Integer or Long depending on the value
						Long count = Long.parseLong(mapRow.get("count").toString());
						//FIXME: I am going to add batch+real but taking into account Pera Ferrera:
						// In the real-time map we set the values from the batch view
						// Therefore if there is a value coming from batch it will override any value from real-time
						// This is the usual decision for lambda architectures.
						Long realTimeCount = consolidatedResult.get(word);
						if(realTimeCount != null){
							consolidatedResult.put(word, realTimeCount+count);							
						}else{
							consolidatedResult.put(word, count);	
						}


					}
				}
			}

			collector.emit(new Values(consolidatedResult));
		}
	}

	public static class CountByWord implements CombinerAggregator<Map<String, Long>> {

		public Map<String, Long> init(TridentTuple tuple) {
			Map<String, Long> map = zero();
			map.put(tuple.getString(0), 1L);
			return map;
		}

		public Map<String, Long> combine(Map<String, Long> val1, Map<String, Long> val2) {
			for(Map.Entry<String, Long> entry : val2.entrySet()) {
				val2.put(entry.getKey(), MapUtils.getLong(val1, entry.getKey(), 0L) + entry.getValue());
			}
			for(Map.Entry<String, Long> entry : val1.entrySet()) {
				if(val2.containsKey(entry.getKey())) {
					continue;
				}
				val2.put(entry.getKey(), entry.getValue());
			}
			return val2;
		}

		public Map<String, Long> zero() {
			return new HashMap<String, Long>();
		}
	}

	public static StormTopology buildTopology(LocalDRPC drpc) {
		TridentTopology topology = new TridentTopology();
		FixedTweetBatch spout = new FixedTweetBatch(new Fields("tweet"), 3);
		StateFactory mapState = new MemoryMapState.Factory();

		//Real-Time
		TridentState wordCounts =
				topology.newStream("spout1", spout)
				.parallelismHint(16)
				.each(new Fields("tweet"), new MySplitFunction(), new Fields("word"))
				.groupBy(new Fields("word"))
				.persistentAggregate(mapState, new Fields("word"),  new CountByWord(), new Fields("count")) 
				.parallelismHint(16);


		// Batch:
		TridentState sploutState = topology.newStaticState(new SploutState.Factory(false,
				"http://localhost:4412"));

		// DRPC service:
		// Accepts a "word" argument and queries first the real-time view and then the batch-view. The results
		// are merged with a custom LambdMerge
		topology
		.newDRPCStream("wordcounter", drpc)
		.each(new Fields("args"), new Split(), new Fields("word"))
		.groupBy(new Fields("word"))
		.stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
		.stateQuery(sploutState, new Fields("word"), new WordSploutQuery(), new Fields("resultbatch"))
		.each(new Fields("word", "count", "resultbatch"), new LambdaMerge(), new Fields("result"))
		.project(new Fields("result"));

		return topology.build();
	}

	public static void main(String[] args) throws Exception {
		Config conf = new Config();
		conf.setMaxSpoutPending(20);
		Jedis jedis = new Jedis("localhost");		
		LocalDRPC drpc = new LocalDRPC();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("wordcounter", conf, buildTopology(drpc));
		String targetWord = "services";
		for(int i = 0; i < 100; i++) {
			String result = drpc.execute("wordcounter", targetWord);
			String value = result.split(":")[1].split("}")[0];			//FIXME:
			System.out.println("Result for word 'services' -> " +value);
			//Publish en redis
			jedis.set(targetWord, value);
			//Simulate alert
			if(Integer.valueOf(value) > MIN_RANGE && Integer.valueOf(value)<MAX_RANGE){
				jedis.set("ALERT", "ACTIVE");
			}else{
				jedis.set("ALERT", "INACTIVE");
			}
			Thread.sleep(1000);
		}
		
	}
}
