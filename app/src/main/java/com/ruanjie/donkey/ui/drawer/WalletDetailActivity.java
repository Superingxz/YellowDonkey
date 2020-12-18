package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.WalletDetailAdapter;
import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.ruanjie.donkey.ui.drawer.contract.MyWalletContract;
import com.ruanjie.donkey.ui.drawer.contract.WalletDetailContract;
import com.ruanjie.donkey.ui.drawer.presenter.MyWalletPresenter;
import com.ruanjie.donkey.ui.drawer.presenter.WalletDetailPresenter;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletDetailActivity extends RefreshActivity<WalletDetailPresenter>
        implements WalletDetailContract.View {

    WalletDetailAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, WalletDetailActivity.class);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("明细")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    protected void initialize() {
        initRefreshLayout();
        initRecyclerView();

        mAdapter = new WalletDetailAdapter(getContext());
        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WalletDetailBean walletDetailBean = mAdapter.getData().get(position);
                WalletDetailActivity2.start(getContext(), new Gson().toJson(walletDetailBean));
            }
        });
    }

    @Override
    public WalletDetailPresenter createPresenter() {
        return new WalletDetailPresenter();
    }

    @Override
    public void walletDetail(List<WalletDetailBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }

    @Override
    public void loadData() {
        getPresenter().walletDetail(mPage, PAGE_COUNT);

    }
}
