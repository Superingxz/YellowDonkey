package com.ruanjie.donkey.ui.shop.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopSortAdapter;
import com.ruanjie.donkey.adapter.ShopsAdapter;
import com.ruanjie.donkey.bean.ShopBean;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.ui.shop.ShopDetailActivity;
import com.ruanjie.donkey.ui.shop.ShopSearchActivity;
import com.ruanjie.donkey.ui.shop.contract.ShopContract;
import com.ruanjie.donkey.ui.shop.presenter.ShopPresenter;
import com.ruanjie.donkey.utils.GridSpacingItemDecoration;
import com.ruanjie.donkey.utils.ImageUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.softgarden.baselibrary.base.RefreshFragment;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.SPUtil;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopFragment extends RefreshFragment<ShopPresenter>
        implements ShopContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvSearch)
    BLTextView tvSearch;
    @BindView(R.id.mMZBanner)
    MZBannerView mMZBanner;
    ShopsAdapter mAdapter;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.mSortRecycler)
    RecyclerView mSortRecycler;
    private String image1 = "http://img5.imgtn.bdimg.com/it/u=1312342535,3076558336&fm=26&gp=0.jpg";
    private String image2 = "http://img4.imgtn.bdimg.com/it/u=1981255082,2115613946&fm=26&gp=0.jpg";
    private String image3 = "http://img1.imgtn.bdimg.com/it/u=2425973259,1645965406&fm=26&gp=0.jpg";

    public static ShopFragment newInstance() {
        Bundle args = new Bundle();

        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_shop;
    }

    @Override
    protected void initEventAndData() {
        initBanner();
        initSortRecycler();

        initRefreshLayout();
        initRecyclerView();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2
                , DisplayUtil.dip2px(getContext(), 10)
                , true));

        mAdapter = new ShopsAdapter(getContext());
        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopsBean shopsBean = mAdapter.getData().get(position);
                ShopDetailActivity.start(getContext(), shopsBean.getId());
            }
        });


        lazyLoad();
    }

    ShopSortAdapter mSortAdapter;

    private void initSortRecycler() {
        mSortRecycler.addItemDecoration(new GridSpacingItemDecoration(4, DisplayUtil.dip2px(getContext(), 10), true));

        mSortAdapter = new ShopSortAdapter(getContext());
        mSortRecycler.setAdapter(mSortAdapter);

        mSortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopSortBean shopSortBean = mSortAdapter.getData().get(position);
                //进入分类搜索页面
                ShopSearchActivity.start(getContext(), new Gson().toJson(shopSortBean));

            }
        });
    }

    private void initBanner() {

        mMZBanner.setDelayedTime(2000);
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int posi) {

            }
        });

    }

    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public void getShopBanners(List<String> data) {
        // 设置数据
        mMZBanner.setPages(data, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    @Override
    public void getShopSorts(List<ShopSortBean> data) {
        mSortAdapter.setNewData(data);
    }

    @Override
    public void getShopList(List<ShopsBean> data) {
        if (mPage == 1) {
            setLoadData(mAdapter, data);
        } else {
            setLoadMore(mAdapter, data);
        }

    }

    public static class BannerViewHolder implements MZViewHolder<String> {
        private RoundedImageView ivImage;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            ivImage = (RoundedImageView) view.findViewById(R.id.ivImage);
            ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            ImageUtil.loadImage(ivImage, data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
    }

    @Override
    protected void lazyLoad() {

        getPresenter().getShopBanners();
        getPresenter().getShopSorts(0);
        String latitudeStr = (String) SPUtil.get("latitude", "");
        String[] split = latitudeStr.split(",");
        if (split != null && split.length == 2) {
            getPresenter().getShopList(split[1], split[0], "", 0, 0, mPage, PAGE_COUNT);
        }
    }

    @OnClick({R.id.ivBack, R.id.tvSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                getActivity().finish();
                break;
            case R.id.tvSearch:
                ShopSearchActivity.start(getContext(), "");
                break;
        }
    }

}
