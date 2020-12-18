package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.JoinAreaInfoBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;


public interface JoinAreaContract {

    interface View extends IBaseDisplay {
        void joinArea(String data);
        void getJoinAreaInfo(JoinAreaInfoBean data);

    }

    interface Model extends IBasePresenter {
        void joinArea(String name, String phone, String area_code, String area_id);
        void getJoinAreaInfo();

    }
}
