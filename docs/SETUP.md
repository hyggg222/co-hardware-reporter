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

Note:
- Clone, do not fork: you have write access, and branches live in this repo.
- On WSL, keep the repo in your Linux home (~).

## Result (WSL)

On WSL2 the output looks like this:

    cores:  16
    model:  11th Gen Intel(R) Core(TM) i7-11800H @ 2.30GHz
    core 0: busy=8627 idle=1133590
    memory: total=7993244 KiB, used=1766408 KiB
    PCI devices: 4
      2680:00:00.0  1414:008e  Microsoft Corporation Basic Render Driver
      ...
    USB devices: 0

The memory total is what WSL is allowed to use, by default half of the laptop's RAM.

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

Then follow CONTRIBUTING.md and make a branch for your issue.
