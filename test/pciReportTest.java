import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.IOException;


public class pciReportTest {
@Test
void summaryTest() throws Exception {
    int[][] devicestestlist = pciStats.getDevices();

    idLookup pci = idLookup.pci();
    int numPCI = pciInfo.getCount();

    String[] output = new String[numPCI+1];
    output[0] = String.format("%d PCI Devices", numPCI);

    for (int i = 0; i < numPCI; i++) {
        int vendor = devicestestlist[i][3];
        int device = devicestestlist[i][4];
        output[i+1] = String.format("  %s  %04x:%04x  %s %s", pciInfo.getAddress(i), vendor, device,
                pci.vendorName(vendor), pci.deviceName(vendor, device));
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
        String address = String.format(
                "%04x:%02x:%02x.%x",
                devicestestlist[i][6],
                devicestestlist[i][0],
                devicestestlist[i][1],
                devicestestlist[i][2]
        );
        int vendor = devicestestlist[i][3];
        int device = devicestestlist[i][4];
        outputalphabet[i+1] = String.format("  %s  %04x:%04x  %s %s", address, vendor, device,
                pci.vendorName(vendor), pci.deviceName(vendor, device));
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
        String address = String.format(
                "%04x:%02x:%02x.%x",
                devicestestlist[i][6],
                devicestestlist[i][0],
                devicestestlist[i][1],
                devicestestlist[i][2]
        );
        int vendor = devicestestlist[i][3];
        int device = devicestestlist[i][4];
        outputbus[i+1] = String.format("  %s  %04x:%04x  %s %s", address, vendor, device,
                pci.vendorName(vendor), pci.deviceName(vendor, device));
    }

    assertArrayEquals(outputbus,pciReport.summarybussort());

}
}