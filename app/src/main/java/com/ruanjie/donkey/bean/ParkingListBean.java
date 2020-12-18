package com.ruanjie.donkey.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   ParkingListBean
 * 创建者:    QJM
 * 创建时间: 2019/8/20 1:14
 * 描述:     TODO
 */
public class ParkingListBean implements Serializable {

    /**
     * id : 4
     * title : 汕头
     * createtime : 1564901293
     * lng : 116.67533000
     * lat : 23.35393000
     * points : [{"lng":"116.67536000","lat":"23.35403000"},{"lng":"116.67524000","lat":"23.35390000"},{"lng":"116.67533000","lat":"23.35387000"},{"lng":"116.67539000","lat":"23.35392000"}]
     */

    private int id;
    private String title;
    private int createtime;
    private String lng;
    private String lat;
    private int type;
    private List<PointsBean> points;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
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

    public List<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<PointsBean> points) {
        this.points = points;
    }

    public static class PointsBean {
        /**
         * lng : 116.67536000
         * lat : 23.35403000
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
