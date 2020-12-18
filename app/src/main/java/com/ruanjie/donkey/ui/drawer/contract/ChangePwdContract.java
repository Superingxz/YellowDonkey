package com.ruanjie.donkey.ui.drawer.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface ChangePwdContract {

    interface View extends IBaseDisplay {
        void getCode(String data);

    }

    interface Model extends IBasePresenter {
        void getCode(String phone, int type);
    }
}
