import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class cpuReport {
    public static void main(String[] args) throws Exception {        
            printReport();
    }

    public static String[] summary(int cores, String model, long[] busy0, long[] idle0, long[] busy1, long[] idle1) throws Exception {
        String[] error = {"CPU: cannot read /proc/stat"};

        if ((cores > 0) && (busy0 != null && idle0 != null && busy1 != null && idle1 != null) && (cores == busy0.length && cores == idle0.length && cores == busy1.length && cores == idle1.length)) {
           String[] summary = new String[cores+3]; //a line for each core + num cores + model + avg load

           //base info
            if (cores > 1) {
                summary[0] = String.format("There are %d cores", cores);
            } else {
                summary[0] = "There is 1 core";
            }
            summary[1] = String.format("The processor model is %s", model);

            double[] loads = cpuStats.getLoads(busy0, idle0, busy1, idle1);

            if (loads.length == 1 && loads[0] < 0) {
                return error;
            }

            
            double avgLoad = loads[loads.length-1];
            
            for (int i=0; i<loads.length; i++) {
                summary[i+2] = String.format("Load for core %d: %.2f%%", i, loads[i]);
            }
            summary[summary.length-1] = String.format("Average load per core: %.2f%%", avgLoad);

            return summary;
        } else {
            return error;
        }
    }

    public static void printReport() throws Exception {
        //loads
        long[][] baseLoads = cpuStats.getBaseLoads();
        int cores = baseLoads.length;

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
        
        //baseinfo
        cores = cpuInfo.getCount(); //will be error checked in summary
        String model = cpuInfo.getModel();
        
        String[] summary = summary(cores, model, busy0, idle0, busy1, idle1);

        //actual printing
        for (int i=0; i<summary.length; i++) {
            System.out.println(summary[i]);
        }
    }
}