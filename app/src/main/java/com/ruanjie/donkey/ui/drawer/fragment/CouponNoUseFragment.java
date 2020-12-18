package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MyCouponsAdapter;
import com.ruanjie.donkey.adapter.MyCouponsAdapter2;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.JoinCityResultActivity;
import com.ruanjie.donkey.ui.drawer.contract.CouponNoUseContract;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.presenter.CouponNoUsePresenter;
import com.ruanjie.donkey.ui.drawer.presenter.CurrentMonthPresenter;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.dialog.PromptDialog;

import java.util.List;

import butterknife.BindView;

public class CouponNoUseFragment extends RefreshFragment<CouponNoUsePresenter>
        implements CouponNoUseContract.View {
    MyCouponsAdapter2 mAdapter;

    public static CouponNoUseFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        CouponNoUseFragment fragment = new CouponNoUseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_coupon_no_use;
    }

    @Override
    protected void lazyLoad() {
        getPresenter().getCoupons(0, 1, -1, mPage, PAGE_COUNT);

    }

    @Override
    protected void initEventAndData() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new MyCouponsAdapter2(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvDelete:

                        DiaLogUtils.showTipDialog(getContext(), "温馨提示", "你确定要删除改优惠券么？", "取消", "确认"
                                , new PromptDialog.OnButtonClickListener() {
                                    @Override
                                    public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                                        if (isPositiveClick) {
                                            getPresenter().deleteCoupon(mAdapter.getData().get(position).getCoupon_id());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
    }

    @Override
    public CouponNoUsePresenter createPresenter() {
        return new CouponNoUsePresenter();
    }

    @Override
    public void getCoupons(List<CouponBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }
    }

    @Override
    public void deleteCoupon(String data) {
        onRefresh();
    }
}
