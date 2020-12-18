package com.ruanjie.donkey.ui.shop.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.ShopAppriceAdapter;
import com.ruanjie.donkey.adapter.ShopTypeAdapter;
import com.ruanjie.donkey.bean.ShopAppraiceBean;
import com.ruanjie.donkey.ui.shop.contract.ShopDetail3Contract;
import com.ruanjie.donkey.ui.shop.presenter.ShopDetail3Presenter;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseLazyFragment;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopDetail3Fragment extends BaseLazyFragment<ShopDetail3Presenter>
implements ShopDetail3Contract.View {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.mRatingBar)
    ScaleRatingBar mRatingBar;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private String image1 = "http://img5.imgtn.bdimg.com/it/u=1312342535,3076558336&fm=26&gp=0.jpg";
    private String image2 = "http://img4.imgtn.bdimg.com/it/u=1981255082,2115613946&fm=26&gp=0.jpg";
    private String image3 = "http://img1.imgtn.bdimg.com/it/u=2425973259,1645965406&fm=26&gp=0.jpg";
    ShopAppriceAdapter mAppraiceAdapter;

    public static ShopDetail3Fragment newInstance(int shopId, String starStr) {
        Bundle args = new Bundle();
        args.putInt("shopId", shopId);
        args.putString("starStr", starStr);

        ShopDetail3Fragment fragment = new ShopDetail3Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.fragment_shop_detail_3;
    }

    @Override
    protected void initEventAndData() {
        int mShopId = getArguments().getInt("shopId");
        String mStarStr = getArguments().getString("starStr");

        if (!TextUtils.isEmpty(mStarStr)) {
            //        ImageUtil.loadImage(ivImage, image3);

            float tempF = Float.parseFloat(mStarStr);
            mRatingBar.setRating(tempF);
            tvScore.setText(mStarStr);
        }

        mAppraiceAdapter = new ShopAppriceAdapter(getContext());
        mRecyclerView.setAdapter(mAppraiceAdapter);

        getPresenter().getAppraiseList(mShopId);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public ShopDetail3Presenter createPresenter() {
        return new ShopDetail3Presenter();
    }

    @Override
    public void getAppraiseList(List<ShopAppraiceBean> data) {
        mAppraiceAdapter.setNewData(data);

    }
}
