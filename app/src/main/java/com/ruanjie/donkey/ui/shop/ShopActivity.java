package com.ruanjie.donkey.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.ui.shop.fragment.MyFragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopFragment;
import com.ruanjie.donkey.ui.shop.fragment.SortFragment;
import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.FragmentBasePagerAdapter;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.OnClick;


public class ShopActivity extends ToolbarActivity {

    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tvShop)
    TextView tvShop;
    @BindView(R.id.tvSort)
    TextView tvSort;
    @BindView(R.id.tvMy)
    TextView tvMy;

    public static void start(Context context) {
        Intent starter = new Intent(context, ShopActivity.class);
//        starter.putExtra();

        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected void initialize() {
        initViewPager();

        setSelected(1);

    }

    private void initViewPager() {
        //初始化viewpager
        FragmentBasePagerAdapter adapter = new FragmentBasePagerAdapter(getSupportFragmentManager()
                , ShopFragment.newInstance()
                , SortFragment.newInstance()
                , MyFragment.newInstance()
        );
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({ R.id.tvShop, R.id.tvSort
            , R.id.tvMy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvShop:
                setSelected(1);
                break;
            case R.id.tvSort:
                setSelected(2);
                break;
            case R.id.tvMy:
                setSelected(3);
                break;

        }
    }

    int currSelect = 0;

    private void setSelected(int which) {

        tvShop.setSelected(which == 1 ? true : false);
        tvSort.setSelected(which == 2 ? true : false);
        tvMy.setSelected(which == 3 ? true : false);
        currSelect = which;
        if (mViewPager.getCurrentItem() != which - 1)
            mViewPager.setCurrentItem(currSelect - 1);
    }


}
