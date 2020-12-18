package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.contract.ShopCouponContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopCouponFragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopOrderFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopCouponPresenter extends BasePresenter
        implements ShopCouponContract.Model {

    @Override
    public void getCouponList(int type,int store_id, int page, int pageSize) {
        RetrofitClient.getService()
                .getShopOrderList(type,store_id, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopOrderBean>>() {
                    @Override
                    public void onSuccess(List<ShopOrderBean> data) {
                        ((ShopCouponFragment) mView).getCouponList(data);
                    }
                });
    }

    @Override
    public void submitOrder(int order_id) {
        RetrofitClient.getService()
                .submitOrder(order_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ShopCouponFragment) mView).submitOrder(data);
                    }
                });
    }

    @Override
    public void deleteOrder(int order_id) {
        RetrofitClient.getService()
                .deleteOrder(order_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((ShopCouponFragment) mView).deleteOrder(data);
                    }
                });
    }
}
