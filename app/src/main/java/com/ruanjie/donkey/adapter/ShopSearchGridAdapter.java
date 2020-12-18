package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.ScreenUtil;


public class ShopSearchGridAdapter extends BaseRVAdapter<ShopsBean> {
    Context mContext;


    public ShopSearchGridAdapter(Context context) {
        super(R.layout.item_shop);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopsBean data, int position) {
        ImageView ivImage = holder.getView(R.id.ivIamge);
        ViewGroup.LayoutParams layoutParams = ivImage.getLayoutParams();
        int width = (ScreenUtil.getScreenWidth(mContext) - DisplayUtil.dip2px(mContext, 10) * 3) / 2;
        layoutParams.width = width;
        layoutParams.height = width;
        ImageUtil.loadImage(ivImage, data.getLogo());

        holder.setText(R.id.tvName, data.getName());
        holder.setText(R.id.tvCount, "销量"+data.getSales());
        holder.setText(R.id.tvScore, data.getStar() + "分");
        holder.setText(R.id.tvDistance, data.getDistance());
    }

}
