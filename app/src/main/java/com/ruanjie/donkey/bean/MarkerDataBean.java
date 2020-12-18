package com.ruanjie.donkey.bean;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   MarkerDataBean
 * 创建者:    QJM
 * 创建时间: 2019/9/12 10:46
 * 描述:     TODO
 */
public class MarkerDataBean {
    private IndexBean indexBean;
    private IndexBean.ListBean listBean;

    public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }

    public IndexBean.ListBean getListBean() {
        return listBean;
    }

    public void setListBean(IndexBean.ListBean listBean) {
        this.listBean = listBean;
    }
}
