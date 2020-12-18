package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.ruanjie.donkey.ui.drawer.MyWalletActivity;
import com.ruanjie.donkey.ui.drawer.WalletDetailActivity;
import com.ruanjie.donkey.ui.drawer.contract.MyWalletContract;
import com.ruanjie.donkey.ui.drawer.contract.WalletDetailContract;
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
public class WalletDetailPresenter extends BasePresenter implements WalletDetailContract.Model {

    @Override
    public void walletDetail(int page, int pageSize) {
        RetrofitClient.getService()
                .walletDetail(page,pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<WalletDetailBean>>() {
                    @Override
                    public void onSuccess(List<WalletDetailBean> data) {
                        ((WalletDetailActivity) mView).walletDetail(data);
                    }
                });
    }
}
