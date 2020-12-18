package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.TodayDatasDetailAdapter;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.ruanjie.donkey.ui.drawer.contract.TodayDatasDetailContract;
import com.ruanjie.donkey.ui.drawer.presenter.TodayDatasDetailPresenter;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.List;

public class TodayDatasDetailActivity extends RefreshActivity<TodayDatasDetailPresenter>
        implements TodayDatasDetailContract.View {

    int mId;
    String mDateStr;
    TodayDatasDetailAdapter mAdapter;

    public static void start(Context context, int id, String dateStr) {
        Intent starter = new Intent(context, TodayDatasDetailActivity.class);
        starter.putExtra("id", id);
        starter.putExtra("dateStr", dateStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("订单详情")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_today_datas_detail;
    }

    @Override
    protected void initialize() {
        mId = getIntent().getIntExtra("id", 0);
        mDateStr = getIntent().getStringExtra("dateStr");

        initRecyclerView();
        mAdapter = new TodayDatasDetailAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void loadData() {
        getPresenter().getTodayDatas(mId, mDateStr);
    }

    @Override
    public TodayDatasDetailPresenter createPresenter() {
        return new TodayDatasDetailPresenter();
    }

    @Override
    public void getTodayDatas(List<TodayDatasDetailBean> data) {
        mAdapter.setNewData(data);
    }
}
