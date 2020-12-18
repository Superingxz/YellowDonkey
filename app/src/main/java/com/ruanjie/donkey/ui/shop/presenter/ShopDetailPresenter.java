package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ShopDetailBean;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.ui.shop.ShopDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopContract;
import com.ruanjie.donkey.ui.shop.contract.ShopDetailContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopFragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopDetailPresenter extends BasePresenter
        implements ShopDetailContract.Model {

    @Override
    public void getShopDetail(int store_id) {
        RetrofitClient.getService()
                .getShopDetail(store_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<ShopDetailBean>() {
                    @Override
                    public void onSuccess(ShopDetailBean data) {
                        ((ShopDetailActivity) mView).getShopDetail(data);
                    }
                });
    }
}
