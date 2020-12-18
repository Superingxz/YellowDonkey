package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.contract.CouponNoUseContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.fragment.CouponNoUseFragment;
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
public class CouponNoUsePresenter extends BasePresenter implements CouponNoUseContract.Model {

    @Override
    public void getCoupons(int type, int tab_type, int status,int page,int pageSize) {
        RetrofitClient.getService()
                .getCoupons(type,tab_type,status,page,pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> data) {
                        ((CouponNoUseFragment) mView).getCoupons(data);
                    }
                });
    }

    @Override
    public void deleteCoupon(int coupon_id) {
        RetrofitClient.getService()
                .deleteCoupon(coupon_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((CouponNoUseFragment) mView).deleteCoupon(data);
                    }
                });
    }

}
