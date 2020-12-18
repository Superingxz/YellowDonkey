package com.ruanjie.donkey.bean;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   LocationBean
 * 创建者:    QJM
 * 创建时间: 2019/8/27 9:57
 * 描述:     TODO
 */
public class LocationBean {

    public double latitude;

    public double longitude;

    public String address;

    public String city;

    public LocationBean() {
    }

    public LocationBean(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longitude = longtitude;
    }

}
