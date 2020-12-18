package com.ruanjie.donkey.bean;

/**
 * @author vondear
 */
public class UploadBean {

    private String url;

    @Override
    public String toString() {
        return "UploadBean{" +
                "url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
