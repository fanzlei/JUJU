package cn.glassx.wear.juju.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;

import cn.glassx.wear.juju.AppConfig;

/**
 * Created by Fanz on 4/14/15.
 * 发送消息到手机管理类
 */
public class SendMessageManager {

    private static GoogleApiClient googleApiClient;

    /**
     * 发送消息到手机
     * @param path 消息的路径信息
     * @param message 消息内容
     * @param logMessage 打印的Log信息
     * */
    public static void sendMessage(String path, String message, String logMessage){
        if(googleApiClient == null){getGoogleApiClient();}
        new StartSendMessageTask().execute(new String[]{path,message,logMessage});
    }

    /**
     * 发送消息到手机
     * @param path 消息的路径信息
     * @param message 消息内容
     * */
    public static void sendMessage(String path, String message){
        if(googleApiClient == null){getGoogleApiClient();}
        new StartSendMessageTask().execute(new String[]{path,message,"发送消息状态："});
    }

    private static void getGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(AppConfig.applicationContext).addApi(Wearable.API).build();
        googleApiClient.connect();
    }

    private static class StartSendMessageTask extends AsyncTask<String[], Void, Void> {

        private Collection<String> getNodes() {
            HashSet<String> results = new HashSet<String>();
            NodeApi.GetConnectedNodesResult nodes =
                    Wearable.NodeApi.getConnectedNodes(googleApiClient).await();

            for (Node node : nodes.getNodes()) {
                results.add(node.getId());
            }

            return results;
        }

        @Override
        protected Void doInBackground(String[]... args) {
            Collection<String> nodes = getNodes();
            final String[] params = args[0];
            for (String node : nodes) {
                Wearable.MessageApi.sendMessage(googleApiClient,node,params[0],params[1].getBytes())
                        .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                            @Override
                            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                Log.d("JUJU", params[2] + sendMessageResult.getStatus().isSuccess());
                            }
                        });
            }
            return null;
        }
    }
}
