package com.ruanjie.donkey.ui.shop.presenter;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.BuyGoodsBean;
import com.ruanjie.donkey.bean.GoodsDetailBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.bean.UploadBean2;
import com.ruanjie.donkey.ui.drawer.UserInfoActivity;
import com.ruanjie.donkey.ui.shop.GoodsDetailActivity;
import com.ruanjie.donkey.ui.shop.ShopAppraiceActivity;
import com.ruanjie.donkey.ui.shop.contract.GoodsDetailContract;
import com.ruanjie.donkey.ui.shop.contract.ShopAppraiceContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

public class ShopAppraicePresenter extends BasePresenter
        implements ShopAppraiceContract.Model {


    @Override
    public void uploadImage2(String content) {
        RetrofitClient.getService()
                .uploadImage2(content,1)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<UploadBean2>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable UploadBean2 data) {
                        ((ShopAppraiceActivity) mView).uploadImage2(data);
                    }
                });
    }

    @Override
    public void appraiceGoods(int order_id, int star, String content, int is_anonymous, String images) {
        RetrofitClient.getService()
                .appraiceGoods(order_id, star, content, is_anonymous, images)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(@android.support.annotation.Nullable String data) {
                        ((ShopAppraiceActivity) mView).appraiceGoods(data);
                    }
                });
    }
}
