package com.yourdomain.myapp;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WiFiUtils {

    public static void connectToWiFi(Context context, String ssid, String password) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = String.format("\"%s\"", ssid);
            wifiConfig.preSharedKey = String.format("\"%s\"", password);

            int netId = wifiManager.addNetwork(wifiConfig);
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();

            Toast.makeText(context, "Connecting to " + ssid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "WiFi is disabled", Toast.LENGTH_SHORT).show();
        }
    }
}
