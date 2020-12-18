package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.TodayDatasDetailActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.TodayDatasDetailContract;
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
public class TodayDatasDetailPresenter extends BasePresenter
        implements TodayDatasDetailContract.Model {


    @Override
    public void getTodayDatas(int id, String date) {
        RetrofitClient.getService()
                .getTodayDatas(id,date)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<TodayDatasDetailBean>>() {
                    @Override
                    public void onSuccess(List<TodayDatasDetailBean> data) {
                        ((TodayDatasDetailActivity) mView).getTodayDatas(data);
                    }
                });
    }
}
