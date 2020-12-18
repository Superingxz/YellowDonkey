package com.ruanjie.donkey.ui.webView.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.ruanjie.donkey.ui.webView.contract.WebViewContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.webView.presenter
 * 文件名:   WebViewPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/22 18:21
 * 描述:     TODO
 */
public class WebViewPresenter extends BasePresenter implements WebViewContract.Model {

    @Override
    public void getCoupon(int messageId,int couponId) {
        RetrofitClient.getService()
                .getCouponData(messageId,couponId)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@Nullable String data) {
                        ((WebViewActivity)mView).getCouponSuccess();
                    }
                });
    }
}
