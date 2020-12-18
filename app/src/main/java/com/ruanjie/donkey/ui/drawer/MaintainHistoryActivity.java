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
import com.ruanjie.donkey.adapter.MainTainHistoryAdapter;
import com.ruanjie.donkey.adapter.MyTravelAdapter;
import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.ruanjie.donkey.ui.drawer.contract.MaintainHistoryContract;
import com.ruanjie.donkey.ui.drawer.contract.MyTravelContract;
import com.ruanjie.donkey.ui.drawer.presenter.MaintainHistoryPresenter;
import com.ruanjie.donkey.ui.drawer.presenter.MyTravelPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DecimalUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class MaintainHistoryActivity extends RefreshActivity<MaintainHistoryPresenter>
        implements MaintainHistoryContract.View {


    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    MainTainHistoryAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MaintainHistoryActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("历史维护")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_maintain_history;
    }

    @Override
    protected void initialize() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new MainTainHistoryAdapter(getContext());
        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivImage1:
                        ShowBigImageActivity.start(getContext(),mAdapter.getData().get(position).getImages().get(0),0);
                        break;
                    case R.id.ivImage2:
                        ShowBigImageActivity.start(getContext(),mAdapter.getData().get(position).getImages().get(1),0);

                        break;
                    case R.id.ivImage3:
                        ShowBigImageActivity.start(getContext(),mAdapter.getData().get(position).getImages().get(2),0);

                        break;
                }
            }
        });
    }


    @Override
    public void getMainHistorys(List<MainTainBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);

        }
    }

    @Override
    public MaintainHistoryPresenter createPresenter() {
        return new MaintainHistoryPresenter();
    }

    @Override
    public void loadData() {
        getPresenter().getMainHistorys(mPage, PAGE_COUNT);
    }
}
