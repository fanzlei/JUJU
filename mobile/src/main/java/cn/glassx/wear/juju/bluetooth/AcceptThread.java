package cn.glassx.wear.juju.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.glassx.wear.juju.ContantPath;
import cn.glassx.wear.juju.protobuf.Proto;

/**
 * Created by Fanz on 4/28/15.
 */
public class AcceptThread extends Thread {

    Context context;
    BluetoothServerSocket serverSocket;
    BluetoothSocket socket;
    public AcceptThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(!bluetoothAdapter.isEnabled()){
                Log.d("bluetooth", "bluetoothadapter not enable");
                return;
            }
            serverSocket =  bluetoothAdapter.listenUsingRfcommWithServiceRecord("MyBluetoothApp", BluetoothUUID.UUID.getUuid());
            socket = serverSocket.accept();
            socket.connect();
            Log.d("juju", "connected to socket,inputStream size= " + socket.getInputStream().available());
            Proto.Envelope envelope = Proto.Envelope.parseFrom(CodedInputStream.newInstance(socket.getInputStream()));
            Log.d("version = ",String.valueOf(envelope.getVersion()));
            Log.d("path = ", envelope.getPath());
            if(envelope.getPath().equals(ContantPath.PATH_GET_JUJUERS)){
                Proto.JUJUerEntity.Builder jujuBuilder = Proto.JUJUerEntity.newBuilder();
                jujuBuilder.setName("fanz");
                Proto.JUJUerEntity jujUerEntity = jujuBuilder.build();
                Proto.Envelope.Builder builder = Proto.Envelope.newBuilder();
                builder.setVersion(envelope.getVersion())
                        .setPath(envelope.getPath())
                        .setJujuerEntity(0, jujUerEntity);
                Proto.Envelope envelope1 = builder.build();
                envelope1.writeTo(CodedOutputStream.newInstance(socket.getOutputStream()));


            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  byte[] readData(InputStream inSream) throws Exception{
        Log.d("InputStream available:",""+inSream.available());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            Log.d("接收到数据长度：",""+buffer.length);
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inSream.close();
        return data;
    }
}
