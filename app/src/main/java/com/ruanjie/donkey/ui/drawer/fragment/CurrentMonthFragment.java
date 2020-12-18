package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.JoinCityResultActivity;
import com.ruanjie.donkey.ui.drawer.contract.CurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.presenter.CurrentMonthPresenter;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;

import butterknife.BindView;

public class CurrentMonthFragment extends RefreshFragment<CurrentMonthPresenter>
        implements CurrentMonthContract.View {


    @BindView(R.id.tvAllMoney)
    TextView tvAllMoney;
    @BindView(R.id.tvCarCount)
    TextView tvCarCount;
    @BindView(R.id.tvAllDistance)
    TextView tvAllDistance;
    @BindView(R.id.tvCouponMoney)
    TextView tvCouponMoney;

    public static CurrentMonthFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        CurrentMonthFragment fragment = new CurrentMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_current_month;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initEventAndData() {
        getPresenter().getCurrentMonthHeader(getDate());

    }

    public String getDate() {
        return ((JoinCityResultActivity) getActivity()).getDateStr(2);
    }

    @Override
    public CurrentMonthPresenter createPresenter() {
        return new CurrentMonthPresenter();
    }

    @Override
    public void getCurrentMonthHeader(CurrentTodayHeaderBean data) {
        tvCarCount.setText(data.getTotal_car() + "");
        tvAllDistance.setText(data.getTotal_mileage() + "");
        tvAllMoney.setText(data.getTotal_money() + "");
        tvCouponMoney.setText(data.getTotal_coupon() + "");
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_CURRENT_MONTH_DATA:
                getPresenter().getCurrentMonthHeader(getDate());
                break;
        }
    }
}
