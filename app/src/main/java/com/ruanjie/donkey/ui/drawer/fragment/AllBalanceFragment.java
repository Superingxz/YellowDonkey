package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.contract.AllBalanceContract;
import com.ruanjie.donkey.ui.drawer.presenter.AllBalancePresenter;
import com.softgarden.baselibrary.base.RefreshFragment;

import butterknife.BindView;

public class AllBalanceFragment extends RefreshFragment<AllBalancePresenter>
        implements AllBalanceContract.View {


    @BindView(R.id.tvAllMoney)
    TextView tvAllMoney;
    @BindView(R.id.tvTotalBonus)
    TextView tvTotalBonus;
    /*@BindView(R.id.tvGetMoney)
    BLTextView tvGetMoney;*/
    @BindView(R.id.tvCarCount)
    TextView tvCarCount;
    @BindView(R.id.tvAllDistance)
    TextView tvAllDistance;
    @BindView(R.id.tvCouponMoney)
    TextView tvCouponMoney;
    @BindView(R.id.tvBonus)
    TextView tvBonus;

    public static AllBalanceFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        AllBalanceFragment fragment = new AllBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_all_balance;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initEventAndData() {
        getPresenter().getAllBalanceHeader("");
    }

    @Override
    public AllBalancePresenter createPresenter() {
        return new AllBalancePresenter();
    }

    @Override
    public void getAllBalanceHeader(CurrentTodayHeaderBean data) {
        tvAllMoney.setText(data.getTotal_money() + "");
        tvTotalBonus.setText(data.getTotal_bonus() + "");
        tvCarCount.setText(data.getTotal_car() + "");
        tvAllDistance.setText(data.getTotal_mileage() + "");
        tvCouponMoney.setText(data.getTotal_coupon() + "");
        tvBonus.setText(data.getBonus() + "");
    }
}
