package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.ruanjie.donkey.utils.LogUtils;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class TodayDatasDetailAdapter extends BaseRVAdapter<TodayDatasDetailBean> {
    Context mContext;

    public TodayDatasDetailAdapter(Context context) {
        super(R.layout.item_today_datas_detail);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, TodayDatasDetailBean data, int position) {
        holder.setText(R.id.tvTime, getTime(data));
        holder.setText(R.id.tvCarMoney, "行驶费用："+data.getTotal_price()+"元");
        holder.setText(R.id.tvCarTime,  getTimeData(data.getDuration()));
        holder.setText(R.id.tvCouponMoney, "使用优惠："+data.getCoupon_fee()+"元");
    }

    private String getTime(TodayDatasDetailBean data) {
        int createtime = data.getCreatetime();
        int endtime = data.getEndtime();

        String tempStr = "";
        tempStr = TimeUtils.time2Str(createtime, "HH:mm:ss")
                + "-"
                + TimeUtils.time2Str(endtime, "HH:mm:ss");

        return tempStr;
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
