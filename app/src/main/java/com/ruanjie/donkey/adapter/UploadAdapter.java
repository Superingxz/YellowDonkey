package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.HelpAndBackBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class UploadAdapter extends BaseRVAdapter<HelpAndBackBean> {
    Context mContext;

    public UploadAdapter(Context context) {
        super(R.layout.item_photo_delete);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, HelpAndBackBean data, int position) {
        LogUtils.i("onBindVH", "onBindVH = " + data.getPicPath());
        AppCompatImageView ivAddPhoto = holder.getView(R.id.iv_add_photo);
        if ("add".equals(data.getPicPath())) {
            ImageUtil.loadImage(ivAddPhoto, R.mipmap.add_photo_icon);
            holder.setVisible(R.id.iv_delete, false);
        } else {
            ImageUtil.loadImageNone(ivAddPhoto, data.getPicPath());
            holder.setVisible(R.id.iv_delete, true);
        }

        holder.addOnClickListener(R.id.iv_add_photo);
        holder.addOnClickListener(R.id.iv_delete);
    }
}
