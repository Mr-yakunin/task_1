package source;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import java.io.*;
import org.apache.hadoop.io.*;

//Public class like Tuple: has fields for total bytes (bytes) in request by IP, 
//count of requests (requests) by IP and average of average bytes per request (average) by IP 
public class WebLogWritable implements Writable {
	private IntWritable bytes, requests;
	private FloatWritable average;
		
	//Default Constructor
	public WebLogWritable() {
		this.bytes = new IntWritable(0);
		this.requests = new IntWritable(0);
		this.average = new FloatWritable(0.0f);
	}
		
	//Custom Constructor
	public WebLogWritable(IntWritable bytes, IntWritable requests, FloatWritable average) {
		this.bytes = bytes;
		this.requests = requests;
		this.average = average;
	}
		
	//Setter method to set the values of WebLogWritable object
	public void set(IntWritable bytes, IntWritable requests, FloatWritable average) {
		this.bytes = bytes;
		this.requests = requests;
		this.average = average;
	}
		
	//to get request bytes from WebLog Record
	public IntWritable getRequestBytes() {
		return bytes;
	}
		
	//to get request bytes from WebLog Record
	public IntWritable getRequestNum() {
		return requests;
	}
		
	//to get request bytes from WebLog Record
	public FloatWritable getRequestAverage() {
		return average;
	}
		
	@Override
	//overriding default readFields method. 
	//It de-serializes the byte stream data
	public void readFields(DataInput in) throws IOException {
		bytes.readFields(in);
		requests.readFields(in);
		average.readFields(in);
	}
		
	@Override
	//It serializes object data into byte stream data
	public void write(DataOutput out) throws IOException {
		bytes.write(out);
		requests.write(out);
		average.write(out);
	}
		
	@Override
	//Rules for creating custom Hadoop Writable
	public boolean equals(Object o) {
		if (o instanceof WebLogWritable) {
			WebLogWritable other = (WebLogWritable) o;
			return bytes.equals(other.bytes) && requests.equals(other.requests) && average.equals(other.average);
		}
		return false;
	}
		
	@Override
	//overriding default toString method
	public String toString() { 
		return ", " + bytes.toString() + ", " + requests.toString() + ", " + average.toString();
	}
		
	@Override
	public int hashCode() {
		return bytes.hashCode();
	}
}