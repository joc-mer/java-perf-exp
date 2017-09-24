#!/bin/sh
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DgroupId=io.jocmer.exp -DartifactId=io.jocmer.exp -Dversion=1.0
