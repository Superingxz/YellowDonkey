package com.ruanjie.donkey.ui.billing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.billing.presenter.BillingModePresenter;
import com.ruanjie.donkey.ui.scanner.ScanUnlockActivity;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.vondear.rxtool.RxTextTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.billing
 * 文件名:   BillingModeActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/10 22:14
 * 描述:     TODO
 */
public class BillingModeActivity extends ToolbarActivity<BillingModePresenter> {

    @BindView(R.id.tv_agreement)
    AppCompatTextView tvAgreement;

    @OnClick(R.id.bt_immediate_use)
    void onImmediateUse(){
        ScanUnlockActivity.start(getContext());
        finish();
    }

    @OnClick(R.id.tv_agreement)
    void onAgreement(){
        getPresenter().vehicleAgreement();
    }

    @Override
    public BillingModePresenter createPresenter() {
        return new BillingModePresenter();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, BillingModeActivity.class));
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.billing_mode).setTitleTextSize(18);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_billing_mode;
    }

    @Override
    protected void initialize() {
        RxTextTool.getBuilder(getString(R.string.read_and_agree))
                .append(getString(R.string.vehicle_agreement))
                .setForegroundColor(ContextCompat.getColor(getApplicationContext(),R.color.text_yellow))
                .into(tvAgreement);
    }
}
