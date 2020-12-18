package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface MaintainHistoryContract {

    interface View extends IBaseDisplay {
        void getMainHistorys(List<MainTainBean> data);

    }

    interface Model extends IBasePresenter {
        void getMainHistorys(int page, int pageSize);

    }
}
