package com.ruanjie.donkey.ui.map;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import com.softgarden.baselibrary.base.BaseActivity;
import com.softgarden.baselibrary.base.EventBusBean;
import com.softgarden.baselibrary.base.IBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.map
 * 文件名:   TencentMapActivity
 * 创建者:    QJM
 * 创建时间: 2019/8/17 16:20
 * 描述:     TODO
 */
@Deprecated
public abstract class TencentMapActivity<P extends IBasePresenter> extends BaseActivity<P> implements ISupportActivity {

    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    public abstract TencentMapFragment mapFragment();

    @IdRes
    public abstract int setContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
       /* final FrameLayout container = new FrameLayout(this);
        container.setId(R.id.fragment_container);
        setContentView(container);*/
        if (savedInstanceState == null) {
            DELEGATE.loadRootFragment(setContainerId(), mapFragment());
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }


    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(new DefaultHorizontalAnimator());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        DELEGATE.post(runnable);
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }

    @Override
    public void onBackPressed() {
        DELEGATE.onBackPressed();
    }

}
