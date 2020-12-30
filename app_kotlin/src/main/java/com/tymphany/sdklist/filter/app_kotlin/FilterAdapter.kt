package com.tymphany.sdklist.filter.app_kotlin

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilterAdapter(private val context: Context, private val list: MutableList<BluetoothDevice?>?) : RecyclerView.Adapter<FilterAdapter.MyViewHolder>(), View.OnClickListener, OnLongClickListener {
    private var onItemClickListener: OnItemClickListener? = null
    fun removeData(position: Int) {
        list?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        val holder: MyViewHolder = MyViewHolder(view)
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tag = position
        val device = list?.get(position)
        holder.info.text = device?.name
        holder.rssi.text = device?.address
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onClick(view: View) {
        if (onItemClickListener != null) {
            onItemClickListener!!.onItemClick(view, view.tag as Int)
        }
    }

    override fun onLongClick(view: View): Boolean {
        if (onItemClickListener != null) {
            onItemClickListener!!.onItemLongClick(view, view.tag as Int)
        }
        return true
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var info: TextView = view.findViewById<View>(R.id.info) as TextView
        var rssi: TextView = view.findViewById<View>(R.id.rssi) as TextView
    }
}