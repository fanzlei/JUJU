package cn.glassx.wear.juju.bluetooth;

import cn.glassx.wear.juju.protobuf.Proto;

/**
 * Created by Fanz on 4/28/15.
 *
 */
public class TransMission {

    private static TransMission transMission;
    private static Object lock = new Object();
    private static TransBehaviour transBehaviour;

    
    public static final int CONNECT_FAIL = 0;
    public static final int PAIRED_FAIL = 1;

    /*区别每次传输的version*/
    private static int version = 1;
    /*----与手表数据传输使用到的PATH值----*/
    public static final String PATH_GET_JUJUERS = "/getJujuers";
    public static final String PATH_ATTENTION = "/attention";
    public static final String PATH_SEND_MESSAGE = "/sendMessage";
    public static final String PATH_OPEN_IN_PHONE = "/openInPhone";
    /*----与手表数据传输使用到的PATH值----*
     */
    private TransMission(){}

    /**
     * 取得实例
     * */
    public static TransMission getInstance(){
        if(transMission == null){
            synchronized (lock){
                transMission = new TransMission();
                //使用protoBuf进行传输
                transBehaviour = new ProtoBehaviour();
            }
        }
        return transMission;
    }


    public void getPersonList(OnDataBackListener listener){
        transBehaviour.getPersonList(PATH_GET_JUJUERS,version++,listener);

    }

    public int getVersion(){
        return version;
    }

   public interface OnDataBackListener{
        void onDataBack(Proto.Envelope envelope);
    }

}
