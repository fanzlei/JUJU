package cn.glassx.wear.juju.view.widget;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.glassx.wear.juju.R;

/**
 * Created by Fanz on 4/7/15.
 */
public class WearableListItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private  float mFadedTextAlpha;
    private  int mFadedCircleColor;
    private int mChonsenCircleColor;
    private CircledImageView mCircle;
    private float mScale;
    private TextView mName;

    public WearableListItemLayout(Context context) {
        this(context,null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha)/100f;
        this.mFadedCircleColor = getResources().getColor(R.color.grey);
        this.mChonsenCircleColor = getResources().getColor(R.color.blue);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (CircledImageView)findViewById(R.id.circle);
        mName = (TextView)findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean b) {
        mName.animate().alpha(1f).scaleX(1.1f).scaleY(1.1f);
        mCircle.animate().alpha(1f).scaleX(1.3f).scaleY(1.3f);
        mCircle.setCircleColor(mChonsenCircleColor);
        //((GradientDrawable)mCircle.getDrawable()).setColor(mChonsenCircleColor);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        mName.animate().alpha(0.5f).scaleY(1f).scaleX(1f);
        mCircle.animate().alpha(0.5f).scaleX(1f).scaleY(1f);
        mCircle.setCircleColor(mFadedCircleColor);
       // ((GradientDrawable)mCircle.getDrawable()).setColor(mFadedCircleColor);
    }
}
