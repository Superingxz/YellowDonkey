package com.ruanjie.donkey.ui.drawer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.mirkowu.basetoolbar.BaseToolbar;
import com.ruanjie.donkey.R;
import com.ruanjie.donkey.utils.ImageUtil;
import com.ruanjie.donkey.utils.LogUtils;
import com.softgarden.baselibrary.base.ToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowBigImageActivity extends ToolbarActivity {
    @BindView(R.id.mPhotoView)
    PhotoView mPhotoView;

    public static void start(Context context, String imageStr, int type) {
        Intent starter = new Intent(context, ShowBigImageActivity.class);
        starter.putExtra("imageStr", imageStr);
        starter.putExtra("type", type);
        context.startActivity(starter);
    }

    @Override
    protected Object getLayoutId() {
        return R.layout.activity_show_big_image;
    }


    @Override
    protected void initialize() {
        String imageStr = getIntent().getStringExtra("imageStr");
        int type = getIntent().getIntExtra("type",0);
        LogUtils.i("imageStr", "imageStr = " + imageStr);
        if(type==0){//不需要补充url
            ImageUtil.loadImageNone(mPhotoView, imageStr);
        }else {
            ImageUtil.loadImage(mPhotoView, imageStr);
        }
    }

    @Nullable
    @Override
    protected BaseToolbar.Builder setToolbar(@NonNull BaseToolbar.Builder builder) {
        return null;
    }

    @OnClick({R.id.mPhotoView})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mPhotoView:
                finish();
                break;
        }
    }
}
