package com.ruanjie.donkey.bean;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   IndexBean
 * 创建者:    QJM
 * 创建时间: 2019/8/10 16:47
 * 描述:     TODO
 */
public class IndexBean  {


    /**
     * is_using : 0
     * using_car :
     * using_car_id : 0
     * using_car_lng :
     * using_car_lat :
     * using_car_enegy : 0
     * using_car_distance : 0
     * using_car_duration : 0
     * is_unpay : 0
     * order_price : 0
     * need_deposit : 0
     * need_recharge : 0
     * min_money : 5
     * is_first : 0
     * is_inside : 1
     * list : [{"id":7,"lng":"116.69372526","lat":"23.36674960","code":"355488020033796","enegy":40,"distance":2121,"status_text":"","is_online_text":"","lock_status_text":""},{"id":13,"lng":"116.70470400","lat":"23.36171700","code":"355488020033824","enegy":90,"distance":915,"status_text":"","is_online_text":"","lock_status_text":""},{"id":21,"lng":"116.71892500","lat":"23.37299800","code":"355488020033724","enegy":90,"distance":1397,"status_text":"","is_online_text":"","lock_status_text":""},{"id":28,"lng":"116.73253400","lat":"23.35958600","code":"355488020033785","enegy":100,"distance":1940,"status_text":"","is_online_text":"","lock_status_text":""},{"id":45,"lng":"116.73082500","lat":"23.37442600","code":"355488020033750","enegy":100,"distance":2275,"status_text":"","is_online_text":"","lock_status_text":""},{"id":46,"lng":"116.68822900","lat":"23.35819100","code":"355488020033708","enegy":70,"distance":2623,"status_text":"","is_online_text":"","lock_status_text":""},{"id":61,"lng":"116.71047800","lat":"23.37073100","code":"355488020033818","enegy":80,"distance":1086,"status_text":"","is_online_text":"","lock_status_text":""},{"id":66,"lng":"116.72215200","lat":"23.37584200","code":"355488020033745","enegy":80,"distance":1825,"status_text":"","is_online_text":"","lock_status_text":""},{"id":74,"lng":"116.71460500","lat":"23.38446400","code":"355488020033696","enegy":90,"distance":2567,"status_text":"","is_online_text":"","lock_status_text":""},{"id":78,"lng":"116.71651200","lat":"23.37409000","code":"355488020033694","enegy":90,"distance":1440,"status_text":"","is_online_text":"","lock_status_text":""},{"id":106,"lng":"116.70705200","lat":"23.35460900","code":"355488020033771","enegy":30,"distance":1015,"status_text":"","is_online_text":"","lock_status_text":""},{"id":111,"lng":"116.72246200","lat":"23.35993000","code":"355488020033801","enegy":60,"distance":915,"status_text":"","is_online_text":"","lock_status_text":""},{"id":114,"lng":"116.71284200","lat":"23.36645400","code":"355488020033689","enegy":90,"distance":566,"status_text":"","is_online_text":"","lock_status_text":""},{"id":118,"lng":"116.70447500","lat":"23.35975400","code":"355488020033723","enegy":80,"distance":956,"status_text":"","is_online_text":"","lock_status_text":""},{"id":122,"lng":"116.73375800","lat":"23.37679000","code":"355488020033703","enegy":50,"distance":2674,"status_text":"","is_online_text":"","lock_status_text":""},{"id":151,"lng":"116.72366800","lat":"23.35684200","code":"355488020033586","enegy":70,"distance":1143,"status_text":"","is_online_text":"","lock_status_text":""}]
     */

    private int is_using;
    private String using_car;
    private int using_car_id;
    private String using_car_lng;
    private String using_car_lat;
    private int using_car_enegy;
    private int using_car_distance;
    private int using_car_duration;
    private int is_unpay;
    private int order_price;
    private int need_deposit;
    private int need_recharge;
    private String min_money;
    private int is_first;
    private int is_inside;
    private List<ListBean> list;

    public int getIs_using() {
        return is_using;
    }

    public void setIs_using(int is_using) {
        this.is_using = is_using;
    }

    public String getUsing_car() {
        return using_car;
    }

    public void setUsing_car(String using_car) {
        this.using_car = using_car;
    }

    public int getUsing_car_id() {
        return using_car_id;
    }

    public void setUsing_car_id(int using_car_id) {
        this.using_car_id = using_car_id;
    }

    public String getUsing_car_lng() {
        return using_car_lng;
    }

    public void setUsing_car_lng(String using_car_lng) {
        this.using_car_lng = using_car_lng;
    }

    public String getUsing_car_lat() {
        return using_car_lat;
    }

    public void setUsing_car_lat(String using_car_lat) {
        this.using_car_lat = using_car_lat;
    }

    public int getUsing_car_enegy() {
        return using_car_enegy;
    }

    public void setUsing_car_enegy(int using_car_enegy) {
        this.using_car_enegy = using_car_enegy;
    }

    public int getUsing_car_distance() {
        return using_car_distance;
    }

    public void setUsing_car_distance(int using_car_distance) {
        this.using_car_distance = using_car_distance;
    }

    public int getUsing_car_duration() {
        return using_car_duration;
    }

    public void setUsing_car_duration(int using_car_duration) {
        this.using_car_duration = using_car_duration;
    }

    public int getIs_unpay() {
        return is_unpay;
    }

    public void setIs_unpay(int is_unpay) {
        this.is_unpay = is_unpay;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public int getNeed_deposit() {
        return need_deposit;
    }

    public void setNeed_deposit(int need_deposit) {
        this.need_deposit = need_deposit;
    }

    public int getNeed_recharge() {
        return need_recharge;
    }

    public void setNeed_recharge(int need_recharge) {
        this.need_recharge = need_recharge;
    }

    public String getMin_money() {
        return min_money;
    }

    public void setMin_money(String min_money) {
        this.min_money = min_money;
    }

    public int getIs_first() {
        return is_first;
    }

    public void setIs_first(int is_first) {
        this.is_first = is_first;
    }

    public int getIs_inside() {
        return is_inside;
    }

    public void setIs_inside(int is_inside) {
        this.is_inside = is_inside;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 7
         * lng : 116.69372526
         * lat : 23.36674960
         * code : 355488020033796
         * enegy : 40
         * distance : 2121
         * status_text :
         * is_online_text :
         * lock_status_text :
         */

        private int id;
        private String lng;
        private String lat;
        private String code;
        private int enegy;
        private int distance;
        private String status_text;
        private String is_online_text;
        private String lock_status_text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getEnegy() {
            return enegy;
        }

        public void setEnegy(int enegy) {
            this.enegy = enegy;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getIs_online_text() {
            return is_online_text;
        }

        public void setIs_online_text(String is_online_text) {
            this.is_online_text = is_online_text;
        }

        public String getLock_status_text() {
            return lock_status_text;
        }

        public void setLock_status_text(String lock_status_text) {
            this.lock_status_text = lock_status_text;
        }
    }
}
