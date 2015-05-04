package cn.glassx.wear.juju.bluetooth.pair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import cn.glassx.wear.juju.AppConfig;

/**
 * Created by Fanz on 4/29/15.
 */
public class DeviceServer {

    private BluetoothAdapter bluetoothAdapter;
    public static BroadcastReceiver receiver;
    private static BluetoothDevice device;


    public BluetoothDevice getCorrectDevice(Context context){

        if(device == null ){
            device = new DeviceSharedPrefs(context).getDevice();
            if(device == null){
                Intent intent = new Intent(context, DeviceSelectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                bluetoothAdapter.enable();
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                discoverableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(discoverableIntent);

                IntentFilter filter = new IntentFilter();
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
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

    public  void setNewDevice(){
        device = null;
        new DeviceSharedPrefs(AppConfig.applicationContext).resetDevice();
        getCorrectDevice(AppConfig.applicationContext);
    }
}
