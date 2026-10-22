import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class cpuStatsTest
{
    // calculates the loads accurately
    @Test
    void loads() throws Exception
    {
        //(215/315) * 100 ≈ 68.25
        assertArrayEquals(new double[]{68.253968253968253968253968253968}, cpuStats.getLoads(new long[]{14850}, new long[]{50000}, new long[]{15065}, new long[]{50100}));
        
        //(215/315) * 100 ≈ 68.25, (100/200) * 100 = 50 [multiple cores]
        assertArrayEquals(new double[]{68.253968253968253968253968253968, 50}, cpuStats.getLoads(new long[]{14850, 14900}, new long[]{50000, 60000}, new long[]{15065, 15000}, new long[]{50100, 60100}));
    }

    // calculates the averages accurately
    @Test
    void average() throws Exception
    {
        //(215/315) * 100 ≈ 68.25
        assertEquals(68.253968253968253968253968253968, cpuStats.getAvgLoad(new long[]{14850, 14850}, new long[]{50000, 50000}, new long[]{15065, 15065}, new long[]{50100, 50100}));
        
        //(215/315) * 100 ≈ 68.25, (100/200) * 100 = 50 --> 59.126984126984126984126984126984 [multiple cores]
        assertEquals(59.126984126984126984126984126984, cpuStats.getAvgLoad(new long[]{14850, 14900}, new long[]{50000, 60000}, new long[]{15065, 15000}, new long[]{50100, 60100}));
    }

    // bad input gives -1   
    @Test
    void unreadable() throws Exception
    {
        //fails with one core, invalid
        assertArrayEquals(new double[]{-1}, cpuStats.getLoads(new long[]{-1}, new long[]{-1}, new long[]{-1}, new long[]{-1}));
        assertEquals(-1, cpuStats.getAvgLoad(new long[]{-1}, new long[]{-1}, new long[]{-1}, new long[]{-1}));

        //fails with eight cores, invalid
        assertArrayEquals(new double[]{-1}, cpuStats.getLoads(new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}));
        assertEquals(-1, cpuStats.getAvgLoad(new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}, new long[]{-1, -1, -1, -1, -1, -1, -1, -1}));

        //fails with two cores, first invalid
        assertArrayEquals(new double[]{-1}, cpuStats.getLoads(new long[]{-1, 14850}, new long[]{-1, 50000}, new long[]{-1, 15065}, new long[]{-1, 50100}));
        assertEquals(-1, cpuStats.getAvgLoad(new long[]{-1, 14850}, new long[]{-1, 50000}, new long[]{-1, 15065}, new long[]{-1, 50100}));

        //fails with two cores, second invalid
        assertArrayEquals(new double[]{-1}, cpuStats.getLoads(new long[]{14850, -1}, new long[]{50000, -1}, new long[]{15065, -1}, new long[]{50100, -1}));
        assertEquals(-1, cpuStats.getAvgLoad(new long[]{14850, -1}, new long[]{50000, -1}, new long[]{15065, -1}, new long[]{50100, -1}));
    }
}
