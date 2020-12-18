package com.ruanjie.donkey.adapter;

import android.content.Context;

import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopSortBean;
import com.ruanjie.donkey.bean.ShopSortRightBean2;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class ShopBottomAdapter extends BaseRVAdapter<ShopSortBean.ChildBean> {
    Context mContext;
    int mSelectPosi;

    public int getmSelectPosi() {
        return mSelectPosi;
    }

    public void setmSelectPosi(int mSelectPosi) {
        this.mSelectPosi = mSelectPosi;
    }

    public ShopBottomAdapter(Context context) {
        super(R.layout.item_shop_right_bottom);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopSortBean.ChildBean data, int position) {
        BLTextView tvSort = holder.getView(R.id.tvSort);
        tvSort.setText(data.getName());

        tvSort.setSelected(position == mSelectPosi ? true : false);
    }
}
