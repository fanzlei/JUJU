package cn.glassx.wear.juju.app;
/**
 * Created by Fanz on 4/8/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.utils.SendMessageManager;
import cn.glassx.wear.juju.adapter.JujuListAdapter;

public class JUJUerList extends Activity implements WearableListView.ClickListener
        ,GoogleApiClient.OnConnectionFailedListener
        ,GoogleApiClient.ConnectionCallbacks{

    private static WearableListView personList;
    private WearableListView.Adapter mAdapter;
    private GoogleApiClient googleApiClient;
    public static Handler handler;
    private boolean isExit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jujuer_list);
        personList = (WearableListView) findViewById(R.id.wearable_list);
        personList.setClickListener(JUJUerList.this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
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
        //当本地没有存储附近的人信息时
        if(RuntimeData.JUJUers.isEmpty()||isExit){
            googleApiClient.connect();
        }
        isExit = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("JUJU","点击了位置："+viewHolder.getPosition());
        isExit = false;
        Intent intent = new Intent(this,JUJUDetail.class);
        intent.putExtra(AppConfig.POSITION_IN_JUJUERS,viewHolder.getPosition());
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
