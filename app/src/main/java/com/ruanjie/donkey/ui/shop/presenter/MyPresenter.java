package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.contract.MyContract;
import com.ruanjie.donkey.ui.shop.fragment.MyFragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopOrderFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class MyPresenter extends BasePresenter
        implements MyContract.Model {

    @Override
    public void getUserInfo() {
        RetrofitClient.getService()
                .getUserInfo()
                .compose(new NetworkTransformer<>(mView, false))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        ((MyFragment) mView).getUserInfo(data);
                    }
                });
    }

    @Override
    public void getShopOrderList(int type, int status, int page, int pageSize) {
        RetrofitClient.getService()
                .getShopOrderList(type, status, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopOrderBean>>() {
                    @Override
                    public void onSuccess(List<ShopOrderBean> data) {
                        ((MyFragment) mView).getShopOrderList(data);
                    }
                });
    }
}
