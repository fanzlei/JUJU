package cn.glassx.wear.juju.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Fanz on 3/25/15.
 * JUJU用户模型
 */
public class JUJUer {

    private Bitmap portrail;
    private String name;
    private String[] labels;
    private String distance;

    public JUJUer() {
    }

    public JUJUer(String name, String[] labels, String distance, Bitmap portrail) {
        this.distance = distance;
        this.labels = labels;
        this.name = name;
        this.portrail = portrail;
    }

    public Bitmap getPortrail() {
        return portrail;
    }

    public void setPortrail(Bitmap portrail) {
        this.portrail = portrail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String labelsToString(){
        String str="";
        for(String label : labels){
            str = str+label+"/";
        }
        return str;
    }
}
