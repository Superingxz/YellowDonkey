package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.RechargeActivity;
import com.ruanjie.donkey.ui.drawer.contract.CouponUseContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.fragment.CouponUseFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentMonthFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class CouponUsePresenter extends BasePresenter implements CouponUseContract.Model {


    @Override
    public void getCoupons(int type, int tab_type, int status, int page, int pageSize) {
        RetrofitClient.getService()
                .getCoupons(type, tab_type, status, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> data) {
                        ((CouponUseFragment) mView).getCoupons(data);
                    }
                });
    }

}
