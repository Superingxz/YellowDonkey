package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.CurrentTodayAdapter;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.ui.drawer.JoinCityResultActivity;
import com.ruanjie.donkey.ui.drawer.TodayDatasDetailActivity;
import com.ruanjie.donkey.ui.drawer.contract.CurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.presenter.CurrentTodayPresenter;
import com.ruanjie.donkey.ui.sign.LoginActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CurrentTodayFragment extends RefreshFragment<CurrentTodayPresenter>
        implements CurrentTodayContract.View {


    CurrentTodayAdapter mAdapter;
    TextView tvCarNum;
    TextView tvAllDistance;
    TextView tvAllBalance;

    public static CurrentTodayFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        CurrentTodayFragment fragment = new CurrentTodayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_current_today;
    }

    @Override
    protected void lazyLoad() {
        if (mPage == 1) {
            getPresenter().getCurrentTodayHeader(getDate());
        } else {
            getPresenter().getCurrentTodayDatas(getDate(), mPage + "", "10");
        }
    }

    @Override
    protected void initEventAndData() {
        initRefreshLayout();
        initRecyclerView();

        mAdapter = new CurrentTodayAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        View mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_current_today, null);

        tvCarNum = mHeaderView.findViewById(R.id.tvCarNum);
        tvAllDistance = mHeaderView.findViewById(R.id.tvAllDistance);
        tvAllBalance = mHeaderView.findViewById(R.id.tvAllBalance);
        mAdapter.addHeaderView(mHeaderView);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TodayDatasBean todayDatasBean = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tvDetail:
                        TodayDatasDetailActivity.start(getContext(), todayDatasBean.getId(), getDate());
                        break;
                    case R.id.tvDelete:
                        DiaLogUtils.showTipDialog(getContext(), "温馨提示"
                                , "您确定要删除该订单吗？"
                                , "取消"
                                , "确定"
                                , new PromptDialog.OnButtonClickListener() {
                                    @Override
                                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                        if (isPositiveClick) {
                                            getPresenter().deleteTodayDatas(todayDatasBean.getId(), getDate());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
    }

    @Override
    public CurrentTodayPresenter createPresenter() {
        return new CurrentTodayPresenter();
    }

    @Override
    public void getCurrentTodayHeader(CurrentTodayHeaderBean data) {
        tvCarNum.setText(data.getTotal_car() + "");
        tvAllDistance.setText(data.getTotal_mileage() + "");
        tvAllBalance.setText(data.getTotal_money() + "");

        mPage = 1;
        getPresenter().getCurrentTodayDatas(getDate(), mPage + "", "10");
    }

    List<TodayDatasBean> mDatas = new ArrayList<>();

    @Override
    public void getCurrentTodayDatas(List<TodayDatasBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }

    @Override
    public void deleteTodayDatas(String data) {
        mPage = 1;
        lazyLoad();
    }

    public String getDate() {
        return ((JoinCityResultActivity) getActivity()).getDateStr(1);
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_CURRENT_TODAY_DATA:
                mPage = 1;
                lazyLoad();
                break;
        }
    }
}
