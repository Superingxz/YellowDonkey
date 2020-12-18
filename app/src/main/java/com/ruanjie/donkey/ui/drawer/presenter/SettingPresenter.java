package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.AgreementBean;
import com.ruanjie.donkey.ui.drawer.contract.SettingContract;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.drawer.presenter
 * 文件名:   SettingPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/27 15:06
 * 描述:     TODO
 */
public class SettingPresenter extends BasePresenter implements SettingContract.Model {


    private String aboutUrl;

    @Override
    public void about() {
        getParams().put("name","protocol_aboutus");
        RetrofitClient.getService()
                .getAgreementData(getParams())
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<AgreementBean>() {
                    @Override
                    public void onSuccess(AgreementBean data) {
                        if (data != null) {
                            aboutUrl = data.getUrl();
                            WebViewActivity.start(getContext(), "关于我们", aboutUrl);
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
