package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.glassx.wear.juju.protobuf.Proto;

/**
 * Created by Fanz on 4/28/15.
 */
public class ConnectThread extends Thread{

    private BluetoothSocket socket;
    private byte[] bytes;
    private TransMission.OnDataBackListener listener;
    private BluetoothServerSocket serverSocket;

    public ConnectThread(BluetoothSocket socket, byte[] bytes, TransMission.OnDataBackListener listener) {
        this.socket = socket;
        this.bytes = bytes;
        this.listener = listener;
        try {
            serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(null,Constants.UUID.getUuid());
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
            os.write(bytes);
            os.flush();
            socket = serverSocket.accept(12 * 1000);
            bytes = readData(socket.getInputStream());
            Proto.Envelope envelope = Proto.Envelope.parseFrom(bytes);
            if(envelope.getVersion() == TransMission.getInstance().getVersion()){
                listener.onDataBack(envelope);
            }
            socket.close();
        } catch (IOException e) {
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

}
