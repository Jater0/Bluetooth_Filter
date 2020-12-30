package com.tymphany.sdklist.filter.app_kotlin

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tymphany.jater.sdk.filter.Filter
import com.tymphany.jater.sdk.filter.GetResMacFilter
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var devices: MutableList<BluetoothDevice?>? = null
    private var devicesFilter: MutableList<BluetoothDevice?>? = null
    private var filterAdapter: FilterAdapter? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var macs: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scanBase = findViewById<Button>(R.id.scan)
        val scanFilter = findViewById<Button>(R.id.scan_by_filter)
        devices = ArrayList()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        scanBase.setOnClickListener {
            initData()
            Log.d("STANDARD", devices.toString())
            initView()
        }
        scanFilter.setOnClickListener {
            initData()
            filterBle()
            initViewFilter()
        }
    }

    fun initData() {
        val bluetoothDevices = bluetoothAdapter!!.bondedDevices
        if (bluetoothDevices.size > 0) {
            for (device in bluetoothDevices) {
                if (!devices!!.contains(device)) {
                    devices!!.add(device)
                }
            }
        }
        scanBle()
    }

    fun scanBle() {
        if (bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter!!.cancelDiscovery()
        }
        bluetoothAdapter!!.startDiscovery()
    }

    fun initDataMacAddress() {
        macs = ArrayList()
        macs = GetResMacFilter().getMacListLocal(this, "mac.json")
    }

    private fun initView() {
        recyclerView = findViewById<View>(R.id.ble_list) as RecyclerView
        setListView()
    }

    private fun initViewFilter() {
        recyclerView = findViewById<View>(R.id.ble_list) as RecyclerView
        setListViewFilter()
    }

    private fun filterBle() {
        val filter = Filter()
        devicesFilter = filter.doFilter(devices, this, "mac.json")
    }

    fun setListView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        filterAdapter = FilterAdapter(this, devices)
        recyclerView!!.adapter = filterAdapter
    }

    private fun setListViewFilter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        filterAdapter = FilterAdapter(this, devicesFilter)
        recyclerView!!.adapter = filterAdapter
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == BluetoothDevice.ACTION_FOUND) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if (device!!.bondState != BluetoothDevice.BOND_BONDED) {
                    if (!devices!!.contains(device)) {
                        devices!!.add(device)
                    }
                }
            } else if (action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
                setProgressBarVisibility(false)
                title = "Filter Demo"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, filter)
        scanBle()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}