# co-hardware-reporter

A small Java program that reads hardware information from a Linux machine and prints it as plain text: processor, memory, PCI devices and USB devices.

## Build, run and test

Needs Java 11 or newer and make. Run from the repo root so the ids folder is found.

    make build    # compile src into out
    make test     # build, then run all unit tests in test
    make run      # build, then run demo

The first make test downloads the JUnit jar into lib (needs internet once). Why make and plain javac: see docs/adr/0001-build-and-test-tool.md.

## Where the data comes from

| Information | File |
| --- | --- |
| Processor model, number of cores | /proc/cpuinfo |
| CPU busy and idle time | /proc/stat |
| Memory | /proc/meminfo |
| PCI devices | /sys/bus/pci/devices/ |
| USB devices | /sys/bus/usb/devices/ |
| Vendor and device names | ids/pci.ids, ids/usb.ids |

## Starter code

cpuInfo, memInfo, pciInfo, usbInfo, idLookup, sysFile and demo in src came with the project as starter code and are unchanged so far. demo only shows how to call them.

ids/pci.ids (version 2026.10.12) is from https://pci-ids.ucw.cz/ and ids/usb.ids (version 2026.06.26) is from https://github.com/vcrhonek/hwdata. Both are under GPL-2.0-or-later or BSD-3-Clause.
