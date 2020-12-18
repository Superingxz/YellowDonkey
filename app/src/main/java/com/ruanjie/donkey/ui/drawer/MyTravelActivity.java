package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MyTravelAdapter;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.ruanjie.donkey.ui.drawer.contract.MyTravelContract;
import com.ruanjie.donkey.ui.drawer.presenter.MyTravelPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DecimalUtil;

import java.util.List;

import butterknife.BindView;

public class MyTravelActivity extends RefreshActivity<MyTravelPresenter>
        implements MyTravelContract.View {


    @BindView(R.id.tvAllDistance)
    TextView tvAllDistance;
    @BindView(R.id.tvAllTime)
    TextView tvAllTime;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    MyTravelAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyTravelActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("我的行程")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_my_travel;
    }

    @Override
    protected void initialize() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new MyTravelAdapter(getContext());
        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TravelDetailActivity.start(getContext(), mAdapter.getData().get(position).getId());
            }
        });
        onRefresh();
    }

    @Override
    public MyTravelPresenter createPresenter() {
        return new MyTravelPresenter();
    }

    @Override
    public void getMyTravelStatistics(TravelStatisticsBean data) {
        tvAllDistance.setText(DecimalUtil.formatDecimal2(data.getMileage_total() / 1000) + "");
        tvAllTime.setText(DecimalUtil.formatDecimal2(data.getDuration_total() / 3600) + "");

        getPresenter().getMyTravelDatas(mPage, PAGE_COUNT);
    }

    @Override
    public void getMyTravelDatas(List<TravelDetailBean> data) {
//        if (mPage == 1) {
//            setLoadData(mAdapter, data);
//        } else {
//            setLoadMore(mAdapter, data);
//        }

        setLoadMore(mAdapter, data);

    }

    @Override
    public void loadData() {
        getPresenter().getMyTravelStatistics();
    }

}
