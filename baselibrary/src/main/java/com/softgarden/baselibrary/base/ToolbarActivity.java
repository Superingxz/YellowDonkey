package com.softgarden.baselibrary.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.mirkowu.statusbarutil.StatusBarUtil;
import com.softgarden.baselibrary.R;
import com.softgarden.baselibrary.utils.BaseSPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public abstract class ToolbarActivity<P extends IBasePresenter> extends BaseActivity<P> {

    private BaseToolbar mBaseToolbar;


    @Override
    public void bindView() {
        /*** 这里可以对Toolbar进行统一的预设置 */
        BaseToolbar.Builder builder
                = new BaseToolbar.Builder(getContext())
                .setBackButton(R.mipmap.back_icon)//统一设置返回键
                //    .setStatusBarColor(ContextUtil.getColor(R.color.colorPrimary))//统一设置颜色
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white))
//                .setSubTextColor(Color.WHITE)
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
            View mView = getActivity().getLayoutInflater().inflate((int) getLayoutId(), layout, false);
            layout.addView(mView);

            setContentView(layout);

            //将toolbar设置为actionbar
            setSupportActionBar(mBaseToolbar);
        } else {
           if (getLayoutId() instanceof Integer){
             setContentView((int) getLayoutId());
           }else if (getLayoutId() instanceof View){
             setContentView((View) getLayoutId());
           }

        }

        //设置沉浸式透明状态栏
        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setStatusBarColor(getActivity(), ContextCompat.getColor(getApplicationContext(),R.color.transparent));
//        StatusBarUtil.setStatusBarLightMode(getActivity(), StatusBarUtil.setStatusBarLightMode(getActivity()));

        //ButterKnife
        unbinder = ButterKnife.bind(this);

        //非夜间模式 要开启亮色模式
         setStatusBarLightMode();
    }

    public void setStatusBarLightMode() {
        if (!BaseSPManager.isNightMode()) {
            if (StatusBarUtil.setStatusBarLightModeWithNoSupport(getActivity(), true)) {
                if (getToolbar() != null) getToolbar().hideStatusBar();
            }
        }
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
    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onDestroy() {
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

}


