# 0001: Build and test with make, javac and the JUnit console jar

Status: accepted (issue #10)

## Context

We need one command to build and one to run tests, the same for everyone. The code is a flat
src folder in the default package, and the starter files must stay as they are.

## Decision

A Makefile with build, test, run and clean. It calls javac directly. Tests live in test and use
JUnit 5. The JUnit console standalone jar (one file) is downloaded into lib on the first
make test, and lib is not committed.

## Options not chosen

- Maven or Gradle: need a wrapper or an install, and want src/main/java, so the starter files
  would have to move. Too much for four people and a small program.
- Committing the JUnit jar: about 2 MB of binary in git, and it is hard to see which version it is.

## Consequences

- Nothing to install except Java and make; the Makefile is short enough to read in a minute.
- The first make test needs internet. Pinned version means the same jar for everyone.
- No dependency management. If we ever need a second library, revisit this (Maven).
