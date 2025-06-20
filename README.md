#### (Demo) Compilation Instructions

1. find -type f -name *java > ./src/main/java/com/app/demo/sources.txt
2. javac -sourcepath ./src/ -d ./bin/ @./src/main/java/com/app/demo/sources.txt
3. make sure manifest file looks like (with whitespace at end):

```
Main-Class: Demo
Class-Path: ../../resources/lib/cmu_us_kal.jar

```
4. jar -c --file ./src/main/java/com/app/demo/demo.jar --manifest ./src/main/java/com/app/demo/Manifest.txt -C ./bin/ .
5. java -jar ./src/main/java/com/app/demo/demo.jar

#### (Main program) Compilation Instructions

1. find -type f -name *java > ./src/main/java/com/app/sources.txt
2. javac -sourcepath ./src/ -d ./bin/ @./src/main/java/com/app/sources.txt
3. make sure manifest file looks like (with whitespace at end):

```
Main-Class: src.main.java.com.app.TextToSpeechified
Class-Path: src/main/java/com/resources/lib/cmu_us_kal.jar

```
4. jar -c --file textToSpeechified.jar --manifest ./src/main/java/com/app/Manifest.txt -C ./bin/ .
5. java -jar textToSpeechified.jar

##### Libraries

- [FreeTTS](https://freetts.sourceforge.io/)
- [javax-speech](https://github.com/umjammer/javax-speech)
- [Apache Commons Text](https://github.com/apache/commons-text/tree/master)
- [Apache Commons Lang](https://github.com/apache/commons-lang/tree/master)
