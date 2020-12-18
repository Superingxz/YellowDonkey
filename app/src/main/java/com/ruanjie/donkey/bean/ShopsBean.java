package com.ruanjie.donkey.bean;

public class ShopsBean {

    /**
     * id : 1
     * name : 测试店铺1
     * logo : http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg
     * star : 4.0
     * sales : 6
     * distance : 236.3公里
     * status_text :
     */

    private int id;
    private String name;
    private String logo;
    private String star;
    private int sales;
    private String distance;
    private String status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
