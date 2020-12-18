package com.ruanjie.donkey.ui.drawer.contract;

import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExTodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

import java.util.List;


public interface ExCurrentTodayContract {

    interface View extends IBaseDisplay {
        void getExCurrentTodayHeader(ExCurrentTodayHeaderBean data);
        void getExCurrentTodayDatas(List<ExTodayDatasBean> data);

    }

    interface Model extends IBasePresenter {
        void getExCurrentTodayHeader(String date);
        void getExCurrentTodayDatas(String date, String page, String pageSize);

    }
}
