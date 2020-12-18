package com.ruanjie.donkey.bean;

public class ShopCouponBean {

    /**
     * id : 2
     * store_id : 1
     * title : 测试现金券2
     * money : 80.00
     * score : 1
     * remark : 每次只能用一张
     * stock : 96
     * createtime : 1583337176
     * deletetime : null
     */

    private int id;
    private int store_id;
    private String title;
    private String money;
    private int score;
    private String remark;
    private int stock;
    private int createtime;
    private Object deletetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
}
