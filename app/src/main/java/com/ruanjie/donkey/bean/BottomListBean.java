package com.ruanjie.donkey.bean;

/**
 * @author by DELL
 * @date on 2018/12/14
 * @describe
 */
public class BottomListBean {
    public String content;
    public int textColor;

    public BottomListBean(String content, int textColor) {
        this.content = content;
        this.textColor = textColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
