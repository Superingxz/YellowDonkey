package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;

import com.noober.background.view.BLLinearLayout;
import com.noober.background.view.BLTextView;
import com.noober.background.view.BLView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopSortTypeBean;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;


public class ShopSortTypeAdapter extends BaseRVAdapter<ShopSortBean> {
    Context mContext;
    int mSelectPosi;

    public int getmSelectPosi() {
        return mSelectPosi;
    }

    public void setmSelectPosi(int mSelectPosi) {
        this.mSelectPosi = mSelectPosi;
    }

    public ShopSortTypeAdapter(Context context) {
        super(R.layout.item_shop_sort_type);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopSortBean data, int position) {
        BLLinearLayout llLayout = holder.getView(R.id.llLayout);
        BLView vSelect = holder.getView(R.id.vSelect);
        BLTextView tvName = holder.getView(R.id.tvName);

        holder.setText(R.id.tvName, data.getName());


        llLayout.setSelected(position == mSelectPosi ? true : false);
        vSelect.setVisibility(position == mSelectPosi ? View.VISIBLE: View.INVISIBLE);
        tvName.setSelected(position == mSelectPosi ? true : false);

    }

}
