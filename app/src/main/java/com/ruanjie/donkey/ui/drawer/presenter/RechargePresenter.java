package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.ui.drawer.RechargeActivity;
import com.ruanjie.donkey.ui.drawer.contract.AllBalanceContract;
import com.ruanjie.donkey.ui.drawer.contract.RechargeContract;
import com.ruanjie.donkey.ui.drawer.fragment.AllBalanceFragment;
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
public class RechargePresenter extends BasePresenter implements RechargeContract.Model {

    @Override
    public void recharge(String money, int pay_type, int coupon_id) {
        RetrofitClient.getService()
                .recharge(money,pay_type,coupon_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<RechargeBean>() {
                    @Override
                    public void onSuccess(RechargeBean data) {
                        ((RechargeActivity) mView).recharge(data);
                    }
                });
    }

    @Override
    public void getCoupons(int type, int tab_type, int status,int page,int pageSize) {
        RetrofitClient.getService()
                .getCoupons(type,tab_type,status,page,pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> data) {
                        ((RechargeActivity) mView).getCoupons(data);
                    }
                });
    }
}
