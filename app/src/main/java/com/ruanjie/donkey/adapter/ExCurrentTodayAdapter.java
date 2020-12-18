package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ExTodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;

public class ExCurrentTodayAdapter extends BaseRVAdapter<ExTodayDatasBean> {
    Context mContext;

    public ExCurrentTodayAdapter(Context context) {
        super(R.layout.item_current_today_ex);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ExTodayDatasBean data, int position) {
        holder.setText(R.id.tvCarNum, data.getCode());
//        状态:0=待换电,1=已完成,2=已取消
        int status = data.getStatus();
        TextView tvState = holder.getView(R.id.tvState);
        if (status == 0) {
            tvState.setText("待换");
            tvState.setTextColor(ContextUtil.getColor(R.color.text_black));
            tvState.setBackgroundResource(R.mipmap.ex_change);
        } else if (status == 1) {
            tvState.setText("完成");
            tvState.setTextColor(ContextUtil.getColor(R.color.white));
            tvState.setBackgroundResource(R.mipmap.ex_cancel);
        } else {
            tvState.setText("取消");
            tvState.setTextColor(ContextUtil.getColor(R.color.white));
            tvState.setBackgroundResource(R.mipmap.ex_cancel);
        }
        tvState.setPadding(0,0, DisplayUtil.dip2px(mContext,20),0);

    }

    public String getTimeData(int mins) {
        //
        String tempStr = "";
        if (mins < 60) {
            tempStr = mins + "分钟";
        } else {
            double v = mins * 1.0 / 60;
            if ((v + "").contains(".")) {
                LogUtils.i("行驶时间", "行驶时间 = " + v);
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
