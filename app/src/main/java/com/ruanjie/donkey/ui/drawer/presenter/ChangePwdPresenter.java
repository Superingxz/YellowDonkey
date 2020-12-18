package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.drawer.ChangePwdActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangePwdContract;
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
public class ChangePwdPresenter extends BasePresenter implements ChangePwdContract.Model {


    @Override
    public void getCode(String phone, int type) {
        RetrofitClient.getService()
                .getVerificationCodeData(phone,type)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        ((ChangePwdActivity) mView).getCode("");
                    }
                });
    }


}
