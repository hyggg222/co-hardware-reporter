JUNIT_VERSION = 1.11.4
JUNIT_JAR = lib/junit-platform-console-standalone-$(JUNIT_VERSION).jar
JUNIT_URL = https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$(JUNIT_VERSION)/junit-platform-console-standalone-$(JUNIT_VERSION).jar

.PHONY: build test run clean

build:
	mkdir -p out
	javac -d out src/*.java

$(JUNIT_JAR):
	mkdir -p lib
	curl -fsSL -o $@ $(JUNIT_URL)

test: build $(JUNIT_JAR)
	mkdir -p out/test
	javac -d out/test -cp out:$(JUNIT_JAR) test/*.java
	java -jar $(JUNIT_JAR) execute --class-path out:out/test --scan-class-path out/test

run: build
	java -cp out demo

clean:
	rm -rf out lib
