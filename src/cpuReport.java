import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class cpuReport {
    public static String[] summary(long[] busy0, long[] idle0, long[] busy1, long[] idle1) throws Exception {
        String[] summary = new String[busy0.length+1];

        double[] loads = cpuStats.getLoads(busy0, idle0, busy1, idle1);
        double avgLoad = cpuStats.getAvgLoad(busy0, idle0, busy1, idle1);

        if (avgLoad < 0) {
            String[] error = {"CPU: cannot read /proc/stat"};
            return error;
        }
        
        for (int i=0; i<loads.length; i++) {
            summary[i] = String.format("Load for core %d: %.2f%%", i, loads[i]);
        }
        summary[summary.length-1] = String.format("Average load per core: %.2f%%", avgLoad);

        return summary;
    }

    public static void main(String[] args) throws Exception {
        int cores = cpuInfo.getCount();
        
        if (cores <= 0) {
            long[] busy0 = {-1};
            long[] idle0 = {-1};
            long[] busy1 = {-1};
            long[] idle1 = {-1};

            String[] summary = summary(busy0, idle0, busy1, idle1);
            for (int i=0; i<summary.length; i++) {
                System.out.println(summary[i]);
            }
        } else {
            long[][] baseLoads = cpuStats.getBaseLoads();

            long[] busy0 = new long[cores];
            long[] idle0 = new long[cores];
            long[] busy1 = new long[cores];
            long[] idle1 = new long[cores];

            for (int i=0; i<cores; i++) {
                busy0[i] = baseLoads[i][0];
                idle0[i] = baseLoads[i][1];
                busy1[i] = baseLoads[i][2];
                idle1[i] = baseLoads[i][3];
            }
            
            String[] summary = summary(busy0, idle0, busy1, idle1);
            for (int i=0; i<summary.length; i++) {
                System.out.println(summary[i]);
            }
        }        
    }
}