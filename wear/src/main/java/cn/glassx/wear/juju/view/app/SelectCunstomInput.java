package cn.glassx.wear.juju.view.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.view.adapter.CustomInputAdapter;

/**
 * Created by Fanz on 4/15/15.
 * 选择快速回复内容界面
 */
public class SelectCunstomInput extends Activity implements WearableListView.ClickListener{

    private WearableListView listView;
    private int position_in_jujuers = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_custom_input);
        position_in_jujuers = getIntent().getIntExtra(AppConfig.POSITION_IN_JUJUERS,-1);
        listView = (WearableListView)findViewById(R.id.wearable_list_custom_input);
        listView.setAdapter(new CustomInputAdapter(this));
        listView.setClickListener(this);
    }


    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        int clickPosition = viewHolder.getPosition();
        String selectedString = getResources().getStringArray(R.array.customAnswer)[clickPosition];
        Intent intent = new Intent(this, AnimationActivity.class);
        intent.putExtra(AnimationActivity.MESSAGE_PATH,AppConfig.PATH_SEND_MESSAGE);
        intent.putExtra(AnimationActivity.MESSAGE_STRING,
                RuntimeData.JUJUers.get(position_in_jujuers).getName()+"/"+selectedString);
        intent.putExtra(AnimationActivity.ANIMATING_TEXT,selectedString);
        intent.putExtra(AnimationActivity.SUCCESS_TEXT,"发送成功");
        startActivity(intent);
        finish();
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
