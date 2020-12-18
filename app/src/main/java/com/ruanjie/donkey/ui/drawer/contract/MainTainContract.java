package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.VehicleListBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.drawer.contract
 * 文件名:   MainTainContract
 * 创建者:    QJM
 * 创建时间: 2019/8/15 14:54
 * 描述:     TODO
 */
public interface MainTainContract {

    interface View extends IBaseDisplay {
        void initMap();
        void initLocation();
        void initLocationStyle();
        void initMarker();
        void chargeVehicleList(List<VehicleListBean> beans);
        void illegalParkingVehicleList(List<VehicleListBean> beans);
        void faultVehicleList(List<VehicleListBean> beans);
    }

    interface Model extends IBasePresenter {
        void chargeVehicleList(Double lng,Double lat,int distance);
        void illegalParkingVehicleList(Double lng,Double lat,int distance);
        void faultVehicleList(Double lng,Double lat,int distance);
    }
}
