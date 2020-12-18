package com.ruanjie.donkey.ui.message;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.adapter.MessageListAdapter;
import com.ruanjie.donkey.bean.MessageListBean;
import com.ruanjie.donkey.ui.message.contract.MyMessageContract;
import com.ruanjie.donkey.ui.message.presenter.MyMessagePresenter;
import com.ruanjie.donkey.ui.webView.WebViewActivity;
import com.ruanjie.donkey.utils.DiaLogUtils;
import com.softgarden.baselibrary.base.RefreshActivity;
import com.softgarden.baselibrary.dialog.PromptDialog;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.List;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.message
 * 文件名:   MyMessageActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/5 2:35
 * 描述:     TODO
 */
public class MyMessageActivity extends RefreshActivity<MyMessagePresenter> implements MyMessageContract.View {


    private MessageListAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyMessageActivity.class);
        // starter.putExtra(F);
        context.startActivity(starter);
    }

    @Override
    public MyMessagePresenter createPresenter() {
        return new MyMessagePresenter();
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle(R.string.my_message)
                .addRightText("全部刪除", v -> rightClick());
    }

    private void rightClick() {
        DiaLogUtils.showTipDialog(getContext(), "溫馨提示", "确定删除所有消息？", "取消", "确定", new PromptDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(PromptDialog dialog, boolean isPositiveClick) {
                if (isPositiveClick) {
                    getPresenter().deleteMessage(0);
                }
            }
        });
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initialize() {
        initRefreshLayout();
        initRecyclerView();
        mAdapter = new MessageListAdapter(getContext());
        //下面2句话开启上拉加载
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.message_layout:
                        getPresenter().getMessageDetail(mAdapter.getItem(position).getId());
                        mAdapter.getItem(position).setStatus(1);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case R.id.tvDelete:
                        getPresenter().deleteMessage(mAdapter.getItem(position).getId());
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {
        getPresenter().getMessageList(0, mPage, PAGE_COUNT);
    }

    @Override
    public void getMessageList(List<MessageListBean> data) {
//        if (mPage == 1) {
//            setLoadData(mAdapter, data);
//        } else {
//            setLoadMore(mAdapter, data);
//        }

        setLoadMore(mAdapter, data);
    }

    @Override
    public void deleteMessage(String data) {
        ToastUtil.s("删除成功");
        getPresenter().getMessageList(0, mPage, PAGE_COUNT);
    }

    @Override
    public void getMessageDetail(MessageListBean data) {
        if (data!=null){
            WebViewActivity.start(getContext(), data.getTitle(), data.getContent_url(),data.getId(),data.getCoupon_id());
        }

//        loadData();
//        getPresenter().getMessageList(0, mPage, PAGE_COUNT);
    }

}
