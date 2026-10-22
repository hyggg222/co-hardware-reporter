public class cpuStats {
    public static long[][] getBaseLoads() throws Exception {
        int cores = cpuInfo.getCount();
    
        long[][] baseLoads = new long[cores][4];
        /*for each core:
        list 0: busy 0
        list 1: idle 0
        list 2: busy 1
        list 3: idle 1 */

         for (int i=0; i<cores; i++) {
            baseLoads[i][0] = cpuInfo.getBusy(i);
            baseLoads[i][1] = cpuInfo.getIdle(i);
        }
        
        Thread.sleep(1000);

        for (int i=0; i<cores; i++) {
            baseLoads[i][2] = cpuInfo.getBusy(i);
            baseLoads[i][3] = cpuInfo.getIdle(i);
        }

        return baseLoads;
    }
    
    public static double getAvgLoad(long[] busy0, long[] idle0, long[] busy1, long[] idle1) throws Exception {
        double[] loads = getLoads(busy0, idle0, busy1, idle1);
        int cores = loads.length; //to avoid breaking if invalid values passed
        double sumLoads = 0;

        for (int i=0; i<cores; i++) {
            if (loads[i] <= -1) {
                return -1;
            }
            sumLoads += loads[i];
        }

        double avgLoad = sumLoads / cores;
        return avgLoad;
    }

    public static double[] getLoads(long[] busy0, long[] idle0, long[] busy1, long[] idle1) { //get all the CPU loads
        int cores = busy0.length;
        
        double[] busyDiff = new double[cores];
        double[] idleDiff = new double[cores];

        double[] totalJiffies = new double[cores];
        
        double[] loads = new double[cores];
 
        for (int i=0; i<cores; i++) {
            if (busy0[i] <= -1 || busy1[i] <= -1 || idle0[i] <= -1 || idle1[i] <= -1) {
                return new double[]{-1};
            }
            busyDiff[i] = busy1[i] - busy0[i];
            idleDiff[i] = idle1[i] - idle0[i];

            totalJiffies[i] = busyDiff[i] + idleDiff[i];

            loads[i] = (busyDiff[i] / totalJiffies[i]) * 100;
        }

        return loads;
    }
}