# Java

### Useful links

##### java version & configuration

[How to Find JAVA_HOME](https://www.baeldung.com/find-java-home)


## Java

### commands

`java -jar <jar_file>`: execute `.jar`.


### Versions & configuration

##### list versions of java installed

`update-java-alternatives` : cool package/command to select the java version easily.

```shell
(base) onyr@aezyr:/opt/maven/apache-maven-3.8.1-bin/apache-maven-3.8.1/bin$ update-java-alternatives -l
java-1.11.0-openjdk-amd64      1111       /usr/lib/jvm/java-1.11.0-openjdk-amd64
java-1.8.0-openjdk-amd64       1081       /usr/lib/jvm/java-1.8.0-openjdk-amd64
```

##### set up JAVA_HOME

`echo $JAVA_HOME` : Displays the content of the variable. Make sure it corresponds with the right java version (see command below). This variable is defined inside `.bashrc`.

`export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")` : command to put inside `.bashrc`.

> **NB :** It seams that there were a problem with the export. If the export fails, try to restart the computer or use `source ~/.bashrc`.

`java -XshowSettings:properties -version` : Show java settings directly using the `java` command.

```shell
(base) onyr@aezyr:~$ java -XshowSettings:properties -version
Property settings:
    awt.toolkit = sun.awt.X11.XToolkit
    file.encoding = UTF-8
    file.separator = /
    java.awt.graphicsenv = sun.awt.X11GraphicsEnvironment
    java.awt.printerjob = sun.print.PSPrinterJob
    java.class.path = 
    java.class.version = 55.0
    java.home = /usr/lib/jvm/java-11-openjdk-amd64
    java.io.tmpdir = /tmp
    java.library.path = /usr/java/packages/lib
        /usr/lib/x86_64-linux-gnu/jni
        /lib/x86_64-linux-gnu
        /usr/lib/x86_64-linux-gnu
        /usr/lib/jni
        /lib
        /usr/lib
    java.runtime.name = OpenJDK Runtime Environment
    java.runtime.version = 11.0.10+9-Ubuntu-0ubuntu1.20.04
[...]
    user.dir = /home/onyr
    user.home = /home/onyr
    user.language = en
    user.name = onyr
    user.timezone = 

openjdk version "11.0.10" 2021-01-19
OpenJDK Runtime Environment (build 11.0.10+9-Ubuntu-0ubuntu1.20.04)
OpenJDK 64-Bit Server VM (build 11.0.10+9-Ubuntu-0ubuntu1.20.04, mixed mode, sharing)
```

## Maven

### Useful links

[Install Maven](https://www.journaldev.com/33588/install-maven-linux-ubuntu)

[maven tutorial | Tutorialspoint](https://www.tutorialspoint.com/maven/maven_creating_project.htm)

### build & run

`mvn --version`: Give back the version of maven, java and directory.
`mvn package`: Compile, run tests and build the .jar file of the project.
`mvn exec:java`: Run the .jar file (run the game).
`mvn package exec:java`: Compile, run tests, build and run the .jar file.

### new Maven project

`mvn archetype:generate -DgroupId=amgs -DartifactId=amgs_maven -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false`

NB: don't forget to create the resources folder inside ./src/main/

### install maven

1. Download and unzip the bin from[there](https://maven.apache.org/download.cgi).
2. Create a `maven` dir inside `/opt`. Use `sudo mkdir maven`.

```shell
(base) onyr@aezyr:/opt$ mkdir maven
mkdir: cannot create directory ‘maven’: Permission denied
(base) onyr@aezyr:/opt$ sudo !!
sudo mkdir maven
[sudo] password for onyr: 
Sorry, try again.
[sudo] password for onyr: 
```

3. Put the bin folder inside `/opt/`.

```shell
(base) onyr@aezyr:~/Downloads$ mv apache-maven-3.8.1-bin /opt/maven/
mv: cannot move 'apache-maven-3.8.1-bin' to '/opt/maven/apache-maven-3.8.1-bin': Permission denied
(base) onyr@aezyr:~/Downloads$ sudo !!
sudo mv apache-maven-3.8.1-bin /opt/maven/
[sudo] password for onyr: 
```

4. Export the bin folder of maven to the `PATH` variable. This is done in `.bashrc`.

```shell
export PATH=$PATH:/opt/maven/apache-maven-3.8.1-bin/apache-maven-3.8.1/bin
```

### configure maven

> Make sure to install the `exec-maven-plugin` to be able to have the `exec:java` command in order to execute the `.jar` file. Add it in `pom.xml` file.

```toml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>1.6.0</version>
    <configuration>
        <mainClass>amgs.App</mainClass>
    </configuration>
</plugin>
```
