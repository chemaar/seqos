1-Put files to be processed in HDFS

Ej: go to /home/chema/data and execute the next command:

$HADOOP_HOME/bin/hadoop fs -put /home/chema/projects/seqos/prototypes/mapreduce-intro/src/main/resources/examples input

2-Compile and package

mvn assembly:assembly

3-Execute

$HADOOP_HOME/bin/hadoop jar target/mapreduce-turorial-0.1-SNAPSHOT-job.jar /home/chema/data/input/ /home/chema/data/output

4-Check results

$HADOOP_HOME/bin/hadoop fs -cat /home/chema/data/output/part-r-00000
