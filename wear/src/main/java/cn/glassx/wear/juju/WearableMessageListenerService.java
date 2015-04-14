package cn.glassx.wear.juju;

/**
 * Created by Fanz on 4/8/15.
 */

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

/**
 * Listens for a message telling it to start the Wearable MainActivity.
 */
public class WearableMessageListenerService extends WearableListenerService {
    public static GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("JUJU", "onWearableListenerServiceCreate");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("JUJU", "onWearableListenerServiceDestroy");
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        Log.d("JUJU", "onMessageReceived");
        if (event.getPath().equals(AppConfig.PATH_GET_JUJUERS)) {

        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("JUJU","onDataChanged"+"  event count:"+dataEvents.getCount()+"  "+Wearable.DataApi.getDataItems(mGoogleApiClient));
        for(DataEvent dataEvent : dataEvents){
            switch (dataEvent.getDataItem().getUri().getPath()){
                case AppConfig.PATH_GET_JUJUERS:
                    DataDecode.getJUJUList(dataEvent,mGoogleApiClient);
                    break;

            }
        }
    }

    @Override
    public void onPeerConnected(Node peer) {

        super.onPeerConnected(peer);
        Log.d("JUJU", "onPeerConnected");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.d("JUJU", "onPeerDisconnected");
    }
}
