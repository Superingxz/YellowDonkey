package com.ruanjie.donkey.bean;

import java.io.Serializable;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.bean
 * 文件名:   LockBean
 * 创建者:    QJM
 * 创建时间: 2019/8/16 2:42
 * 描述:     TODO
 */
public class LockBean implements Serializable {

    /**
     * total_price : 6
     */

    private int total_price;

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
