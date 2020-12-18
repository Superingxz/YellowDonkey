package com.ruanjie.donkey.ui.billing.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.billing.contract.PayArrearsContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing.presenter
 * 文件名:   PayArrearsPresenter
 * 创建者:    QJM
 * 创建时间: 2019/8/16 14:37
 * 描述:     TODO
 */
public class PayArrearsPresenter extends BasePresenter implements PayArrearsContract.Model {

    private final PayArrearsContract.View view;

    public PayArrearsPresenter(PayArrearsContract.View view) {
        this.view = view;
    }

    @Override
    public void payArrears(int payType,int couponId) {
        RetrofitClient.getService()
                .getPayOrderData(payType,couponId)
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<RechargeBean>() {
                    @Override
                    public void onSuccess(RechargeBean data) {
                        view.payArrears(data);
                    }
                });
    }

    @Override
    public void getCoupons() {
        getParams().put("scope",1);
        RetrofitClient.getService()
                .getCoupons(getParams())
                .compose(new NetworkTransformer<>(view))
                .subscribe(new RxCallback<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> data) {
                        view.getCoupons(data);
                    }
                });
    }
}
