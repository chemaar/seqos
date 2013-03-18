package org.seerc.relate.relatweet.lambda.server;

import java.util.ArrayList;
import java.util.List;

import org.seerc.relate.relatweet.lambda.speed.LambdaArchitectureConstants;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseQueryFunction;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;

/**
 * Encapsulates the business logic of querying word counts to Splout SQL (http://sploutsql.com).
 */
@SuppressWarnings("serial")
public class WordSploutQuery extends BaseQueryFunction<SploutState, Object> {

	public List<Object> batchRetrieve(SploutState state, List<TridentTuple> args) {
		List<String> sqls = new ArrayList<String>();
		List<String> partitionKeys = new ArrayList<String>();

		// fill the data
		for(TridentTuple arg: args) {
			String word = arg.getString(0);
			sqls.add("SELECT word, count FROM words WHERE word = '" + word + "';");
			partitionKeys.add(word);
		}

		return state.querySplout(LambdaArchitectureConstants.TABLESPACE, sqls, partitionKeys);
	}

	public void execute(TridentTuple tuple, Object result, TridentCollector collector) {
		collector.emit(new Values(result));
	}
}
