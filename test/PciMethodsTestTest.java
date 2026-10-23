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
}