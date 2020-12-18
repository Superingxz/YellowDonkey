package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.ShopCoinBean;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.ShopCoinActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.contract.ShopCoinContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopCoinPresenter extends BasePresenter
        implements ShopCoinContract.Model {

    @Override
    public void getCoinList(int page, int pageSize) {
        RetrofitClient.getService()
                .getCoinList(page,pageSize)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopCoinBean>>() {
                    @Override
                    public void onSuccess(List<ShopCoinBean> data) {
                        ((ShopCoinActivity) mView).getCoinList(data);
                    }
                });
    }
}
