package com.ruanjie.toolsdk.ui.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ruanjie.toolsdk.R;
import com.vondear.rxtool.RxRegTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxui.view.dialog.RxDialog;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.softgarden.baselibrary.dialog
 * 文件名:   SureDialog
 * 创建者:    QJM
 * 创建时间: 2019/8/29 19:20
 * 描述:     TODO
 */
public class SureDialog extends RxDialog implements View.OnClickListener {

    private AppCompatTextView mTvTitle;
    private AppCompatTextView mTvContent;
    private AppCompatTextView mTvSure;
    private OnSureClickListener listenter;

    public SureDialog(Context context) {
        super(context);
        initView();
    }

    public SureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected SureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public SureDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public AppCompatTextView getTitleView() {
        return mTvTitle;
    }

    public AppCompatTextView getSureView() {
        return mTvSure;
    }


    public TextView getContentView() {
        return mTvContent;
    }


    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setSure(String text) {
        mTvSure.setText(text);
    }

    public void setContent(String content) {
        if (RxRegTool.isURL(content)) {
            // 响应点击事件的话必须设置以下属性
            mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
            mTvContent.setText(RxTextTool.getBuilder("").setBold().append(content).setUrl(content).create());//当内容为网址的时候，内容变为可点击
        } else {
            mTvContent.setText(content);
        }

    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure, null);
        mTvTitle = dialogView.findViewById(R.id.tv_title);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mTvSure = dialogView.findViewById(R.id.tv_sure);
        mTvTitle.setTextIsSelectable(true);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setTextIsSelectable(true);
        setContentView(dialogView);
        mTvSure.setOnClickListener(this);
    }

    public void setSureListener(View.OnClickListener listener) {
        mTvSure.setOnClickListener(listener);
    }

    public SureDialog setOnSureListener(OnSureClickListener listener) {
        this.listenter = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (listenter != null){
            listenter.onSureClick(this);
        }
        dismiss();
    }

    public interface OnSureClickListener {
        void onSureClick(SureDialog dialog);
    }

}
