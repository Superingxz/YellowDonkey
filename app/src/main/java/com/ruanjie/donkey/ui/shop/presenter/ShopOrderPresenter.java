package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.ui.shop.contract.MyContract;
import com.ruanjie.donkey.ui.shop.contract.ShopOrderContract;
import com.ruanjie.donkey.ui.shop.fragment.MyFragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopOrderFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopOrderPresenter extends BasePresenter
        implements ShopOrderContract.Model {


    @Override
    public void getShopOrderList(int type, int status, int page, int pageSize) {
        RetrofitClient.getService()
                .getShopOrderList(type,status, page, pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopOrderBean>>() {
                    @Override
                    public void onSuccess(List<ShopOrderBean> data) {
                        ((ShopOrderFragment) mView).getShopOrderList(data);
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
                        ((ShopOrderFragment) mView).submitOrder(data);
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
                        ((ShopOrderFragment) mView).deleteOrder(data);
                    }
                });
    }
}
