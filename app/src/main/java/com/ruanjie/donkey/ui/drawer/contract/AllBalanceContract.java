package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface AllBalanceContract {

    interface View extends IBaseDisplay {
        void getAllBalanceHeader(CurrentTodayHeaderBean data);

    }

    interface Model extends IBasePresenter {
        void getAllBalanceHeader(String date);

    }
}
