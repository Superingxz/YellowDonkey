package com.ruanjie.donkey.bean;

import java.io.Serializable;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   VehicleListBean
 * 创建者:    QJM
 * 创建时间: 2019/8/14 18:29
 * 描述:     TODO
 */
public class VehicleListBean implements Serializable {

    /**
     * id : 4
     * code : 355488020032883
     * lng : 113.39616394
     * lat : 23.13066292
     * need_charge : 0
     * need_stop : 0
     * need_repair : 0
     * distance : 21
     * status_text :
     * is_online_text :
     * lock_status_text :
     */

    private int id;
    private String code;
    private String lng;
    private String lat;
    private int need_charge;
    private int need_stop;
    private int need_repair;
    private int distance;
    private String status_text;
    private String is_online_text;
    private String lock_status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getNeed_charge() {
        return need_charge;
    }

    public void setNeed_charge(int need_charge) {
        this.need_charge = need_charge;
    }

    public int getNeed_stop() {
        return need_stop;
    }

    public void setNeed_stop(int need_stop) {
        this.need_stop = need_stop;
    }

    public int getNeed_repair() {
        return need_repair;
    }

    public void setNeed_repair(int need_repair) {
        this.need_repair = need_repair;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getIs_online_text() {
        return is_online_text;
    }

    public void setIs_online_text(String is_online_text) {
        this.is_online_text = is_online_text;
    }

    public String getLock_status_text() {
        return lock_status_text;
    }

    public void setLock_status_text(String lock_status_text) {
        this.lock_status_text = lock_status_text;
    }
}
