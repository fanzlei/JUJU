package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.glassx.wear.juju.bluetooth.BluetoothService;
import cn.glassx.wear.juju.bluetooth.ConnectThread;
import cn.glassx.wear.juju.bluetooth.Constants;
import cn.glassx.wear.juju.bluetooth.TransBehaviour;
import cn.glassx.wear.juju.bluetooth.TransMission;
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
                socket = device.createRfcommSocketToServiceRecord(Constants.UUID.getUuid());
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
                    envelope.toByteArray(),
                    listener).start();
        }
    }



    @Override
    public void openInPhone(String path, int version, String name, TransMission.OnDataBackListener listener) {

    }
}
