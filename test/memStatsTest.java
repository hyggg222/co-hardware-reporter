import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class memStatsTest
{
    @Test
    void convertsKiBToGiB()
    {
        assertEquals(1.0, memStats.toGiB(1048576), 0.0001);
        assertEquals(1.5, memStats.toGiB(1572864), 0.0001);
    }

    @Test
    void worksOutPercentUsed()
    {
        assertEquals(25.0, memStats.percentUsed(8000000, 2000000), 0.0001);
    }

    @Test
    void returnsMinusOneWhenMemoryCannotBeRead()
    {
        assertEquals(-1, memStats.toGiB(-1), 0.0001);
        assertEquals(-1, memStats.percentUsed(-1, -1), 0.0001);
        assertEquals(-1, memStats.percentUsed(0, 0), 0.0001);
    }
}
