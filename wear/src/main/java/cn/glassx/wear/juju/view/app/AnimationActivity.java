package cn.glassx.wear.juju.view.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.widget.TextView;

import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.utils.SendMessageManager;


/**
 * Created by Fanz on 4/15/15.
 * 手表事件操作后动画类
 * 该Activity创建后自动执行动画，自动结束退出
 * 启动该Activity必须要Intent中带属性 ANIMATING_TEXT、SUCCESS_TEXT、MESSAGE_STRING、MESSAGE_PATH
 */
public class AnimationActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener{

    private DelayedConfirmationView delayedView;
    private TextView animating_text;
    private String successText = "";
    private String animatingText = "";
    private String messageString = "";
    private String messagePath = "";

    /*判断是否用户取消操作*/
    private boolean isForceStop = false;

    /**正在执行中动画中显示的内容*/
    public static final String ANIMATING_TEXT = "ANIMATING_TEXT";
    /**动画结束提示的内容*/
    public static final String SUCCESS_TEXT = "SUCCESS_TEXT";
    /**向手机发送的消息内容*/
    public static final String MESSAGE_STRING = "MESSAGE_STRING";
    /**向手机发送的消息路径*/
    public static final String MESSAGE_PATH = "MESSAGE_PATH";
    public AnimationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);
        animatingText = getIntent().getStringExtra(ANIMATING_TEXT);
        successText = getIntent().getStringExtra(SUCCESS_TEXT);
        messageString = getIntent().getStringExtra(MESSAGE_STRING);
        messagePath = getIntent().getStringExtra(MESSAGE_PATH);
        delayedView = (DelayedConfirmationView)findViewById(R.id.delayed_confirm);
        animating_text = (TextView)findViewById(R.id.animating_text);
        animating_text.setText(animatingText);
        delayedView.setListener(this);
        delayedView.setTotalTimeMs(2000);
        delayedView.start();
    }

    @Override
    public void onTimerFinished(View view) {
        if(isForceStop){ isForceStop = false;return;}
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                successText);
        this.startActivity(intent);
        SendMessageManager.sendMessage(messagePath, messageString);
        finish();
    }

    @Override
    public void onTimerSelected(View view) {
        isForceStop = true;
        finish();
    }



}
