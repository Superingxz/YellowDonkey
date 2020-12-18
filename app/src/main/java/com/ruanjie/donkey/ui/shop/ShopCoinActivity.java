package com.ruanjie.donkey.ui.shop;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopCoinAdapter;
import com.ruanjie.donkey.adapter.ShopCouponsAdapter;
import com.ruanjie.donkey.bean.ShopCoinBean;
import com.ruanjie.donkey.ui.shop.contract.ShopCoinContract;
import com.ruanjie.donkey.ui.shop.presenter.ShopCoinPresenter;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.base.ToolbarActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCoinActivity extends RefreshActivity<ShopCoinPresenter>
        implements ShopCoinContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    ShopCoinAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ShopCoinActivity.class);
//        starter.putExtra("which", which);
//        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop_coin;
    }

    @Override
    protected void initialize() {
        initRecyclerView();
        initRefreshLayout();

        mAdapter = new ShopCoinAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);


    }

    @OnClick({R.id.ivBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    public void loadData() {
        getPresenter().getCoinList(mPage, PAGE_COUNT);
    }

    @Override
    public ShopCoinPresenter createPresenter() {
        return new ShopCoinPresenter();
    }

    @Override
    public void getCoinList(List<ShopCoinBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);

        }
    }
}
