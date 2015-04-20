package cn.glassx.wear.juju.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import cn.glassx.wear.juju.utils.MessageHandler;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.utils.RuntimeData;
import cn.glassx.wear.juju.model.JUJUer;

/**
 * Created by Fanz on 4/15/15.
 */
public class PersonDetail extends Activity {


    private JUJUer jujUer;
    private ImageView imageView;
    private TextView nameText;
    private TextView labelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_detail);
        for(JUJUer jj : RuntimeData.JUJUers){
            if(jj.getName().equals(getIntent().getStringExtra(MessageHandler.PERSON_NAME))){
                jujUer = jj;
                break;
            }
        }
        if(jujUer == null){throw new  NullPointerException("错误，没有找到用户");}
        nameText = (TextView)findViewById(R.id.name);
        labelText = (TextView)findViewById(R.id.labels);
        imageView = (ImageView)findViewById(R.id.image);
        nameText.setText(jujUer.getName());
        labelText.setText(jujUer.getLabels().toString());
        imageView.setImageBitmap(jujUer.getPortrail());

    }

}
