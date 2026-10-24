public class cpuStats {
    public static long[][] getBaseLoads() throws Exception {
        int cores = cpuInfo.getCount();

        if (cores <= 0) { //if it doesn't find any cores/gets a negative
            long[][] baseLoads = new long[][]{{-1,-1,-1,-1}};
            return baseLoads;
        }
    
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

    public static double[] getLoads(long[] busy0, long[] idle0, long[] busy1, long[] idle1) { //get all the CPU loads
        int cores = busy0.length; //there's a check in cpuReport in case the lists are different lengths
                
        double[] busyDiff = new double[cores];
        double[] idleDiff = new double[cores];

        double[] totalJiffies = new double[cores];
        
        double[] loads = new double[cores+1];
        double sumLoads = 0; //for average
 
        for (int i=0; i<cores; i++) {
            if (busy0[i] <= -1 || busy1[i] <= -1 || idle0[i] <= -1 || idle1[i] <= -1) {
                return new double[]{-1};
            }
            busyDiff[i] = busy1[i] - busy0[i];
            idleDiff[i] = idle1[i] - idle0[i];

            totalJiffies[i] = busyDiff[i] + idleDiff[i];
            
            if (busyDiff[i] > 0) { //don't check for totalJiffies > 0 if busyDiff > 0, idleDiff doesn't matter
                loads[i] = (busyDiff[i] / totalJiffies[i]) * 100;
            } else {
                loads[i] = 0;
            }
            sumLoads += loads[i]; //for average
        }

        double avg = sumLoads/cores;
        loads[cores] = avg;

        return loads;
    }
}