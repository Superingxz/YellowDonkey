package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentMonthFragment;
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
public class CurrentMonthPresenter extends BasePresenter implements CurrentMonthContract.Model {

    @Override
    public void getCurrentMonthHeader(String date) {
        RetrofitClient.getService()
                .getCurrentTodayHeader(date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<CurrentTodayHeaderBean>() {
                    @Override
                    public void onSuccess(CurrentTodayHeaderBean data) {
                        ((CurrentMonthFragment) mView).getCurrentMonthHeader(data);
                    }
                });
    }

}
