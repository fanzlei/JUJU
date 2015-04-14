package cn.glassx.wear.juju.app;
/**
 * Created by Fanz on 4/8/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.RuntimeData;
import cn.glassx.wear.juju.SendMessageManager;
import cn.glassx.wear.juju.WearableMessageListenerService;
import cn.glassx.wear.juju.adapter.JujuListAdapter;
import cn.glassx.wear.juju.model.JUJUer;

public class JUJUerList extends Activity implements WearableListView.ClickListener
        ,GoogleApiClient.OnConnectionFailedListener
        ,GoogleApiClient.ConnectionCallbacks{

    private static WearableListView personList;
    private WearableListView.Adapter mAdapter;
    private GoogleApiClient googleApiClient;
    public static Handler handler;
    /*用于判断应用是否处于后台，使得再次打开应用时自动从手机获取最新信息*/
    private boolean isExit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jujuer_list);
        personList = (WearableListView) findViewById(R.id.wearable_list);
        personList.setClickListener(JUJUerList.this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x123){
                    Log.d("JUJU", "handler msg,set the adapter of personList");
                    personList.setAdapter(new JujuListAdapter(JUJUerList.this, RuntimeData.JUJUers));
                }
            }
        };
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d("JUJU","JUJUList onStart");
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isExit = true;
        //当本地没有存储附近的人信息时
        if(RuntimeData.JUJUers.isEmpty()){
            googleApiClient.connect();
        }
        personList.setAdapter(new JujuListAdapter(JUJUerList.this, RuntimeData.JUJUers));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isExit){
            RuntimeData.JUJUers = new ArrayList<JUJUer>();
        }
        googleApiClient.disconnect();
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Integer tagPosition = (Integer) viewHolder.itemView.getTag();
        isExit = false;
        Log.d("JUJU","点击了位置："+tagPosition);
        Intent intent = new Intent(this,JUJUDetail.class);
        intent.putExtra(AppConfig.POSITION_IN_JUJUERS,tagPosition);
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("JUJU","onConnectionFailed");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("JUJU","onConnected");
        SendMessageManager.sendMessage(AppConfig.PATH_GET_JUJUERS,"","发送获取附近的人的消息到手机：");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("JUJU", "onConnectionSuspended");
    }


}
