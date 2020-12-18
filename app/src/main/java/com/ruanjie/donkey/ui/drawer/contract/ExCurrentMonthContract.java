package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface ExCurrentMonthContract {

    interface View extends IBaseDisplay {
        void getExCurrentMonthHeader(ExCurrentTodayHeaderBean data);

    }

    interface Model extends IBasePresenter {
        void getExCurrentMonthHeader(String date);

    }
}
