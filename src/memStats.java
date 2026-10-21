// Memory maths on the KiB values from memInfo. -1 means "could not read".
public class memStats
{
    // Converts KiB to GiB. Returns -1 if kib is negative.
    public static double toGiB(long kib)
    {
        return kib < 0 ? -1 : kib / (1024.0 * 1024.0);
    }

    // Used memory as a percentage of total. Returns -1 if total is 0 or less, or used is negative.
    public static double percentUsed(long total, long used)
    {
        return (total <= 0 || used < 0) ? -1 : used * 100.0 / total;
    }
}
