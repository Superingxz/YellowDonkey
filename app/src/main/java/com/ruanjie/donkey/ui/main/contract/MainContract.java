package com.ruanjie.donkey.ui.main.contract;

import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.main.contract
 * 文件名:   ShopContract
 * 创建者:    QJM
 * 创建时间: 2019/8/13 10:15
 * 描述:     TODO
 */
public interface MainContract {

    interface View extends IBaseDisplay {
       void initNotifyMessage(List<NotifyMessageBean> beans);
       void initIndex(IndexBean bean);
       void initParkingList(List<ParkingListBean> beans);
       void initFenceList(List<FenceListBean> beans);
       void initBitmap();
       void initMap();
       void initLocation();
//       void initLocationStyle();
       void initMyLocationMarker();
       void initMarker();
       void isShowPrice();
       void vehicleDetail(VehicleDetailBean bean);
       void getUnReadCount(UnReadBean data);

    }

    interface Model extends IBasePresenter {
          void notifyMessage();
          void index(String lng,String lat,int distance);
          void parikingList(Double lng,Double lat,int distance);
          void fenceList();
          void showPrice();
        void vehicleDetail(String code);
        void getUnReadCount();
    }
}
