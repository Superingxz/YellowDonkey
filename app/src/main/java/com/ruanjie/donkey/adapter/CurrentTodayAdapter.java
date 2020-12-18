package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class CurrentTodayAdapter extends BaseRVAdapter<TodayDatasBean> {
    Context mContext;

    public CurrentTodayAdapter(Context context) {
        super(R.layout.item_current_today);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, TodayDatasBean data, int position) {
        holder.setText(R.id.tvCarNum, "电动车编号：" + data.getCode());
        holder.setText(R.id.tvCarMoney, "行驶费用：" + data.getTotal_price()+"元");
        holder.setText(R.id.tvCarTime, "行驶时间：" + getTimeData(data.getDuration()));
        holder.setText(R.id.tvCouponMoney, "使用优惠：" + data.getCoupon_fee()+"元");

        holder.addOnClickListener(R.id.tvDetail);
        holder.addOnClickListener(R.id.tvDelete);
    }

    public String getTimeData(int mins) {
        //
        String tempStr = "";
        if (mins < 60) {
            tempStr = mins + "分钟";
        } else {
            double v = mins * 1.0 / 60;
            if ((v + "").contains(".")) {
                LogUtils.i("行驶时间","行驶时间 = "  + v);
                String[] split = (v + "").split("\\.");
                String s = split[1];
                if (s.length() > 2) {
                    s = s.substring(0, 2);
                }
                tempStr = split[0] + "." + s + "小时";
            } else {
                tempStr = v + "小时";
            }

        }
        return tempStr;
    }
}
