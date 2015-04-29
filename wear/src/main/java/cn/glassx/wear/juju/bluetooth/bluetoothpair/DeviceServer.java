package cn.glassx.wear.juju.bluetooth.bluetoothpair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Fanz on 4/29/15.
 */
public class DeviceServer {

    private BluetoothAdapter bluetoothAdapter;
    public static BroadcastReceiver receiver;
    private static BluetoothDevice device;
    interface DeviceCallBack{
        void onDeviceBack(BluetoothDevice device);
    }

    public BluetoothDevice getCorrectDevice(Context context){
        //todo 连接测试已有的device不可用

        if(device == null ){
            device = new DeviceSharedPrefs(context).getDevice();
            if(device.getAddress().equals("")){
                context.startActivity(new Intent(context, DeviceSelectActivity.class));
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.enable();
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
                context.startActivity(discoverableIntent);

                IntentFilter filter = new IntentFilter();
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                context.registerReceiver(receiver = new BluetoothStateReceiver(), filter);
                bluetoothAdapter.startDiscovery();
            }

        }
        return device;
    }

    public static void setDevice(BluetoothDevice device1){
        device = device1;
    }
    public static BluetoothDevice getDevice(){
        return device;
    }
}
