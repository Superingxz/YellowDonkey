package com.ruanjie.donkey.ui.demo.presenter;

import android.support.annotation.Nullable;

import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ImageBean;
import com.ruanjie.donkey.ui.demo.DemoActivity;
import com.ruanjie.donkey.ui.demo.contract.DemoContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * @author Moligy
 * @date 2019/7/22.
 */
public class DemoPresenter extends BasePresenter implements DemoContract.Presenter  {
    @Override
    public void getData() {
        RetrofitClient.getTestService()
                .getData()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<List<ImageBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<ImageBean> data) {
                        ((DemoActivity) mView).getData(data);
                    }
                });
    }
}
