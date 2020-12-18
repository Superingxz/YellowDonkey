package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.drawer.ChangePhoneActivity;
import com.ruanjie.donkey.ui.drawer.contract.ChangePhoneContract;
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
public class ChangePhonePresenter extends BasePresenter implements ChangePhoneContract.Model {


    @Override
    public void getCode(String phone, int type) {
        RetrofitClient.getService()
                .getVerificationCodeData(phone,type)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        ((ChangePhoneActivity) mView).getCode("");
                    }
                });
    }

    @Override
    public void changePhone(String new_phone, String new_code) {
        RetrofitClient.getService()
                .changePhone(new_phone,new_code)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ChangePhoneActivity) mView).changePhone(data);
                    }
                });
    }
}
