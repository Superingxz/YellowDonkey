package com.ruanjie.donkey.ui.shop;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.TabEntity;
import com.ruanjie.donkey.ui.shop.fragment.ShopCouponFragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopOrderFragment;
import com.softgarden.baselibrary.base.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopOrderActivity extends ToolbarActivity {


    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.mTabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    MyPagerAdapter mPagerAdapter;
    int mWhich;


    public static void start(Context context, int which) {
        Intent starter = new Intent(context, ShopOrderActivity.class);
        starter.putExtra("which", which);
//        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop_order;
    }

    @Override
    protected void initialize() {
        List<String> mDatas = new ArrayList<>();
        mDatas.add("全部");
        mDatas.add("待使用");
        mDatas.add("已完成");
        mTitles = new String[mDatas.size()];

        for (int i = 0; i < mDatas.size(); ++i) {
            mTitles[i] = mDatas.get(i);
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(ShopOrderFragment.newInstance(i));
        }

        mTabLayout.setTabData(mTabEntities);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);

        mWhich = getIntent().getIntExtra("which", 0);
        mTabLayout.setCurrentTab(mWhich);
        mViewPager.setCurrentItem(mWhich);
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

    @OnClick({R.id.ivBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
