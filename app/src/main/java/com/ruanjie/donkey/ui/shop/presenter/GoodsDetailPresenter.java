package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail1Fragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

public class GoodsDetailPresenter extends BasePresenter
        implements GoodsDetailContract.Model {

    @Override
    public void getGoodsDetail(int goods_id) {
        RetrofitClient.getService()
                .getGoodsDetail(goods_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<GoodsDetailBean>() {
                    @Override
                    public void onSuccess(GoodsDetailBean data) {
                        ((GoodsDetailActivity) mView).getGoodsDetail(data);
                    }
                });
    }@Override
    public void buyGoods(int store_id) {
        RetrofitClient.getService()
                .buyGoods(store_id)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<BuyGoodsBean>() {
                    @Override
                    public void onSuccess(BuyGoodsBean data) {
                        ((GoodsDetailActivity) mView).buyGoods(data);
                    }
                });
    }
}
