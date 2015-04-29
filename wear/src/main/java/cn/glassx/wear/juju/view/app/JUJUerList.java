package cn.glassx.wear.juju.view.app;
/**
 * Created by Fanz on 4/8/15.
 */
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.widget.Toast;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.bluetooth.bluetoothpair.BluetoothStateReceiver;
import cn.glassx.wear.juju.bluetooth.bluetoothpair.DeviceSharedPrefs;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.view.adapter.JujuListAdapter;

public class JUJUerList extends Activity implements WearableListView.ClickListener {

    private static WearableListView personList;
    private WearableListView.Adapter mAdapter;
    public static Handler handler;
    public static final int MESSAGE_WHAT_BLUETOOTH = 0x334;
    private static BluetoothDevice device;
    private boolean isExit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jujuer_list);
        personList = (WearableListView) findViewById(R.id.wearable_list);
        personList.setClickListener(JUJUerList.this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x123){
                    Log.d("JUJU", "handler msg,set the adapter of personList");
                    personList.setAdapter(new JujuListAdapter(JUJUerList.this, RuntimeData.JUJUers));
                }else if(msg.what == MESSAGE_WHAT_BLUETOOTH){
                    device = (BluetoothDevice)msg.obj;

                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        BluetoothDevice device = new DeviceSharedPrefs(this).getDevice();
        if(device.getAddress().equals("")){
            Toast.makeText(this,"请用手机连接手表蓝牙",Toast.LENGTH_SHORT).show();
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(new BluetoothStateReceiver(),filter);

    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("JUJU","点击了位置："+viewHolder.getPosition());
        isExit = false;
        Intent intent = new Intent(this,JUJUDetail.class);
        intent.putExtra(AppConfig.POSITION_IN_JUJUERS, viewHolder.getPosition());
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
