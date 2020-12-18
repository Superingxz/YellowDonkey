package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.ui.drawer.TravelDetailActivity;
import com.ruanjie.donkey.ui.drawer.contract.TravelDetailContract;
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
public class TravelDetailPresenter extends BasePresenter implements TravelDetailContract.Model {

    @Override
    public void getTravelDetail(int id) {
        RetrofitClient.getService()
                .getTravelDetail(id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<TravelDetailBean>() {
                    @Override
                    public void onSuccess(TravelDetailBean data) {
                        ((TravelDetailActivity) mView).getTravelDetail(data);
                    }
                });
    }
}
