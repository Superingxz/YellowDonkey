package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ConfigBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface MyWalletContract {

    interface View extends IBaseDisplay {
        void getUserInfo(LoginBean data);
        void getConfigInfo(ConfigBean data);

    }

    interface Model extends IBasePresenter {
        void getUserInfo();
        void getConfigInfo(String name);

    }
}
