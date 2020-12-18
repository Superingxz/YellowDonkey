package com.ruanjie.donkey.ui.sign.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.AliLoginBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.ui.sign.contract.LoginContract;
import com.ruanjie.donkey.ui.sign.model.SignHandler;
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
public class LoginPresenter extends BasePresenter implements LoginContract.Model {


    private final LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
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
    public void getAliLoginData(String pid) {
        RetrofitClient.getService()
                .getAliLoginData(pid)
                .compose(new NetworkTransformer<>(view, true))
                .subscribe(new RxCallback<AliLoginBean>() {
                    @Override
                    public void onSuccess(@Nullable AliLoginBean data) {
                        ((LoginActivity) view).getAliLoginData(data);
                    }
                });
    }

    @Override
    public void alipayLogin(String authCode) {
        RetrofitClient.getService()
                .alipayLogin(authCode)
                .compose(new NetworkTransformer<>(view, true))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(@Nullable LoginBean data) {
                        ((LoginActivity) view).alipayLogin(data);
                    }
                });
    }

    @Override
    public void wechatLogin(String type, String access_token, String openid) {
        RetrofitClient.getService()
                .wechatLogin(type,access_token,openid)
                .compose(new NetworkTransformer<>(view, true))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(@Nullable LoginBean data) {
                        ((LoginActivity) view).wechatLogin(data);
                    }
                });
    }


}
