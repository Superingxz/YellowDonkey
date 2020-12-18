package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface JoinSelectContract {

    interface View extends IBaseDisplay {
        void getJoinCityInfo(JoinCityInfoBean data);

    }

    interface Model extends IBasePresenter {
        void getJoinCityInfo();

    }
}
