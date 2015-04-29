package cn.glassx.wear.juju.view.widget;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.CircledImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.glassx.wear.juju.R;

/**
 * Created by Fanz on 4/7/15.
 */
public class SimpleFragment extends Fragment {

    private String title;
    private Drawable icon;
    private View fragmentView;
    public SimpleFragment(){

    }

    /**
     * 获得SimpleFragment实例
     * */
    public static SimpleFragment create(String title, Drawable icon){
        SimpleFragment instance = new SimpleFragment();
        instance.title = title;
        instance.icon = icon;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.simple_fragment,container,false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CircledImageView)fragmentView.findViewById(R.id.fragment_image)).setImageDrawable(icon);
        ((TextView)fragmentView.findViewById(R.id.fragment_text)).setText(title);
    }


}
