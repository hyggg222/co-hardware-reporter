# Setup

Needs git and Java 11 or newer.

## Clone, build, run

    cd ~
    git clone git@github.com:hyggg222/co-hardware-reporter.git
    cd co-hardware-reporter
    mkdir -p out
    javac -d out src/*.java
    java -cp out demo

Run from the repo root, or the ids folder is not found.

## Where it runs

| Machine | Result |
| --- | --- |
| Linux, Linux VM | Everything |
| WSL2 | CPU and memory are real (memory is what WSL may use). PCI shows only a few virtual devices. No USB. |
| alderaan | Everything except USB |
| macOS | Does not run, no /proc or /sys |

docs/starter-README.md says WSL2 does not provide these files. It does, but it has no real PCI devices or USB. WSL2 is fine for development. Use alderaan or Linux for the final output.

## Before you start work

    git switch main
    git pull

Then follow CONTRIBUTING.md.
