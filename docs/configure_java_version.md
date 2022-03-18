# Install and configure Java

### Useful links

[set up tooling version](https://askubuntu.com/questions/121654/how-to-set-default-java-version)


## Installation

##### install OpenJDK 8 - Java Developpement Kit

`sudo apt-get install openjdk-8-jre`

`sudo update-java-alternatives --set java-1.8.0-openjdk-amd64`

# how to set up Java tooling version

See this; run: https://stackoverflow.com/questions/21115133/ubuntu-change-the-path-from-openjdk-6-to-oracle-jdk-7/35305363#35305363

sudo  update-java-alternatives --list

to list off all the Java installations on a machine by name and directory, and then run

sudo  update-java-alternatives --set [JDK/JRE name e.g. java-8-oracle]

to choose which JRE/JDK to use.

If you want to use different JDKs/JREs for each Java task, you can run update-alternatives to configure one java executable at a time; you can run

sudo  update-alternatives --config java[Tab]

to see the Java commands that can be configured (java, javac, javah, javaws, etc). And then

sudo  update-alternatives --config [javac|java|javadoc|etc.]

will associate that Java task/command to a particular JDK/JRE.

You may also need to set JAVA_HOME for some applications: from this answer you can use

export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")

for JDKs, or

export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:jre/bin/java::")

for JREs.
