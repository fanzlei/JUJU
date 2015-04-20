package cn.glassx.wear.juju.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.app.JUJUerList;
import cn.glassx.wear.juju.model.JUJUer;

/**
 * Created by Fanz on 4/14/15.
 * 数据解析类
 * 解析从手机端传过来的数据
 */
public class DataDecode {

    private static GoogleApiClient mGoogleApiClient;

    /**
     * 把从手机端传过来的数据保存到RuntimeData中
     */
    public static void getJUJUList(DataEvent dataEvent, GoogleApiClient client) {
        mGoogleApiClient = client;
        DataItem dataItem = dataEvent.getDataItem();
        DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItem);
        ArrayList<DataMap> arrayList = dataMapItem.getDataMap().getDataMapArrayList(AppConfig.PATH_GET_JUJUERS);
        for (DataMap dataMap : arrayList) {
            JUJUer jujUer = new JUJUer();
            jujUer.setName(dataMap.getString(AppConfig.NAME));
            jujUer.setDistance(dataMap.getString(AppConfig.DISTANCE));
            String[] labels =
                    dataMap.getString(AppConfig.LABELS).split("/");
            jujUer.setLabels(labels);
            jujUer.setPortrail(loadBitmapFromAsset(dataMap.getAsset(AppConfig.PORTRAIL)));
            Log.d("JUJU", "获得jujuer：" + jujUer.getName() +
                    "distance=" + jujUer.getDistance() +
                    "labels=" + jujUer.getLabels() +
                    "portrail size=" + jujUer.getPortrail().getByteCount());
            RuntimeData.JUJUers.add(jujUer);
        }
        Log.d("JUJU", "JUJUList size=" + RuntimeData.JUJUers.size());

        JUJUerList.handler.sendEmptyMessage(0x123);
    }

    private static Bitmap loadBitmapFromAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset must be non-null");
        }
        ConnectionResult result =
                mGoogleApiClient.blockingConnect(5000, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset).await().getInputStream();
        mGoogleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.w("JUJU", "Requested an unknown Asset.");
            return null;
        }
        // decode the stream into a bitmap
        return BitmapFactory.decodeStream(assetInputStream);
    }
}
