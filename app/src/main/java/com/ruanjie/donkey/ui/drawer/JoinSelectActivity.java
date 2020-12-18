package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.ruanjie.donkey.ui.drawer.contract.JoinSelectContract;
import com.ruanjie.donkey.ui.drawer.presenter.JoinSelectPresenter;
import com.ruanjie.donkey.utils.MEventBus;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class JoinSelectActivity extends ToolbarActivity<JoinSelectPresenter>
        implements JoinSelectContract.View {

    @BindView(R.id.ivArea)
    ImageView ivArea;
    @BindView(R.id.ivCity)
    ImageView ivCity;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;

    public static void start(Context context) {
        Intent starter = new Intent(context, JoinSelectActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("招商加盟")
                .setTitleTextColor(Color.BLACK)
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_join_select;
    }

    @Override
    protected void initialize() {
        //设置图片宽高比例
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int mWidth = screenWidth - DisplayUtil.dip2px(getContext(), 8) * 2;
        int mHeight = mWidth * 178 / 361;

        LinearLayout.LayoutParams areaLayoutParams = (LinearLayout.LayoutParams) ivArea.getLayoutParams();
        LinearLayout.LayoutParams cityLayoutParams = (LinearLayout.LayoutParams) ivCity.getLayoutParams();
        areaLayoutParams.height = mHeight;
        cityLayoutParams.height = mHeight;

        ivArea.setLayoutParams(areaLayoutParams);
        ivCity.setLayoutParams(cityLayoutParams);
    }

    @OnClick({R.id.ivArea, R.id.ivCity})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivArea:
                JoinAreaActivity.start(getContext());
                break;
            case R.id.ivCity:
                //获取城市合伙人信息，已通过则进入认购用户详情页面
                getPresenter().getJoinCityInfo();
                break;
        }
    }

    @OnLongClick({R.id.ivCity})
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.ivCity:
                JoinCityResultActivity.start(getContext());
                break;
        }

        return true;
    }

    @Override
    public JoinSelectPresenter createPresenter() {
        return new JoinSelectPresenter();
    }

    @Override
    public void getJoinCityInfo(JoinCityInfoBean data) {
        if (data != null) {
            if (data.getCreatetime() == 0) {
                JoinCityActivity.start(getContext(), "");
            } else {
                //状态:0=待审核,1=已通过,2=已拒绝
                if (data.getStatus() == 1) {
                    JoinCityResultActivity.start(getContext());
                } else {
                    JoinCityActivity.start(getContext(), new Gson().toJson(data));
                }
            }
        }
    }
}
