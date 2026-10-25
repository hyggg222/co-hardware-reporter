public class systemReport {
    // Method to print combined hardware report.
    public static void printSystemReport() {

        //Heading of the report to report the system information
        System.out.println("==================== SYSTEM HARDWARE REPORT ====================");
        System.out.println();

        //1. CPU Section
        System.out.println("---------- CPU ----------");
        try {
            cpuReport.printReport();
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
        try {
            String[] output = pciReport.summary();
            String[] alphabetoutput = pciReport.summaryalphabet();
            String[] bussortoutput = pciReport.summarybussort();
            String[] vendorcount = PciMethods.vendorCount();
            for (int i=0; i<output.length; i++) {
                System.out.println(output[i]);
            }
            for (int i=0; i<alphabetoutput.length; i++) {
                System.out.println(alphabetoutput[i]);
            }
            for (int i=0; i<bussortoutput.length; i++) {
                System.out.println(bussortoutput[i]);
            }
            System.out.println("Amount of PCI devices per vendor :");
            for (int i=0; i<vendorcount.length; i++) {
                System.out.println("  " + vendorcount[i]);
            }
        } catch (Exception e) {
            System.out.println("PCI Devices: 0 (Unable to read PCI devices)");
        }
        System.out.println();

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
    // Main method to execute the system report.
     public static void main(String[] args) {
        printSystemReport();
     }
}