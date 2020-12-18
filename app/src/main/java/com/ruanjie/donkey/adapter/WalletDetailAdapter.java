package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.ContextUtil;

public class WalletDetailAdapter extends BaseRVAdapter<WalletDetailBean> {
    Context mContext;


    public WalletDetailAdapter(Context context) {
        super(R.layout.item_wallet_detail);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, WalletDetailBean data, int position) {
        holder.setText(R.id.tvTitle, data.getInfo());

        String tempStr = "";
        int type = data.getType();
        tempStr = (type == 1 ? "-" : "+") + data.getValue();
        holder.setText(R.id.tvMoney, tempStr);
        holder.setTextColor(R.id.tvMoney, type == 1 ? ContextUtil.getColor(R.color.text_black)
                : ContextUtil.getColor(R.color.walletRedColor));

        holder.setText(R.id.tvTime, TimeUtils.timeStr2Str(data.getCreatetime()+"000","yyyy-MM-dd HH:mm:ss"));
    }
}
