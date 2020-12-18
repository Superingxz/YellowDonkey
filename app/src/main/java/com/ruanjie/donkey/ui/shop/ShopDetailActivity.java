package com.ruanjie.donkey.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopDetail4Bean;
import com.ruanjie.donkey.bean.ShopDetailBean;
import com.ruanjie.donkey.ui.shop.contract.ShopDetailContract;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail1Fragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail2Fragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail3Fragment;
import com.ruanjie.donkey.ui.shop.fragment.ShopDetail4Fragment;
import com.ruanjie.donkey.ui.shop.presenter.ShopDetailPresenter;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.FragmentBasePagerAdapter;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.OnClick;


public class ShopDetailActivity extends ToolbarActivity<ShopDetailPresenter>
        implements ShopDetailContract.View {
    int mShopId;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.vSelect1)
    View vSelect1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.vSelect2)
    View vSelect2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.vSelect3)
    View vSelect3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.vSelect4)
    View vSelect4;
    @BindView(R.id.mViewPager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.mRootView)
    LinearLayout mRootView;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.ivHead)
    RoundedImageView ivHead;
    @BindView(R.id.tvCount)
    TextView tvCount;

    public static void start(Context context, int shopId) {
        Intent starter = new Intent(context, ShopDetailActivity.class);
        starter.putExtra("shopId", shopId);

        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @Override
    protected void initialize() {
        mShopId = getIntent().getIntExtra("shopId", 0);

        getPresenter().getShopDetail(mShopId);

    }


    @OnClick({R.id.ivBack, R.id.tv1, R.id.tv2
            , R.id.tv3, R.id.tv4})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv1:
                setSelect(0);
                break;
            case R.id.tv2:
                setSelect(1);
                break;
            case R.id.tv3:
                setSelect(2);
                break;
            case R.id.tv4:
                setSelect(3);
                break;
        }
    }

    int mWhich = 0;

    private void setSelect(int which) {
        if (mWhich == which) return;
        mWhich = which;
        mViewPager.setCurrentItem(mWhich == 0 ? 0
                : mWhich == 1 ? 1
                : mWhich == 2 ? 2
                : 3
        );

        vSelect1.setVisibility(mWhich == 0 ? View.VISIBLE : View.INVISIBLE);
        vSelect2.setVisibility(mWhich == 1 ? View.VISIBLE : View.INVISIBLE);
        vSelect3.setVisibility(mWhich == 2 ? View.VISIBLE : View.INVISIBLE);
        vSelect4.setVisibility(mWhich == 3 ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public ShopDetailPresenter createPresenter() {
        return new ShopDetailPresenter();
    }

    @Override
    public void getShopDetail(ShopDetailBean data) {

        initViews(data);

    }

    private void initViews(ShopDetailBean data) {
        //获取商店详情，然后赋值
        ImageUtil.loadImage(ivImage, data.getBanner());
        ImageUtil.loadImage(ivHead, data.getLogo());
        tvName.setText(data.getName());
        tvDesc.setText(data.getIntro());
        tvCount.setText(data.getCommentCount() + "");

        //初始化viewpager
        FragmentBasePagerAdapter adapter = new FragmentBasePagerAdapter(getSupportFragmentManager()
                , ShopDetail1Fragment.newInstance(new Gson().toJson(data.getGoodsList()))
                , ShopDetail2Fragment.newInstance(data.getId())
                , ShopDetail3Fragment.newInstance(data.getId(), data.getStar())
                , ShopDetail4Fragment.newInstance(new Gson().toJson(new ShopDetail4Bean(data.getAddress()
                , data.getImages()
                , data.getIntro()
                , data.getTel(), data.getLat(), data.getLng()
        )))
        );
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
    }
}
