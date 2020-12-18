package com.ruanjie.donkey.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MySection extends SectionEntity<ShopDetailBean.GoodsListBean.ListBean> {
    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(ShopDetailBean.GoodsListBean.ListBean t) {
        super(t);
    }

}
