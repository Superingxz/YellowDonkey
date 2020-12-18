package com.ruanjie.donkey.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ruanjie.toolsdk.ui.fragments.RootFragment;

/**
 * 项目名:   WabaoEC
 * 包名:     com.qjm.wabao.util
 * 文件名:   GlideLoader
 * 创建者:    QJM
 * 创建时间: 2019/6/6 15:53
 * 描述:     TODO
 */

public class GlideLoader {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .centerCrop()
            .dontAnimate();

    public static AppCompatImageView show(Context context, Object path, AppCompatImageView imageView) {
//        Glide.with(context).load(path).apply(OPTIONS).into(imageView);
        Glide.with(context).load(path).apply(OPTIONS).into(imageView);
        return imageView;
    }
   /* public static void show(RootFragment fragment, Object path, ImageView imageView) {
        Glide.with(fragment).load(path).apply(OPTIONS).override(100,100).into(imageView);
    }*/
//    public static void display(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().into(imageView);
//    }
//
//    public static void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
//    }
//
    public static void showImage(RootFragment fragment, Object path, AppCompatImageView imageView) {
        Glide.with(fragment).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static AppCompatImageView create(Context context) {
        return new AppCompatImageView(context);
    }

}
