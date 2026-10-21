import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class cpuReport {
    public static double getAvgLoad() throws Exception {
        int cores = cpuInfo.getCount();
        double[] loads = getLoads();
        double sumLoads = 0;

        for (int i=0; i<cores; i++) {
            sumLoads += loads[i];
        }

        double avgLoad = sumLoads / cores;
        return avgLoad;
    }

    public static double[] getLoads() throws Exception { //get all the CPU loads
        int cores = cpuInfo.getCount();

        long[] busy0 = new long[cores];
        long[] idle0 = new long[cores];

        long[] busy1 = new long[cores];
        long[] idle1 = new long[cores];

        
        double[] busyDiff = new double[cores];
        double[] idleDiff = new double[cores];

        double[] totalJiffies = new double[cores];
        
        double[] loads = new double[cores];

        for (int i=0; i<cores; i++) {
            busy0[i] = cpuInfo.getBusy(i);
            idle0[i] = cpuInfo.getIdle(i);
        }
        
        Thread.sleep(1000);

        for (int i=0; i<cores; i++) {
            busy1[i] = cpuInfo.getBusy(i);
            idle1[i] = cpuInfo.getIdle(i);

            busyDiff[i] = busy1[i] - busy0[i];
            idleDiff[i] = idle1[i] - idle0[i];

            totalJiffies[i] = busyDiff[i] + idleDiff[i];

            loads[i] = (busyDiff[i] / totalJiffies[i]) * 100;
        }

        return loads;
    }
}