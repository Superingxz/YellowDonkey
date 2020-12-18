package com.ruanjie.donkey.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.bean.ImageBean;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

/**
 * @author by DELL
 * @date on 2018/2/24
 * @describe
 */

public class TestFragment extends BaseLazyFragment {

    public static TestFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected Object getLayoutId() {
        return R.layout.activity_test_toolbar;
    }


    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoad() {
//        loadData();

    }

    private void loadData() {
        RetrofitClient.getTestService()
                .getData()
                .compose(new NetworkTransformer<>(this))
                .subscribe(new RxCallback<List<ImageBean>>() {
                    @Override
                    public void onSuccess(@Nullable List<ImageBean> data) {
                    }
                });
    }


}
