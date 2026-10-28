import java.util.Arrays;

public class pciReport {
    public static String[] summary () throws Exception {
        int[][] devices = pciStats.getDevices();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] output = new String[numPCI+1];
        output[0] = String.format("%d PCI Devices", numPCI);

        for (int i = 0; i < numPCI; i++) {
            output[i+1] = format(devices[i], pci);
        }
        return output;
    }

    public static String[] summaryalphabet () throws Exception {
        int[][] devices = PciMethods.alphabeticallist();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] outputalphabet = new String[numPCI+1];
        outputalphabet[0] = String.format("%d PCI Devices ( alphabetically sorted )", numPCI);

        for (int i = 0; i < numPCI; i++) {
            outputalphabet[i+1] = format(devices[i], pci);
        }

        return outputalphabet;
    }

    public static String[] summarybussort () throws Exception {
        int[][] devices = PciMethods.pcibussort();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] outputbus = new String[numPCI+1];
        outputbus[0] = String.format("%d PCI Devices ( sorted by bus )", numPCI);


        for (int i = 0; i < numPCI; i++) {
            outputbus[i+1] = format(devices[i], pci);
        }
        return outputbus;
    }

    // One PCI line: address, IDs, class, vendor and device name
    public static String format(int[] d, idLookup pci) {
        String address = String.format("%04x:%02x:%02x.%x", d[6], d[0], d[1], d[2]);
        String cls = pci.className(d[5]);
        if (cls == null) {
            cls = String.format("%06x", d[5]);
        }
        return String.format("  %s  %04x:%04x  %s: %s %s", address, d[3], d[4], cls,
                PciMethods.orUnknown(pci.vendorName(d[3])), PciMethods.orUnknown(pci.deviceName(d[3], d[4])));
    }

    public static void pciPrintReport() throws Exception {
        String[] output = summary();
        String[] outputalphabet = summaryalphabet();
        String[] outputbussort = summarybussort();
        String[] vendorcount = PciMethods.vendorCount();

        for (int i=0; i<output.length; i++) {
            System.out.println(output[i]);
        }
        System.out.println("");
        for (int i=0; i<outputalphabet.length; i++) {
            System.out.println(outputalphabet[i]);
        }
        System.out.println("");
        for (int i=0; i<outputbussort.length; i++) {
            System.out.println(outputbussort[i]);
        }
        System.out.println("");
        System.out.println("Count of PCI devices per vendor: ");
        for (int i=0; i<vendorcount.length; i++) {
            System.out.println("  " + vendorcount[i]);
        }

    }

    public static void printJSON () throws Exception {
        String[] output = summary();
        String[] outputAlphabet = summaryalphabet();
        String[] outputBusSort = summarybussort();

        //vendors
        String[] baseVendorCount = PciMethods.vendorCount();
        String[][] vendorCount = new String[baseVendorCount.length][2]; //have every vendor split into a list with its name & count
        for (int i=0; i<baseVendorCount.length; i++) {
            vendorCount[i] = baseVendorCount[i].split(" - ");
        }

        String starting = String.format(
            "\t\"pci\": {\n\t\t\"count\": "
        );

        System.out.printf("%s", starting);
        System.out.printf("\"There are %s\",\n", output[0]); //count

        //devices
        System.out.printf("\t\t\"devices\": [\n");
        if (output.length > 1) {
            webReport.listJSON(Arrays.stream(output, 1, output.length).toArray(String[]::new));
            System.out.printf(",\n");
        } else {
            System.out.printf("],\n");
        }

        System.out.printf("\t\t\"alphaDevices\": [\n"); //devices by alphabet
        if (outputAlphabet.length > 1) {
            webReport.listJSON(Arrays.stream(outputAlphabet, 1, outputAlphabet.length).toArray(String[]::new));
            System.out.printf(",\n");
        } else {
            System.out.printf("],\n");
        }

        System.out.printf("\t\t\"busDevices\": [\n"); //devices by bus
        if (outputBusSort.length > 1) {
            webReport.listJSON(Arrays.stream(outputBusSort, 1, outputBusSort.length).toArray(String[]::new));
            System.out.printf(",\n");
        } else {
            System.out.printf("],\n");
        }

        //vendors printing
        System.out.printf("\t\t\"vendorCount\": {\n\t\t\t\"names\": [\n");

        if (baseVendorCount.length > 0) {
            //names
            for (int i=0; i<baseVendorCount.length-1; i++) {
                System.out.printf("\t\t\t\t\"%s\",\n", vendorCount[i][0]);
            }
            System.out.printf("\t\t\t\t\"%s\"\n\t\t\t],\n", vendorCount[baseVendorCount.length-1][0]); //last vendor

            //counts
            System.out.printf("\t\t\t\"counts\": [\n");
            for (int i=0; i<baseVendorCount.length-1; i++) {
                System.out.printf("\t\t\t\t\"%s\",\n", vendorCount[i][1]);
            }
            System.out.printf("\t\t\t\t\"%s\"\n\t\t\t]\n\t\t}\n", vendorCount[baseVendorCount.length-1][1]); //last count
        } else {
            System.out.printf("]\n}");
        }
        System.out.printf("\n\t}");
    }
}