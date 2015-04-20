package cn.glassx.wear.juju.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.utils.SendMessageManager;
import cn.glassx.wear.juju.adapter.GridPagerAdapter;

/**
 * Created by Fanz on 4/7/15.
 */
public class JUJUDetail extends Activity{

    GridViewPager pager;
    public static int position = -1;
    private GestureDetector detector;
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
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        googleApiClient.connect();
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
            Intent intent;
            switch(pager.getCurrentItem().x){
                case 0:

                    break;
                case 1:
                    intent = new Intent(JUJUDetail.this,InputMessage.class);
                    intent.putExtra(AppConfig.POSITION_IN_JUJUERS,position);
                    startActivity(intent);


                    break;
                case 2:
                    intent = new Intent(JUJUDetail.this, AnimationActivity.class);
                    intent.putExtra(AnimationActivity.MESSAGE_PATH,AppConfig.PATH_ATTENTION);
                    intent.putExtra(AnimationActivity.MESSAGE_STRING,RuntimeData.JUJUers.get(position).getName());
                    intent.putExtra(AnimationActivity.ANIMATING_TEXT,"关注");
                    intent.putExtra(AnimationActivity.SUCCESS_TEXT,"关注成功");
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(JUJUDetail.this, ConfirmationActivity.class);
                    intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                            ConfirmationActivity.SUCCESS_ANIMATION);
                    intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                            "请查看手机");
                    startActivity(intent);
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
