package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.contract.WithDrawContract;
import com.ruanjie.donkey.ui.drawer.presenter.WithDrawPresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.OtherUtils;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends ToolbarActivity<WithDrawPresenter>
        implements WithDrawContract.View {


    @BindView(R.id.etMoney)
    AppCompatEditText etMoney;
    @BindView(R.id.tvAll)
    TextView tvAll;
    @BindView(R.id.etBankName)
    AppCompatEditText etBankName;
    @BindView(R.id.etBankNum)
    AppCompatEditText etBankNum;
    @BindView(R.id.tvAllMoney)
    TextView tvAllMoney;
    @BindView(R.id.tvOk)
    TextView tvOk;
    LoginBean mLoginBean;

    public static void start(Context context) {
        Intent starter = new Intent(context, WithDrawActivity.class);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("提现")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_with_draw;
    }

    @Override
    protected void initialize() {

        mLoginBean = SPManager.getLoginBean();
        tvAllMoney.setText("当前可提现：￥" + mLoginBean.getMoney());


    }

    @OnClick({R.id.tvAll, R.id.tvOk})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAll:
                etMoney.setText(mLoginBean.getMoney());
                etMoney.setSelection(mLoginBean.getMoney().length());
                break;
            case R.id.tvOk:
                String money = mLoginBean.getMoney();
                if (TextUtils.isEmpty(money) || "0.00".equals(money)) {
                    ToastUtil.s("没有可提现金额");
                    return;
                }
                String moneyStr = etMoney.getText().toString().trim();
                String bankNameStr = etBankName.getText().toString().trim();
                String bankNumStr = etBankNum.getText().toString().trim();

                if (TextUtils.isEmpty(moneyStr)) {
                    ToastUtil.s("请输入提现金额");
                    return;
                }
                if (TextUtils.isEmpty(bankNameStr)) {
                    ToastUtil.s("请输入开户银行");
                    return;
                }
                if (TextUtils.isEmpty(bankNumStr)) {
                    ToastUtil.s("请输入银行卡卡号");
                    return;
                }

                getPresenter().withDraw(moneyStr, bankNumStr, bankNameStr);
                break;
        }
    }

    @Override
    public WithDrawPresenter createPresenter() {
        return new WithDrawPresenter();
    }

    @Override
    public void withDraw(String data) {
        ToastUtil.s("提现申请将会在1-7个工作日处理完毕");
        EventBusUtils.post(MEventBus.REFRESH_WALLET, null);
        finish();
    }
}
