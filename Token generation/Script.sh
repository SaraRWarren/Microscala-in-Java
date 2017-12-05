#!/bin/bash
echo "Assignment 2: MicroScala with Jflex"
echo "Sara Warren srw0207"
echo "..."
sleep 1s;
echo "Compiling Java Micro Scala..."
javac MicroScala.java
sleep 2s;
echo "Running Scala Test Files..."
java MicroScala < Test1.scala >> Output.file
java MicroScala < Test2.scala >> Output.file
java MicroScala < Test3.scala >> Output.file
java MicroScala < Test4.scala >> Output.file
java MicroScala < Test5.scala >> Output.file
java MicroScala < Test6.scala >> Output.file
sleep 1s;
echo "Done! File output is located in Output.file"
