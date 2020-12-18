package com.ruanjie.donkey.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class AreaBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private int id;
    private String name;
    private String area_code;
    private int pid;
    private List<AreaBean> _child;


    @Override
    public String getPickerViewText() {
        return this.name;
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

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<AreaBean> get_child() {
        return _child;
    }

    public void set_child(List<AreaBean> _child) {
        this._child = _child;
    }
}
