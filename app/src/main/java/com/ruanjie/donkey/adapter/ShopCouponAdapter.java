package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.noober.background.view.BLLinearLayout;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.bean.ShopTypeBean2;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;


public class ShopCouponAdapter extends BaseRVAdapter<ShopCouponBean> {
    Context mContext;

    public ShopCouponAdapter(Context context) {
        super(R.layout.item_shop_coupon);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopCouponBean data, int position) {

        holder.setText(R.id.tvName,data.getTitle());
        holder.setText(R.id.tvPrice,"面值 ￥"+data.getMoney());
        holder.setText(R.id.tvResidueCount,"剩余 "+data.getStock());
        holder.setText(R.id.tvCoin,data.getScore()+"驴币");
        holder.addOnClickListener(R.id.tvExChange);
    }

}
