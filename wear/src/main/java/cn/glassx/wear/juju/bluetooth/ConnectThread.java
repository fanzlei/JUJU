package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.protobuf.Proto;

/**
 * Created by Fanz on 4/28/15.
 */
public class ConnectThread extends Thread{

    private BluetoothSocket socket;
    private byte[] bytes;
    private Proto.Envelope envelope;
    private TransMission.OnDataBackListener listener;
    private BluetoothServerSocket serverSocket;
    private BluetoothAdapter bluetoothAdapter;

    public ConnectThread(BluetoothSocket socket, Proto.Envelope envelope, TransMission.OnDataBackListener listener) {
        this.socket = socket;
        this.listener = listener;
        this.envelope = envelope;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable();
        /*IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        AppConfig.applicationContext.registerReceiver(new BluetoothAdapterStateReceiver(),filter);*/
        try {
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("MyBluetoothApp", BluetoothUUID.UUID.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            socket.connect();
            OutputStream os = socket.getOutputStream();
            envelope.writeTo(CodedOutputStream.newInstance(os));
            os.flush();
            os.close();
            socket.close();
            Log.d("发送数据，PATH = ：", envelope.getPath());

            BluetoothSocket socket1 = serverSocket.accept();
            socket1.connect();
            Proto.Envelope envelope1 = Proto.Envelope.parseFrom(CodedInputStream.newInstance(socket1.getInputStream()));
            if(envelope1.getVersion() == envelope.getVersion()){
                listener.onDataBack(envelope1);
            }
            socket1.close();
        } catch (IOException e) {
            Log.d("juju", "连接到蓝牙SOCKET失败");
            Log.d("juju"," socket fail , rebind bluetooth");
            AppConfig.applicationContext.bindService(new Intent(AppConfig.applicationContext, BluetoothService.class),
                    new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            Log.d("juju", "onServiceConnected ");
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {

                        }
                    }, Context.BIND_AUTO_CREATE);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  byte[] readData(InputStream inSream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return data;
    }

    public class BluetoothAdapterStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            context.unregisterReceiver(this);
        }
    }
}
