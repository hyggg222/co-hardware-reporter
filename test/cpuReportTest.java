import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class cpuReportTest
{
    String model = "AMD Ryzen 5 7520U with Radeon Graphics";
    // readable values
    @Test
    void readable() throws Exception
    {

        //Load for core 0: 68.25%, Average load per core: 68.25%
        assertArrayEquals(new String[]{"There is 1 core", "The processor model is "+model, "Load for core 0: 68.25%", "Average load per core: 68.25%"},
                     cpuReport.summary(1, model, new long[]{14850}, new long[]{50000}, new long[]{15065}, new long[]{50100}));

        //Load for core 0: 68.25%, Load for core 1: 50.00%, Average load per core: 59.13%
        assertArrayEquals(new String[]{"There are 2 cores", "The processor model is "+model, "Load for core 0: 68.25%", "Load for core 1: 50.00%", "Average load per core: 59.13%"},
                     cpuReport.summary(2, model, new long[]{14850, 14900}, new long[]{50000, 60000}, new long[]{15065, 15000}, new long[]{50100, 60100}));
    }

    // a value of -1 prints the message instead of numbers
    @Test
    void unreadable() throws Exception
    {
        //-1
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(1, model, new long[]{-1}, new long[]{-1}, new long[]{-1}, new long[]{-1}));

        //different lengths
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(3, model, new long[]{1, 2, 3}, new long[]{1, 2}, new long[]{1, 2, 3}, new long[]{1, 2, 3, 4}));
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(4, model, new long[]{1, 2, 3, 4}, new long[]{1, 2, 3}, new long[]{1, 2, 3}, new long[]{1, 2, 3}));

        //empty input
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(3, model, new long[]{}, new long[]{}, new long[]{1, 2, 3}, new long[]{1, 2, 3}));

        //incorrect num cores
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(0, model, new long[]{}, new long[]{}, new long[]{1, 2, 3}, new long[]{1, 2, 3}));
        assertArrayEquals(new String[]{"CPU: cannot read /proc/stat"}, cpuReport.summary(-1, model, new long[]{}, new long[]{}, new long[]{1, 2, 3}, new long[]{1, 2, 3}));
    }
}