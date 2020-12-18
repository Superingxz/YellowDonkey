package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.drawer.presenter.BackDepositPresenter;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BackDepositResultActivity extends ToolbarActivity {


    @BindView(R.id.tvBack)
    TextView tvBack;

    public static void start(Context context) {
        Intent starter = new Intent(context, BackDepositResultActivity.class);
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
        return R.layout.activity_back_deposit_result;
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.tvBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
        }
    }


}
