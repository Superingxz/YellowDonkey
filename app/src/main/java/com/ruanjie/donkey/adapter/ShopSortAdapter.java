package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;


public class ShopSortAdapter extends BaseRVAdapter<ShopSortBean> {
    Context mContext;


    public ShopSortAdapter(Context context) {
        super(R.layout.item_shop_sort);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopSortBean data, int position) {
        ImageView ivImage = holder.getView(R.id.ivImage);
        ImageUtil.loadImage(ivImage, data.getImage());

        holder.setText(R.id.tvName, data.getName());

    }

}
