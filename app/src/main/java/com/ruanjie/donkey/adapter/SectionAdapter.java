package com.ruanjie.donkey.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.MySection;
import com.ruanjie.donkey.utils.ImageUtil;
import com.umeng.commonsdk.debug.I;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder holder, final MySection data) {
        holder.setText(R.id.tvHead, data.header);
    }


    @Override
    protected void convert(BaseViewHolder holder, MySection data) {
        ImageView ivImage = holder.getView(R.id.ivImage);
        ImageUtil.loadImage(ivImage, data.t.getThumb());
        holder.setText(R.id.tvName, data.t.getName());
        holder.setText(R.id.tvExChangeCount, "已兑换" + data.t.getSales());
        holder.setText(R.id.tvResidueCount, "剩余" + data.t.getStock());
        holder.setText(R.id.tvRealPrice, data.t.getScore() + "驴币+￥" + data.t.getPrice());
        TextView tvPrePrice = holder.getView(R.id.tvPrePrice);
        tvPrePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.setText(R.id.tvPrePrice, "￥" + data.t.getOrigin_price());

        holder.addOnClickListener(R.id.tvExChange);

    }
}
