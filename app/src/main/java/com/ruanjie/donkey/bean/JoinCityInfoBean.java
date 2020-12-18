package com.ruanjie.donkey.bean;


public class JoinCityInfoBean  {

    /**
     * id : 4
     * user_id : 26
     * name : 陈文超
     * phone : 13544385327
     * num : 999
     * status : 0
     * createtime : 1564976335
     * auth_time : 0
     * status_text : 待审核
     */

    private int id;
    private int user_id;
    private String name;
    private String phone;
    private int num;
    private int status;
    private int createtime;
    private int auth_time;
    private String status_text;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(int auth_time) {
        this.auth_time = auth_time;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
