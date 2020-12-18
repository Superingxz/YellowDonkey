package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.HelpAndBackBean;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

public class MaintainUploadAdapter extends BaseRVAdapter<HelpAndBackBean> {
    Context mContext;

    public MaintainUploadAdapter(Context context) {
        super(R.layout.item_miantain_upload);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, HelpAndBackBean data, int position) {
        LogUtils.i("onBindVH", "onBindVH = " + data.getPicPath());
        AppCompatImageView ivImage = holder.getView(R.id.ivImage);
        if ("add".equals(data.getPicPath())) {
            ImageUtil.loadImage(ivImage, R.mipmap.add_picture);
            holder.setVisible(R.id.ivClose, false);
        } else {
            ImageUtil.loadImageNone(ivImage, data.getPicPath());
            holder.setVisible(R.id.ivClose, true);
        }

        holder.addOnClickListener(R.id.ivImage);
        holder.addOnClickListener(R.id.ivClose);
    }
}
