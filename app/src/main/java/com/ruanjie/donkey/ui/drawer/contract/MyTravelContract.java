package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface MyTravelContract {

    interface View extends IBaseDisplay {
        void getMyTravelStatistics(TravelStatisticsBean data);
        void getMyTravelDatas(List<TravelDetailBean> data);

    }

    interface Model extends IBasePresenter {
        void getMyTravelStatistics();
        void getMyTravelDatas(int page, int pageSize);

    }
}
