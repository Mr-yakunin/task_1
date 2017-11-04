DS:BDA Task 1 Report from Mr-yakunin (github)

### Challenge (Task 1) ###

- program which count average bytes per request by IP and total bytes by IP. Output
is file with rows as: IP,175.5,109854;
- output format: sequenceFile with Snappy encoding (read content of compressed file
from console using command line);
- combiner is used;
- writable entities are reused;
- number of Reducers more than 1.

### Report contains ###

- README.txt (report and instructions);
- working MapReduce application (web.jar) with source code (WebLogReader.java with 
package source) and CLASS file;
- unit tests source code (MapperTest.java with package testmap and ReducerTest.java
with package testreduce) and CLASS file;
- texts directory with test files and batch script (split.ps1) for input file 
generation to HDFS;
- screenshots directory with successfully executed tests, uploaded files into HDFS,
executed job and result.

### Requirements ###

- Windows 10 OS;
- hadoop version 2.6.5;
- java version 1.8.0_152;
- MRUnit jar file from 
http://www.java2s.com/Code/Jar/m/Downloadmrunit100hadoop2jar.htm;

### Install Java 8 ###

Download Java 8 from the link: 
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
1. Set environmental system variable. Do: My computer -> Properties -> Advance 
system settings -> Advanced -> Environmental variables:
	- Variable: PATH
	- Value: C:\Program Files\Java\jdk1.8.0_152\bin
2. Check on cmd: java -version
Output must be like:
	D:\hadoop\project>java -version
	java version "1.8.0_152"
	Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
	Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)

### Hadoop install on Windows 10 ###

Download Hadoop-2.6.5: download Hadoop 2.6.5 from the link: 
http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.6.5/hadoop-2.6.5.tar.gz
a. Put extracted Hadoop-2.6.5 files into D drive. Note that do not put these 
extracted files into C drive, where you installed your Windows.
b. Download "winutils" from the link:
https://github.com/steveloughran/winutils
Paste files including "winutils/hadoop-2.6.0/bin/" into the the "bin" folder of 
Hadoop-2.6.5.
c. Create a "data" folder inside Hadoop-2.6.5, and also create two more folders in 
the "data" folder as "data" and "name".
d. Create a folder to store temporary data during execution of a project, such as 
"D:\hadoop\temp".
e. Create a log folder, such as "D:\hadoop\userlog"
f. Go to Hadoop-2.6.5 -> etc -> Hadoop and edit four files (add these properties)  
in <configuration></configuration> (put site-specific property in this file):
i. core-site.xml

	<property>
		<name>hadoop.tmp.dir</name>
		<value>D:\hadoop\temp</value>
	</property>

	<property>
		<name>fs.default.name</name>
		<value>hdfs://localhost:50071</value>
	</property>

ii. hdfs-site.xml

	<property>
		<name>dfs.replication</name><value>1</value>
	</property>

	<property>
		<name>dfs.namenode.name.dir</name>
		<value>/hadoop/data/name</value><final>true</final>
	</property>

	<property>
		<name>dfs.datanode.data.dir</name>
		<value>/hadoop/data/data</value><final>true</final>
	</property>

iii. mapred.xml

	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
	</property>

	<property>
		<name>mapred.job.tracker</name>
		<value>localhost:9001</value>
	</property>

	<property>
		<name>mapreduce.application.classpath</name>
		<value>/hadoop/share/hadoop/mapreduce/*,
		/hadoop/share/hadoop/mapreduce/lib/*,
		/hadoop/share/hadoop/common/*,
		/hadoop/share/hadoop/common/lib/*,
		/hadoop/share/hadoop/yarn/*,
		/hadoop/share/hadoop/yarn/lib/*,
		/hadoop/share/hadoop/hdfs/*,
		/hadoop/share/hadoop/hdfs/lib/*,
		</value>
	</property>

iv. yarn.xml

	<property>
		<name>yarn.nodemanager.aux-services</name>
		<value>mapreduce_shuffle</value>
	</property>

	<property>
		<name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
		<value>org.apache.hadoop.mapred.ShuffleHandler</value>
	</property>

	<property>
		<name>yarn.nodemanager.log-dirs</name>
		<value>D:\hadoop\userlog</value><final>true</final>
	</property>

	<property>
		<name>yarn.nodemanager.local-dirs</name>
		<value>D:\hadoop\temp\nm-local-dir</value>
	</property>

	<property>
		<name>yarn.nodemanager.delete.debug-delay-sec</name>
		<value>600</value>
	</property>

	<property>
		<name>yarn.application.classpath</name>
		<value>/hadoop/,
		/hadoop/share/hadoop/common/*,
		/hadoop/share/hadoop/common/lib/*,
		/hadoop/share/hadoop/hdfs/*,
		/hadoop/share/hadoop/hdfs/lib/*,
		/hadoop/share/hadoop/mapreduce/*,
		/hadoop/share/hadoop/mapreduce/lib/*,
		/hadoop/share/hadoop/yarn/*,
		/hadoop/share/hadoop/yarn/lib/*,
		/hadoop/share/hadoop/tools/lib/*
		</value>
	</property>
	
g. Go to the location: "Hadoop-2.6.5 -> etc -> hadoop" and edit "hadoop-env.cmd" by
writing
	set JAVA_HOME=C:\PROGRA~1\Java\jdk1.8.0_152
	set HADOOP_CLASSPATH=%JAVA_HOME%\lib\tools.jar
h. Set environmental variables: Do: My computer -> Properties -> Advance system 
settings -> Advanced -> Environmental variables
	i. User variables:
		- Variable: HADOOP_HOME
		- Value: D:\hadoop
	ii. System variable
		- Variable: Path
		- Value: D:\hadoop\bin
				 D:\hadoop\sbin
				 D:\hadoop\share\hadoop\common\*
				 D:\hadoop\share\hadoop\common\lib\*
				 D:\hadoop\share\hadoop\hdfs\*
				 D:\hadoop\share\hadoop\hdfs\lib\*
				 D:\hadoop\share\hadoop\yarn\*
				 D:\hadoop\share\hadoop\yarn\lib\*
				 D:\hadoop\share\hadoop\mapreduce\*
				 D:\hadoop\share\hadoop\mapreduce\lib\*				 
i. Check on cmd: hadoop version
Output must be like: 
	D:\hadoop\project>hadoop version
	Hadoop 2.6.5
	Subversion https://github.com/apache/hadoop.git -r e8c9fe0b4c252caf2ebf1464220599650f119997
	Compiled by sjlee on 2016-10-02T23:43Z
	Compiled with protoc 2.5.0
	From source with checksum f05c9fa095a395faa9db9f7ba5d754
	This command was run using /D:/hadoop/share/hadoop/common/hadoop-common-2.6.5.jar

Project folder uses in %HADOOP_HOME%

### /* Get test files for application */ ###

Go to directory texts and start split.ps1 batch script. 
Screenshots of successfully uploaded files into HDFS you will see in 
screenshots/split_work1.png and screenshots/split_work2.png
File texts/input.txt were spliting and uploaded into HDFS.

This type of logs were used in the program: 
lordgun.org - - [07/Mar/2004:17:01:53 -0800] "GET /razor.html HTTP/1.1" 200 2869
File for splitting was from http://www.monitorware.com/en/logsamples/apache.php 
Apache (Unix) Log Samples -> access_log

### /* Testing Mapper and Reducer class */ ###

Unit tests of mapper and reducer used MRUnit (MR Unit Testing).
Why MR Unit? 
Problem: – JUnit can not be used directly to test Mappers and Reducers because Unit
tests require mocking up classes like InputSplits, OutputCollector, Reporter etc) 
in the MapReduce framework.
MR Unit is build on top of JUnit.
– Works with the mockito framework to provide required mock objects.
MR Unit Provides a mock InputSplit and other classes.
– It can test
Mapper – Supported by MapDriver Class.
Reducer – Supported by ReduceDriver Class.

So, source code of of testing mapper and reducer MapperTest.java and 
ReducerTest.java.

Mapper (also you can use map_test.bat file):
Compilation: javac -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Maptest.java
In our case: javac -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; MapperTest.java
Starting: java -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Maptest
In our case: java -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; org.junit.runner.JUnitCore MapperTest

Reducer (also you can use reduce_test.bat file):
Compilation: javac -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Reducetest.java
In our case: javac -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; ReducerTest.java
Starting: java -cp 'hadoop classpath';path\to\mrunit-1.0.0-hadoop2.jar Reducetest
In our case: java -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; org.junit.runner.JUnitCore ReducerTest

Screenshots of successfully executed tests you will see in 
screenshots/map_test_success.png, screenshots/reduce_test_success.

### /* Main application */ ###

Compilation:
in source directory:

D:\hadoop\project\source>hadoop com.sun.tools.javac.Main WebLogWritable.java
D:\hadoop\project\source>hadoop com.sun.tools.javac.Main WebLogMapper.java
D:\hadoop\project\source>hadoop com.sun.tools.javac.Main WebLogReducer.java
D:\hadoop\project\source>

in main project directory:

D:\hadoop\project>hadoop com.sun.tools.javac.Main WebLogReader.java
D:\hadoop\project>jar cf web.jar WebLogReader.class source/*.class
D:\hadoop\project>

Then copy file into hadoop/share/hadoop/mapreduce:
D:\hadoop\project>copy web.jar D:\hadoop\share\hadoop\mapreduce\
Заменить D:\hadoop\share\hadoop\mapreduce\web.jar [Yes (да)/No (нет)/All (все)]: Y
Скопировано файлов:         1.
D:\hadoop\project>

Start application: hadoop jar web.jar WebLogReader /inputlogs /outputlogs

Screenshots of successfully executed job and result you can see in 
screenshots/job_and_result_1.png and screenshots/job_and_result_2.png.
Result of files in folder /outputlogs you can see on 
screenshots/result_outputlogs.png

Screenshots of reading compressed content you can see in screenshots/log_part0.png,
screenshots/log_part1.png, screenshots/log_part2.png, screenshots/log_part3.png.

Some words about program.
Application reads input text file or text files in directory line by line and 
splits it to get fields of request. Strings splits by spaces. So we expect that 
one line can get us 10 words. We need these fields: word[0] that is an IP of
request and word[9] whick can give us bytes of the request. 
So mapper creates output as <key out as IP or word[0]><value out as tuple class of
several fields: bytes, number of requests, bytes per request>
Then reducer count number of requests by IP and bytes per requests by IP.
This is output information which save on file like:
IP , total bytes by IP, total number of requests, bytes per request.
Thanks.

Copyright © 2017, All rights reserved