package com.tymphany.sdklist.filter.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.tymphany.jater.sdk.filter.GetResMacFilter;
import com.tymphany.jater.sdk.filter.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<BluetoothDevice> devices;
    private List<BluetoothDevice> devices_filter;
    private FilterAdapter filterAdapter;
    private FilterAdapter2 filterAdapter2;
    private BluetoothAdapter bluetoothAdapter;
    private List<String> macs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scan_base = findViewById(R.id.scan);
        Button scan_filter = findViewById(R.id.scan_by_filter);
        Button get_macs = findViewById(R.id.macs_list);
        devices = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        scan_base.setOnClickListener(view -> {
            initData();
            Log.d("STANDARD", devices.toString());
            initView();
        });

        scan_filter.setOnClickListener(view -> {
            initData();
            filterBle();
            initViewFilter();
        });

        get_macs.setOnClickListener(view -> {
            initDataMacAddress();
            initViewMacAddress();
        });
    }

    public void initData() {
        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
        if (bluetoothDevices.size() > 0) {
            for (BluetoothDevice device: bluetoothDevices) {
                if (!devices.contains(device)) {
                    devices.add(device);
                }
            }
        }
        scanBle();
    }

    public void scanBle() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    public void initDataMacAddress() {
        macs = new ArrayList<>();
        macs = new GetResMacFilter().getMacListLocal(this, "mac.json");
    }

    private void initView() {
        recyclerView = (RecyclerView)this.findViewById(R.id.ble_list);
        setListView();
    }

    private void initViewFilter() {
        recyclerView = (RecyclerView)this.findViewById(R.id.ble_list);
        setListViewFilter();
    }

    private void initViewMacAddress() {
        recyclerView = (RecyclerView)this.findViewById(R.id.ble_list);
        setListViewMacAddress();
    }

    private void filterBle() {
        Filter filter = new Filter();
        devices_filter = new ArrayList<>();
        devices_filter = filter.doFilter(devices, this, "mac.json");
    }

    public void setListView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterAdapter = new FilterAdapter(this, devices);
        recyclerView.setAdapter(filterAdapter);
    }

    public void setListViewFilter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterAdapter = new FilterAdapter(this, devices_filter);
        recyclerView.setAdapter(filterAdapter);
    }

    public void setListViewMacAddress() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        filterAdapter2 = new FilterAdapter2(this, macs);
        recyclerView.setAdapter(filterAdapter2);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!devices.contains(device)) {
                        devices.add(device);
                    }
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                setProgressBarVisibility(false);
                setTitle("Filter Demo");
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        scanBle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
