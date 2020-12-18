package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExTodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.ui.drawer.contract.CurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.contract.ExCurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentTodayFragment;
import com.ruanjie.donkey.ui.drawer.fragment.ExCurrentTodayFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class ExCurrentTodayPresenter extends BasePresenter implements ExCurrentTodayContract.Model {

    @Override
    public void getExCurrentTodayHeader(String date) {
        RetrofitClient.getService()
                .getExCurrentTodayHeader(date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<ExCurrentTodayHeaderBean>() {
                    @Override
                    public void onSuccess(ExCurrentTodayHeaderBean data) {
                        ((ExCurrentTodayFragment) mView).getExCurrentTodayHeader(data);
                    }
                });
    }

    @Override
    public void getExCurrentTodayDatas(String date, String page, String pageSize) {
        RetrofitClient.getService()
                .getExCurrentTodayDatas(date, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ExTodayDatasBean>>() {
                    @Override
                    public void onSuccess(List<ExTodayDatasBean> data) {
                        ((ExCurrentTodayFragment) mView).getExCurrentTodayDatas(data);
                    }
                });
    }


}
