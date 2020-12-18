package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface CurrentTodayContract {

    interface View extends IBaseDisplay {
        void getCurrentTodayHeader(CurrentTodayHeaderBean data);
        void getCurrentTodayDatas(List<TodayDatasBean> data);
        void deleteTodayDatas(String data);

    }

    interface Model extends IBasePresenter {
        void getCurrentTodayHeader(String date);
        void getCurrentTodayDatas(String date,String page,String pageSize);
        void deleteTodayDatas(int id,String date);

    }
}
