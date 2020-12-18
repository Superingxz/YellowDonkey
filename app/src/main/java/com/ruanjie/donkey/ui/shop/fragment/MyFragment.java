package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.ui.shop.ShopCoinActivity;
import com.ruanjie.donkey.ui.shop.ShopCouponActivity;
import com.ruanjie.donkey.ui.shop.ShopOrderActivity;
import com.ruanjie.donkey.ui.shop.contract.MyContract;
import com.ruanjie.donkey.ui.shop.presenter.MyPresenter;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.RefreshFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MyFragment extends RefreshFragment<MyPresenter>
        implements MyContract.View {

    @BindView(R.id.llWhole)
    LinearLayout llWhole;
    @BindView(R.id.llNoUse)
    LinearLayout llNoUse;
    @BindView(R.id.llHasOver)
    LinearLayout llHasOver;
    @BindView(R.id.llCoupons)
    LinearLayout llCoupons;
    @BindView(R.id.llMyCoins)
    LinearLayout llMyCoins;
    @BindView(R.id.civImage)
    CircleImageView civImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCoin)
    TextView tvCoin;
    @BindView(R.id.ivNoUse)
    ImageView ivNoUse;
    Badge mMsgBadge;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_my;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.llWhole, R.id.llNoUse, R.id.llHasOver, R.id.llCoupons, R.id.llMyCoins,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llWhole:
                ShopOrderActivity.start(getContext(), 0);
                break;
            case R.id.llNoUse:
                ShopOrderActivity.start(getContext(), 1);
                break;
            case R.id.llHasOver:
                ShopOrderActivity.start(getContext(), 2);
                break;
            case R.id.llCoupons:
                ShopCouponActivity.start(getContext(), 0);
                break;
            case R.id.llMyCoins:
                ShopCoinActivity.start(getContext());
                break;
        }
    }

    @Override
    public MyPresenter createPresenter() {
        return new MyPresenter();
    }

    @Override
    public void getUserInfo(LoginBean data) {

        ImageUtil.loadImage(civImage, data.getAvatar());

        tvName.setText(data.getNickname());
        tvCoin.setText(data.getScore() + "驴币");


    }

    @Override
    public void getShopOrderList(List<ShopOrderBean> data) {
        mMsgBadge = new QBadgeView(getContext())
                .bindTarget(ivNoUse)
                .setBadgeTextSize(12, true)
                .setBadgePadding(1, true)
                .setBadgePadding(1, true);
        if(mMsgBadge!=null){
            int num = data.size();
            if (num <= 0) {//隐藏红点
                mMsgBadge.setBadgeText("");
            } else if (num > 99) {//显示99+
                mMsgBadge.setBadgeText("99+");
            } else {
                mMsgBadge.setBadgeNumber(num);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserInfo();
        getPresenter().getShopOrderList(1, 1, 1, 10000);
    }
}
