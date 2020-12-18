package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.drawer.contract.BackDepositContract;
import com.ruanjie.donkey.ui.drawer.presenter.BackDepositPresenter;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BackDepositActivity extends ToolbarActivity<BackDepositPresenter>
        implements BackDepositContract.View {

    @BindView(R.id.tvReason1)
    BLTextView tvReason1;
    @BindView(R.id.tvReason2)
    BLTextView tvReason2;
    @BindView(R.id.tvReason3)
    BLTextView tvReason3;
    @BindView(R.id.tvReason4)
    BLTextView tvReason4;
    @BindView(R.id.tvReason5)
    BLTextView tvReason5;
    @BindView(R.id.tvSubmit)
    BLTextView tvSubmit;
    @BindView(R.id.tvCancel)
    BLTextView tvCancel;

    public static void start(Context context) {
        Intent starter = new Intent(context, BackDepositActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("退押金")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    int mSelect;

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_back_deposit;
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.tvReason1, R.id.tvReason2, R.id.tvReason3
            , R.id.tvReason4, R.id.tvReason5, R.id.tvSubmit
            , R.id.tvCancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReason1:
                setSelectView(1);
                break;
            case R.id.tvReason2:
                setSelectView(2);
                break;
            case R.id.tvReason3:
                setSelectView(3);
                break;
            case R.id.tvReason4:
                setSelectView(4);
                break;
            case R.id.tvReason5:
                setSelectView(5);
                break;
            case R.id.tvSubmit:
                if (mSelect == 0) {
                    ToastUtil.s("请选择退款原因");
                    return;
                }
                String tempStr = "";
                switch (mSelect) {
                    case 1:
                        tempStr = tvReason1.getText().toString();
                        break;
                    case 2:
                        tempStr = tvReason2.getText().toString();
                        break;
                    case 3:
                        tempStr = tvReason3.getText().toString();
                        break;
                    case 4:
                        tempStr = tvReason4.getText().toString();
                        break;
                    case 5:
                        tempStr = tvReason5.getText().toString();
                        break;
                }
                getPresenter().backDeposit(tempStr);
                break;
            case R.id.tvCancel:
                finish();
                break;
        }
    }

    private void setSelectView(int which) {
        mSelect = which;
        tvReason1.setSelected(which == 1 ? true : false);
        tvReason2.setSelected(which == 2 ? true : false);
        tvReason3.setSelected(which == 3 ? true : false);
        tvReason4.setSelected(which == 4 ? true : false);
        tvReason5.setSelected(which == 5 ? true : false);
    }

    @Override
    public BackDepositPresenter createPresenter() {
        return new BackDepositPresenter();
    }

    @Override
    public void backDeposit(String data) {
        BackDepositResultActivity.start(getContext());
        finish();
    }
}
