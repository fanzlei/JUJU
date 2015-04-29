package cn.glassx.wear.juju.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.JUJUer;


/**
 * Created by Fanz on 3/25/15.
 * 存储运行时需要的一些数据类
 */
public class RuntimeData {

    /*获取的附近JUJU用户信息*/
    public static List<JUJUer> JUJUers = new ArrayList<JUJUer>();

    /**
     * 用于测试的方法，添加用户到附近列表
     */
    public static void addJUJUer(Context context, int size) {
        JUJUers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.portrail);
            JUJUers.add(new JUJUer(
                    "fanz",
                    new String[]{"宅", "动漫", "科技"},
                    "256m",
                    bitmap
            ));
        }
    }
}
