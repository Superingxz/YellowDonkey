package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.ruanjie.donkey.ui.drawer.contract.JoinAreaContract;
import com.ruanjie.donkey.ui.drawer.contract.JoinCityContract;
import com.ruanjie.donkey.ui.drawer.presenter.JoinCityPresenter;
import com.ruanjie.donkey.utils.OtherUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinCityActivity extends ToolbarActivity<JoinCityPresenter>
        implements JoinCityContract.View {


    @BindView(R.id.ivImageTop)
    ImageView ivImageTop;
    @BindView(R.id.etName)
    AppCompatEditText etName;
    @BindView(R.id.etPhone)
    AppCompatEditText etPhone;
    @BindView(R.id.etCount)
    AppCompatEditText etCount;
    @BindView(R.id.llInputLayout)
    LinearLayout llInputLayout;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    public static void start(Context context,String jsonStr) {
        Intent starter = new Intent(context, JoinCityActivity.class);
         starter.putExtra("jsonStr",jsonStr);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("城市合伙人")
                .setTitleTextColor(Color.BLACK)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_join_city;
    }

    @Override
    protected void initialize() {
        String mJsonStr = getIntent().getStringExtra("jsonStr");
        if(!TextUtils.isEmpty(mJsonStr)){
            JoinCityInfoBean data = new Gson().fromJson(mJsonStr, JoinCityInfoBean.class);
            //状态:0=待审核,1=已通过,2=已拒绝
            int status = data.getStatus();

            etName.setText(data.getName());
            etName.setSelection(data.getName().length());
            etPhone.setText(data.getPhone());
            etCount.setText(data.getNum()+"");

            tvSubmit.setText(status == 0 ? "待审核"
                    : status == 1 ? "已通过"
                    : "未通过，重新提交"
            );

            etName.setEnabled(status == 2 ? true : false);
            etPhone.setEnabled(status == 2 ? true : false);
            etCount.setEnabled(status == 2 ? true : false);
            tvSubmit.setEnabled(status == 2 ? true : false);
        }
    }

    @Override
    public JoinCityPresenter createPresenter() {
        return new JoinCityPresenter();
    }

    @Override
    public void joinCity(String data) {
        ToastUtil.s("提交成功");
        finish();
    }


    @OnClick({R.id.tvSubmit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                if (OtherUtils.stringIsNull(etName, "请填写姓名")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etPhone, "请填写联系方式")) {
                    return;
                }
                if (OtherUtils.stringIsNull(etCount, "请填写认购数量")) {
                    return;
                }
                String nameStr = etName.getText().toString().trim();
                String phoneStr = etPhone.getText().toString().trim();
                String countStr = etCount.getText().toString().trim();
                getPresenter().joinCity(nameStr, phoneStr, countStr);
                break;
        }
    }
}
