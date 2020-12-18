package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.contract.MyWalletContract;
import com.ruanjie.donkey.ui.drawer.presenter.MyWalletPresenter;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends ToolbarActivity<MyWalletPresenter>
        implements MyWalletContract.View {

    @BindView(R.id.tvBalance1)
    TextView tvBalance1;
    @BindView(R.id.tvBalance2)
    TextView tvBalance2;
    @BindView(R.id.tvBalance3)
    TextView tvBalance3;
    @BindView(R.id.tvPledge)
    TextView tvPledge;
    @BindView(R.id.tvRecharge)
    TextView tvRecharge;
    @BindView(R.id.tvWithdraw)
    TextView tvWithdraw;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    LoginBean mLoginBean;
    @BindView(R.id.tvGivePledge)
    TextView tvGivePledge;
    double mNeedDeposit;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyWalletActivity.class);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("我的钱包")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initialize() {
//        getPresenter().getConfigInfo("deposit");
        getPresenter().getUserInfo();

    }

    private void initViews(LoginBean data) {
        mLoginBean = data;

//        LogUtils.logBean("mLoginBean", mLoginBean);
        tvBalance1.setText(mLoginBean.getMoney());
        tvBalance2.setText(mLoginBean.getMoney_recharge());
        tvBalance3.setText(mLoginBean.getMoney_reward());

        String deposit = mLoginBean.getDeposit();

        boolean isHasDeposit = !"0.00".equals(deposit);
        if (isHasDeposit) {
            tvPledge.setText(mLoginBean.getDeposit());
        }
        tvRecharge.setSelected(true);
        tvWithdraw.setSelected(true);

        tvGivePledge.setText(isHasDeposit ? "退押金" : "交押金");
    }

    @OnClick({R.id.tvBalance1, R.id.tvBalance2, R.id.tvBalance3
            , R.id.tvPledge, R.id.tvRecharge, R.id.tvWithdraw
            , R.id.tvDetail, R.id.tvGivePledge})
    public void onClick(View v) {
        if (mLoginBean == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.tvBalance1:
                break;
            case R.id.tvBalance2:
                break;
            case R.id.tvBalance3:
                break;
            case R.id.tvPledge:
                break;
            case R.id.tvRecharge:
                RechargeActivity.start(getContext());
                break;
            case R.id.tvWithdraw:
                WithDrawActivity.start(getContext());
                break;
            case R.id.tvDetail:
                WalletDetailActivity.start(getContext());
                break;
            case R.id.tvGivePledge:
                String trim = tvGivePledge.getText().toString().trim();
                if ("交押金".equals(trim)) {
                    getPresenter().getConfigInfo("deposit");
                } else if ("退押金".equals(trim)) {
                    //退押金页面
                    BackDepositActivity.start(getContext());
                }
                break;
        }
    }

    @Override
    public MyWalletPresenter createPresenter() {
        return new MyWalletPresenter();
    }


    @Override
    public void getUserInfo(LoginBean data) {
        SPManager.setLoginBean(data);
        initViews(data);

    }

    @Override
    public void getConfigInfo(ConfigBean data) {
        String needDepositStr = data.getContent();
        mNeedDeposit = Double.parseDouble(needDepositStr);

        if (mNeedDeposit == 0) {
            ToastUtil.s("暂时还不需要交押金");
        } else {
            //交押金页面
            PayDepositActivity.start(getContext(), needDepositStr);
        }
    }

    @Override
    public void onEventBus(EventBusBean busBean) {
        super.onEventBus(busBean);
        switch (busBean.getCode()) {
            case MEventBus.REFRESH_WALLET:
            case MEventBus.PAY_SUCCESS:
            case MEventBus.PAY_SUCCESS_WECHAT:
                //刷新页面
                getPresenter().getUserInfo();
                break;
        }
    }
}
