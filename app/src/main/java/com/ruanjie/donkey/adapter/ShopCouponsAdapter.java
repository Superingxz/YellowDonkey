package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.noober.background.drawable.DrawableCreator;
import com.noober.background.view.BLTextView;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ShopCouponBean;
import com.ruanjie.donkey.bean.ShopOrderBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;
import com.softgarden.baselibrary.utils.ContextUtil;
import com.softgarden.baselibrary.utils.DisplayUtil;


public class ShopCouponsAdapter extends BaseRVAdapter<ShopOrderBean> {
    Context mContext;


    public ShopCouponsAdapter(Context context) {
        super(R.layout.item_shop_coupon_order);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, ShopOrderBean data, int position) {
        ShopOrderBean.CouponListBean bean = data.getCouponList().get(0);
        holder.setText(R.id.tvShopName, data.getStoreInfo().getName());
        holder.setText(R.id.tvStatus, data.getStatus() == 1 ? "待使用" : "已完成");
//        ImageView ivImage = holder.getView(R.id.ivImage);
//        ImageUtil.loadImage(ivImage, bean.getThumb());

        holder.setText(R.id.tvName, bean.getTitle());
        holder.setText(R.id.tvPrice, "面值￥" + bean.getMoney());
        holder.setText(R.id.tvCoin, bean.getScore() + "驴币");
        holder.setText(R.id.tvRealCoin, bean.getScore() + "驴币");

        holder.setText(R.id.tvBtn1, data.getStatus() == 1 ? "核验码" : "删除订单");
        holder.setText(R.id.tvBtn2, data.getStatus() == 1 ? "确认使用" : "");

        holder.setVisible(R.id.tvBtn2,data.getStatus() == 1 ? true : false);

        holder.addOnClickListener(R.id.tvBtn1);
        holder.addOnClickListener(R.id.tvBtn2);

    }

}
