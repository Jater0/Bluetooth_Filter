package com.tymphany.sdklist.filter.filter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.google.gson.Gson;
import com.tymphany.jater.sdk.filter.AntiFilter;
import com.tymphany.jater.sdk.filter.Filter;
import com.tymphany.jater.sdk.filter.GetResMacFilter;
import com.tymphany.jater.sdk.filter.bean.MacBean;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This Android Junit Test Demo running with the phone 'Samsung galaxy s8' ID = 2112001758 and ITID = TYSZ2017070013R
 * Tymphany's Phone Test
 * And others point, do not run with Virtual Devices
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    List<BluetoothDevice> bigDevices;

    @Test
    public void getMacLocalFileString() {
        MacBean origin = new MacBean();
        List<String> macs = new ArrayList<>();
        macs.add("C8:63:14");
        macs.add("4F:CE:1E");
        macs.add("54:B7:E5");
        origin.setVersion("0.0.1");
        origin.setUseful(1);
        origin.setMac_host("Jater");
        origin.setMac_address_list(macs);

        String init = new GetResMacFilter().getMacLocalFileString(context, "test.json");
        Gson gson = new Gson();
        MacBean init_macBean = gson.fromJson(init, MacBean.class);
        assertEquals(origin.toString(), init_macBean.toString());
    }

    @Test
    public void getMacListLocal() {
        List<String> origin = new ArrayList<>();
        origin.add("C8:63:14");
        origin.add("4F:CE:1E");
        origin.add("54:B7:E5");
        List<String> init = new GetResMacFilter().getMacListLocal(context, "test.json");
        assertEquals(origin.toString(), init.toString());
    }

    @Test
    public void doFilter01() {
        Filter filter = new Filter();
        List<BluetoothDevice> devices_filter = filter.doFilter(bigDevices, context, "test.json");
        List<String> origin = new ArrayList<>();
        origin.add("C8:63:14");
        origin.add("4F:CE:1E");
        origin.add("54:B7:E5");
        int j = 0;
        for (int i = 0; i < devices_filter.size(); i++) {
            if (origin.contains(devices_filter.get(i).getAddress().substring(0, 8))) {
                j++;
            }
        }
        assertEquals(j, devices_filter.size());
    }

    @Test
    public void doFilter02() {
        List<String> origin = new ArrayList<>();
        origin.add("C8:63:14");
        origin.add("4F:CE:1E");
        origin.add("54:B7:E5");
        Filter filter = new Filter();
        List<BluetoothDevice> devices_filter = filter.doFilter(bigDevices, origin);
        int j = 0;
        for (int i = 0; i < devices_filter.size(); i++) {
            if (origin.contains(devices_filter.get(i).getAddress().substring(0, 8))) {
                j++;
            }
        }
        assertEquals(j, devices_filter.size());
    }

    @Test
    public void doFilterOne() {
        String mac = "C8:63:14:28:CD:86";
        Filter filter = new Filter();
        BluetoothDevice device = filter.doFilterOne(bigDevices, mac);
        assertEquals(device.getAddress(), mac);
    }

    @Test
    public void doAntiFilter01() {
        AntiFilter antiFilter = new AntiFilter();
        List<BluetoothDevice> devices = antiFilter.doAntiFilter(bigDevices, context, "test.json");
        assertEquals(devices.size(), 0);
    }

    @Test
    public void doAntiFilter02() {
        List<String> origin = new ArrayList<>();
        origin.add("C8:63:14");
        origin.add("4F:CE:1E");
        origin.add("54:B7:E5");
        AntiFilter antiFilter = new AntiFilter();
        List<BluetoothDevice> devices_filter = antiFilter.doAntiFilter(bigDevices, origin);
        System.out.println(devices_filter);
        assertEquals(devices_filter.size(), 0);
    }

    @Test
    public void doAntiFilterOne() {
        String mac = "C8:63:14:28:CD:86";
        AntiFilter antiFilter = new AntiFilter();
        List<BluetoothDevice> device =  antiFilter.doAntiFilterOne(bigDevices, mac);
        assertEquals(device.size(), 0);
    }

    /**
     * Scan the Bluetooth Device before the test 'doFilter01' and 'doFilter02' run
     * the ble device what i connect before
     */
    @Before
    public void getDevice() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        List<BluetoothDevice> devices = new ArrayList<>();
        for(BluetoothDevice device: bluetoothAdapter.getBondedDevices()) {
            devices.add(device);
        }
        bigDevices = new ArrayList<>();
        bigDevices = devices;
    }
}