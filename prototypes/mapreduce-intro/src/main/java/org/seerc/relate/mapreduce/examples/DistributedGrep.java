package org.seerc.relate.mapreduce.examples;

import java.io.*;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Examples adapted from the book Map/Reduce patterns http://shop.oreilly.com/product/0636920025122.do
 * @author chema
 *
 * Given a list of tweets in the form: (username, date, text)
 * 
 * determine the tweets that contain a word.
 *
 *
 *
 */


public class DistributedGrep {

	public static class GrepMapper extends
			Mapper<Object, Text, NullWritable, Text> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Map<String, String> parsed = MRDPUtils.parse(value.toString());
			String txt = parsed.get(MRDPUtils.TEXT);
			String mapRegex = ".*\\b"+context.getConfiguration().get("mapregex")+"(.)*\\b.*";
			if (txt.matches(mapRegex)) {
				context.write(NullWritable.get(), value);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err.println("Usage: DistributedGrep <regex> <in> <out>");
			System.exit(2);
		}
		conf.set("mapregex", otherArgs[0]);

		Job job = new Job(conf, "Distributed Grep");
		job.setJarByClass(DistributedGrep.class);
		job.setMapperClass(GrepMapper.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(0); // Set number of reducers to zero
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
