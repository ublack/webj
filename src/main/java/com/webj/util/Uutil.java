package com.webj.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Uutil {

    public static void displayTrayAndEXit() throws AWTException, IOException {

        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        TrayIcon trayIcon1 = new TrayIcon(ImageIO.read(new URL("https://f11.baidu.com/it/u=19624881,807413502&fm=72")));
        //Set tooltip text for the tray icon
        tray.add(trayIcon1);
        trayIcon1.displayMessage("Hello", "complete\n", TrayIcon.MessageType.INFO);
        System.exit(0);
    }

    public static boolean isNetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.baidu.com");//ping this IP
            if (address.isReachable(3000)) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }



}
