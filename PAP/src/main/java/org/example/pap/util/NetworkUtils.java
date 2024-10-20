package org.example.pap.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {

    // 判斷某個 IP 是否在 Target 網段內
    public static boolean isIpInRange(String ip, String networkAddress, String netmask) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getByName(ip);
        InetAddress networkAddressInet = InetAddress.getByName(networkAddress);
        InetAddress netmaskAddress = InetAddress.getByName(netmask);

        byte[] ipBytes = ipAddress.getAddress();
        byte[] networkBytes = networkAddressInet.getAddress();
        byte[] netmaskBytes = netmaskAddress.getAddress();

        for (int i = 0; i < ipBytes.length; i++) {
            if ((ipBytes[i] & netmaskBytes[i]) != (networkBytes[i] & netmaskBytes[i])) {
                return false; // 不在網段內
            }
        }
        return true; // 在網段內
    }
}

