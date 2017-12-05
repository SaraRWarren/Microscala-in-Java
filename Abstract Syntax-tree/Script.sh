#!/bin/bash
echo "Assignment 4: MicroScala Parser Tree"
echo "Sara Warren srw0207"
echo "..."
sleep 1s;
echo "Compiling Java MicroScala..."
jflex MicroScala.jflex
javac MicroScalaLexer.java
javac MicroScalaTree.java
javac SyntaxAnalyzer.java
javac MicroScalaSyn.java

sleep 2s;

if [ $# -gt 0 ]; then
while [ "$1" != "" ];
do
	name="$1" 
	echo "Running $name File..."
	java MicroScalaSyn < $name >> Output.file
	shift
done
else
	echo "Running Scala Test Files..."
	java MicroScalaSyn < Test1.scala >> Output.file
	java MicroScalaSyn < Test2.scala >> Output.file
	java MicroScalaSyn < Test3.scala >> Output.file
	java MicroScalaSyn < Test4.scala >> Output.file
	java MicroScalaSyn < Test5.scala >> Output.file
	java MicroScalaSyn < Test6.scala >> Output.file
fi
sleep 1s;
echo "Done! File output is located in Output.file"
