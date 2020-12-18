package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.TravelDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface TravelDetailContract {

    interface View extends IBaseDisplay {
        void getTravelDetail(TravelDetailBean data);
        void initMap();
        void initMarker();
    }

    interface Model extends IBasePresenter {
        void getTravelDetail(int id);

    }
}
