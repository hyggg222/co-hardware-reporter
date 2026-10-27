import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class usbReportTest {

    @Test
    public void testPrintReport() {
        // This test will simply call the printReport method to ensure it runs without exceptions.
        assertDoesNotThrow(() -> usbReport.printReport());
    }
    @Test
    void testFormatUsbDevice() {
        // Testing the formatUsbDevice method with sample input
        //Mock data for testing
        int bus = 1;
        int device = 5;
        int vendor = 0x8086;
        int product = 0x1234;
        String vendorName = "Intel Corp. ";
        String deviceName = "Test Controller";

        //Getting the result
        String result = usbReport.formatUsbDevice(bus, device, vendor, product, vendorName, deviceName);
        //Expected output
        String expected = "  Bus 001 Device 005: ID 8086:1234  Intel Corp.  Test Controller\n";
        assertEquals(expected, result);
    }

    @Test
    void unknownUsbTest() {
        assertEquals("  Bus 001 Device 005: ID 1234:5678  Unknown Unknown\n",
                    usbReport.formatUsbDevice(1, 5, 0x1234, 0x5678, null, null));
    }
}