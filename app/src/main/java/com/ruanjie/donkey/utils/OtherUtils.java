package com.ruanjie.donkey.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.ruanjie.donkey.widget.Glide4Engine;
import com.softgarden.baselibrary.utils.AppUtil;
import com.softgarden.baselibrary.utils.L;
import com.softgarden.baselibrary.utils.RxPermissionsUtil;
import com.softgarden.baselibrary.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import io.reactivex.functions.Consumer;

public class OtherUtils {
    public static final int IMAGE_BACK = 0x01;
    public static final int VIDEO_BACK = 0x02;

    public static void selectImages(Activity activity, int count) {
        if (!MRxPermissionsUtil.isHasAll(activity, RxPermissionsUtil.CAMERA_STORAGE)) {
            MRxPermissionsUtil.requestMore(activity, RxPermissionsUtil.CAMERA_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {

                        }
                    });
        } else {
            Matisse.from(activity)
                    .choose(MimeType.ofImage(), false) //参数1 显示资源类型 参数2 是否可以同时选择不同的资源类型 true表示不可以 false表示可以
//            .theme(R.style.Matisse_Dracula) //选择主题 默认是蓝色主题，Matisse_Dracula为黑色主题
                    .countable(true) //是否显示数字
                    .capture(true)  //是否可以拍照
                    .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            new CaptureStrategy(true, AppUtil.getPackageInfo(activity).packageName + ".fileprovider"))
                    .maxSelectable(count)  //最大选择资源数量
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K)) //添加自定义过滤器
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size)) //设置列宽
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) //设置屏幕方向
                    .thumbnailScale(0.85f)  //图片缩放比例
                    .imageEngine(new Glide4Engine())  //选择图片加载引擎
                    .forResult(IMAGE_BACK);  //设置requestcode,开启Matisse主页面
        }
    }

    public static void selectVideos(Activity activity, int count) {
        if (!MRxPermissionsUtil.isHasAll(activity, RxPermissionsUtil.CAMERA_STORAGE)) {
            MRxPermissionsUtil.requestMore(activity, RxPermissionsUtil.CAMERA_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                        }
                    });
        } else {
            Matisse.from(activity)
                    .choose(MimeType.ofVideo(), false)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            new CaptureStrategy(true, AppUtil.getPackageInfo(activity).packageName + ".fileprovider", "test"))
                    .maxSelectable(1)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                    .imageEngine(new Glide4Engine())    // for glide-V4
                    .setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void onSelected(
                                @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                            // DO SOMETHING IMMEDIATELY HERE
                            Log.e("onSelected", "onSelected: pathList=" + pathList);

                        }
                    })
                    .originalEnable(true)
                    .maxOriginalSize(10)
//                    .addFilter(new VideoSizeFilter(5 * Filter.K * Filter.K)) //添加自定义过滤器
                    .autoHideToolbarOnSingleTap(true)
                    .setOnCheckedListener(new OnCheckedListener() {
                        @Override
                        public void onCheck(boolean isChecked) {
                            // DO SOMETHING IMMEDIATELY HERE
                            Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                        }
                    })
                    .forResult(VIDEO_BACK);
//            Matisse.from(activity)
//                    .choose(MimeType.ofVideo(), false) //参数1 显示资源类型 参数2 是否可以同时选择不同的资源类型 true表示不可以 false表示可以
////            .theme(R.style.Matisse_Dracula) //选择主题 默认是蓝色主题，Matisse_Dracula为黑色主题
//                    .countable(true) //是否显示数字
//                    .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
//                            new CaptureStrategy(true, AppUtil.getPackageInfo(activity).packageName + ".fileprovider"))
//                    .maxSelectable(count)  //最大选择资源数量
////                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K)) //添加自定义过滤器
////                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size)) //设置列宽
//                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) //设置屏幕方向
//                    .forResult(Constants.VIDEO_BACK);  //设置requestcode,开启Matisse主页面
        }
    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    //明文密文输入框修改
    public static void changeEye(View v, AppCompatEditText editText) {
        boolean isSelected = !v.isSelected();
        v.setSelected(isSelected);
        editText.setTransformationMethod(isSelected
                ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
        editText.setSelection(editText.getText().length());
    }

    //把bitmap转换成String(上传图片使用)
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样 JPG会忽略透明度，png不会
        bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] b = baos.toByteArray();
        L.i("bitmapToString", "压缩后的大小=" + b.length);
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 6-16位英文数字符号
     *
     * @param pass
     * @return
     */
    public static boolean checkPwd6_16(String pass) {
        String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (pass.matches(passRegex)) {
            return true;
        } else {
            ToastUtil.s("请输入6-16位英文数字符号");
            return false;
        }
    }


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从本地path中获取bitmap，压缩后保存小图片到本地
     *
     * @param path 图片存放的路径
     * @return 返回压缩后图片的存放路径
     */
    public static String saveBitmap(String path) {

        //★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
      /*  //★如果不压缩直接从path获取bitmap，这个bitmap会很大，下面在压缩文件到100kb时，会循环很多次，
        // ★而且会因为迟迟达不到100k，options一直在递减为负数，直接报错
        //★ 即使原图不是太大，options不会递减为负数，也会循环多次，UI会卡顿，所以不推荐不经过压缩，直接获取到bitmap
        Bitmap bitmap=BitmapFactory.decodeFile(path);*/
        //      ★★★★★★★★★★★★★★重点★★★★★★★★★★★★★
        Bitmap bitmap = decodeSampledBitmapFromPath(path, 720, 1280);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        while (baos.toByteArray().length / 1024 > 200) {
            // 循环判断如果压缩后图片是否大于500kb继续压缩
            baos.reset();
            options -= 10;
            if (options < 11) {//为了防止图片大小一直达不到200kb，options一直在递减，当options<0时，下面的方法会报错
                // 也就是说即使达不到200kb，也就压缩到10了
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                break;
            }
            // 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        String mDir = Environment.getExternalStorageDirectory() + "/zj";
        File dir = new File(mDir);
        if (!dir.exists()) {
            dir.mkdirs();//文件不存在，则创建文件
        }
        File file = new File(mDir, ".jpg");
        FileOutputStream fOut = null;

        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
            return file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            return "";

        }
    }

    /**
     * 根据图片要显示的宽和高，对图片进行压缩，避免OOM
     *
     * @param path
     * @param width  要显示的imageview的宽度
     * @param height 要显示的imageview的高度
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {

//      获取图片的宽和高，并不把他加载到内存当中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);
//      使用获取到的inSampleSize再次解析图片(此时options里已经含有压缩比 options.inSampleSize，再次解析会得到压缩后的图片，不会oom了 )
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }


    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth  要显示的imageview的宽度
     * @param reqHeight 要显示的imageview的高度
     * @return
     * @compressExpand 这个值是为了像预览图片这样的需求，他要比所要显示的imageview高宽要大一点，放大才能清晰
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width >= reqWidth || height >= reqHeight) {

            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(width * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);

        }

        return inSampleSize;
    }

    public static boolean stringIsNull(AppCompatEditText editText, String toastStr) {
        String textStr = editText.getText().toString().trim();
        if (TextUtils.isEmpty(textStr)) {
            ToastUtil.s(toastStr);
            return true;
        } else {
            return false;
        }
    }
    public static boolean stringIsNull(TextView textView, String toastStr) {
        String textStr = textView.getText().toString().trim();
        if (TextUtils.isEmpty(textStr)) {
            ToastUtil.s(toastStr);
            return true;
        } else {
            return false;
        }
    }
    public static boolean stringIsSame(AppCompatEditText editText1
            , AppCompatEditText editText2, String toastStr) {
        String textStr1 = editText1.getText().toString().trim();
        String textStr2 = editText2.getText().toString().trim();
        if(textStr1.equals(textStr2)){
            return true;
        }else{
            ToastUtil.s(toastStr);
            return false;
        }
    }
}
