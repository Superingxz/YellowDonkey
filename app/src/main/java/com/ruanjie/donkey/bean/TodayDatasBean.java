package com.ruanjie.donkey.bean;


import com.contrarywind.interfaces.IPickerViewData;

public class TodayDatasBean{


    /**
     * id : 1
     * code : 355488020032546
     * total_price : 0.25
     * duration : 79
     * coupon_fee : 0.00
     * endtime_text :
     * is_pay_text :
     * pay_type_text :
     */

    private int id;
    private String code;
    private String total_price;
    private int duration;
    private String coupon_fee;
    private String endtime_text;
    private String is_pay_text;
    private String pay_type_text;

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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getEndtime_text() {
        return endtime_text;
    }

    public void setEndtime_text(String endtime_text) {
        this.endtime_text = endtime_text;
    }

    public String getIs_pay_text() {
        return is_pay_text;
    }

    public void setIs_pay_text(String is_pay_text) {
        this.is_pay_text = is_pay_text;
    }

    public String getPay_type_text() {
        return pay_type_text;
    }

    public void setPay_type_text(String pay_type_text) {
        this.pay_type_text = pay_type_text;
    }
}
