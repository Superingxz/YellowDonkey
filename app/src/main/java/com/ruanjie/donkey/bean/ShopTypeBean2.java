package com.ruanjie.donkey.bean;

public class ShopTypeBean2 {

    private String typeName;
    private int typePosi;

    public ShopTypeBean2(String typeName, int typePosi) {
        this.typeName = typeName;
        this.typePosi = typePosi;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypePosi() {
        return typePosi;
    }

    public void setTypePosi(int typePosi) {
        this.typePosi = typePosi;
    }
}
