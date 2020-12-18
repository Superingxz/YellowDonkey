package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.VehicleListBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface ExChangeContract {

    interface View extends IBaseDisplay {
        void getExChangeTask(String data);
        void initMap();
//        void initLocation();
//        void initLocationStyle();
        void initMarker();
        void initExChangeList(List<VehicleListBean> beans);
    }

    interface Model extends IBasePresenter {
        void getExChangeTask(String code);
        void exchangeList(Double lng, Double lat, int distance);
    }
}
