package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.ShopDetailBean;
import com.ruanjie.donkey.ui.shop.ShopDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail1Contract;
import com.ruanjie.donkey.ui.shop.contract.ShopDetailContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail1Fragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

public class ShopDetail1Presenter extends BasePresenter
        implements ShopDetail1Contract.Model {

    @Override
    public void buyGoods(int store_id) {
        RetrofitClient.getService()
                .buyGoods(store_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<BuyGoodsBean>() {
                    @Override
                    public void onSuccess(BuyGoodsBean data) {
                        ((ShopDetail1Fragment) mView).buyGoods(data);
                    }
                });
    }
}
