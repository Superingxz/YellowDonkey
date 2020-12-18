package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail1Contract;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail2Contract;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail1Fragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail2Fragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopDetail2Presenter extends BasePresenter
        implements ShopDetail2Contract.Model {

    @Override
    public void getCouponList(int store_id) {
        RetrofitClient.getService()
                .getCouponList(1,store_id, 1, 1000)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopCouponBean>>() {
                    @Override
                    public void onSuccess(List<ShopCouponBean> data) {
                        ((ShopDetail2Fragment) mView).getCouponList(data);
                    }
                });
    }

    @Override
    public void exChangeCoupon(int coupon_id) {
        RetrofitClient.getService()
                .exchangeCoupon(coupon_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<BuyGoodsBean>() {
                    @Override
                    public void onSuccess(BuyGoodsBean data) {
                        ((ShopDetail2Fragment) mView).exChangeCoupon(data);
                    }
                });
    }
}
