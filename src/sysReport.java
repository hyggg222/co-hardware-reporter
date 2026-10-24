public class sysReport {
    public static void main(String[] args) {

        //Heading of the report to report the system information
        System.out.println("==================== SYSTEM HARDWARE REPORT ====================");
        System.out.println();

        //1. CPU Section
        System.out.println("---------- CPU ----------");
        try {
            System.out.println(cpuReport.summary());
        } catch (Exception e) {
            System.out.println("CPU: Unable to read CPU information");
        }
        System.out.println();
        



        //2. Memory Section
        System.out.println("---------- Memory ----------");
        try {
            System.out.println(memReport.summary(memInfo.getTotal(), memInfo.getAvailable()));
        } catch (Exception e) {
            System.out.println("Memory: Unable to read memory information");
        }
        System.out.println();

        //3. PCI devices Section
        System.out.println("---------- PCI Devices ----------");
        


        //4. USB devices Section
        System.out.println("---------- USB Devices ----------");
        try {
            usbReport.printReport();
        } catch (Exception e) {
            System.out.println("USB Devices: 0 (Unable to read USB devices)");
        }
        System.out.println();
        System.out.println("===============================================================");

    }
}