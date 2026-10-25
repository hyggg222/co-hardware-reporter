public class pciStats {
    /*List every PCI device with its bus, device and function numbers, vendor ID, device ID and class. 
    Store them in an array or list.*/
    public static int[][] getDevices() {
        int numDevices = pciInfo.getCount();

        int[][] deviceInfo = new int[numDevices][7];

        for (int i=0; i<numDevices; i++) {
            deviceInfo[i][0] = pciInfo.getBus(i); //bus num
            deviceInfo[i][1] = pciInfo.getDevice(i); //device num
            deviceInfo[i][2] = pciInfo.getFunction(i); //function num
            deviceInfo[i][3] = pciInfo.getVendorId(i); //vendor ID
            deviceInfo[i][4] = pciInfo.getDeviceId(i); //device ID
            deviceInfo[i][5] = pciInfo.getClassCode(i); //class
            //domain
            try {
                deviceInfo[i][6] = Integer.parseInt(pciInfo.getAddress(i).substring(0,4), 16);
                //converts the hexadecimal in the string to an int
            } catch (NumberFormatException e) {
                deviceInfo[i][6] = 0;
            }
        }

        return deviceInfo;
    }
}