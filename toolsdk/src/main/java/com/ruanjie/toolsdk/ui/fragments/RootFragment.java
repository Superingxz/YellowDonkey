package com.ruanjie.toolsdk.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.toolsdk.R;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.IBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.toolsdk.ui.fragments
 * 文件名:   RootFragment
 * 创建者:    QJM
 * 创建时间: 2019/8/1 14:52
 * 描述:     TODO
 */
public abstract class RootFragment<P extends IBasePresenter> extends ProxyFragment<P>{

    protected BaseToolbar mBaseToolbar;

    @SuppressWarnings("unchecked")
    public <T extends RootFragment> T getParentFragments() {
        return (T) getParentFragment();
    }


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

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    private View setSupportToolbar(View mView) {

//        StatusBarUtil.setStatusBarColor(getActivity(), ContextCompat.getColor(BaseApplication.getInstance(),R.color.white));
        /*** 这里可以对Toolbar进行统一的预设置 */
        BaseToolbar.Builder builder = new BaseToolbar.Builder(getContext())
                .setBackButton(R.mipmap.back_icon)//统一设置返回键
//                  .setStatusBarColor(Color.TRANSPARENT)//统一设置颜色
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent))
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
            layout.setOrientation(LinearLayoutCompat.VERTICAL);
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


    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
//        PgyCrashManager.unregister();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventBusBean busBean) {
        //弹对话框
        mEventBus(busBean);
    }

    //所有ToolBarActivity都能收到eventbus回调，需要使用时候调用此方法
    public void mEventBus(EventBusBean busBean) {
        //如果有全局的弹框，什么之类的在这里做
    }


    /**
     * 不需要toolbar的 可以不用管
     *
     * @return
     */
    @Nullable
    protected abstract BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder);
}
