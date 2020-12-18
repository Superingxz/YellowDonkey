package com.ruanjie.donkey.adapter;

import android.content.Context;
import android.view.View;

import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.UseHelpBean;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.softgarden.baselibrary.base.BaseRVAdapter;
import com.softgarden.baselibrary.base.BaseRVHolder;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.adapter
 * 文件名:   HelpListAdapter
 * 创建者:    QJM
 * 创建时间: 2019/8/14 17:48
 * 描述:     TODO
 */
public class HelpListAdapter extends BaseRVAdapter<UseHelpBean> {

    Context mContext;
    public HelpListAdapter(Context context) {
        super(R.layout.item_help_list);
        mContext = context;
    }


    @Override
    public void onBindVH(BaseRVHolder holder, UseHelpBean data, int position) {
            holder.setText(R.id.tv_list_text,data.getTitle());
            holder.getView(R.id.help_list_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.start(mContext,data.getTitle(),data.getContent_url());
                }
            });
    }
}
