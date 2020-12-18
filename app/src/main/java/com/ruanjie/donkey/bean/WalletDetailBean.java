package com.ruanjie.donkey.bean;


import com.contrarywind.interfaces.IPickerViewData;

public class WalletDetailBean  {


    /**
     * id : 118
     * user_id : 1
     * before : 10100.00
     * value : 111.00
     * after : 9989.00
     * type : 1
     * info : 支付费用
     * order_sn : 2019080615410232462
     * createtime : 1565077336
     * type_text : 扣减
     */

    private int id;
    private int user_id;
    private String before;
    private String value;
    private String after;
    private int type;
    private String info;
    private String order_sn;
    private int createtime;
    private String type_text;
    private int pay_type;

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

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

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
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
