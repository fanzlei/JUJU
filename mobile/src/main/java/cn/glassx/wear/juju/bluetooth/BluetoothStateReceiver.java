package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothDevice;
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
        if(intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (device.getBondState()){
                case BluetoothDevice.BOND_BONDING:

                    break;
                case BluetoothDevice.BOND_BONDED:
                    new BluetoothServer(context).startAcceptSocket();
                    break;
                case BluetoothDevice.BOND_NONE:

                    break;
            }
        }
    }
}
