cd /D D:\hadoop\project
hadoop com.sun.tools.javac.Main testreduce\WebLogWritable.java && hadoop com.sun.tools.javac.Main testreduce\WebLogWritable.java testreduce\WebLogReducer.java && javac -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; ReducerTest.java && java -cp "D:\hadoop\share\hadoop\common\*";D:\hadoop\testing\*;"D:\hadoop\share\hadoop\common\lib\*";"D:\hadoop\testing\t\*"; org.junit.runner.JUnitCore ReducerTest