package com.ruanjie.donkey.bean;

/**
 * @author vondear
 */
public class VersionBean {

    private int id;//": 1,
    private String version;//": "V.1.0",
    private String url;//": "asdsad",
    private String desc;//": "asdasdasd",
    private int createtime;//": 1541671902,
    private int type;//": 1

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", createtime=" + createtime +
                ", type=" + type +
                '}';
    }
}
