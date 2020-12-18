package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.ruanjie.donkey.ui.drawer.JoinCityActivity;
import com.ruanjie.donkey.ui.drawer.JoinSelectActivity;
import com.ruanjie.donkey.ui.drawer.contract.JoinCityContract;
import com.ruanjie.donkey.ui.drawer.contract.JoinSelectContract;
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
public class JoinSelectPresenter extends BasePresenter implements JoinSelectContract.Model {



    @Override
    public void getJoinCityInfo() {
        RetrofitClient.getService()
                .getJoinCityInfo()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<JoinCityInfoBean>() {
                    @Override
                    public void onSuccess(JoinCityInfoBean data) {
                        ((JoinSelectActivity) mView).getJoinCityInfo(data);
                    }
                });
    }
}
