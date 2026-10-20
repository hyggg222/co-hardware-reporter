/**
 * Memory maths on the KiB values from memInfo. Every method returns -1 when the
 * input shows that memory could not be read, so the caller can print a message.
 */
public class memStats
{
    /** KiB converted to GiB (1 GiB = 1024 * 1024 KiB), or -1 if kib is negative. */
    public static double toGiB(long kib)
    {
        return kib < 0 ? -1 : kib / (1024.0 * 1024.0);
    }

    /** Share of memory in use as a percentage, or -1 if total is not above 0 or used is negative. */
    public static double percentUsed(long total, long used)
    {
        return (total <= 0 || used < 0) ? -1 : used * 100.0 / total;
    }
}
