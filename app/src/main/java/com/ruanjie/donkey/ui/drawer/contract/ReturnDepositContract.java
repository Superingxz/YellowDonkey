package com.ruanjie.donkey.ui.drawer.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface ReturnDepositContract {

    interface View extends IBaseDisplay {
        void changeUserInfo(String data);

    }

    interface Model extends IBasePresenter {
        void changeUserInfo(int sex, String nickname, String birthday);

    }
}
