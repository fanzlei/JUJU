package cn.glassx.wear.juju.bluetooth;

import android.content.Context;

/**
 * Created by Fanz on 4/29/15.
 */
public class BluetoothServer {

    private Context context;

    public BluetoothServer(Context context){
        this.context = context;
    }
    public void startAcceptSocket(){
        new AcceptThread(context).start();
    }

}
