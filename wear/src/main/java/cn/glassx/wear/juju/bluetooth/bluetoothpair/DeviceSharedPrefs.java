package cn.glassx.wear.juju.bluetooth.bluetoothpair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fanz on 4/29/15.
 */
public class DeviceSharedPrefs {

    private SharedPreferences sp ;
    private SharedPreferences.Editor editor;
    private Context context;
    public static final String SHAREDPREFERENCE_NAME = "device";

    public DeviceSharedPrefs(Context context){
        this.context = context;
        sp = context.getSharedPreferences(DeviceSharedPrefs.SHAREDPREFERENCE_NAME,Context.MODE_WORLD_READABLE);
        editor = sp.edit();
    }

    public void putDevice(BluetoothDevice device){
        editor.putString("deviceName",device.getName());
        editor.putString("deviceAddress",device.getAddress());
        editor.commit();
    }

    public BluetoothDevice getDevice(){
        if(sp.getString("deviceAddress","").equals("")){
            return  null;
        }else{
            BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(sp.getString("deviceAddress",""));
            return device;
        }
    }
}
