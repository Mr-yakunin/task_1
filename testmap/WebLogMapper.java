package testmap;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class WebLogMapper extends Mapper <Object, Text, Text, WebLogWritable> {
	/*
	public class Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
	in this case KEYIN maybe folder of file with text value like string,
	KEYOUT value is an IP of request and VALUEOUT is a public class WebLogWritable like Tuple
	*/
		
	private WebLogWritable wLog = new WebLogWritable();
	private IntWritable reqbytes = new IntWritable(0);
	private IntWritable requests = new IntWritable(1);
	private FloatWritable average = new FloatWritable(1.0f);
	private Text rip = new Text();
		
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		/*
		read input line by line and splits it to get fields of request like
		request: 64.242.88.10 - - [07/Mar/2004:16:05:49 -0800] "GET /twiki/bin/edit/Main/Double_bounce_sender?topicparent=Main.ConfigurationVariables HTTP/1.1" 401 12846
		words:   [0]        [1][2][3]                   [4]    [5]  [6]                                                                               [7]       [8]  [9]
		*/
		String [] words = value.toString().split("\\s");
		//so words[9] = 12846, words[0] = 64.242.88.10
		if (!words[9].equals("-"))
			reqbytes.set(Integer.parseInt(words[9]));
		else
			reqbytes.set(0);
		rip.set(words[0]);
		wLog.set(reqbytes, requests, average);
		context.write(rip, wLog);
	}
}