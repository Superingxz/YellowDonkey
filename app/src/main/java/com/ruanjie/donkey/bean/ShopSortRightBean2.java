package com.ruanjie.donkey.bean;

public class ShopSortRightBean2 {
    private int parentId;
    private int id;
    private String name;

    public ShopSortRightBean2(int parentId, int id, String name) {
        this.parentId = parentId;
        this.id = id;
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

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
}
