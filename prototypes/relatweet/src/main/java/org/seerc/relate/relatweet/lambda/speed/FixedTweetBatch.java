package org.seerc.relate.relatweet.lambda.speed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;
import backtype.storm.Config;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;

public class FixedTweetBatch implements IBatchSpout {

	    Fields fields;
	    int maxBatchSize;
		List<String> messages;
	    
	    public FixedTweetBatch(Fields fields, int maxBatchSize) {
	        this.fields = fields;
	        this.maxBatchSize = maxBatchSize;
	        this.messages = load();
	    }
	    
	    int index = 0;
	    boolean cycle = false;
	    
	    public void setCycle(boolean cycle) {
	        this.cycle = cycle;
	    }
	    

	    public void open(Map conf, TopologyContext context) {
	        index = 0;
	    }


	    public void emitBatch(long batchId, TridentCollector collector) {
	        //Utils.sleep(2000);
	        if(index>=messages.size() && cycle) {
	            index = 0;
	        }
	        for(int i=0; index < messages.size() && i < maxBatchSize; index++, i++) {
	        	List list = new LinkedList();
	        	list.add(messages.get(index));
	            collector.emit(list);
	        }
	    }

	   
	    public void ack(long batchId) {
	        
	    }


	    public void close() {
	    }


	    public Map getComponentConfiguration() {
	        Config conf = new Config();
	        conf.setMaxTaskParallelism(1);
	        return conf;
	    }


	    public Fields getOutputFields() {
	        return fields;
	    }
	    private List<String> load() {
			List<String> lines = new LinkedList<String>();
			try {
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("test/dump-tweets.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				line = br.readLine();
				while(line!=null){
					lines.add(line);
					line = br.readLine();
				}
				is.close();
				br.close();
			} catch (IOException e) {
				//FIXME
			}
			return lines;
		}

}
