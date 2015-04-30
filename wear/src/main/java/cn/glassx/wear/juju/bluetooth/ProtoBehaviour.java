package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import cn.glassx.wear.juju.protobuf.Proto;

/**
 * Created by Fanz on 4/28/15.
 */
public class ProtoBehaviour implements TransBehaviour {


    private Proto.Envelope.Builder builder;
    private Proto.Envelope envelope;
    private BluetoothDevice device;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private BluetoothServerSocket serverSocket;

    public ProtoBehaviour(){

    }

    private BluetoothSocket getSocket(){
        device = BluetoothService.getDevice();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            if(device != null){
                socket = device.createRfcommSocketToServiceRecord(BluetoothUUID.UUID.getUuid());
                Log.d("juju","对方蓝牙设备："+device.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            BluetoothService.getDevice();
        }
        return socket;
    }
    @Override
    public void attention(String path, int version, String name, TransMission.OnDataBackListener listener) {

    }

    @Override
    public void sendMessage(String path, int version, String message, TransMission.OnDataBackListener listener) {

    }

    @Override
    public void getPersonList(String path, int version, TransMission.OnDataBackListener listener) {
        builder = Proto.Envelope.newBuilder();
        builder.setPath(path)
                .setVersion(version);
        envelope  = builder.build();
        BluetoothSocket bluetoothSocket = getSocket();
        if(bluetoothSocket != null){
            new ConnectThread(bluetoothSocket,
                    envelope,
                    listener).start();
        }
    }



    @Override
    public void openInPhone(String path, int version, String name, TransMission.OnDataBackListener listener) {

    }
}
