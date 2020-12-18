package com.ruanjie.donkey.ui.billing.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.AgreementBean;
import com.ruanjie.donkey.ui.billing.contract.BillingModeContract;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.presenter
 * 文件名:   BillingModePresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/28 11:23
 * 描述:     TODO
 */
public class BillingModePresenter extends BasePresenter implements BillingModeContract.Model {

    @Override
    public void vehicleAgreement() {
        getParams().put("name","protocol_car");
        RetrofitClient.getService()
                .getAgreementData(getParams())
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<AgreementBean>() {
                    @Override
                    public void onSuccess(AgreementBean data) {
                        if (data != null) {
                            WebViewActivity.start(getContext(), "用车协议", data.getUrl());
                        }
                    }
                });
    }
}
