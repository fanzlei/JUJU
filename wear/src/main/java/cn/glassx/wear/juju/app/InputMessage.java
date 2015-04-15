package cn.glassx.wear.juju.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;

/**
 * Created by Fanz on 4/15/15.
 * 传小纸条输入界面
 * 若有语音输入则进入语音输入显示界面
 * 若点击快速回复内容则进入快速回复界面
 */
public class InputMessage extends Activity {

    private int position_in_jujuers = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_message);
        position_in_jujuers = getIntent().getIntExtra(AppConfig.POSITION_IN_JUJUERS,-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Todo 执行监听是否有语音输入
    }

    /*点击事件*/
    public void startSelectAnswerActivity(View view){
        Intent intent = new Intent(this,SelectCunstomInput.class);
        intent.putExtra(AppConfig.POSITION_IN_JUJUERS,position_in_jujuers);
        startActivity(intent);
        finish();
    }
}
