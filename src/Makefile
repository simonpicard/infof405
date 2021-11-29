JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

CLASSES = Main.java

default: classes

classes:
	find . -name "*.java" > sources.txt
	javac $(JFLAGS) @sources.txt
	rm sources.txt

clean:
	find . -name "*.class" > classes.txt
	xargs rm < classes.txt
	rm classes.txt