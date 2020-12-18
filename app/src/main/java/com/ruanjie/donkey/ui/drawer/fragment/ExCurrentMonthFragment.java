package com.ruanjie.donkey.ui.drawer.fragment;


import android.os.Bundle;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.ui.drawer.ExChangeHistoryActivity;
import com.ruanjie.donkey.ui.drawer.contract.ExCurrentMonthContract;
import com.ruanjie.donkey.ui.drawer.presenter.ExCurrentMonthPresenter;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.RefreshFragment;

import butterknife.BindView;

public class ExCurrentMonthFragment extends RefreshFragment<ExCurrentMonthPresenter>
        implements ExCurrentMonthContract.View {


    @BindView(R.id.tvAllCount)
    TextView tvAllCount;
    @BindView(R.id.tvApplyCount)
    TextView tvApplyCount;
    @BindView(R.id.tvCancelCount)
    TextView tvCancelCount;

    public static ExCurrentMonthFragment newInstance() {

        Bundle args = new Bundle();
//        args.putInt("type", type);

        ExCurrentMonthFragment fragment = new ExCurrentMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_current_month_ex;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initEventAndData() {
        getPresenter().getExCurrentMonthHeader(getDate());

    }

    public String getDate() {
        return ((ExChangeHistoryActivity) getActivity()).getDateStr(2);
    }

    @Override
    public ExCurrentMonthPresenter createPresenter() {
        return new ExCurrentMonthPresenter();
    }

    @Override
    public void getExCurrentMonthHeader(ExCurrentTodayHeaderBean data) {
        tvAllCount.setText(data.getTotal() + "");
        tvApplyCount.setText(data.getFinish() + "");
        tvCancelCount.setText(data.getCancel() + "");
    }

    @Override
    public void mEventBus(EventBusBean busBean) {
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_CURRENT_MONTH_DATA:
                getPresenter().getExCurrentMonthHeader(getDate());
                break;
        }
    }
}
