package cn.glassx.wear.juju.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.RuntimeData;
import cn.glassx.wear.juju.SendMessageManager;
import cn.glassx.wear.juju.adapter.GridPagerAdapter;

/**
 * Created by Fanz on 4/7/15.
 */
public class JUJUDetail extends Activity implements DelayedConfirmationView.DelayedConfirmationListener{

    GridViewPager pager;
    public static int position = -1;
    private GestureDetector detector;
    private DelayedConfirmationView mDelayedView;
    private boolean isAnimating = false;
    private boolean isForceStop =false;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jujuer_detail);
        position = getIntent().getIntExtra(AppConfig.POSITION_IN_JUJUERS, -1);
        pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
        detector = new GestureDetector(this,new MyDetectorListener());
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        mDelayedView = (DelayedConfirmationView)findViewById(R.id.delayed_confirm);
        mDelayedView.setListener(this);
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.setVisibility(View.VISIBLE);
        mDelayedView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTimerFinished(View view) {
        if(isForceStop){
            isAnimating =false;
            isForceStop = false;
            return;}
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                "关注成功");
        startActivity(intent);
        isForceStop = false;
        isAnimating = false;
        //Todo 向手机发送关注消息，消息内容未确定,这里为用户名
        SendMessageManager.sendMessage(
                AppConfig.PATH_ATTENTION,
                RuntimeData.JUJUers.get(position).getName(),
                "发送关注用户消息：");
    }

    @Override
    public void onTimerSelected(View view) {
        isForceStop = true;
        pager.setVisibility(View.VISIBLE);
        mDelayedView.setVisibility(View.INVISIBLE);
    }

    private class MyDetectorListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("JUJU","onDown");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("JUJU","onShowPress");

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("JUJU","onSingleTapUp");
            switch(pager.getCurrentItem().x){
                case 0:

                    break;
                case 1:
                    Intent intent = new Intent(JUJUDetail.this, ConfirmationActivity.class);
                    intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                            ConfirmationActivity.SUCCESS_ANIMATION);
                    intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                            "发送成功");
                    startActivity(intent);
                    SendMessageManager.sendMessage(
                            AppConfig.PATH_SEND_MESSAGE,
                            RuntimeData.JUJUers.get(position).getName(),
                            "传小纸条：");
                    break;
                case 2:
                    if(isAnimating){
                        break;
                    }
                    pager.setVisibility(View.INVISIBLE);
                    mDelayedView.setVisibility(View.VISIBLE);
                    mDelayedView.setTotalTimeMs(2000);
                    mDelayedView.start();
                    isAnimating = true;
                    break;
                case 3:
                    Intent intent1 = new Intent(JUJUDetail.this, ConfirmationActivity.class);
                    intent1.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                            ConfirmationActivity.SUCCESS_ANIMATION);
                    intent1.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                            "请查看手机");
                    startActivity(intent1);
                    SendMessageManager.sendMessage(
                            AppConfig.PATH_OPEN_IN_PHONE,
                            RuntimeData.JUJUers.get(position).getName(),
                            "在手机中打开：");
                    break;
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

}
