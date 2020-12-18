package com.ruanjie.donkey.ui.drawer.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface BackDepositContract {

    interface View extends IBaseDisplay {
        void backDeposit(String data);

    }

    interface Model extends IBasePresenter {
        void backDeposit(String reason);

    }
}
