package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface WithDrawContract {

    interface View extends IBaseDisplay {
        void withDraw(String data);

    }

    interface Model extends IBasePresenter {
        void withDraw(String money, String bankcard, String bank);

    }
}
