package cn.glassx.wear.juju.view.app;
/**
 * Created by Fanz on 4/8/15.
 */
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.bluetooth.BluetoothService;
import cn.glassx.wear.juju.bluetooth.TransMission;
import cn.glassx.wear.juju.protobuf.Proto;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.view.adapter.JujuListAdapter;

public class JUJUerList extends Activity implements WearableListView.ClickListener {

    private static WearableListView personList;
    BluetoothService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BluetoothService.MyBinder)service;
            Log.d("bluetooth","onServiceConnected");
            getPersonList();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jujuer_list);
        personList = (WearableListView) findViewById(R.id.wearable_list);
        personList.setClickListener(JUJUerList.this);
        bindService(new Intent(this, BluetoothService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Log.d("JUJU","点击了位置："+viewHolder.getPosition());
        Intent intent = new Intent(this,JUJUDetail.class);
        intent.putExtra(AppConfig.POSITION_IN_JUJUERS, viewHolder.getPosition());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(binder != null){
            getPersonList();
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    private void getPersonList(){
        binder.getPersonList(new TransMission.OnDataBackListener() {
            @Override
            public void onDataBack(Proto.Envelope envelope) {
                Log.d("got personList size = ",String.valueOf(envelope.getJujuerEntityList().size()));
            }
        });
    }
}
