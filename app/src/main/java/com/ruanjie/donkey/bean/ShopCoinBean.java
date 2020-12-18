package com.ruanjie.donkey.bean;

public class ShopCoinBean {


    /**
     * id : 232769
     * user_id : 97900
     * before : 9835
     * value : 10
     * after : 9825
     * type : 1
     * info : 兑换商品
     * order_sn : 20200410172015199819
     * createtime : 1586510415
     * type_text : 扣减
     */

    private int id;
    private int user_id;
    private int before;
    private int value;
    private int after;
    private int type;
    private String info;
    private String order_sn;
    private int createtime;
    private String type_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }
}
