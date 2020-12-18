package com.softgarden.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.baselibrary.R;

import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


/**
 */

public abstract class ToolbarFragment<P extends IBasePresenter> extends RefreshFragment<P> {

    private BaseToolbar mBaseToolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView;
        if (getLayoutId() instanceof Integer) {
            mView = inflater.inflate((int) getLayoutId(), container, false);
        } else if (getLayoutId() instanceof View) {
            mView = (View) getLayoutId();
        } else {
            throw new ClassCastException("type of getLayoutId() must be int or View");
        }
        if (mView != null) {
            mView = setSupportToolbar(mView);
            unbinder = ButterKnife.bind(this, mView);
        }
        return mView;
    }

    private View setSupportToolbar(View mView) {

        StatusBarUtil.setStatusBarColor(getActivity(), ContextCompat.getColor(BaseApplication.getInstance(),R.color.white));
        /*** 这里可以对Toolbar进行统一的预设置 */
        BaseToolbar.Builder builder = new BaseToolbar.Builder(getContext())
                  .setBackButton(R.mipmap.back_icon)//统一设置返回键
//                  .setStatusBarColor(Color.TRANSPARENT)//统一设置颜色
                  .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white))
//                  .setSubTextColor(Color.WHITE)
                  .setTitleTextColor(ContextCompat.getColor(getContext(), R.color.text_black));

        builder = setToolbar(builder);
        if (builder != null) {
            mBaseToolbar = builder.build();
        }

        if (mBaseToolbar != null) {
            //添加Toolbar
            LinearLayoutCompat layout = new LinearLayoutCompat(getContext());
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(mBaseToolbar);
            layout.addView(mView);
            return layout;
        }

        return mView;
    }

    public BaseToolbar getToolbar() {
        return mBaseToolbar;
    }


    public void showToolbar() {
        if (mBaseToolbar != null) mBaseToolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar() {
        if (mBaseToolbar != null) mBaseToolbar.setVisibility(View.GONE);
    }


    /**
     * 不需要toolbar的 可以不用管
     *
     * @return
     */
    @Nullable
    protected abstract BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder);
}
