package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MyCouponsAdapter;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.JoinCityResultActivity;
import com.ruanjie.donkey.ui.drawer.contract.CouponUseContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.presenter.CouponUsePresenter;
import com.ruanjie.donkey.ui.drawer.presenter.CurrentMonthPresenter;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;

import java.util.List;

import butterknife.BindView;

public class CouponUseFragment extends RefreshFragment<CouponUsePresenter>
        implements CouponUseContract.View {
    MyCouponsAdapter mAdapter;

    public static CouponUseFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        CouponUseFragment fragment = new CouponUseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_coupon_use;
    }

    @Override
    protected void lazyLoad() {
        getPresenter().getCoupons(0, 0, -1, mPage, PAGE_COUNT);

    }

    @Override
    protected void initEventAndData() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new MyCouponsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public CouponUsePresenter createPresenter() {
        return new CouponUsePresenter();
    }

    @Override
    public void getCoupons(List<CouponBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }
}
