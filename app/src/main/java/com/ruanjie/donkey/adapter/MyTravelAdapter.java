package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class MyTravelAdapter extends BaseRVAdapter<TravelDetailBean> {
    Context mContext;

    public MyTravelAdapter(Context context) {
        super(R.layout.item_travel_detail);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, TravelDetailBean data, int position) {
        holder.setText(R.id.tvTime, data.getCreatetime_str());
        holder.setText(R.id.tvCarNum, "电动车编号：" + data.getCode());
        holder.setText(R.id.tvCarMoney, "行驶费用：" + data.getTotal_price() + "元");
        holder.setText(R.id.tvCarTime, "行驶时间：" + data.getDuration2() + "分钟");

    }
}
