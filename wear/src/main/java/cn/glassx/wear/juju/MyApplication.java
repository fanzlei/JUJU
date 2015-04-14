package cn.glassx.wear.juju;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fanz on 4/14/15.
 */
public class MyApplication extends Application {


    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.applicationContext = this;
    }
}
