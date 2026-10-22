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

    public static void main (String[] args) throws Exception {
        String[] output = summary();
        for (int i=0; i<output.length; i++) {
            System.out.println(output[i]);
        }
    }
}