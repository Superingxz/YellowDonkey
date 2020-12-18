package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopBean;
import com.ruanjie.donkey.bean.ShopsBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.DisplayUtil;
import com.softgarden.baselibrary.utils.ScreenUtil;

public class ShopRightAdapter extends BaseRVAdapter<ShopsBean> {
    Context mContext;

    public ShopRightAdapter(Context context) {
        super(R.layout.item_shop_right);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopsBean data, int position) {

        ImageView ivImage = holder.getView(R.id.ivImage);

        ImageUtil.loadImage(ivImage, data.getLogo());

        holder.setText(R.id.tvName, data.getName());
        holder.setText(R.id.tvCount, "销量" + data.getSales());
        holder.setText(R.id.tvScore, data.getStar() + "分");
        holder.setText(R.id.tvDistance, data.getDistance());
    }
}
