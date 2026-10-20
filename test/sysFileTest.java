import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class sysFileTest
{
    @Test
    void readsHexAndDecimalNumbers(@TempDir Path dir) throws Exception
    {
        Path hex = dir.resolve("vendor");
        Files.writeString(hex, "0x8086\n");
        Path decimal = dir.resolve("count");
        Files.writeString(decimal, "42\n");

        assertEquals(32902, sysFile.number(hex));
        assertEquals(42, sysFile.number(decimal));
    }

    @Test
    void returnsMinusOneWhenFileIsMissingOrNotANumber(@TempDir Path dir) throws Exception
    {
        Path words = dir.resolve("words");
        Files.writeString(words, "not a number\n");

        assertEquals(-1, sysFile.number(dir.resolve("missing")));
        assertEquals(-1, sysFile.number(words));
    }
}
