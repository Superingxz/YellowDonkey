package com.ruanjie.donkey.event;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.event
 * 文件名:   PositionEvent
 * 创建者:    QJM
 * 创建时间: 2019/8/16 18:46
 */
public class PhoneEvent {
    private String phone;
    private String code;

    public PhoneEvent(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
