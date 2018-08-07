package com.xtoolapp.file.filedemo.wifi;

import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by WangYu on 2018/6/6.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder> {
    private String[] statusArr = {"CONNECTED", "INVITED", "FAILED", "AVAILABLE", "UNAVAILABLE"};
    private List<WifiP2pDevice> mDataList=new ArrayList<>();

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    private ItemClickListener listener;

    public void setDataList(Collection<WifiP2pDevice> data) {
        if (mDataList != null) {
            mDataList.clear();
            mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tvDeviceInfo = new TextView(parent.getContext());
        return new MyViewHolder(tvDeviceInfo);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView tv = (TextView) holder.itemView;
        final WifiP2pDevice wifiP2pDevice = mDataList.get(position);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("可连接设备\nname:")
                .append(wifiP2pDevice.deviceName)
                .append("\naddress:")
                .append(wifiP2pDevice.deviceAddress)
                .append("\nstatus:")
                .append(statusArr[wifiP2pDevice.status]);
        tv.setText(stringBuilder.toString());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(wifiP2pDevice);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
interface ItemClickListener{
    void onClick(WifiP2pDevice device);
}
