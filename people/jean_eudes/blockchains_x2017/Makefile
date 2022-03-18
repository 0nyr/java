# echo -e colors
# WARN : don't put " and use the echo command, not echo -e
LIGHT_ORANGE_COLOR=\e[38;5;216m
TURQUOISE_COLOR=\e[38;5;43m
LIGHT_BLUE_COLOR=\e[38;5;153m
RED_COLOR=\e[38;5;196m
NO_COLOR=\e[0m

# vars
ECHO = @echo # @echo hides this command in terminal, not its output
JC = javac
JFLAGS = -g
CLASSPATH = bin/
SOURCEPATH = src/

SRCS=$(wildcard src/*.java) $(wildcard src/*/*.java) $(wildcard src/*/*/*.java)
SFILE=$(SRCS:src/%.java=bin/%.class)
EXAMPLE=bin/MyFirstJavaProgram.class

# rules
default: compile

compile: $(SFILE)

bin/%.class: src/%.java
	@$(ECHO) "$(LIGHT_BLUE_COLOR)*** compiling $< *** $(NO_COLOR)"
	mkdir -p $(dir $@)
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) -sourcepath $(SOURCEPATH) $< -d $(dir $@)
	@$(ECHO) "$(TURQUOISE_COLOR)*** $< compilation success *** $(NO_COLOR)"


run: compile
	@$(ECHO) "$(TURQUOISE_COLOR)*** Executing code *** $(NO_COLOR)"
	ls bin/ | grep [A-Z] | head -1 | cut -f 1 -d '.' | xargs java -classpath bin/ 
# first print the main .class name
# We expect the Main or App to start with a capital letter, so grep that
# We expect Xxxx and XxxxTest, so run the first one only, head -1 retreive first line
# then remove the `.class` string because the java command doesn't like it
# then use xargs to transform the output into arguments
# finally, use -classpath bin/ to tell java bin/ is a directory

test: compile
	@$(ECHO) "$(TURQUOISE_COLOR)*** Executing code *** $(NO_COLOR)"
	ls bin/ | grep [Tt]est | cut -f 1 -d '.' | xargs java -classpath bin/ 

# commands
clean:
	rm -rf bin/*

dirs:
	mkdir -p bin/
	mkdir -p src/
	mkdir -p doc/


.PHONY: clean run dirs default compile