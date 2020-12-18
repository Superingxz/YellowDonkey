package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface JoinCityContract {

    interface View extends IBaseDisplay {
        void joinCity(String data);

    }

    interface Model extends IBasePresenter {
        void joinCity(String name, String phone, String num);

    }
}
