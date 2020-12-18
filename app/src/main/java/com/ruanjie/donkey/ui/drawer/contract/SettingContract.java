package com.ruanjie.donkey.ui.drawer.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface SettingContract {

    interface View extends IBaseDisplay {
    }

    interface Model extends IBasePresenter {
            void about();
            void userAgreement();
    }
}
