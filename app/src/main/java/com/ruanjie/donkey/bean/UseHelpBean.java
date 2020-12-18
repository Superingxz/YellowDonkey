package com.ruanjie.donkey.bean;

import java.io.Serializable;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   UseHelpBean
 * 创建者:    QJM
 * 创建时间: 2019/8/13 17:58
 * 描述:     TODO
 */
public class UseHelpBean implements Serializable {


    /**
     * id : 2
     * title : 使用帮助2
     * sort : 0
     * createtime : 1562232085
     * content_url : http://xiaohuanglv.test3.ruanjiekeji.com/api/wap/page/type/help/field/content/id/2.html
     */

    private int id;
    private String title;
    private int sort;
    private int createtime;
    private String content_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }
}
