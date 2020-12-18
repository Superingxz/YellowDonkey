package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.ShopAppraiceBean;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail3Contract;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail3Fragment;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class ShopDetail3Presenter extends BasePresenter
        implements ShopDetail3Contract.Model {



    @Override
    public void getAppraiseList(int store_id) {
        RetrofitClient.getService()
                .getAppraiseList(store_id,1,10000)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ShopAppraiceBean>>() {
                    @Override
                    public void onSuccess(List<ShopAppraiceBean> data) {
                        ((ShopDetail3Fragment) mView).getAppraiseList(data);
                    }
                });
    }
}
