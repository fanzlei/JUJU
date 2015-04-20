package cn.glassx.wear.juju.app;

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
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.model.JUJUer;


public class MainActivity extends ActionBarActivity {

    GoogleApiClient client;
    public static int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d("JUJU", "onConnected");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("JUJU","onConnectionSuspended");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d("JUJU", "onConnectionFailed");
                    }
                }).build();
        client.connect();
    }

public void sendData(View view){
    if(client.isConnected()){
        //本地模拟数据
        RuntimeData.addJUJUer(this, 8);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(AppConfig.PATH_GET_JUJUERS);
        DataMap dataMap =  putDataMapRequest.getDataMap();
        ArrayList<DataMap> arrayList = new ArrayList<>();
        for(JUJUer jujUer : RuntimeData.JUJUers){
            Bundle bundle = new Bundle();
            bundle.putString(AppConfig.NAME,jujUer.getName());
            bundle.putString(AppConfig.DISTANCE,jujUer.getDistance());
            bundle.putString(AppConfig.LABELS,jujUer.labelsToString());
            DataMap dataMap1 = DataMap.fromBundle(bundle);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            jujUer.getPortrail().compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            dataMap1.putAsset(AppConfig.PORTRAIL, Asset.createFromBytes(byteArrayOutputStream.toByteArray()));
            arrayList.add(dataMap1);
        }
        dataMap.putDataMapArrayList(AppConfig.PATH_GET_JUJUERS,arrayList);
        dataMap.putInt("makeSureDefirence",i++);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(client, request);
    }else{
        Log.d("JUJU","client not connected");
    }

}

}
