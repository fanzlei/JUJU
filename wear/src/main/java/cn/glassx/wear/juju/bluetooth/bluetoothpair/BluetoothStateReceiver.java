package cn.glassx.wear.juju.bluetooth.bluetoothpair;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

/**
 * Created by Fanz on 4/28/15.
 */
public class BluetoothStateReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Message msg = new Message();
        if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            msg.obj = device;
            msg.what = DeviceSelectActivity.ACTION_FOUND;
            DeviceSelectActivity.handler.sendMessage(msg);
        }
        else if(intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (device.getBondState()){
                case BluetoothDevice.BOND_BONDING:
                    msg.what = DeviceSelectActivity.ACTION_BONDING;
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
                case BluetoothDevice.BOND_BONDED:
                    msg.what = DeviceSelectActivity.ACTION_BONDED;
                    msg.obj = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
                case BluetoothDevice.BOND_NONE:
                    msg.what = DeviceSelectActivity.ACTION_NONE;
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
            }
        }
    }
}
