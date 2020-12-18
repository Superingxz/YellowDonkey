package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.MessageListBean;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.TimeUtils;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.adapter
 * 文件名:   NotifyMessageAdapter
 * 创建者:    QJM
 * 创建时间: 2019/8/15 1:06
 * 描述:     TODO
 */
public class MessageListAdapter extends BaseRVAdapter<MessageListBean> {
    Context mContext;

    public MessageListAdapter(Context context) {
        super(R.layout.item_notification_message);
        mContext = context;
    }

    @Override
    public void onBindVH(BaseRVHolder holder, MessageListBean data, int position) {
        holder.setText(R.id.tv_message_title, data.getTitle());
        holder.setText(R.id.tv_message_content, data.getIntro());
//        holder.getView(R.id.message_layout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WebViewActivity.start(mContext,data.getTitle(),data.getContent_url());
//            }
//        });

        AppCompatImageView ivHead = holder.getView(R.id.ivHead);
//        1=系统消息,2=活动公告
        if (data.getType() == 1) {
            if (data.getStatus() == 1) {//已读
                ImageUtil.loadImage(ivHead, R.mipmap.system_gray);
            } else {
                ImageUtil.loadImage(ivHead, R.mipmap.system_green);
            }
        } else if (data.getType() == 2) {
            if (data.getStatus() == 1) {//已读
                ImageUtil.loadImage(ivHead, R.mipmap.activity_gray);
            } else {
                ImageUtil.loadImage(ivHead, R.mipmap.activity_red);
            }
        }
        holder.setText(R.id.tvTime, TimeUtils.timeStr2Str(data.getCreatetime() + "000", "MM-dd HH:mm"));
        holder.addOnClickListener(R.id.message_layout);
        holder.addOnClickListener(R.id.tvDelete);
    }
}
