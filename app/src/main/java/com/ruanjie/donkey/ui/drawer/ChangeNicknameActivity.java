package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.contract.ChangeNicknameContract;
import com.ruanjie.donkey.ui.drawer.presenter.ChangeNicknamePresenter;
import com.ruanjie.donkey.utils.EventBusUtils;
import com.ruanjie.donkey.utils.MEventBus;
import com.ruanjie.donkey.utils.SPManager;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNicknameActivity extends ToolbarActivity<ChangeNicknamePresenter>
        implements ChangeNicknameContract.View {


    @BindView(R.id.etNickname)
    AppCompatEditText etNickname;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    String mNickName;

    public static void start(Context context, String nickName) {
        Intent starter = new Intent(context, ChangeNicknameActivity.class);
        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("修改昵称")
                .addRightText("保存", v -> rightClick())
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    private void rightClick() {
        String trim = etNickname.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            ToastUtil.s("请输入您的新昵称");
            return;
        }
        getPresenter().changeUserInfo(-1, trim, "");

    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_change_nickname;
    }

    @Override
    protected void initialize() {
        mNickName = getIntent().getStringExtra("nickName");
        etNickname.setText(mNickName);
        etNickname.setSelection(mNickName.length());
    }


    @OnClick({R.id.ivClose})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                etNickname.setText("");
                break;
        }
    }

    @Override
    public ChangeNicknamePresenter createPresenter() {
        return new ChangeNicknamePresenter();
    }

    @Override
    public void changeUserInfo(String data) {
        LoginBean mLoginBean = SPManager.getLoginBean();
        mLoginBean.setNickname(data);
        SPManager.setLoginBean(mLoginBean);
        ToastUtil.s("修改成功");
        EventBusUtils.post(MEventBus.REFRESH_USERINFO, null);
        finish();
    }
}
