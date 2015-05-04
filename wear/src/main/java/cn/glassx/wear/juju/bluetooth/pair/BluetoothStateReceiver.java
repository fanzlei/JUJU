package cn.glassx.wear.juju.bluetooth.pair;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

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
                    Log.d("bluetooth","正在配对蓝牙");
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
                case BluetoothDevice.BOND_BONDED:
                    Log.d("bluetooth","蓝牙匹配成功");
                    msg.what = DeviceSelectActivity.ACTION_BONDED;
                    msg.obj = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
                case BluetoothDevice.BOND_NONE:
                    Log.d("bluetooth","蓝牙取消匹配");
                    msg.what = DeviceSelectActivity.ACTION_NONE;
                    DeviceSelectActivity.handler.sendMessage(msg);
                    break;
            }
        }
    }
}
