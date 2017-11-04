import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import source.WebLogMapper;
import source.WebLogReducer;
import source.WebLogWritable;

//main class of project
public class WebLogReader 
{
	
	public static void main(String[] args) throws Exception {
		//this program must be used as: WebLogReader <input directories or files> [<in>] <output directory>
		//output directory must not be exist
		int num_of_reducers = 4;
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length < 2) {
		  System.err.println("Usage: web log reader <in> [<in>...] <out>");
		  System.exit(2);
		}
		Job job = Job.getInstance(conf, "web log reader");
		//set mapper/combiner/reducer etc classes for job
		job.setJarByClass(WebLogReader.class);
		job.setMapperClass(WebLogMapper.class);
		job.setCombinerClass(WebLogReducer.class);
		job.setReducerClass(WebLogReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(WebLogWritable.class);
		job.setNumReduceTasks(num_of_reducers);
		//get input
		for (int i = 0; i < otherArgs.length - 1; ++i) {
		  FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		//set output directory
		FileOutputFormat.setOutputPath(job,
		  new Path(otherArgs[otherArgs.length - 1]));
		//this program needs snappy encoding sequence file in output
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
		SequenceFileOutputFormat.setOutputCompressionType(job,CompressionType.BLOCK);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
}