package cn.glassx.wear.juju.widget;

import android.content.Context;
import android.support.wearable.view.GridViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Fanz on 4/7/15.
 */
public class MyGridViewPager extends GridViewPager {
    Context context;
    public MyGridViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyGridViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyGridViewPager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        if(mGestureDetector.onTouchEvent(event)){
//            event.setAction(MotionEvent.ACTION_CANCEL);
//        }
        //不可以省去，否则将接受不到singleTapUp和scroll事件
        return super.dispatchTouchEvent(event);
    }


}
