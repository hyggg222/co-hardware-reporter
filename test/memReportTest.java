import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class memReportTest
{
    // 8 GiB total, 6 GiB available: 2 GiB used, 25%
    @Test
    void line()
    {
        assertEquals("Memory: total 8.00 GiB, used 2.00 GiB (25.0%)",
                     memReport.summary(8 * 1048576L, 6 * 1048576L));
    }

    // a value of -1 prints the message instead of numbers
    @Test
    void unreadable()
    {
        assertEquals("Memory: cannot read /proc/meminfo", memReport.summary(-1, -1));
        assertEquals("Memory: cannot read /proc/meminfo", memReport.summary(8388608, -1));
    }
}
