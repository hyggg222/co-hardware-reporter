
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
    //Main method used that uses real PCI devices of machine.
    public class PciMethods {


        // Assisted with gen-AI Copilot
        public static int[][] alphabeticallist() throws IOException {

            int[][] devices = pciStats.getDevices();
            idLookup pci = idLookup.pci();

            Arrays.sort(devices, (a, b) -> {

                String vendorA = orUnknown(pci.vendorName(a[3]));
                String vendorB = orUnknown(pci.vendorName(b[3]));

                return vendorA.compareTo(vendorB);
            });

            return devices;
        }

        public static int[][] pcibussort() {

            int[][] pcibussortlist = pciStats.getDevices();

            Arrays.sort(pcibussortlist, (a, b) ->
                    Integer.compare(a[0], b[0])
            );

            return pcibussortlist;
        }

        public static String[] vendorCount() throws IOException {

            int[][] devices = alphabeticallist();
            idLookup pci = idLookup.pci();

            List<String> results = new ArrayList<>();

            if (devices.length == 0) {
                return new String[0];
            }

            String currentVendor = orUnknown(pci.vendorName(devices[0][3]));

            int count = 1;

            for (int i = 1; i < devices.length; i++) {
                String vendor = orUnknown(pci.vendorName(devices[i][3]));

                if (vendor.equals(currentVendor)) {
                    count++;
                } else {
                    results.add(currentVendor + " - " + count);
                    currentVendor = vendor;
                    count = 1;
                }
            }

            results.add(currentVendor + " - " + count);

            return results.toArray(new String[0]);
        }

        // The name, or "Unknown" when it is not in the ids file
        public static String orUnknown(String name) {
            return name != null ? name : "Unknown";
        }
    }