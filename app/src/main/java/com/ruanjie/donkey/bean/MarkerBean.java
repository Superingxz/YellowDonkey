package com.ruanjie.donkey.bean;


public class MarkerBean {


    /**
     * id : 5
     * lng : 113.39614090
     * lat : 23.13076535
     * code : 355488020032551
     * enegy : 0
     * distance : 650
     * status_text :
     * is_online_text :
     * lock_status_text :
     */

    private int id;
    private String lng;
    private String lat;
    private String code;
    private int enegy;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEnegy() {
        return enegy;
    }

    public void setEnegy(int enegy) {
        this.enegy = enegy;
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
