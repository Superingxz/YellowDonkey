package com.ruanjie.donkey.bean;

import java.util.List;

public class ShopSortRightBean {
    private int parentId;
    private List<ShopSortRightBean2> typeDatas;
    private List<ShopBean> shopDatas;

    public ShopSortRightBean(int parentId, List<ShopSortRightBean2> typeDatas, List<ShopBean> shopDatas) {
        this.parentId = parentId;
        this.typeDatas = typeDatas;
        this.shopDatas = shopDatas;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ShopSortRightBean2> getTypeDatas() {
        return typeDatas;
    }

    public void setTypeDatas(List<ShopSortRightBean2> typeDatas) {
        this.typeDatas = typeDatas;
    }

    public List<ShopBean> getShopDatas() {
        return shopDatas;
    }

    public void setShopDatas(List<ShopBean> shopDatas) {
        this.shopDatas = shopDatas;
    }
}
