package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentTodayFragment;
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
public class CurrentTodayPresenter extends BasePresenter implements CurrentTodayContract.Model {

    @Override
    public void getCurrentTodayHeader(String date) {
        RetrofitClient.getService()
                .getCurrentTodayHeader(date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<CurrentTodayHeaderBean>() {
                    @Override
                    public void onSuccess(CurrentTodayHeaderBean data) {
                        ((CurrentTodayFragment) mView).getCurrentTodayHeader(data);
                    }
                });
    }

    @Override
    public void getCurrentTodayDatas(String date, String page, String pageSize) {
        RetrofitClient.getService()
                .getCurrentTodayDatas(date, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<TodayDatasBean>>() {
                    @Override
                    public void onSuccess(List<TodayDatasBean> data) {
                        ((CurrentTodayFragment) mView).getCurrentTodayDatas(data);
                    }
                });
    }

    @Override
    public void deleteTodayDatas(int id, String date) {
        RetrofitClient.getService()
                .deleteTodayDatas(id, date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((CurrentTodayFragment) mView).deleteTodayDatas(data);
                    }
                });
    }
}
