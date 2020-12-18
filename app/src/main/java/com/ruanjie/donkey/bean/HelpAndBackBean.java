package com.ruanjie.donkey.bean;

/**
 * @author vondear
 */
public class HelpAndBackBean {
    private String picPath;

    public HelpAndBackBean(String picPath) {
        this.picPath = picPath;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "HelpAndBackBean{" +
                "picPath='" + picPath + '\'' +
                '}';
    }
}
