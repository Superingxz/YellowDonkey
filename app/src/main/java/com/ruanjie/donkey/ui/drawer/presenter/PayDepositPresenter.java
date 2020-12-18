package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.PriceBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.drawer.ChangeNicknameActivity;
import com.ruanjie.donkey.ui.drawer.PayDepositActivity;
import com.ruanjie.donkey.ui.drawer.contract.PayDepositContract;
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
public class PayDepositPresenter extends BasePresenter implements PayDepositContract.Model {


    @Override
    public void payDeposit(int pay_type) {
        RetrofitClient.getService()
                .payDeposit(pay_type)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<RechargeBean>() {
                    @Override
                    public void onSuccess(RechargeBean data) {
                        ((PayDepositActivity) mView).payDeposit(data);
                    }
                });
    }

    public void payPrice(String type) {
        RetrofitClient.getService()
                .getPrice(type)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<PriceBean>() {
                    @Override
                    public void onSuccess(PriceBean data) {
                        ((PayDepositActivity) mView).payPrice(data);
                    }
                });
    }
}
