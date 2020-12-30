package com.tymphany.sdklist.filter.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    private final List<BluetoothDevice> list;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public FilterAdapter(Context context, List<BluetoothDevice> list) {
        this.context = context;
        this.list = list;
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        BluetoothDevice device = list.get(position);
        holder.info.setText(device.getName());
        holder.rssi.setText(device.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (this.onItemClickListener !=  null) {
            this.onItemClickListener.onItemLongClick(view, (int)view.getTag());
        }
        return true;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView info;
        TextView rssi;
        public MyViewHolder(View view) {
            super(view);
            info = (TextView)view.findViewById(R.id.info);
            rssi = (TextView)view.findViewById(R.id.rssi);
        }
    }
}