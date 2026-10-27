import java.util.Arrays;

public class pciReport {
    public static String[] summary () throws Exception {
        int[][] devices = pciStats.getDevices();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] output = new String[numPCI+1];
        output[0] = String.format("%d PCI Devices", numPCI);

        for (int i = 0; i < numPCI; i++) {
            int vendor = devices[i][3];
            int device = devices[i][4];
            output[i+1] = String.format("  %s  %04x:%04x  %s %s", pciInfo.getAddress(i), vendor, device,
                              pci.vendorName(vendor), pci.deviceName(vendor, device));
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
            String address = String.format(
                    "%04x:%02x:%02x.%x",
                    devices[i][6],
                    devices[i][0],
                    devices[i][1],
                    devices[i][2]
            );
            int vendor = devices[i][3];
            int device = devices[i][4];
            outputalphabet[i+1] = String.format("  %s  %04x:%04x  %s %s", address, vendor, device,
                    pci.vendorName(vendor), pci.deviceName(vendor, device));
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
            String address = String.format(
                    "%04x:%02x:%02x.%x",
                    devices[i][6],
                    devices[i][0],
                    devices[i][1],
                    devices[i][2]
            );
            int vendor = devices[i][3];
            int device = devices[i][4];
            outputbus[i+1] = String.format("  %s  %04x:%04x  %s %s", address, vendor, device,
                pci.vendorName(vendor), pci.deviceName(vendor, device));
        }
        return outputbus;
    }

    public static void main (String[] args) throws Exception {
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
        try {
            String[] output = summary();
            String[] outputAlphabet = summaryalphabet();
            String[] outputBusSort = summarybussort();

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


            //vendors
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
        } catch (Exception e) {
            System.out.println("}");
        }
    }
}