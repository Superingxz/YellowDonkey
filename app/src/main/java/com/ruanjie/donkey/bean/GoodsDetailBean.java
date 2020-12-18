package com.ruanjie.donkey.bean;

import java.util.List;

public class GoodsDetailBean {


    /**
     * id : 1
     * images : ["http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png"]
     * name : 测试商品1
     * content : 啊手动阀手动阀
     * price : 50.00
     * origin_price : 100.00
     * score : 10
     * stock : 87
     * sales : 12
     * is_hot_text :
     * is_show_text :
     */

    private int id;
    private String name;
    private String content;
    private String price;
    private String origin_price;
    private int score;
    private int stock;
    private int sales;
    private String is_hot_text;
    private String is_show_text;
    private List<String> images;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(String origin_price) {
        this.origin_price = origin_price;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getIs_hot_text() {
        return is_hot_text;
    }

    public void setIs_hot_text(String is_hot_text) {
        this.is_hot_text = is_hot_text;
    }

    public String getIs_show_text() {
        return is_show_text;
    }

    public void setIs_show_text(String is_show_text) {
        this.is_show_text = is_show_text;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
