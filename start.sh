#!/bin/bash
mvn clean
mvn package
java -jar target/reactive-flashcards-1.0.0.jar
#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar target/reactive-flashcards-1.0.0.jar
