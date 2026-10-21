// Prints total and used memory in GiB and the percentage in use.
// Run with: make run MAIN=memReport
public class memReport
{
    // Builds the line to print from total and available memory in KiB.
    public static String summary(long total, long available)
    {
        if (total < 0 || available < 0)
            return "Memory: cannot read /proc/meminfo";
        long used = total - available;
        return String.format("Memory: total %.2f GiB, used %.2f GiB (%.1f%%)",
                             memStats.toGiB(total), memStats.toGiB(used),
                             memStats.percentUsed(total, used));
    }

    // Reads /proc/meminfo and prints the line.
    public static void main(String[] args)
    {
        System.out.println(summary(memInfo.getTotal(), memInfo.getAvailable()));
    }
}
