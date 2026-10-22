public class usbReport {
    public static void printReport() {
        try {
            int count = usbInfo.getCount();
            System.out.println("USB devices: " + count);
            // Handling systems without usb devices
            if (count == 0) {
                System.out.println("No USB devices found on this system.");
                return;
            }
            idLookup usb = idLookup.usb();
            for (int i = 0; i < count; i++) {
                int vendor = usbInfo.getVendorId(i);
                int product = usbInfo.getProductId(i);

                System.out.printf("  Bus %03d Device %03d: ID %04x:%04x  %s %s%n", usbInfo.getBusNumber(i),
                                  usbInfo.getDeviceNumber(i), vendor, product,
                                  usb.vendorName(vendor), usb.deviceName(vendor, product)
                );
            }
        } catch (Exception e) {
            System.err.println("USB devices : 0, (Unable to read USB devices)");
        }
    }
    //Method for mock testing 
    public static String formatUsbDevice(int bus, int device, int vendor, int product, String vendorName, String deviceName) {
        return String.format("  Bus %03d Device %03d: ID %04x:%04x  %s %s%n", bus, device, vendor, product, vendorName, deviceName);
    }
}