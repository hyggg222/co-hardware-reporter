
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public class PciMethods {

        public static List<Path> alphabet() throws IOException {
            //fill new arraylist with same values of devices,
            //so that we don't override or add new values to devices.

            List<Path> alphabetlist = new ArrayList<>(pciInfo.getlist());

            idLookup pci = idLookup.pci();

            //Lambda here basically tells .sort to use this logic
            // instead when deciding the order of two elements

            // Assisted with GENAI Co-pilot
            alphabetlist.sort((a, b) -> {
                int indexA = pciInfo.getlist().indexOf(a);
                int indexB = pciInfo.getlist().indexOf(b);

                String vendorA = pci.vendorName(pciInfo.getVendorId(indexA));
                String vendorB = pci.vendorName(pciInfo.getVendorId(indexB));

                if ((vendorA) == null){
                    vendorA = "Unknown Origin";
                }
                if ((vendorB) == null){
                    vendorB = "Unknown Origin";
                }

                return vendorA.compareTo(vendorB);
            });

            return alphabetlist;

        }
        //Methods but for alphabetically sorted list
        /** How many PCI devices there are. */
        public static int getCounta() throws IOException {
            return alphabet().size();
        }

        /** The address as text, for example "0000:00:02.0". */
        public static String getAddressa(int index) throws IOException {
            return alphabet().get(index).getFileName().toString();
        }

        /** The bus number, taken from the address. */
        public static int getBusa(int index) throws IOException {
            return Integer.parseInt(getAddressa((index)).substring(5, 7), 16);
        }

        /** The device number on the bus, taken from the address. */
        public static int getDevicea(int index) throws IOException {
            return Integer.parseInt(getAddressa((index)).substring(8, 10), 16);
        }

        /** The function number of the device, taken from the address. */
        public static int getFunctiona(int index) throws IOException {
            return Integer.parseInt(getAddressa((index)).substring(11, 12), 16);
        }

        /** The vendor ID, for example 0x8086, or -1 if it cannot be read. */
        public static int getVendorIda(int index) throws IOException {
            return (int) sysFile.number(alphabet().get(index).resolve("vendor"));
        }

        /** The device ID, or -1 if it cannot be read. */
        public static int getDeviceIda(int index) throws IOException {
            return (int) sysFile.number(alphabet().get(index).resolve("device"));
        }

        /** The 24-bit class code, for example 0x060000 for a host bridge, or -1. */
        public static int getClassCodea(int index) throws IOException {
            return (int) sysFile.number(alphabet().get(index).resolve("class"));
        }
    }


    // Class to fill a sample pci list to fulfill tests.
    // To have a constant test for every device as every device
    // expects different pci devices.
 class PciMethodsTest {

     private static final Path path = Paths.get("samplepcidevices");
     private static List<Path> devices;

     private static List<Path> listtest()
     {
         if (devices == null) {
             List<Path> found = new ArrayList<>();
             if (Files.isDirectory(path)) {
                 try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                     for (Path p : dir)
                         found.add(p);
                 } catch (IOException e) {
                     // leave the list as it is
                 }
             }
             Collections.sort(found);
             devices = found;

         }
         return devices;
     }

    public static List<Path> alphabettest() throws IOException {
        //fill new arraylist with same values of devices,
        //so that we don't override or add new values to devices.

        List<Path> alphabetlist = new ArrayList<>(listtest());

        idLookup pci = idLookup.pci();

        //Lambda here basically tells .sort to use this logic
        // instead when deciding the order of two elements

        // Assisted with GENAI Co-pilot
        alphabetlist.sort((a, b) -> {
            int indexA = listtest().indexOf(a);
            int indexB = listtest().indexOf(b);

            String vendorA = pci.vendorName(pciInfo.getVendorId(indexA));
            String vendorB = pci.vendorName(pciInfo.getVendorId(indexB));

            if ((vendorA) == null){
                vendorA = "Unknown Origin";
            }
            if ((vendorB) == null){
                vendorB = "Unknown Origin";
            }

            return vendorA.compareTo(vendorB);
        });

        return alphabetlist;

    }
    //Methods but for alphabetically sorted list
    /** How many PCI devices there are. */
    public static int getCounta() throws IOException {
        return alphabettest().size();
    }

    /** The address as text, for example "0000:00:02.0". */
    public static String getAddressa(int index) throws IOException {
        return alphabettest().get(index).getFileName().toString();
    }

    /** The bus number, taken from the address. */
    public static int getBusa(int index) throws IOException {
        return Integer.parseInt(getAddressa((index)).substring(5, 7), 16);
    }

    /** The device number on the bus, taken from the address. */
    public static int getDevicea(int index) throws IOException {
        return Integer.parseInt(getAddressa((index)).substring(8, 10), 16);
    }

    /** The function number of the device, taken from the address. */
    public static int getFunctiona(int index) throws IOException {
        return Integer.parseInt(getAddressa((index)).substring(11, 12), 16);
    }

    /** The vendor ID, for example 0x8086, or -1 if it cannot be read. */
    public static int getVendorIda(int index) throws IOException {
        return (int) sysFile.number(alphabettest().get(index).resolve("vendor"));
    }

    /** The device ID, or -1 if it cannot be read. */
    public static int getDeviceIda(int index) throws IOException {
        return (int) sysFile.number(alphabettest().get(index).resolve("device"));
    }

    /** The 24-bit class code, for example 0x060000 for a host bridge, or -1. */
    public static int getClassCodea(int index) throws IOException {
        return (int) sysFile.number(alphabettest().get(index).resolve("class"));
    }
}



