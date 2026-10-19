import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class idLookupTest
{
    @Test
    void readsVendorAndDeviceNames(@TempDir Path dir) throws Exception
    {
        Path ids = dir.resolve("pci.ids");
        Files.writeString(ids, "8086  Intel Corporation\n\t1237  440FX - 82441FX PMC [Natoma]\n");

        idLookup lookup = new idLookup(ids);

        assertEquals("Intel Corporation", lookup.vendorName(0x8086));
        assertEquals("440FX - 82441FX PMC [Natoma]", lookup.deviceName(0x8086, 0x1237));
        assertNull(lookup.vendorName(0x1234));
    }
}
