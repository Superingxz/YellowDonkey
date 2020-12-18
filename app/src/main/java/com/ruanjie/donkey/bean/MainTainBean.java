package com.ruanjie.donkey.bean;


import java.util.List;

public class MainTainBean {


    /**
     * id : 6
     * user_id : 26
     * content : 轮子都没了
     * images : []
     * createtime : 1565682646
     */

    private int id;
    private int user_id;
    private String content;
    private int createtime;
    private List<String> images;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
