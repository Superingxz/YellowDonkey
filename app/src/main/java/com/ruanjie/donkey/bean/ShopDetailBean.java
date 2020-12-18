package com.ruanjie.donkey.bean;

import java.io.Serializable;
import java.util.List;

public class ShopDetailBean implements Serializable {


    /**
     * id : 1
     * name : 测试店铺1
     * logo : http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg
     * star : 4.0
     * banner : http://xhlbike.cn/uploads/202004/07/qiBOtWy1j7DsvEkQUTeMhnu9JKRfw5lc.jpg
     * images : ["http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg","http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg","http://xhlbike.cn/uploads/202004/07/nSalXLJYfcDeIgd2zExrtUGsV1KkuW90.jpg"]
     * sales : 6
     * address : 广东省东莞市公园路1号
     * lng : 113.75921000
     * lat : 23.03965000
     * tel : 13800000000
     * intro : 哈哈哈哈哈
     * status_text :
     * goodsList : [{"id":0,"name":"热销","list":[{"id":1,"name":"测试商品1","thumb":"http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","price":"50.00","origin_price":"100.00","score":10,"star":"0.0","stock":94,"sales":5,"is_hot":1,"category_id":1,"category_name":"分类1","is_hot_text":"是","is_show_text":""}]},{"id":1,"name":"分类1","list":[{"id":1,"name":"测试商品1","thumb":"http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","price":"50.00","origin_price":"100.00","score":10,"star":"0.0","stock":94,"sales":5,"is_hot":1,"category_id":1,"category_name":"分类1","is_hot_text":"是","is_show_text":""}]},{"id":2,"name":"分类2","list":[{"id":2,"name":"测试商品2","thumb":"http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","price":"80.00","origin_price":"88.00","score":1,"star":"0.0","stock":78,"sales":1,"is_hot":0,"category_id":2,"category_name":"分类2","is_hot_text":"否","is_show_text":""}]}]
     * commentCount : 1
     */

    private int id;
    private String name;
    private String logo;
    private String star;
    private String banner;
    private int sales;
    private String address;
    private String lng;
    private String lat;
    private String tel;
    private String intro;
    private String status_text;
    private int commentCount;
    private List<String> images;
    private List<GoodsListBean> goodsList;

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

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * id : 0
         * name : 热销
         * list : [{"id":1,"name":"测试商品1","thumb":"http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png","price":"50.00","origin_price":"100.00","score":10,"star":"0.0","stock":94,"sales":5,"is_hot":1,"category_id":1,"category_name":"分类1","is_hot_text":"是","is_show_text":""}]
         */

        private int id;
        private String name;
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * name : 测试商品1
             * thumb : http://xhlbike.cn/uploads/202003/11/CYiMWwGU0XAmIgFLoHzRSu2NPyt6BkZV.png
             * price : 50.00
             * origin_price : 100.00
             * score : 10
             * star : 0.0
             * stock : 94
             * sales : 5
             * is_hot : 1
             * category_id : 1
             * category_name : 分类1
             * is_hot_text : 是
             * is_show_text :
             */

            private int id;
            private String name;
            private String thumb;
            private String price;
            private String origin_price;
            private int score;
            private String star;
            private int stock;
            private int sales;
            private int is_hot;
            private int category_id;
            private String category_name;
            private String is_hot_text;
            private String is_show_text;

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

            public String getOrigin_price() {
                return origin_price;
            }

            public void setOrigin_price(String origin_price) {
                this.origin_price = origin_price;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(int is_hot) {
                this.is_hot = is_hot;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getIs_hot_text() {
                return is_hot_text;
            }

            public void setIs_hot_text(String is_hot_text) {
                this.is_hot_text = is_hot_text;
            }

            public String getIs_show_text() {
                return is_show_text;
            }

            public void setIs_show_text(String is_show_text) {
                this.is_show_text = is_show_text;
            }
        }
    }
}
