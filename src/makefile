JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Main.java \
	Sattothreesat.java \
	Coltosat.java \
	Threesattocol.java \
	CNF.java \
	COL.java

default: classes run1 run2 run3

classes: $(CLASSES:.java=.class)

run1:
	@echo "#!/bin/bash" > sattothreesat
	@echo "java Main 1" >> sattothreesat
	chmod 777 sattothreesat

run2:
	@echo "#!/bin/bash" > coltosat
	@echo "java Main 2" >> coltosat
	chmod 777 coltosat

run3:
	@echo "#!/bin/bash" > threesattocol
	@echo "java Main 3" >> threesattocol
	chmod 777 threesattocol

clean:
	$(RM) *.class sattothreesat coltosat threesattocol