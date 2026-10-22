import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class memStatsTest
{
    // 1 GiB is 1048576 KiB
    @Test
    void gib()
    {
        assertEquals(1.0, memStats.toGiB(1048576), 0.0001);
        assertEquals(1.5, memStats.toGiB(1572864), 0.0001);
    }

    // 2 of 8 is 25%
    @Test
    void percent()
    {
        assertEquals(25.0, memStats.percentUsed(8000000, 2000000), 0.0001);
        assertEquals(100.0, memStats.percentUsed(0, 0), 0.0001);
    }

    // bad input gives -1   
    @Test
    void unreadable()
    {
        assertEquals(-1, memStats.toGiB(-1), 0.0001);
        assertEquals(-1, memStats.percentUsed(-1, -1), 0.0001);
    }
}
