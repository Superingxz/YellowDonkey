package com.ruanjie.donkey.bean;

public class ShopBean {
    private int id;
    private String image;
    private String name;
    private String count;
    private String score;
    private String distance;

    public ShopBean(int id, String image, String name, String count, String score, String distance) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.count = count;
        this.score = score;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
