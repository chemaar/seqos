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

b) Running the Speed Layer:

1-Run redis-server
2-Run rabbitmq-server
3-Run src/main/webapp/app.js
4-Run topology
5-Run Twitter worker

c)Running the Server Layer:

1-Create batch view with SploutSQL

1.1-bin/splout-service.sh qnode start
1.2-bin/splout-service.sh dnode start
1.3-Edit hadoop script: exec "$JAVA" $JAVA_HEAP_MAX $HADOOP_OPTS -Djava.library.path=/home/chema/develop/tools/sqlite4java-282 -classpath "$CLASSPATH" (only to ensure the Java library path is correct)
1.4-hadoop jar splout-hadoop-0.2.1-hadoop.jar simple-generate -i /home/chema/projects/seqos/prototypes/relatweet/output/part-r-00000 -o out-words -pby word -p 2 -s "word:string,count:int" --index "word" -t words -tb words
1.5-hadoop jar splout-hadoop-*-hadoop.jar deploy -q http://localhost:4412 -root out-words -ts words
1.6 Check http://localhost:4412
1.7 Remember to remove the dir out-words

2-Create real view with SploutSQL

3-Merge views

4-Show results
4.1 mvn clean install
4.2 mvn dependency:copy-dependencies
4.3 mvn exec:exec -Dexec.executable="java" -Dexec.args="-cp target/classes:target/dependency/* FIXME-NAMED CLASS"


Notes:

1) Clean Redis with redis-cli:

FLUSHDB       - Removes data from your connection's CURRENT database.
FLUSHALL      - Removes data from ALL databases.


