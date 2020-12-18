package com.ruanjie.donkey.bean;

import java.io.Serializable;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   NotifyMessageBean
 * 创建者:    QJM
 * 创建时间: 2019/8/13 11:13
 * 描述:     TODO
 */
public class NotifyMessageBean implements Serializable {


        /**
         * id : 11
         * title : 11
         * intro : 222
         * content : <p>222</p>
         * createtime : 1565434254
         * is_all : 1
         * type : 2
         * deletetime : null
         * coupon_id : 11
         * status : 0
         * coupon_title : 123
         * type_text : 活动公告
         * is_all_text : 是
         * content_url : http://xiaohuanglv.test3.ruanjiekeji.com/api/wap/page/id/11/field/content/type/message.html
         */

        private int id;
        private String title;
        private String intro;
        private String content;
        private int createtime;
        private int is_all;
        private int type;
        private Object deletetime;
        private int coupon_id;
        private int status;
        private String coupon_title;
        private String type_text;
        private String is_all_text;
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getIs_all() {
            return is_all;
        }

        public void setIs_all(int is_all) {
            this.is_all = is_all;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(Object deletetime) {
            this.deletetime = deletetime;
        }

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoupon_title() {
            return coupon_title;
        }

        public void setCoupon_title(String coupon_title) {
            this.coupon_title = coupon_title;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getIs_all_text() {
            return is_all_text;
        }

        public void setIs_all_text(String is_all_text) {
            this.is_all_text = is_all_text;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

}
