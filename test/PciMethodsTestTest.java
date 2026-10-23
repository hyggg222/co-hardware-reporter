import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class PciMethodsTestTest {

    @Test
    void testAlphabeticalSort() throws IOException {

        List<Path> testlist = pciInfo.list();
        idLookup pci = idLookup.pci();

        testlist.sort((a, b) -> {
            try {
                int indexA = testlist.indexOf(a);
                int indexB = testlist.indexOf(b);

                String vendorA = pci.vendorName(pciInfo.getVendorId(indexA));
                String vendorB = pci.vendorName(pciInfo.getVendorId(indexB));

                if (vendorA == null) {
                    vendorA = "Unknown Origin";
                }
                if (vendorB == null) {
                    vendorB = "Unknown Origin";
                }

                return vendorA.compareTo(vendorB);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(testlist, PciMethods.alphabet());

    }


    @Test
    void testBusSort() throws IOException {

        List<Path> testlist = pciInfo.list();
        idLookup pci = idLookup.pci();

        pcibussortlist.sort((a, b) -> {
            int indexA = testlist.indexOf(a);
            int indexB = testlist.indexOf(b);

            try {
            return Integer.compare(
                    pciInfo.getBus(indexA),
                    pciInfo.getBus(indexB)
            );
        });
             catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(testlist, PciMethods.pcibussort());

    }

    @Test
    void testVendorCount() throws IOException {

        List<Path> testlistdevices = PciMethods.alphabet();
        idLookup pci = idLookup.pci();

        List<String> resultstest = new ArrayList<>();

        if (testlistdevices.isEmpty()) {
            return;
        }

        int count = 1;

        String currentVendor = pci.vendorName(PciMethods.getVendorIdA(0));
        if (currentVendor == null) {
            currentVendor = "Unknown Origin";
        }

        for (int i = 1; i < testlistdevices.size(); i++) {

            String vendor = pci.vendorName(PciMethods.getVendorIdA(i));

            if (vendor == null) {
                vendor = "Unknown Origin";
            }

            if (vendor.equals(currentVendor)) {
                count++;
            } else {
                resultstest.add(currentVendor + " - " + count);

                currentVendor = vendor;
                count = 1;
            }
        }

        resultstest.add(currentVendor + " - " + count);

        String[] expectedvendorcount = resultstest.toArray(new String[0]);

        assertArrayEquals(expectedvendorcount, PciMethods.vendorCount());
    }
}