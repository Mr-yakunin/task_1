package source;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class WebLogReducer extends Reducer <Text, WebLogWritable, Text, WebLogWritable> {
	/*
	public class Reducer<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
	in this case KEYIN is an IP of request and VALUEIN is a public class WebLogWritable
	KEYOUT is an IP request and VALUEOUT is a public class WebLogWritable
	so reducer counts total bytes of requests by IP, total requests and count bytes per request
	*/
		
	private IntWritable total_bytes = new IntWritable();
	private IntWritable total_requests = new IntWritable();
	private FloatWritable bytes_per_request = new FloatWritable();
	private WebLogWritable result = new WebLogWritable();
		
	public void reduce(Text key, Iterable<WebLogWritable> values, Context context) throws IOException, InterruptedException {
		int total = 0;
		int requests = 0;
		float per_request = 0.0f;
		//count total bytes and total number of requests
		for (WebLogWritable val : values) {
			total += val.getRequestBytes().get();
			requests += val.getRequestNum().get();
		}
		//count bytes per request
		per_request = total / requests;
		total_bytes.set(total);
		total_requests.set(requests);
		bytes_per_request.set(per_request);
		//save as result
		result.set(total_bytes, total_requests, bytes_per_request);
		context.write(key, result);	
	}
}