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

                System.out.print(formatUsbDevice(usbInfo.getBusNumber(i), usbInfo.getDeviceNumber(i),
                                  vendor, product, usb.vendorName(vendor), usb.deviceName(vendor, product)));
            }
        } catch (Exception e) {
            System.err.println("USB devices : 0, (Unable to read USB devices)");
        }
    }
    //Method for mock testing 
    public static String formatUsbDevice(int bus, int device, int vendor, int product, String vendorName, String deviceName) {
        return String.format("  Bus %03d Device %03d: ID %04x:%04x  %s %s%n", bus, device, vendor, product,
                             PciMethods.orUnknown(vendorName), PciMethods.orUnknown(deviceName));
    }

    //One USB line as a JSON string, using formatUsbDevice so missing names show Unknown, not null
    public static String jsonDevice(int bus, int device, int vendor, int product, String vendorName, String deviceName) {
        return "\"" + formatUsbDevice(bus, device, vendor, product, vendorName, deviceName).trim() + "\"";
    }

    public static void printJSON() {
        String starting = String.format("\t\"usb\": {\n\t\t"); //starting line
        String ending = String.format("\t}"); //ending line
        System.out.printf("%s", starting);

        try {
            int count = usbInfo.getCount();
            System.out.printf("\"count\": \"There are %d USB devices\",\n", count);
            
            //devices
            System.out.printf("\t\t\"devices\": [\n");
            // Handling systems without usb devices
            if (count == 0) {
                System.out.print("\t\t\t\"No USB devices found on this system.\"\n\t\t]\n");
            } else {
                idLookup usb = idLookup.usb();
                for (int i = 0; i < count; i++) {
                    int vendor = usbInfo.getVendorId(i);
                    int product = usbInfo.getProductId(i);

                    System.out.printf("\t\t\t%s%s\n", jsonDevice(usbInfo.getBusNumber(i), usbInfo.getDeviceNumber(i),
                                    vendor, product, usb.vendorName(vendor), usb.deviceName(vendor, product)),
                                    i < count-1 ? "," : ""); //no comma after the last device
                }
                System.out.print("\t\t]\n");
            }
            
            System.out.printf("%s", ending);
        } catch (Exception e) {
            // System.out.printf("%s", ending);
        }
    }
}