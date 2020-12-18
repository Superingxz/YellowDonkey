package com.ruanjie.donkey.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   FenceListBean
 * 创建者:    QJM
 * 创建时间: 2019/8/20 2:19
 * 描述:     TODO
 */
public class FenceListBean implements Serializable {


    /**
     * id : 1
     * title : 广州
     * lng : 113.32063500
     * lat : 23.13063800
     * createtime : 1564997032
     * points : [{"lng":"113.13676000","lat":"23.27163000"},{"lng":"113.49656000","lat":"23.27037000"},{"lng":"113.48810000","lat":"23.04183000"},{"lng":"113.21482000","lat":"23.02160000"},{"lng":"113.07337000","lat":"23.13531000"}]
     */

    private int id;
    private String title;
    private String lng;
    private String lat;
    private int createtime;
    private List<PointsBean> points;

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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public List<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<PointsBean> points) {
        this.points = points;
    }

    public static class PointsBean {
        /**
         * lng : 113.13676000
         * lat : 23.27163000
         */

        private String lng;
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
