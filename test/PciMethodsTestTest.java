import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class PciMethodsTestTest {

    @Test
    void testGetCounta() throws IOException {

        assertEquals(4, PciMethods.getCounta());
    }

    @Test
    void testGetAddressa() throws IOException {
        assertEquals("0000:02:00.0", PciMethods.getAddressa(0));
    }

    @Test
    void testGetBusa() throws IOException {
        assertEquals(2, PciMethodsTest.getBusa(0));
    }

    @Test
    void testGetDevicea() throws IOException {
        assertEquals(0, PciMethodsTest.getDevicea(0));
    }

    @Test
    void testGetFunctiona() throws IOException {
        assertEquals(0, PciMethodsTest.getFunctiona(0));
    }

    @Test
    void testGetVendorIda() throws IOException {
        assertEquals(0x1022, PciMethodsTest.getVendorIda(0));
    }

    @Test
    void testGetDeviceIda() throws IOException {
        assertEquals(0x1481, PciMethodsTest.getDeviceIda(0));
    }

    @Test
    void testGetClassCodea() throws IOException {
        assertEquals(0x060000, PciMethodsTest.getClassCodea(0));
    }

    @Test
    void testAlphabeticalSort() throws IOException {

        //Creates sorted list in sorted.
        List<Path> sorted = PciMethodsTest.alphabettest();


        //Resolves vendor names of sorted list.
        String vendor1 = idLookup.pci().vendorName(
                (int) sysFile.number(sorted.get(0).resolve("vendor")));

        String vendor2 = idLookup.pci().vendorName(
                (int) sysFile.number(sorted.get(1).resolve("vendor")));

        String vendor3 = idLookup.pci().vendorName(
                (int) sysFile.number(sorted.get(2).resolve("vendor")));

        String vendor4 = idLookup.pci().vendorName(
                (int) sysFile.number(sorted.get(3).resolve("vendor")));

        //In sample data, vendor4 is intentionally null.
        //Since we're resolving vendorname again here it needs to be reassigned.
        if (vendor4 == null) {
            vendor4 = "Unknown Origin";
        }

        assertEquals("Advanced Micro Devices, Inc. [AMD]", vendor1);
        assertEquals("Intel Corporation", vendor2);
        assertEquals("NVIDIA Corporation", vendor3);
        assertEquals("Unknown Origin", vendor4);
    }
}