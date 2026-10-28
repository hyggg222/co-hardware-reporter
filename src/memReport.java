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
        if (used < 0) 
            return "Memory: invalid values (total < used)";
        return String.format("Memory: total %.2f GiB, used %.2f GiB (%.1f%%)",
                             memStats.toGiB(total), memStats.toGiB(used),
                             memStats.percentUsed(total, used));
    }

    // Reads /proc/meminfo and prints the line.
    public static void main(String[] args)
    {
        System.out.println(summary(memInfo.getTotal(), memInfo.getAvailable()));
    }

    public static void printJSON() {
        String start = String.format(
            "\t\"memory\": {\n"
        );

        long total = memInfo.getTotal();
        long available = memInfo.getAvailable();

        if (total < 0 || available < 0){
            System.out.println(start);
            System.out.println("}");
            return;
        }

        long used = total - available;
        if (used < 0) {
            System.out.println("}");
        }
        
        System.out.printf("%s", start);
        System.out.printf("\t\t\"total\": %.2f,\n", memStats.toGiB(total));
        System.out.printf("\t\t\"used\": %.2f,\n", memStats.toGiB(used));
        System.out.printf("\t\t\"percent\": \"Using %.2f%% of total memory\"\n", memStats.percentUsed(total, used));
        System.out.printf("\t}");
    }
}
