package com.tymphany.jater.sdk.filter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AntiFilter {
    public List<BluetoothDevice> doAntiFilter(List<BluetoothDevice> devices, Context context, String fileName) {
        GetResMacFilter getResMacFilter = new GetResMacFilter();
        List<String> macs;
        macs = getResMacFilter.getMacListLocal(context, fileName);
        List<BluetoothDevice> devices_filter = new ArrayList<>();
        for (int i=0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (!macs.contains(device_mac.substring(0, 8))) {
                devices_filter.add(device);
            }
        }
        return devices_filter;
    }

    public List<BluetoothDevice> doAntiFilter(List<BluetoothDevice> devices, List<String> macs) {
        List<BluetoothDevice> devices_filter = new ArrayList<>();
        for (int i = 0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (!macs.contains(device_mac.substring(0, 8))) {
                devices_filter.add(device);
            }
        }
        return devices_filter;
    }

    public List<BluetoothDevice> doAntiFilterOne(List<BluetoothDevice> devices, String target_mac) {
        for (int i = 0; i < devices.size(); i++) {
            BluetoothDevice device = devices.get(i);
            String device_mac = device.getAddress();
            if (device_mac.equals(target_mac)) {
                devices.remove(i);
                break;
            }
        }
        return devices;
    }
}
