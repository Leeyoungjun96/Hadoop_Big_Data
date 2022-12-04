package com.lee.lee;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class WordReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int total = 0;
		// process values
		for (IntWritable val : values) {
			total += val.get();
		}
		
		context.write(_key, new IntWritable(total));
	}

}
