package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MyCouponsAdapter;
import com.ruanjie.donkey.ui.drawer.contract.MyCouponsContract;
import com.ruanjie.donkey.ui.drawer.fragment.AllBalanceFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CouponNoUseFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CouponUseFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentMonthFragment;
import com.ruanjie.donkey.ui.drawer.fragment.CurrentTodayFragment;
import com.ruanjie.donkey.ui.drawer.presenter.MyCouponsPresenter;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.utils.ContextUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCouponsActivity extends RefreshActivity {
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyCouponsActivity.class);
//        starter.putExtra("nickName", nickName);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setBackgroundColor(Color.WHITE)
                .setBackButton(R.mipmap.back_black)
                .setTitle("我的优惠券")
                .setBottomDivider(ContextUtil.getColor(R.color.lineColor), 1);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_my_coupons;
    }

    @Override
    protected void initialize() {
        initTabDatas();
    }

    String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private void initTabDatas() {
        mTitles = new String[2];
        mTitles[0] = "可使用";
        mTitles[1] = "已过期";
        mFragments.add(CouponUseFragment.newInstance());
        mFragments.add(CouponNoUseFragment.newInstance());

        MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.getTabAt(0).select();
    }

    @Override
    public void loadData() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
