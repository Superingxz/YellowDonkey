package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;

import butterknife.BindView;

public class WalletDetailActivity2 extends ToolbarActivity {
    String mJsonStr;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvType1)
    TextView tvType1;
    @BindView(R.id.tvType2)
    TextView tvType2;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvNum)
    TextView tvNum;


    public static void start(Context context, String jsonStr) {
        Intent starter = new Intent(context, WalletDetailActivity2.class);
        starter.putExtra("jsonStr", jsonStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("明细详情")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_wallet_detail2;
    }

    @Override
    protected void initialize() {
        mJsonStr = getIntent().getStringExtra("jsonStr");

        WalletDetailBean walletDetailBean = new Gson().fromJson(mJsonStr, WalletDetailBean.class);

        initViews(walletDetailBean);
    }

    private void initViews(WalletDetailBean data) {
//        支付类型:0=未知,1=微信,2=支付宝,3=银联,9=余额
        tvMoney.setText((data.getType() == 1 ? "-" : "+") + data.getValue());
        tvStatus.setText("交易成功");
        tvType1.setText(data.getPay_type() == 1 ? "微信支付"
                : data.getPay_type() == 2 ? "支付宝支付"
                : data.getPay_type() == 3 ? "银联支付"
                : data.getPay_type() == 4 ? "余额支付"
                : "其他");
        tvType2.setText(data.getInfo());
        tvTime.setText(TimeUtils.timeStr2Str(data.getCreatetime() + "000", "yyyy-MM-dd HH:mm:ss"));
        tvNum.setText(data.getOrder_sn());
    }
}
