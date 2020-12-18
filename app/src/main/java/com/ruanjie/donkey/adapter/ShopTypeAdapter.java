package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;

import com.noober.background.view.BLLinearLayout;
import com.noober.background.view.BLTextView;
import com.noober.background.view.BLView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopDetailBean;
import com.ruanjie.donkey.bean.ShopTypeBean;
import com.ruanjie.donkey.bean.ShopTypeBean2;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;


public class ShopTypeAdapter extends BaseRVAdapter<ShopTypeBean2> {
    Context mContext;
    int mSelectPosi;

    public int getmSelectPosi() {
        return mSelectPosi;
    }

    public void setmSelectPosi(int mSelectPosi) {
        this.mSelectPosi = mSelectPosi;
    }

    public ShopTypeAdapter(Context context) {
        super(R.layout.item_shop_type);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopTypeBean2 data, int position) {
        BLLinearLayout llLayout = holder.getView(R.id.llLayout);
        BLTextView tvName = holder.getView(R.id.tvName);

        holder.setText(R.id.tvName, data.getTypeName());


        llLayout.setSelected(position == mSelectPosi ? true : false);
        tvName.setSelected(position == mSelectPosi ? true : false);

    }

}
