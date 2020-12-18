package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.drawer.BackDepositActivity;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.contract.BackDepositContract;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
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
public class BackDepositPresenter extends BasePresenter implements BackDepositContract.Model {



    @Override
    public void backDeposit(String reason) {
        RetrofitClient.getService()
                .backDeposit(reason)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((BackDepositActivity) mView).backDeposit(data);
                    }
                });
    }
}
