package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

import java.util.List;

public class MainTainHistoryAdapter extends BaseRVAdapter<MainTainBean> {
    Context mContext;

    public MainTainHistoryAdapter(Context context) {
        super(R.layout.item_maintain_history);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, MainTainBean data, int position) {

        holder.setText(R.id.tvContent, data.getContent());
        holder.setText(R.id.tvTime, TimeUtils.timeStr2Str(data.getCreatetime() + "000", "yyyy-MM-dd HH:mm:ss"));

        List<String> images = data.getImages();
        if (images.size() > 0) {
            holder.setVisible(R.id.llImages, true);
            ImageView ivImage1 = holder.getView(R.id.ivImage1);
            ImageView ivImage2 = holder.getView(R.id.ivImage2);
            ImageView ivImage3 = holder.getView(R.id.ivImage3);
            if (images.size() == 1) {
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.INVISIBLE);
                ivImage3.setVisibility(View.INVISIBLE);
                ImageUtil.loadImage(ivImage1, images.get(0));
                holder.addOnClickListener(R.id.ivImage1);
            } else if (images.size() == 2) {
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.VISIBLE);
                ivImage3.setVisibility(View.INVISIBLE);
                ImageUtil.loadImage(ivImage1, images.get(0));
                ImageUtil.loadImage(ivImage2, images.get(1));
                holder.addOnClickListener(R.id.ivImage1);
                holder.addOnClickListener(R.id.ivImage2);
            } else {
                ivImage1.setVisibility(View.VISIBLE);
                ivImage2.setVisibility(View.VISIBLE);
                ivImage3.setVisibility(View.VISIBLE);
                ImageUtil.loadImage(ivImage1, images.get(0));
                ImageUtil.loadImage(ivImage2, images.get(1));
                ImageUtil.loadImage(ivImage3, images.get(2));
                holder.addOnClickListener(R.id.ivImage1);
                holder.addOnClickListener(R.id.ivImage2);
                holder.addOnClickListener(R.id.ivImage3);

            }
        } else {
            holder.setVisible(R.id.llImages, false);
        }


    }
}
