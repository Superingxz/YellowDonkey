package com.ruanjie.donkey.bean;

import java.util.List;

public class ShopSortBean {


    /**
     * id : 3
     * pid : 0
     * name : 美食
     * image : http://xhlbike.cn/uploads/202003/11/mV085crfzJGvi14Q3YowMRlNDqxHuWsZ.jpg
     * createtime : 1583316787
     * _child : [{"id":5,"pid":3,"name":"西餐","image":"","createtime":1584432415},{"id":6,"pid":3,"name":"中餐","image":"","createtime":1584432423},{"id":7,"pid":3,"name":"火锅","image":"","createtime":1584432432},{"id":8,"pid":3,"name":"自助餐","image":"","createtime":1584432439}]
     */

    private int id;
    private int pid;
    private String name;
    private String image;
    private int createtime;
    private List<ChildBean> _child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public List<ChildBean> get_child() {
        return _child;
    }

    public void set_child(List<ChildBean> _child) {
        this._child = _child;
    }

    public static class ChildBean {
        /**
         * id : 5
         * pid : 3
         * name : 西餐
         * image :
         * createtime : 1584432415
         */

        private int id;
        private int pid;
        private String name;
        private String image;
        private int createtime;

        public ChildBean(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }
    }
}
