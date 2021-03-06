package cn.glassx.wear.juju.bluetooth.pair;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.view.app.JUJUerList;

/**
 * Created by Fanz on 4/28/15.
 */
public class DeviceSelectActivity extends Activity implements WearableListView.ClickListener{

    private static List<BluetoothDevice> deviceList = new ArrayList<>();
    private static WearableListView listView;
    private static Context context;
    private static TextView textView;
    public static final int ACTION_FOUND = 0;
    public static final int ACTION_BONDING = 1;
    public static final int ACTION_NONE = 2;
    public static final int ACTION_BONDED = 3;
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BluetoothDevice device;
            switch (msg.what){
                case ACTION_FOUND:
                    device = (BluetoothDevice)msg.obj;
                    if(deviceList.contains(device)){
                        break;
                    }
                    deviceList.add(device);
                    textView.setVisibility(View.GONE);
                    listView.setAdapter(new DeviceListAdapter(context, deviceList));
                    break;
                case ACTION_BONDING:
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("正在匹配...");
                    break;
                case ACTION_BONDED:
                    Toast.makeText(context,"匹配成功",Toast.LENGTH_SHORT).show();
                    device = (BluetoothDevice)msg.obj;
                    DeviceServer.setDevice(device);
                    new DeviceSharedPrefs(context).putDevice(device);
                    ((DeviceSelectActivity)context) .finish();
                    ((DeviceSelectActivity) context).unRegisteBluetoothReceiver();
                    break;
                case ACTION_NONE:
                    Toast.makeText(context,"匹配失败",Toast.LENGTH_SHORT).show();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("正在扫描蓝牙...");
                    new DeviceServer().getCorrectDevice(context);
                    break;

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_select_activity);
        textView = (TextView)findViewById(R.id.scaning_text);
        listView = (WearableListView)findViewById(R.id.wearable_list);
        listView.setClickListener(this);
        context = this;

    }


    private void unRegisteBluetoothReceiver(){
        unregisterReceiver(DeviceServer.receiver);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        BluetoothDevice device = deviceList.get(viewHolder.getPosition());
        switch (device.getBondState()){
            case BluetoothDevice.BOND_BONDING:

                break;
            case BluetoothDevice.BOND_BONDED:
                DeviceServer.setDevice(device);
                new DeviceSharedPrefs(context).putDevice(device);
                Intent intent = new Intent(context, JUJUerList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case BluetoothDevice.BOND_NONE:
                device.createBond();
                break;
        }


    }

    @Override
    public void onTopEmptyRegionClick() {

    }


}
