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

public class FilterAdapter2 extends RecyclerView.Adapter<FilterAdapter2.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    private final List<String> list;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public FilterAdapter2(Context context, List<String> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_macs, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.info.setText(list.get(position));
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
        public MyViewHolder(View view) {
            super(view);
            info = (TextView)view.findViewById(R.id.info);
        }
    }
}
