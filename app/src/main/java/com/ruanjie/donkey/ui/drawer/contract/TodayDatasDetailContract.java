package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface TodayDatasDetailContract {

    interface View extends IBaseDisplay {
        void getTodayDatas(List<TodayDatasDetailBean> data);

    }

    interface Model extends IBasePresenter {
        void getTodayDatas(int id, String date);

    }
}
