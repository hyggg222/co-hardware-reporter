import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class cpuReportTest
{
    // readable values
    @Test
    void line() throws Exception
    {
        //Load for core 0: 68.25%, Average load per core: 68.25%
        assertArrayEquals(new String[]{"Load for core 0: 68.25%", "Average load per core: 68.25%"},
                     cpuReport.summary(new long[]{14850}, new long[]{50000}, new long[]{15065}, new long[]{50100}));

        //Load for core 0: 68.25%, Load for core 1: 50.00%, Average load per core: 59.13%
        assertArrayEquals(new String[]{"Load for core 0: 68.25%", "Load for core 1: 50.00%", "Average load per core: 59.13%"},
                     cpuReport.summary(new long[]{14850, 14900}, new long[]{50000, 60000}, new long[]{15065, 15000}, new long[]{50100, 60100}));
    }

    // a value of -1 prints the message instead of numbers
    @Test
    void unreadable() throws Exception
    {
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(new long[]{-1}, new long[]{-1}, new long[]{-1}, new long[]{-1}));
    }
}