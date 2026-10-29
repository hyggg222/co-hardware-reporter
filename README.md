# co-hardware-reporter

A small Java program that reads hardware information from a Linux machine and prints it as plain text: processor, memory, PCI devices and USB devices.

## Build, run and test

Needs Java 11 or newer and make. Run from the repo root so the ids folder is found.

    make build    # compile src into out
    make test     # build, then run all unit tests in test
    make run      # build, then run demo
    make run MAIN=report   # build, then run any class that has a main method

The first make test downloads the JUnit jar into lib (needs internet once). Why make and plain javac: see docs/adr/001-build-an-test-tool.md.

## Run the report

    make report   # build, then run systemReport and save its output to report.txt

Run it from the repo root. It prints one line when it is done, and the report is in report.txt. The CPU load takes about one second to measure, so the run is not instant. It needs no display, so it also works on alderaan. The report has these parts, in this order:

| Part | What it prints |
| --- | --- |
| CPU | Number of cores, processor model, load of each core and the average load, from two readings about one second apart |
| Memory | Total memory, used memory and the percentage in use |
| PCI devices | The count, then the list as found, sorted by vendor name, and sorted by bus, device and function. Each line has the address, vendor and device ID, class and names. A name that is not found shows as Unknown |
| PCI summary | Number of devices per vendor |
| USB devices | The count, then each device with vendor and product ID and names. If there are none it says so |

## Extension: HTML page

    make export   # build, write the report as JSON to report.json, then run npx serve

Needs Node (npx). Open the address that npx serve prints. index.html, src/style.css and src/report.js read report.json and show it as a web page:

| Block | What the page shows |
| --- | --- |
| CPU | The processor model, one tile per core with its load, and the average load as a ring |
| Memory | A pie chart of used and unused memory in GiB, and the percentage in use |
| USB | The list of devices, or a message when there are none |
| PCI | One table with bus, ID, class and name, and three buttons to sort it (read order, name, bus; bus is the default). Next to it, a bar chart of devices per vendor |

The page has a dark layout. If report.json is missing or a part could not be read, the page shows a message in place of that part. The charts come from Chart.js, which the page loads from cdnjs.cloudflare.com, so the page needs internet. make report works on its own without any of this.

To see the error messages, run the report with no /proc and /sys, then serve the folder:

    java -Dhw.root=/nonexistent -cp out webReport > report.json && npx serve

## Where the data comes from

| Information | File |
| --- | --- |
| Processor model, number of cores | /proc/cpuinfo |
| CPU busy and idle time | /proc/stat |
| Memory | /proc/meminfo |
| PCI devices | /sys/bus/pci/devices/ |
| USB devices | /sys/bus/usb/devices/ |
| Vendor and device names | ids/pci.ids, ids/usb.ids |

## Files the program reads and writes

| File | Read or written | By |
| --- | --- | --- |
| /proc, /sys and ids files from the table above | read | make report, make export, make run |
| report.txt | written | make report |
| report.json | written, then read by the web page (index.html, src/report.js, src/style.css) | make export |
| out | written, the compiled classes | make build, make test, make report, make export |
| lib | written, the JUnit jar | make test, first run only |

report.txt, report.json, out and lib are in .gitignore. make clean deletes out and lib.

## Starter code

cpuInfo, memInfo, pciInfo, usbInfo, idLookup, sysFile and demo in src came with the project as starter code. We changed three:

- pciInfo: added getlist, which returns the list of device paths, and replaced the java.util imports with java.util.*. Used by the PCI report.
- demo: keeps the PCI count in a variable instead of calling getCount in the loop.
- cpuInfo: only a blank line added.

memInfo, usbInfo, idLookup and sysFile are unchanged. The report code is ours: systemReport, cpuReport, cpuStats, memReport, memStats, pciReport, pciStats, PciMethods, usbReport and webReport.

ids/pci.ids (version 2026.10.12) is from https://pci-ids.ucw.cz/ and ids/usb.ids (version 2026.06.26) is from https://github.com/vcrhonek/hwdata. Both are under GPL-2.0-or-later or BSD-3-Clause.
