package cn.glassx.wear.juju.bluetooth;

import android.support.annotation.Nullable;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Fanz on 4/28/15.
 */
public interface TransBehaviour {

    void attention(String path , int version, String name, TransMission.OnDataBackListener listener);
    void sendMessage(String path, int version, String message, TransMission.OnDataBackListener listener);
    void getPersonList(String path, int version, TransMission.OnDataBackListener listener);
    void openInPhone(String path, int version, String name, TransMission.OnDataBackListener listener);
}
