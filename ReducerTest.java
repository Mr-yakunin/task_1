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
import java.util.ArrayList;
import java.util.List;
import testreduce.WebLogWritable;
import testreduce.WebLogReducer;

//public class for reducer testing
//compilation: javac -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Reducetest.java
//for example: javac -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; ReducerTest.java
//starting: java -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Reducetest
//for example: java -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; org.junit.runner.JUnitCore ReducerTest
public class ReducerTest extends TestCase {
	
	ReduceDriver<Text, WebLogWritable, Text, WebLogWritable> reduceDriver;
 
    @Before
	//set up reducedriver
    public void setUp() {
        WebLogReducer reducer = new WebLogReducer();
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
    }
	
	@Test
	//tests with input information (withInput()) and output (withOutput()) for checking
    public void testReducer() throws IOException {
		int [] value = {12846,12846,8964,3675,2023};
		int sum1 = value[0] + value[1] + value[3];
		int sum2 = value[2] + value[4];
		int num1 = 3;
		int num2 = 2;
		float float1 = sum1 / num1;
		float float2 = sum2 / num2;
		System.out.println("\nStarting Reducer Testing");
		System.out.println("If tests were passed we can see 'OK (1 test)' message :)");
		List<WebLogWritable> inputs1 = new ArrayList<WebLogWritable>();
		List<WebLogWritable> inputs2 = new ArrayList<WebLogWritable>();
		inputs1.add(new WebLogWritable(new IntWritable(value[0]), new IntWritable(1), new FloatWritable(1.0f)));
		inputs1.add(new WebLogWritable(new IntWritable(value[1]), new IntWritable(1), new FloatWritable(1.0f)));
		inputs1.add(new WebLogWritable(new IntWritable(value[3]), new IntWritable(1), new FloatWritable(1.0f)));
		inputs2.add(new WebLogWritable(new IntWritable(value[2]), new IntWritable(1), new FloatWritable(1.0f)));
		inputs2.add(new WebLogWritable(new IntWritable(value[4]), new IntWritable(1), new FloatWritable(1.0f)));
        reduceDriver.withInput(new Text("64.242.88.10"), inputs1);
		reduceDriver.withInput(new Text("10.0.0.153"), inputs2);
		reduceDriver.withOutput(new Text("64.242.88.10"), new WebLogWritable(new IntWritable(sum1), new IntWritable(num1), new FloatWritable(float1)));
		reduceDriver.withOutput(new Text("10.0.0.153"), new WebLogWritable(new IntWritable(sum2), new IntWritable(num2), new FloatWritable(float2)));
        reduceDriver.runTest();
    }

}