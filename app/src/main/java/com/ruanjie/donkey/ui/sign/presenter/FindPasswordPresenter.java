package com.ruanjie.donkey.ui.sign.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.sign.contract.FindPasswordContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.WeakHashMap;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   FindPasswordPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/31 14:41
 * 描述:     TODO
 */
public class FindPasswordPresenter extends BasePresenter implements FindPasswordContract.Model {


    private final FindPasswordContract.View view;

    private final WeakHashMap<String,Object> PARAMS = getParams();

    public FindPasswordPresenter(FindPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void getVerificationCode(String phone, int type) {
        RetrofitClient.getService()
                .getVerificationCodeData(phone,type)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.getVerificationCodeSuccess();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        view.getVerificationCodeFail(t.getMessage());
                    }
                });
    }

    @Override
    public void checkVerificationCode(String phone, String verificationCode, int type) {

        PARAMS.put("phone",phone);
        PARAMS.put("code",verificationCode);
        PARAMS.put("type",type);

        RetrofitClient.getService()
                .checkVerificationCodeData(PARAMS)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        view.verificationSuccess(phone,verificationCode);
                    }
                });

    }
}
