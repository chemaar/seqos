Naive Example of Lambda Architecture for counting words

a) Running the Batch Layer

1-Start the HADOOP instance
$HADOOP_HOME/bin/start-all.sh

2-Load input data in HDFS. For instance "input" is the directory with the input files
$HADOOP_HOME/bin/hadoop fs -put input

Ej: $HADOOP_HOME/bin/hadoop fs -put /home/chema/data/input/ input

3-Compile and package with Maven
mvn assembly:assembly

4-Run with Hadoop

$HADOOP_HOME/bin/hadoop jar target/relatweet-0.1-SNAPSHOT-job.jar input output

5-Check the results

$HADOOP_HOME/bin/hadoop fs -cat output/part-r-00000

6-Create Batch views with SploutSQL

6.1-bin/splout-service.sh qnode start
6.2-bin/splout-service.sh dnode start
6.3-Edit hadoop script: exec "$JAVA" $JAVA_HEAP_MAX $HADOOP_OPTS -Djava.library.path=/home/chema/develop/tools/sqlite4java-282 -classpath "$CLASSPATH" (only to ensure the Java library path is correct)
6.4-hadoop jar splout-hadoop-0.2.1-hadoop.jar simple-generate -i /home/chema/projects/seqos/prototypes/relatweet/output/part-r-00000 -o out-words -pby word -p 2 -s "word:string,count:int" --index "word" -t words -tb words
6.5-hadoop jar splout-hadoop-*-hadoop.jar deploy -q http://localhost:4412 -root out-words -ts words
6.6 Check http://localhost:4412
6.7 Remember to remove the dir out-words


b)Running the Server and Speed Layer:

1-Run redis-server
2-Run rabbitmq-server
3-Run src/main/webapp/app.js
4-Run topology of speed and server layer
5-Run Twitter worker

c)-Show results

http://localhost:3000


Notes:

1) Clean Redis with redis-cli:

FLUSHDB       - Removes data from your connection's CURRENT database.
FLUSHALL      - Removes data from ALL databases.


