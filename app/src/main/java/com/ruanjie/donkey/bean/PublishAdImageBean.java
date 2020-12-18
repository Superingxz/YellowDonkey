package com.ruanjie.donkey.bean;

/**
 * @author vondear
 */
public class PublishAdImageBean {
    private String picPath;

    public PublishAdImageBean(String picPath) {
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
        return "BackAndSaleBean{" +
                "picPath='" + picPath + '\'' +
                '}';
    }
}
