// Memory maths on the KiB values from memInfo. -1 means "could not read".
public class memStats
{
    // Converts KiB to GiB. Returns -1 if kib is negative.
    public static double toGiB(long kib)
    {
        return kib < 0 ? -1 : kib / (1024.0 * 1024.0);
    }

    // Used memory as a percentage of total. Returns -1 if total or used is negative and if total is smaller than used.
    public static double percentUsed(long total, long used)
    {
        if (total == 0 && used == 0) return 100.0;
        return (total <= 0 || used < 0 || total < used) ? -1 : used * 100.0 / total;
    }
}
