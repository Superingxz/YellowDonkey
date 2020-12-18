package com.ruanjie.donkey.ui.testMvp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.bean.ImageBean;
import com.softgarden.baselibrary.base.ToolbarActivity;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.ToastUtil;

import java.util.List;

/**
 * 1.这里要泛型 要记得写上当前Presenter  要继承view的接口Contract.Display
 */
public class TestMvpActivity extends ToolbarActivity<TestMvpPresenter> {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestMvpActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return builder.setTitle("MVP模板");
    }


    @Override
    protected Object getLayoutId() {
        return R.layout.activity_test_mvp;
    }

    @Override
    protected void initialize() {
        /**
         * 2.调用 多种方式任选
         */
        getPresenter().getIndexData().subscribe(new RxCallback<List<ImageBean>>() {
            @Override
            public void onSuccess(@Nullable List<ImageBean> data) {

            }
        });


        getPresenter().getIndexData().subscribe(goodsBeans -> {

        }, throwable -> showError(throwable));

    }


    public void onClick(View view) {
        ToastUtil.s(getString(R.string.app_name));
      //  ActivityManager.getInstance().finishAfter(MainActivity.class);
    }


}
