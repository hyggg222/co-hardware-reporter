import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class cpuReport {
    public static void main(String[] args) throws Exception {        
            printReport();
    }

    public static String[] summary(long[] busy0, long[] idle0, long[] busy1, long[] idle1) throws Exception {
        String[] error = {"CPU: cannot read /proc/stat"};

        if ((busy0 != null && idle0 != null && busy1 != null && idle1 != null) && (busy0.length == idle0.length && busy0.length == busy1.length && busy0.length == idle1.length)) {
           String[] summary = new String[busy0.length+1];

            double[] loads = cpuStats.getLoads(busy0, idle0, busy1, idle1);

            if (loads.length == 1 && loads[0] < 0) {
                return error;
            }

            
            double avgLoad = loads[loads.length-1];
            
            for (int i=0; i<loads.length; i++) {
                summary[i] = String.format("Load for core %d: %.2f%%", i, loads[i]);
            }
            summary[summary.length-1] = String.format("Average load per core: %.2f%%", avgLoad);

            return summary;
        } else {
            return error;
        }
    }

    public static void printReport() throws Exception {
        //base info
        int cores;
        String model = cpuInfo.getModel();

        //loads
        long[][] baseLoads = cpuStats.getBaseLoads();
        cores = baseLoads.length;

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

        //actual printing
        System.out.printf("There are %d cores.\n", cores);
        System.out.printf("The processor model is %s.\n", model);
        for (int i=0; i<summary.length; i++) {
            System.out.println(summary[i]);
        }
    }
}