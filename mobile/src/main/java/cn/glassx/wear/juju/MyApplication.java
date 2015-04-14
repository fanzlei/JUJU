package cn.glassx.wear.juju;

import android.app.Application;

/**
 * Created by Fanz on 4/14/15.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.applicationContext = this;
    }
}
