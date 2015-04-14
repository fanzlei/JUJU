package cn.glassx.wear.juju;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cn.glassx.wear.juju.app.MainActivity;
import cn.glassx.wear.juju.model.JUJUer;

/**
 * Created by Fanz on 4/14/15.
 * 处理手表发送过来的消息类
 */
public class MessageHandler {

    private GoogleApiClient mGoogleApiClient;

    public void handler(MessageEvent event){
        getGoogleApiClient();
        switch (event.getPath()){
            case AppConfig.PATH_GET_JUJUERS:
                sendJUJUerListData();
                break;
            case AppConfig.PATH_ATTENTION:
                String str = new String(event.getData());
                makeAttention(str);
                break;
            case AppConfig.PATH_SEND_MESSAGE:
                sendMessageToJUJUer(new String(event.getData()));
                break;
            case AppConfig.PATH_OPEN_IN_PHONE:
                openInPhone(new String(event.getData()));
                break;
        }
    }

    private void getGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(AppConfig.applicationContext).addApi(Wearable.API).build();
        mGoogleApiClient.connect();
    }

    /*执行传小纸条到用户*/
    private void sendMessageToJUJUer(String param){
        String name = param;
        Log.d("JUJU","执行传小纸条到用户："+name);
        //Todo 执行传小纸条到用户

    }

    /*执行在手机中打开*/
    private void openInPhone(String param){
        String name = param;
        Log.d("JUJU","执行在手机中打开："+name);
        //Todo 执行在手机中打开


    }
    /*执行发送附近的人的信息到手表*/
    private void sendJUJUerListData(){
        //Todo 从服务器获取附近的人列表,暂时模拟数据
        Log.d("JUJU", "onMessageReceived path =" + AppConfig.PATH_GET_JUJUERS);
        //本地模拟数据
        SyncData.addJUJUer(AppConfig.applicationContext,8);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(AppConfig.PATH_GET_JUJUERS);
        DataMap dataMap =  putDataMapRequest.getDataMap();
        ArrayList<DataMap> arrayList = new ArrayList<>();
        for(JUJUer jujUer : SyncData.JUJUers){
            Bundle bundle = new Bundle();
            bundle.putString(AppConfig.NAME,jujUer.getName());
            bundle.putString(AppConfig.DISTANCE,jujUer.getDistance());
            bundle.putString(AppConfig.LABELS, jujUer.labelsToString());
            DataMap dataMap1 = DataMap.fromBundle(bundle);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            jujUer.getPortrail().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            dataMap1.putAsset(AppConfig.PORTRAIL, Asset.createFromBytes(byteArrayOutputStream.toByteArray()));
            arrayList.add(dataMap1);
        }
        dataMap.putDataMapArrayList(AppConfig.PATH_GET_JUJUERS,arrayList);
        dataMap.putInt("makeSureDefirence", MainActivity.i++);
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient,request);
    }

    /*执行关注某一用户操作*/
    private void makeAttention(String params){
        //Todo 暂定关注某一用户，收到的消息内容为用户名
        String name = params;
        Log.d("JUJU","执行关注："+name);
    }
}
