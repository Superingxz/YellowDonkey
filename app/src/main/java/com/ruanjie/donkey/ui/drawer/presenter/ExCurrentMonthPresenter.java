package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.contract.ExCurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentMonthFragment;
import com.ruanjie.donkey.ui.drawer.fragment.ExCurrentMonthFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class ExCurrentMonthPresenter extends BasePresenter implements ExCurrentMonthContract.Model {

    @Override
    public void getExCurrentMonthHeader(String date) {
        RetrofitClient.getService()
                .getExCurrentTodayHeader(date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<ExCurrentTodayHeaderBean>() {
                    @Override
                    public void onSuccess(ExCurrentTodayHeaderBean data) {
                        ((ExCurrentMonthFragment) mView).getExCurrentMonthHeader(data);
                    }
                });
    }

}
