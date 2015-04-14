package cn.glassx.wear.juju;

import android.content.Context;

/**
 * Created by Fanz on 3/25/15.
 * 常量类
 */
public class AppConfig {

    public static final String POSITION_IN_JUJUERS = "position";

    public static Context applicationContext;

    /*----与手表数据传输使用到的PATH值----*/
    public static final String PATH_GET_JUJUERS = "/getJujuers";
    public static final String PATH_ATTENTION = "/attention";
    public static final String PATH_SEND_MESSAGE = "/sendMessage";
    public static final String PATH_OPEN_IN_PHONE = "/openInPhone";
    /*----与手表数据传输使用到的PATH值----*/

    /*-----用户模型使用到的key-----*/
    public static final String NAME = "name";
    public static final String LABELS = "labels";
    public static final String DISTANCE = "distance";
    public static final String PORTRAIL = "portrail";
    /*-----用户模型使用到的key-----*/
}
