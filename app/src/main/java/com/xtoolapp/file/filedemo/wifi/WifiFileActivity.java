package com.xtoolapp.file.filedemo.wifi;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xtoolapp.file.filedemo.R;

import java.util.Collection;

/**
 * Created by WangYu on 2018/6/4.
 */
public class WifiFileActivity extends AppCompatActivity {

    TextView tvMine;
    TextView tvWifiStatus;
    RecyclerView recyclerView;
    private WifiP2pManager mWifiP2pManager;
    private static String TAG = "wangyu";
    private String[] statusArr = {"CONNECTED", "INVITED", "FAILED", "AVAILABLE", "UNAVAILABLE"};
    private DirectBroadcastReceiver mReceiver;
    private WifiP2pManager.Channel mChannel;
    private DeviceListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_file);
        tvMine = findViewById(R.id.tv_mine);
        tvWifiStatus = findViewById(R.id.tv_wifi_status);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DeviceListAdapter();
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(WifiP2pDevice device) {
                connect(device);
            }
        });
        recyclerView.setAdapter(mAdapter);
        mWifiP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), listener);
        mReceiver = new DirectBroadcastReceiver(mWifiP2pManager, mChannel, listener);
        registerReceiver(mReceiver, DirectBroadcastReceiver.getIntentFilter());
        searchDeviceList();
    }

    /**
     * 连接设备
     * @param device
     */
    private void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        if (config.deviceAddress != null && device != null) {
            config.deviceAddress = device.deviceAddress;
            config.wps.setup = WpsInfo.PBC;
            showToast("正在连接。。。");
            mWifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    //这里的回调不准确  也可能是国产手机的原因
//                    showToast("连接成功");
//                    Log.e(TAG, "connect onSuccess");
                }

                @Override
                public void onFailure(int reason) {
//                    showToast("连接失败 " + reason);
                }
            });
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    /**
     * 搜索设备列表
     */
    private void searchDeviceList() {
        Log.i(TAG, "onCreate: 正在搜索");
        mWifiP2pManager.discoverPeers(mChannel, discoveryListener);
    }

  WifiP2pManager.ActionListener discoveryListener = new WifiP2pManager.ActionListener() {
        @Override
        public void onSuccess() {
            Log.i(TAG, "onSuccess: 搜索完毕");
        }

        @Override
        public void onFailure(int reason) {
            Log.i(TAG, "onFailure: " + reason);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mWifiP2pManager.stopPeerDiscovery(mChannel,discoveryListener);
    }

    DirectActionListener listener = new DirectActionListener() {
        @Override
        public void wifiP2pEnabled(boolean enabled) {
            tvWifiStatus.setText(String.format("wifi状态：%s", enabled));
            Log.i(TAG, "wifiP2pEnabled: " + enabled);
        }

        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            //来到这个回调说明已经配对成功
            showToast("连接成功");
            Log.i(TAG, "onConnectionInfoAvailable: " + wifiP2pInfo.toString());
        }

        @Override
        public void onDisconnection() {
            Log.i(TAG, "onDisconnection: ");
        }

        @Override
        public void onSelfDeviceAvailable(WifiP2pDevice wifiP2pDevice) {
            //本机状态就绪
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("本机设备\nname:")
                    .append(wifiP2pDevice.deviceName)
                    .append("\naddress:")
                    .append(wifiP2pDevice.deviceAddress)
                    .append("\nstatus:")
                    .append(statusArr[wifiP2pDevice.status]);
            tvMine.setText(stringBuilder.toString());
            Log.i(TAG, "onSelfDeviceAvailable: " + wifiP2pDevice.toString());
        }

        @Override
        public void onPeersAvailable(Collection<WifiP2pDevice> wifiP2pDeviceList) {
            Log.i(TAG, "onPeersAvailable: " + wifiP2pDeviceList.size());
            mAdapter.setDataList(wifiP2pDeviceList);
        }

        @Override
        public void onChannelDisconnected() {
            Log.i(TAG, "onChannelDisconnected: ");
        }
    };
}
