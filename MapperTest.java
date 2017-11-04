import junit.framework.TestCase;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.*;
import org.apache.hadoop.io.*;
import testmap.WebLogWritable;
import testmap.WebLogMapper;

//public class for mapper testing
//compilation: javac -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Maptest.java
//for example: javac -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; MapperTest.java
//starting: java -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Maptest
//for example: java -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; org.junit.runner.JUnitCore MapperTest
public class MapperTest extends TestCase {

    MapDriver<Object, Text, Text, WebLogWritable> mapDriver;
	
	@Before
	//set up mapdriver
    public void setUp() {
        WebLogMapper mapper = new WebLogMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }
 
    @Test
	//tests with input information (withInput()) and output (withOutput()) for checking
    public void testMapper() throws IOException {
		System.out.println("\nStarting Mapper Testing");
		System.out.println("If tests were passed we can see 'OK (1 test)' message :)");
		System.out.println("Input value: 216-160-111-121.tukw.qwest.net - - [11/Mar/2004:20:49:38 -0800] \"GET /dccstats/stats-hashes.1month.png HTTP/1.1\" 200 1624");
		System.out.println("Should recognize as: IP = 216-160-111-121.tukw.qwest.net with total bytes = 1624");
		System.out.println("Input value: 216-160-111-121.tukw.qwest.net - - [11/Mar/2004:20:49:38 -0800] \"GET /dccstats/stats-spam.1year.png HTTP/1.1\" 200 2243");
		System.out.println("Should recognize as: IP = 216-160-111-121.tukw.qwest.net with total bytes = 2243");
		System.out.println("etc...");
		mapDriver.withInput(new Text("a"), new Text("216-160-111-121.tukw.qwest.net - - [11/Mar/2004:20:49:38 -0800] \"GET /dccstats/stats-hashes.1month.png HTTP/1.1\" 200 1624"));
        mapDriver.withInput(new Text("b"), new Text("216-160-111-121.tukw.qwest.net - - [11/Mar/2004:20:49:38 -0800] \"GET /dccstats/stats-spam.1year.png HTTP/1.1\" 200 2243"));
        mapDriver.withInput(new Text("c"), new Text("10.0.0.153 - - [12/Mar/2004:12:23:17 -0800] \"GET /cgi-bin/mailgraph2.cgi HTTP/1.1\" 200 2987"));
		mapDriver.withInput(new Text("d"), new Text("10.0.0.153 - - [12/Mar/2004:12:23:18 -0800] \"GET /cgi-bin/mailgraph.cgi/mailgraph_0_err.png HTTP/1.1\" 200 6324"));
        mapDriver.withOutput(new Text("216-160-111-121.tukw.qwest.net"), new WebLogWritable(new IntWritable(1624), new IntWritable(1), new FloatWritable(1.0f)));
		mapDriver.withOutput(new Text("216-160-111-121.tukw.qwest.net"), new WebLogWritable(new IntWritable(2243), new IntWritable(1), new FloatWritable(1.0f)));
        mapDriver.withOutput(new Text("10.0.0.153"), new WebLogWritable(new IntWritable(2987), new IntWritable(1), new FloatWritable(1.0f)));
		mapDriver.withOutput(new Text("10.0.0.153"), new WebLogWritable(new IntWritable(6324), new IntWritable(1), new FloatWritable(1.0f)));
        mapDriver.runTest();
    }
}