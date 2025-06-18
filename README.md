#### Compilation Instructions

1. find -type f -name *java > sources.txt
2. 
	a. javac -sourcepath ./src/ -d ./bin/ ./src/main/java/com/app/*.java
	b. javac -sourcepath ./src/ -d ./bin/ @sources.txt
3. jar -c --file demo.jar --manifest Manifest.txt -C ./bin/ .
4. make sure manifest file looks like (with whitespace at end):

```
Main-Class: src.main.java.com.app.Demo
Class-Path: 

```
4. java -jar demo.jar
