package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopAppraiceBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.umeng.commonsdk.debug.I;
import com.willy.ratingbar.ScaleRatingBar;


public class AppriceImageAdapter extends BaseRVAdapter<String> {
    Context mContext;


    public AppriceImageAdapter(Context context) {
        super(R.layout.item_appraice_image);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, String data, int position) {
        ImageView ivImage = holder.getView(R.id.ivImage);
        ImageUtil.loadImage(ivImage,data);

    }

}
