package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopCoinBean;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;


public class ShopCoinAdapter extends BaseRVAdapter<ShopCoinBean> {
    Context mContext;


    public ShopCoinAdapter(Context context) {
        super(R.layout.item_shop_coin);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopCoinBean data, int position) {

        holder.setText(R.id.tvTitle, data.getInfo());
        holder.setText(R.id.tvContent, (data.getType() == 1 ? "-" : "") + data.getValue());
        holder.setText(R.id.tvTime, TimeUtils.timeStr2Str(data.getCreatetime() + "000", "yyyy-MM-dd HH:mm:ss"));

    }

}
