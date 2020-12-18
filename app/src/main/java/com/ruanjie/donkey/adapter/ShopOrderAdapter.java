package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.noober.background.drawable.DrawableCreator;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;


public class ShopOrderAdapter extends BaseRVAdapter<ShopOrderBean> {
    Context mContext;


    public ShopOrderAdapter(Context context) {
        super(R.layout.item_shop_order);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopOrderBean data, int position) {
        ShopOrderBean.GoodsListBean bean = data.getGoodsList().get(0);
        holder.setText(R.id.tvShopName, data.getStoreInfo().getName());
        holder.setText(R.id.tvStatus, data.getStatus() == 1 ? "待使用" : "已完成");
        ImageView ivImage = holder.getView(R.id.ivImage);
        if (bean != null) {
            ImageUtil.loadImage(ivImage, bean.getThumb());
            holder.setText(R.id.tvName, bean.getGoods_name());
            holder.setText(R.id.tvCoinAndPrice, bean.getScore() + "驴币+￥" + bean.getPrice());
            holder.setText(R.id.tvCount, "x" + bean.getNum());
        }

        holder.setText(R.id.tvRealCoin, data.getPay_score() + "驴币");

        holder.setText(R.id.tvBtn1, data.getStatus() == 1 ? "核验码" : "删除订单");
        holder.setText(R.id.tvBtn2, data.getStatus() == 1 ? "确认使用" : data.getIs_comment() == 1 ?"已评价":"去评价");

        if (data.getStatus() == 2) {
            BLTextView tvBtn2 = holder.getView(R.id.tvBtn2);
            //是否已评价
            Drawable drawable = new DrawableCreator.Builder()
                    .setCornersRadius(DisplayUtil.dip2px(mContext, 6))
                    .setSolidColor(ContextUtil.getColor(R.color.white))
                    .setStrokeWidth(DisplayUtil.dip2px(mContext, 1))
                    .setStrokeColor(ContextUtil.getColor(data.getIs_comment() == 1 ? R.color.gray : R.color.orange))
                    .build();

            tvBtn2.setTextColor(ContextUtil.getColor(data.getIs_comment() == 1 ? R.color.textColor : R.color.orange));

            tvBtn2.setBackground(drawable);
        }


        holder.addOnClickListener(R.id.tvBtn1);
        holder.addOnClickListener(R.id.tvBtn2);
    }

}
