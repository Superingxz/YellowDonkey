package com.ruanjie.donkey.bean;

import java.util.List;

public class ShopDetail4Bean {

    private String address;
    private List<String> images;
    private String intro;
    private String tel;
    private String lat;
    private String lng;

    public ShopDetail4Bean(String address, List<String> images, String intro, String tel, String lat, String lng) {
        this.address = address;
        this.images = images;
        this.intro = intro;
        this.tel = tel;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
