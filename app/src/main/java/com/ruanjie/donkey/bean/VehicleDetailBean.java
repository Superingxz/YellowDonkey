package com.ruanjie.donkey.bean;

import java.io.Serializable;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   VehicleDetailBean
 * 创建者:    QJM
 * 创建时间: 2019/8/16 16:02
 * 描述:     TODO
 */
public class VehicleDetailBean implements Serializable {


    /**
     * id : 1
     * code : 355488020032546
     * enegy : 70
     * mileage : 38
     * status : 1
     * user_id : 0
     * createtime : 1564652711
     * deletetime : null
     * lng : 116.71689000
     * lat : 23.42848900
     * city_id : 0
     * is_online : 1
     * lock_status : 0
     * partner_id : 15
     * partner_time : 1564728485
     * need_charge : 0
     * need_stop : 0
     * need_repair : 0
     * break_code : 00
     * is_outer : 0
     * e_status : 0
     * is_noele : 0
     * status_text : 空闲
     * is_online_text : 是
     * lock_status_text : 关
     * price_minute : 1
     * duration : 0
     * curMileage :
     * curMileageUnit : null
     * curPrice : 0
     */

    private int id;
    private String code;
    private int enegy;
    private int mileage;
    private int status;
    private int user_id;
    private int createtime;
    private Object deletetime;
    private String lng;
    private String lat;
    private int city_id;
    private int is_online;
    private int lock_status;
    private int partner_id;
    private int partner_time;
    private int need_charge;
    private int need_stop;
    private int need_repair;
    private String break_code;
    private int is_outer;
    private int e_status;
    private int is_noele;
    private String status_text;
    private String is_online_text;
    private String lock_status_text;
    private String price_minute;
    private int duration;
    private String curMileage;
    private Object curMileageUnit;
    private int curPrice;

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

    public int getEnegy() {
        return enegy;
    }

    public void setEnegy(int enegy) {
        this.enegy = enegy;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public Object getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(Object deletetime) {
        this.deletetime = deletetime;
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public int getLock_status() {
        return lock_status;
    }

    public void setLock_status(int lock_status) {
        this.lock_status = lock_status;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public int getPartner_time() {
        return partner_time;
    }

    public void setPartner_time(int partner_time) {
        this.partner_time = partner_time;
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

    public String getBreak_code() {
        return break_code;
    }

    public void setBreak_code(String break_code) {
        this.break_code = break_code;
    }

    public int getIs_outer() {
        return is_outer;
    }

    public void setIs_outer(int is_outer) {
        this.is_outer = is_outer;
    }

    public int getE_status() {
        return e_status;
    }

    public void setE_status(int e_status) {
        this.e_status = e_status;
    }

    public int getIs_noele() {
        return is_noele;
    }

    public void setIs_noele(int is_noele) {
        this.is_noele = is_noele;
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

    public String getPrice_minute() {
        return price_minute;
    }

    public void setPrice_minute(String price_minute) {
        this.price_minute = price_minute;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCurMileage() {
        return curMileage;
    }

    public void setCurMileage(String curMileage) {
        this.curMileage = curMileage;
    }

    public Object getCurMileageUnit() {
        return curMileageUnit;
    }

    public void setCurMileageUnit(Object curMileageUnit) {
        this.curMileageUnit = curMileageUnit;
    }

    public int getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(int curPrice) {
        this.curPrice = curPrice;
    }
}
