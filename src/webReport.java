public class webReport {
    public static void main(String[] args) throws Exception {
        System.out.println("{");

        //cpu
        cpuReport.printJSON();
        System.out.println(",");

        //pci
        pciReport.printJSON();
        System.out.println(",");

        //usb
        usbReport.printJSON();
        // System.out.println(",");

        // //memory
        // memReport.printJSON();
        System.out.println();

        System.out.println("}");
    }

    public static void listJSON(String[] list) {
        for (int i=0; i<list.length-1; i++) { //items
            System.out.printf("\t\t\t\"%s\",\n",list[i]);
        }
        System.out.printf("\t\t\t\"%s\"\n\t\t]", list[list.length-1]); //last item
    }
}