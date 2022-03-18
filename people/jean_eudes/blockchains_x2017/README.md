# Blockchains - X2017

### Useful links

[Java naming conventions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)

[Java | w3school](https://www.w3schools.com/java/default.asp)

##### enums in java

[enum | the Java tutorial](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)

[Java enum tutorial | HowToDoInJava](https://howtodoinjava.com/java/enum/enum-tutorial/) 

##### shell scripting and piping

[grep for words containing both uppercase and lowercase letters](https://stackoverflow.com/questions/46324444/grep-for-words-containing-both-uppercase-and-lowercase-letters)

[first two results from ls command](https://stackoverflow.com/questions/10520120/first-two-results-from-ls-command)

[bash:tip_colors_and_formatting](https://misc.flogisoft.com/bash/tip_colors_and_formatting)

##### java Makefiles

[Makefile with Jar and Package Dependencies](https://stackoverflow.com/questions/5487838/makefile-with-jar-and-package-dependencies)

[How to compile packages in java?](https://stackoverflow.com/questions/10546720/how-to-compile-packages-in-java)

##### errors

[Why is Eclipse asking to declare strictfp inside enum](https://stackoverflow.com/questions/28306145/why-is-eclipse-asking-to-declare-strictfp-inside-enum)

##### hash & hashCode()

[Guide to hashCode() in Java](https://www.baeldung.com/java-hashcode) 

##### java objects

[using copy constructor](https://www.tutorialspoint.com/how-do-we-copy-objects-in-java)

[class String](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#concat(java.lang.String))

[String.format()](https://www.javatpoint.com/java-string-format)

[class StringBuilder](https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html#append(boolean))

[class LinkedList<E>](https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html)


### TODOs

* [X] Complete Makefile
* [X] Finish color console prints
* [X] do subject

### make commands

> These commands are to be run in the root directory of the project. This is where the `Makefile` is located.

`make` : build the project.

`make run` : run the project code.

`make clean` : erase project `.class` files.

`make test` : run project test main.

### compilation

`javac -classpath bin/ -sourcepath src/ src/MainTest.java -d bin/` : This command worked to compile the whole stuff.

```
(base) onyr@aezyr:~/Documents/code/java/jean_eudes/blockchains_x2017$ javac -classpath bin/ -sourcepath src/ src/MainTest.java -d bin/
(base) onyr@aezyr:~/Documents/code/java/jean_eudes/blockchains_x2017$ ls bin/ | grep [Tt]est | cut -f 1 -d '.' | xargs java -classpath bin/
ESCFG_256TURQUOISE_CmHello World

```

### end result

Compile and run with `make run`. Clean if necessary with `make clean`. Launch tests with `make test`.

```shell
(base) onyr@aezyr:~/Documents/code/java/jean_eudes/blockchains_x2017$ make clean
rm -rf bin/*
(base) onyr@aezyr:~/Documents/code/java/jean_eudes/blockchains_x2017$ make run
*** compiling src/Main.java *** 
mkdir -p bin/
javac -g -classpath bin/ -sourcepath src/ src/Main.java -d bin/
*** src/Main.java compilation success *** 
*** compiling src/MainTest.java *** 
mkdir -p bin/
javac -g -classpath bin/ -sourcepath src/ src/MainTest.java -d bin/
*** src/MainTest.java compilation success *** 
*** Executing code *** 
ls bin/ | grep [A-Z] | head -1 | cut -f 1 -d '.' | xargs java -classpath bin/ 
Client.hash() : 345217
Client.hash() : 109297
Transaction.hash() : 335350681
H.hash() : 335357408
blockchain : ([(Bob, 7400), (Alice, 22600), 3000], 0) ->  null 
blockchain : ([(Bob, 7400), (Alice, 22600), 3000], 0) -> ([(Alice, 22600), (Bob, 7400), 400], 335357408) ->  null 

```
