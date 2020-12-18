package com.ruanjie.donkey.ui.demo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ImageBean;
import com.ruanjie.donkey.ui.demo.contract.DemoContract;
import com.ruanjie.donkey.ui.demo.presenter.DemoPresenter;
import com.softgarden.baselibrary.base.ToolbarActivity;

import java.util.List;

/**
 * @author Moligy
 * @date 2019/7/22.
 */
public class DemoActivity extends ToolbarActivity<DemoPresenter> implements DemoContract.Display {

    public static void start(Context context) {
        Intent starter = new Intent(context, DemoActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }
    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("demo");
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void initialize() {
        initView();
        loadData();
    }

    private void initView() {

    }

    private void loadData() {
        getPresenter().getData();
    }


    @Override
    public void getData(List<ImageBean> imageBeans) {

    }
}
