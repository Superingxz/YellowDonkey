package com.ruanjie.donkey.ui.billing.contract;

import com.ruanjie.donkey.bean.LockBean;
import com.ruanjie.donkey.bean.ParkingAreaBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.contract
 * 文件名:   UseTimingContract
 * 创建者:    QJM
 * 创建时间: 2019/8/15 2:43
 * 描述:     TODO
 */
public interface UseTimingContract {
    interface View extends IBaseDisplay {
        void lockVehicleSuccess(LockBean data);
        void retainSuccess();
        void pauseSuccess();
        void vehicleDetail(VehicleDetailBean bean);
        void isParkingArea(ParkingAreaBean data,boolean isEnd);
    }

    interface Model extends IBasePresenter {
        void continueUseVehicle();
        void pauseUseVehicle();
        void lockVehicle(String lng,String lat);
        void vehicleDetail(String code);
        void isParkingArea(String lng,String lat,boolean isEnd);
    }
}
