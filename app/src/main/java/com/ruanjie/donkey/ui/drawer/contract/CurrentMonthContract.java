package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface CurrentMonthContract {

    interface View extends IBaseDisplay {
        void getCurrentMonthHeader(CurrentTodayHeaderBean data);

    }

    interface Model extends IBasePresenter {
        void getCurrentMonthHeader(String date);

    }
}
