package com.ruanjie.donkey.bean;

import java.io.Serializable;


public class GoodBean implements Serializable {
    private String type;
    private String image;
    private String name;
    private int exchangeCount;//已兑换数量
    private int residueCount;//剩余数量
    private int coin;//所需驴币
    private double rmb;//rmb
    private double prePrice;//原价

    public GoodBean(String type, String image, String name, int exchangeCount, int residueCount, int coin, double rmb, double prePrice) {
        this.type = type;
        this.image = image;
        this.name = name;
        this.exchangeCount = exchangeCount;
        this.residueCount = residueCount;
        this.coin = coin;
        this.rmb = rmb;
        this.prePrice = prePrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getExchangeCount() {
        return exchangeCount;
    }

    public void setExchangeCount(int exchangeCount) {
        this.exchangeCount = exchangeCount;
    }

    public int getResidueCount() {
        return residueCount;
    }

    public void setResidueCount(int residueCount) {
        this.residueCount = residueCount;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getRmb() {
        return rmb;
    }

    public void setRmb(double rmb) {
        this.rmb = rmb;
    }

    public double getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(double prePrice) {
        this.prePrice = prePrice;
    }
}
