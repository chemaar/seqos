package org.seerc.relate.relatweet.storm.naive;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;


public class PrinterBolt extends BaseBasicBolt {

    
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        System.out.println(tuple);
    }

    
    public void declareOutputFields(OutputFieldsDeclarer ofd) {
    }
    
}
