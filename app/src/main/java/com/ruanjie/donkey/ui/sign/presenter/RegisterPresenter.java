package com.ruanjie.donkey.ui.sign.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.AgreementBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.RegisterBean;
import com.ruanjie.donkey.ui.sign.contract.RegisterContract;
import com.ruanjie.donkey.ui.sign.model.SignHandler;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.WeakHashMap;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   RegisterPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/30 10:55
 * 描述:     TODO
 */
public class RegisterPresenter extends BasePresenter implements RegisterContract.Model {


    private final RegisterContract.View view;

    private final WeakHashMap<String,Object> PARAMS = getParams();

    public RegisterPresenter(RegisterContract.View view) {
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

    @Override
    public void register(String phone, String password, String repassword, String code,String name,String id) {
        PARAMS.put("phone",phone);
        PARAMS.put("password",password);
        PARAMS.put("repassword",repassword);
        PARAMS.put("code",code);
        PARAMS.put("username",name);
        PARAMS.put("idcard",id);
        RetrofitClient.getService()
                .getRegisterData(PARAMS)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<RegisterBean>() {
                    @Override
                    public void onSuccess(@Nullable RegisterBean data) {
                        if (data != null) {
                            SignHandler.register(data, view,phone,repassword);
                        }
                        }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    @Override
    public void login(String phone, String password) {
        RetrofitClient.getService()
                .getLoginData(phone, password)
                .compose(new NetworkTransformer<>(view, true))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(@Nullable LoginBean data) {
                        if (data != null) {
                            SignHandler.login(data, view);
                        }
                    }


                });
    }

    @Override
    public void userAgreement() {
        getParams().put("name","protocol_user");
        RetrofitClient.getService()
                .getAgreementData(getParams())
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<AgreementBean>() {
                    @Override
                    public void onSuccess(AgreementBean data) {
                        if (data != null) {
                            WebViewActivity.start(getContext(), "用户协议", data.getUrl());
                        }
                    }
                });
    }

}
