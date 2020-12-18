package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.CircleImageView;
import com.noober.background.view.BLLinearLayout;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopAppraiceBean;
import com.ruanjie.donkey.bean.ShopTypeBean2;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.willy.ratingbar.ScaleRatingBar;


public class ShopAppriceAdapter extends BaseRVAdapter<ShopAppraiceBean> {
    Context mContext;


    public ShopAppriceAdapter(Context context) {
        super(R.layout.item_shop_appraice);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopAppraiceBean data, int position) {

        CircleImageView ivHead = holder.getView(R.id.ivHead);
        ImageUtil.loadImage(ivHead, data.getAvatar());

        holder.setText(R.id.tvName, data.getIs_anonymous() == 1 ? "****" : data.getNickname());
        holder.setText(R.id.tvTime, TimeUtils.timeStr2Str(data.getCreatetime() + "000", "yyyy-MM-dd"));
        ScaleRatingBar mRatingBar = holder.getView(R.id.mRatingBar);
        mRatingBar.setRating(Float.parseFloat(data.getStar()));

        holder.setText(R.id.tvContent, data.getContent());


        RecyclerView mRecyclerView = holder.getView(R.id.mRecyclerView);
        AppriceImageAdapter mAdapter = new AppriceImageAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setNewData(data.getImages());


    }

}
