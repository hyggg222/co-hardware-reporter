import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.IOException;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;



public class pciReportTest {
    @Test
    void summaryTest() throws Exception {
        int[][] devicestestlist = pciStats.getDevices();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] output = new String[numPCI+1];
        output[0] = String.format("%d PCI Devices", numPCI);

        for (int i = 0; i < numPCI; i++) {
            output[i+1] = pciReport.format(devicestestlist[i], pci);
        }

        assertArrayEquals(output,pciReport.summary());

    }

    @Test
    void alphabetReportTest() throws Exception {
        int[][] devicestestlist = PciMethods.alphabeticallist();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] outputalphabet = new String[numPCI+1];
        outputalphabet[0] = String.format("%d PCI Devices ( alphabetically sorted )", numPCI);

        for (int i = 0; i < numPCI; i++) {
            outputalphabet[i+1] = pciReport.format(devicestestlist[i], pci);
        }

        assertArrayEquals(outputalphabet,pciReport.summaryalphabet());
    }

    @Test
    void busSortReportTest() throws Exception{
        int[][] devicestestlist = PciMethods.pcibussort();

        idLookup pci = idLookup.pci();
        int numPCI = pciInfo.getCount();

        String[] outputbus = new String[numPCI+1];
        outputbus[0] = String.format("%d PCI Devices ( sorted by bus )", numPCI);


        for (int i = 0; i < numPCI; i++) {
            outputbus[i+1] = pciReport.format(devicestestlist[i], pci);
        }

        assertArrayEquals(outputbus,pciReport.summarybussort());

    }

    @Test
    void formatTest(@TempDir Path dir) throws Exception {
        Path ids = dir.resolve("pci.ids");
        Files.writeString(ids, "8086  Intel Corporation\n\t1237  440FX\nC 03  Display controller\n\t00  VGA compatible controller\n");
        int[] row = {0x00, 0x02, 0x0, 0x8086, 0x1237, 0x030000, 0x0000};
        assertEquals("  0000:00:02.0  8086:1237  VGA compatible controller: Intel Corporation 440FX",
                    pciReport.format(row, new idLookup(ids)));
    }

    @Test
    void unknownTest(@TempDir Path dir) throws Exception {
        Path ids = dir.resolve("pci.ids");
        Files.writeString(ids, "8086  Intel Corporation\n");
        int[] row = {0x00, 0x03, 0x0, 0x1234, 0x5678, 0xff0000, 0x0000};
        assertEquals("  0000:00:03.0  1234:5678  ff0000: Unknown Unknown", pciReport.format(row, new idLookup(ids)));
    }
}