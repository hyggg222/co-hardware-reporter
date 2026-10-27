import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PciMethodsTest {

    @Test
    void testAlphabeticalSort() throws IOException {

        int[][] testalphabet = pciStats.getDevices();
        idLookup pci = idLookup.pci();

        Arrays.sort(testalphabet, (a, b) -> {

            String vendorA = pci.vendorName(a[3]);
            String vendorB = pci.vendorName(b[3]);

            if (vendorA == null) {
                vendorA = "Unknown";
            }

            if (vendorB == null) {
                vendorB = "Unknown";
            }

            return vendorA.compareTo(vendorB);
        });

        assertArrayEquals(testalphabet, PciMethods.alphabeticallist());

    }


    @Test
    void testBusSort() throws IOException {

        int[][] pcibussortlisttest = pciStats.getDevices();

        Arrays.sort(pcibussortlisttest, (a, b) ->
                Integer.compare(a[0], b[0])
        );

        assertArrayEquals(pcibussortlisttest, PciMethods.pcibussort());

    }

    @Test
    void testVendorCount() throws IOException {

        int[][] devicestest = PciMethods.alphabeticallist();
        idLookup pci = idLookup.pci();

        List<String> resultstest = new ArrayList<>();

        if (devicestest.length == 0) {
            return;
        }

        String currentVendor = pci.vendorName(devicestest[0][3]);

        if (currentVendor == null) {
            currentVendor = "Unknown";
        }

        int count = 1;

        for (int i = 1; i < devicestest.length; i++) {
            String vendor = pci.vendorName(devicestest[i][3]);

            if (vendor == null) {
                vendor = "Unknown";
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

        String[] expected = resultstest.toArray(new String[0]);

        assertArrayEquals(expected, PciMethods.vendorCount());
    }

    @Test
    void testOrUnknown() {
        assertEquals("Unknown", PciMethods.orUnknown(null));
        assertEquals("Intel Corporation", PciMethods.orUnknown("Intel Corporation"));
    }
}