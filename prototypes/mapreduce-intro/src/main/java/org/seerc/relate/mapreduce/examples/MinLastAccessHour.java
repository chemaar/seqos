package org.seerc.relate.mapreduce.examples;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;



import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Examples adapted from the book Map/Reduce patterns http://shop.oreilly.com/product/0636920025122.do
 * @author chema
 *
 * Given a list of tweets in the form: (username, date, text)
 * 
 * determine the tweets after the 12:00.
 *
 *
 *
 */
public class MinLastAccessHour {

	public static class LastAccessHourMapper extends
			Mapper<Object, Text, IntWritable, Text> {

		private IntWritable outkey = new IntWritable();

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			// Parse the input string into a nice map
			Map<String, String> parsed = MRDPUtils.parse(value.toString());

			// Grab the last access date
			String strDate = parsed.get(MRDPUtils.CREATION_DATE);

			// skip this record if date is null
			if (strDate != null) {
				try {
					// Parse the string into a Calendar object
					Calendar cal = Calendar.getInstance();
					cal.setTime(MRDPUtils.frmt.parse(strDate));
					outkey.set(cal.get(Calendar.HOUR));
					System.out.println(outkey);
					// Write out the year with the input value
					context.write(outkey, value);
				} catch (ParseException e) {
					// An error occurred parsing the creation Date string
					// skip this record
				}
			}
		}
	}

	public static class LastAccessDatePartitioner extends
			Partitioner<IntWritable, Text> implements Configurable {

		private static final String MIN_LAST_ACCESS_HOUR_YEAR = "min.last.access.date.hour";

		private Configuration conf = null;
		private int minLastAccessDateHour = 0;

		@Override
		public int getPartition(IntWritable key, Text value, int numPartitions) {
			return key.get() - minLastAccessDateHour;
		}

		public Configuration getConf() {
			return conf;
		}


		public void setConf(Configuration conf) {
			this.conf = conf;
			minLastAccessDateHour = conf.getInt(MIN_LAST_ACCESS_HOUR_YEAR, 0);
		}

		/**
		 * Sets the minimum possible last access date to subtract from each key
		 * to be partitioned<br>
		 * <br>
		 * 
		 * That is, if the last min access date is "12" and the key to
		 * partition is "13", it will go to partition 13 - 12 = 1
		 * 
		 * @param job
		 *            The job to configure
		 * @param minLastAccessDateHour
		 *            The minimum access date.
		 */
		public static void setMinLastAccessDate(Job job,
				int minLastAccessDateHour) {
			job.getConfiguration().setInt(MIN_LAST_ACCESS_HOUR_YEAR,
					minLastAccessDateHour);
		}
	}

	public static class ValueReducer extends
			Reducer<IntWritable, Text, Text, NullWritable> {

		protected void reduce(IntWritable key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {
			for (Text t : values) {
				context.write(t, NullWritable.get());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: MinLastAccessHour <users> <outdir>");
			System.exit(2);
		}

		Job job = new Job(conf, "MinLastAccessHour");

		job.setJarByClass(MinLastAccessHour.class);

		job.setMapperClass(LastAccessHourMapper.class);

		// Set custom partitioner and min last access hour
		job.setPartitionerClass(LastAccessDatePartitioner.class);
		LastAccessDatePartitioner.setMinLastAccessDate(job, 12);

		// Last access dates span between 12-16, or 4 hours
		job.setNumReduceTasks(4);
		job.setReducerClass(ValueReducer.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);

		job.setOutputFormatClass(TextOutputFormat.class);
		job.getConfiguration().set("mapred.textoutputformat.separator", "");

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
