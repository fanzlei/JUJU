package cn.glassx.wear.juju.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import cn.glassx.wear.juju.bluetooth.pair.DeviceServer;

/**
 * Created by Fanz on 4/28/15.
 * 蓝牙服务类
 * 负责蓝牙的连接和与手机数据的通信
 */
public class BluetoothService  extends Service{

    private static BluetoothDevice device;
    private TransMission transMission;
    private static BluetoothAdapter bluetoothAdapter;
    private static Context context;
    private DeviceServer deviceServer;
    private MyBinder binder = new MyBinder();




    @Override
    public void onCreate() {
        super.onCreate();
        transMission = TransMission.getInstance();
        deviceServer = new DeviceServer();
        this.context = getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("juju","onBind");
        device = deviceServer.getCorrectDevice(this);
        return binder;

    }


    public static BluetoothDevice getDevice(){
       return new DeviceServer().getCorrectDevice(context);
    }


    public static void setDevice(BluetoothDevice device1){
        device = device1;
    }

    public class MyBinder extends Binder{

        public void getPersonList(TransMission.OnDataBackListener listener){
            transMission.getPersonList(listener);
        }



    }
}
