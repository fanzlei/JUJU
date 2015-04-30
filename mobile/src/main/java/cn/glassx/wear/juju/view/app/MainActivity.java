package cn.glassx.wear.juju.view.app;

import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.bluetooth.BluetoothServer;
import cn.glassx.wear.juju.bluetooth.BluetoothStateReceiver;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.model.JUJUer;


public class MainActivity extends ActionBarActivity {

    public static int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(new BluetoothStateReceiver(),filter);
        new BluetoothServer(this).startAcceptSocket();

    }

    public void sendData(View view){

    }
}
