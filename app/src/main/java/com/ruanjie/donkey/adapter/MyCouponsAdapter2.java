package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CouponBean;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class MyCouponsAdapter2 extends BaseRVAdapter<CouponBean> {
    Context mContext;


    public MyCouponsAdapter2(Context context) {
        super(R.layout.item_my_coupons2);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, CouponBean data, int position) {
        holder.setText(R.id.tvMoney, data.getMoney());
        holder.setText(R.id.tvCondition1, "满" + data.getReach_money() + "元可用");
        holder.setText(R.id.tvName, data.getTitle());
        holder.setText(R.id.tvCondition2, data.getRemark());
        holder.setText(R.id.tvTime, data.getStarttime_text() + "-" + data.getEndtime_text());

        holder.addOnClickListener(R.id.tvDelete);
    }
}
