package cn.glassx.wear.juju.widget;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.CircledImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.glassx.wear.juju.AppConfig;
import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.RuntimeData;
import cn.glassx.wear.juju.app.JUJUDetail;
import cn.glassx.wear.juju.model.JUJUer;

/**
 * Created by Fanz on 4/7/15.
 */
public class JUJUerDetailFirst extends Fragment{


    private JUJUer jujUer;
    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.first_fragment,container,false);
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jujUer = RuntimeData.JUJUers.get(JUJUDetail.position);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CircledImageView)fragmentView.findViewById(R.id.circle)).setImageDrawable(
                new BitmapDrawable(AppConfig.applicationContext.getResources(),jujUer.getPortrail())
        );
        ((TextView)fragmentView.findViewById(R.id.name)).setText(jujUer.getName());
        ((TextView)fragmentView.findViewById(R.id.label_text1)).setText(jujUer.getLabels()[0]);
        ((TextView)fragmentView.findViewById(R.id.label_text2)).setText(jujUer.getLabels()[1]);
        ((TextView)fragmentView.findViewById(R.id.label_text3)).setText(jujUer.getLabels()[2]);
    }
}
