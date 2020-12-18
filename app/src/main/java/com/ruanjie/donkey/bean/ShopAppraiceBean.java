package com.ruanjie.donkey.bean;

import java.util.List;

public class ShopAppraiceBean {


    /**
     * id : 1
     * star : 4.0
     * content : 哈哈哈，味道不错
     * images : ["http://xhlbike.cn/uploads/202003/11/0ff23203175db331f1acc9ea93c8fdff.png","http://xhlbike.cn/uploads/202003/11/1b920b60225afd78489e588aef55394d.png"]
     * createtime : 1583894507
     * user_id : 1
     * nickname : xj-康
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erGyBpg0QLtyeth0tMeTFkJmAMf6NqGsM3r58x5HuSOujp5cfHB23eoCPuOfEJPNbAbmDMBicHrCuQ/132
     * is_anonymous : 0
     * reply_content :
     * reply_time : 0
     * is_anonymous_text : 否
     * reply_time_text : 1970-01-01 08:00:00
     */

    private int id;
    private String star;
    private String content;
    private int createtime;
    private int user_id;
    private String nickname;
    private String avatar;
    private int is_anonymous;
    private String reply_content;
    private int reply_time;
    private String is_anonymous_text;
    private String reply_time_text;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(int is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getReply_time() {
        return reply_time;
    }

    public void setReply_time(int reply_time) {
        this.reply_time = reply_time;
    }

    public String getIs_anonymous_text() {
        return is_anonymous_text;
    }

    public void setIs_anonymous_text(String is_anonymous_text) {
        this.is_anonymous_text = is_anonymous_text;
    }

    public String getReply_time_text() {
        return reply_time_text;
    }

    public void setReply_time_text(String reply_time_text) {
        this.reply_time_text = reply_time_text;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
