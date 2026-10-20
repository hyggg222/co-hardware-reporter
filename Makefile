JUNIT_VERSION = 1.11.4
JUNIT_JAR = lib/junit-platform-console-standalone-$(JUNIT_VERSION).jar
JUNIT_SHA256 = b016ef6b1c3454d6d7c2c88ce081dabf289699686af6622d6e4e2e1b54b4a2fc
JUNIT_URL = https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/$(JUNIT_VERSION)/junit-platform-console-standalone-$(JUNIT_VERSION).jar

.PHONY: build test run clean

build:
	mkdir -p out
	javac -d out src/*.java

$(JUNIT_JAR):
	mkdir -p lib
	curl -fsSL -o $@ $(JUNIT_URL)
	echo "$(JUNIT_SHA256)  $@" | shasum -a 256 -c - || { rm -f $@; echo "JUnit jar failed the checksum check and was deleted"; exit 1; }

test: build $(JUNIT_JAR)
	mkdir -p out/test
	javac -d out/test -cp out:$(JUNIT_JAR) test/*.java
	java -jar $(JUNIT_JAR) execute --class-path out:out/test --scan-class-path out/test

run: build
	java -cp out demo

clean:
	rm -rf out lib
