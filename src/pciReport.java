public class pciReport {
    public static void main (String[] args) {
        int[][] devices = getDevices();

        System.out.printf("%d devices\n", pciInfo.getCount());
        for (int i=0; i<devices.length; i++) {
            for (int j=0; j<devices[i].length; j++) {
                System.out.printf("%d, ", devices[i][j]);
            }
            System.out.println();
        }
    }

    /*List every PCI device with its bus, device and function numbers, vendor ID, device ID and class. 
    Store them in an array or list.*/
    public static int[][] getDevices() {
        int numDevices = pciInfo.getCount();

        int[][] deviceInfo = new int[numDevices][6];

        for (int i=0; i<numDevices; i++) {
            deviceInfo[i][0] = pciInfo.getBus(i); //bus num
            deviceInfo[i][1] = pciInfo.getDevice(i); //device num
            deviceInfo[i][2] = pciInfo.getFunction(i); //function num
            deviceInfo[i][3] = pciInfo.getVendorId(i); //vendor ID
            deviceInfo[i][4] = pciInfo.getDeviceId(i); //device ID
            deviceInfo[i][5] = pciInfo.getClassCode(i); //class
        }

        return deviceInfo;
    }
}