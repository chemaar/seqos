#!/bin/bash
#Getting automatically names
artifactId=`grep artifactId pom.xml | head -1 | cut -d">" -f2 | cut -d"<" -f1`
version=`grep version pom.xml | head -1 | cut -d">" -f2 | cut -d"<" -f1`
input="input"
output="output"
#Compiling and packaging
mvn -o clean
mvn -o assembly:assembly -Dmaven.test.skip=true
#Preparing dirs
#$HADOOP_HOME/bin/hadoop fs -put "${input}"
$HADOOP_HOME/bin/hadoop fs -rmr "${output}"
#Executing
$HADOOP_HOME/bin/hadoop jar target/"${artifactId}-${version}"-job.jar "${input}" "${output}"

