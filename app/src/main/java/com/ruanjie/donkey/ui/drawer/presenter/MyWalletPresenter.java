package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.MyWalletActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.contract.MyWalletContract;
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
public class MyWalletPresenter extends BasePresenter implements MyWalletContract.Model {


    @Override
    public void getUserInfo() {
        RetrofitClient.getService()
                .getUserInfo()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        ((MyWalletActivity) mView).getUserInfo(data);
                    }
                });
    }

    @Override
    public void getConfigInfo(String name) {
        RetrofitClient.getService()
                .getConfigInfo(name)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<ConfigBean>() {
                    @Override
                    public void onSuccess(ConfigBean data) {
                        ((MyWalletActivity) mView).getConfigInfo(data);
                    }
                });
    }
}
