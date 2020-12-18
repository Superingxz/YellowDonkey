package com.ruanjie.donkey.ui.testRefresh;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ImageBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.base.SelectedAdapter;
import com.softgarden.baselibrary.network.RxCallback;

import java.util.List;

public class TestRefreshActivity extends RefreshActivity<TestRefreshPresenter> {
    private SelectedAdapter<ImageBean> mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, TestRefreshActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_test_refresh;
    }

    @Override
    protected void initialize() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new SelectedAdapter<ImageBean>(R.layout.item_images) {
            @Override
            public void onBindVH(BaseRVHolder holder, ImageBean data, int position) {
                ImageUtil.load(holder.getView(R.id.ivImage),data.getUrl());
            }
        };
        mAdapter.setSelectMode(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("上拉刷新，下拉加载");
    }

    public void loadData() {
        getPresenter().getData2(mPage, PAGE_COUNT).subscribe(new RxCallback<List<ImageBean>>() {
            @Override
            public void onSuccess(@Nullable List<ImageBean> data) {
                setLoadMore(mAdapter, data);
            }
        });

    }


}
