public class mainReport
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("cores:  " + cpuInfo.getCount());
        System.out.println("model:  " + cpuInfo.getModel());
        System.out.println("core 0: busy=" + cpuInfo.getBusy(0) + " idle=" + cpuInfo.getIdle(0));

        //System.out.println("memory: total=" + memInfo.getTotal() + " KiB, used=" + memInfo.getUsed() + " KiB");
        // Adding memory report
        System.out.println("================MEMORY================");
        // System.out.println(memReport.summary(memInfo.getTotal(), memInfo.getUsed()));
        System.out.printf("Total memory: %.2f GB\n", memStats.toGiB(memInfo.getTotal()));
        System.out.printf("Used memory (%.1f%%): %.2f GB\n", memStats.percentUsed(memInfo.getTotal(), memInfo.getUsed())
                                                                                , memStats.toGiB(memInfo.getUsed()));
        System.out.printf("Available memory: %.2f GB\n", memStats.toGiB(memInfo.getAvailable()));
        System.out.println("======================================");

        idLookup pci = idLookup.pci();
        System.out.println("PCI devices: " + pciInfo.getCount());
        for (int i = 0; i < pciInfo.getCount(); i++) {
            int vendor = pciInfo.getVendorId(i);
            int device = pciInfo.getDeviceId(i);
            System.out.printf("  %s  %04x:%04x  %s %s%n", pciInfo.getAddress(i), vendor, device,
                              pci.vendorName(vendor), pci.deviceName(vendor, device));
        }

        idLookup usb = idLookup.usb();
        System.out.println("USB devices: " + usbInfo.getCount());
        for (int i = 0; i < usbInfo.getCount(); i++) {
            int vendor = usbInfo.getVendorId(i);
            int product = usbInfo.getProductId(i);
            System.out.printf("  Bus %03d Device %03d: ID %04x:%04x  %s %s%n", usbInfo.getBusNumber(i),
                              usbInfo.getDeviceNumber(i), vendor, product,
                              usb.vendorName(vendor), usb.deviceName(vendor, product));
        }
    }
}
