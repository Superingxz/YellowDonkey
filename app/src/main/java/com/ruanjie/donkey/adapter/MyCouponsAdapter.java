package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.ContextUtil;

public class MyCouponsAdapter extends BaseRVAdapter<CouponBean> {
    Context mContext;


    public MyCouponsAdapter(Context context) {
        super(R.layout.item_my_coupons);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, CouponBean data, int position) {
        holder.setText(R.id.tvMoney, data.getMoney());
        holder.setText(R.id.tvCondition1, "满" + data.getReach_money() + "元可用");
        holder.setText(R.id.tvName, data.getTitle());
        holder.setText(R.id.tvCondition2, data.getRemark());
        holder.setText(R.id.tvTime, data.getStarttime_text() + "-" + data.getEndtime_text());

    }
}
