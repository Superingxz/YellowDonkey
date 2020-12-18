package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.CurrentTodayAdapter;
import com.ruanjie.donkey.adapter.ExCurrentTodayAdapter;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExTodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.ui.drawer.ExChangeHistoryActivity;
import com.ruanjie.donkey.ui.drawer.JoinCityResultActivity;
import com.ruanjie.donkey.ui.drawer.TodayDatasDetailActivity;
import com.ruanjie.donkey.ui.drawer.contract.CurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.contract.ExCurrentTodayContract;
import com.ruanjie.donkey.ui.drawer.presenter.CurrentTodayPresenter;
import com.ruanjie.donkey.ui.drawer.presenter.ExCurrentTodayPresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.ArrayList;
import java.util.List;

public class ExCurrentTodayFragment extends RefreshFragment<ExCurrentTodayPresenter>
        implements ExCurrentTodayContract.View {


    ExCurrentTodayAdapter mAdapter;
    TextView tvCar1;
    TextView tvCar2;
    TextView tvCar3;

    public static ExCurrentTodayFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        ExCurrentTodayFragment fragment = new ExCurrentTodayFragment();
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
            getPresenter().getExCurrentTodayHeader(getDate());
        } else {
            getPresenter().getExCurrentTodayDatas(getDate(), mPage + "", "10");
        }
    }

    @Override
    protected void initEventAndData() {
        initRefreshLayout();
        initRecyclerView();

        mAdapter = new ExCurrentTodayAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        View mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.header_current_today_ex, null);

        tvCar1 = mHeaderView.findViewById(R.id.tvCar1);
        tvCar2 = mHeaderView.findViewById(R.id.tvCar2);
        tvCar3 = mHeaderView.findViewById(R.id.tvCar3);
        mAdapter.addHeaderView(mHeaderView);

    }

    @Override
    public ExCurrentTodayPresenter createPresenter() {
        return new ExCurrentTodayPresenter();
    }

    @Override
    public void getExCurrentTodayHeader(ExCurrentTodayHeaderBean data) {
        tvCar1.setText(data.getTotal() + "");
        tvCar2.setText(data.getFinish() + "");
        tvCar3.setText(data.getCancel() + "");

        mPage = 1;
        getPresenter().getExCurrentTodayDatas(getDate(), mPage + "", "10");
    }

    List<TodayDatasBean> mDatas = new ArrayList<>();

    @Override
    public void getExCurrentTodayDatas(List<ExTodayDatasBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }


    public String getDate() {
        return ((ExChangeHistoryActivity) getActivity()).getDateStr(1);
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
