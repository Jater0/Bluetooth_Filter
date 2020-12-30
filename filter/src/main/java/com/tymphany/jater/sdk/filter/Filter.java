package com.tymphany.jater.sdk.filter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Maybe we will use rxjava/rxAndroid rewrite this sdk; why: make it quickly respond and smart
 */
public class Filter {

    /**
     * Use Local file to do filter
     * @param devices
     * @param context
     * @param fileName
     * @return
     */
    public List<BluetoothDevice> doFilter(List<BluetoothDevice> devices, Context context, String fileName) {
        GetResMacFilter getResMacFilter = new GetResMacFilter();
        List<String> macs;
        macs = getResMacFilter.getMacListLocal(context, fileName);
        List<BluetoothDevice> devices_filter = new ArrayList<>();
        for (int i=0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (macs.contains(device_mac.substring(0, 8))) {
                devices_filter.add(device);
            }
        }
        return devices_filter;
    }

    /**
     * implement a filter by developer's code
     * if you want to use this function, u should implement 'List<String> macs' to contains mac address
     * @author Jater
     * @version 0.01-alpha
     * @param devices where devices contains;
     * @param macs what mac address we need;
     * @return
     */
    public List<BluetoothDevice> doFilter(List<BluetoothDevice> devices, List<String> macs) {
        List<BluetoothDevice> devices_filter = new ArrayList<>();
        for (int i = 0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (macs.contains(device_mac.substring(0, 8))) {
                devices_filter.add(device);
            }
        }
        return devices_filter;
    }

    public BluetoothDevice doFilterOne(List<BluetoothDevice> devices, String target_mac) {
        BluetoothDevice device_filter = null;
        for (int i = 0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (device_mac.equals(target_mac)) {
                device_filter = device;
                break;
            }
        }
        return device_filter;
    }
}