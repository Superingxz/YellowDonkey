package com.ruanjie.donkey.bean;

import java.util.List;

public class ShopOrderBean {

    /**
     * id : 31
     * store_id : 1
     * order_sn : 20200410142324126796
     * status : 1
     * pay_score : 10
     * total_score : 10
     * type : 1
     * is_comment : 0
     * createtime : 1586499804
     * ticket : SU59TXHL04
     * status_text : 待使用
     * type_text : 兑换商品
     * is_comment_text : 否
     * is_pay_text :
     * pay_time_text :
     * finish_time_text :
     * cancel_time_text :
     * user_deletetime_text :
     * storeInfo : {"id":1,"name":"测试店铺1","logo":"http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg"}
     * goodsList : [{"goods_id":1,"goods_name":"测试商品1","thumb":"http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","price":"50.00","num":1,"score":10,"content":"啊手动阀手动阀","order_id":31}]
     */

    private int id;
    private int store_id;
    private String order_sn;
    private int status;
    private int pay_score;
    private int total_score;
    private int type;
    private int is_comment;
    private int createtime;
    private String ticket;
    private String status_text;
    private String type_text;
    private String is_comment_text;
    private String is_pay_text;
    private String pay_time_text;
    private String finish_time_text;
    private String cancel_time_text;
    private String user_deletetime_text;
    private StoreInfoBean storeInfo;
    private List<GoodsListBean> goodsList;
    private List<CouponListBean> couponList;

    public List<CouponListBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListBean> couponList) {
        this.couponList = couponList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_score() {
        return pay_score;
    }

    public void setPay_score(int pay_score) {
        this.pay_score = pay_score;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getIs_comment_text() {
        return is_comment_text;
    }

    public void setIs_comment_text(String is_comment_text) {
        this.is_comment_text = is_comment_text;
    }

    public String getIs_pay_text() {
        return is_pay_text;
    }

    public void setIs_pay_text(String is_pay_text) {
        this.is_pay_text = is_pay_text;
    }

    public String getPay_time_text() {
        return pay_time_text;
    }

    public void setPay_time_text(String pay_time_text) {
        this.pay_time_text = pay_time_text;
    }

    public String getFinish_time_text() {
        return finish_time_text;
    }

    public void setFinish_time_text(String finish_time_text) {
        this.finish_time_text = finish_time_text;
    }

    public String getCancel_time_text() {
        return cancel_time_text;
    }

    public void setCancel_time_text(String cancel_time_text) {
        this.cancel_time_text = cancel_time_text;
    }

    public String getUser_deletetime_text() {
        return user_deletetime_text;
    }

    public void setUser_deletetime_text(String user_deletetime_text) {
        this.user_deletetime_text = user_deletetime_text;
    }

    public StoreInfoBean getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfoBean storeInfo) {
        this.storeInfo = storeInfo;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class StoreInfoBean {
        /**
         * id : 1
         * name : 测试店铺1
         * logo : http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg
         */

        private int id;
        private String name;
        private String logo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class GoodsListBean {
        /**
         * goods_id : 1
         * goods_name : 测试商品1
         * thumb : http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png
         * price : 50.00
         * num : 1
         * score : 10
         * content : 啊手动阀手动阀
         * order_id : 31
         */

        private int goods_id;
        private String goods_name;
        private String thumb;
        private String price;
        private int num;
        private int score;
        private String content;
        private int order_id;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }
    }
    public static class CouponListBean {

        /**
         * coupon_id : 2
         * title : 测试现金券2
         * money : 80.00
         * num : 1
         * score : 1
         * content : 每次只能用一张
         * order_id : 24
         */

        private int coupon_id;
        private String title;
        private String money;
        private int num;
        private int score;
        private String content;
        private int order_id;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }
    }
}
