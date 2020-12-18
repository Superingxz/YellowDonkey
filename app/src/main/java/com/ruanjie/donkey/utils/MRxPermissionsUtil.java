package com.ruanjie.donkey.utils;

import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by MirkoWu on 2017/4/5 0005.
 */

public class MRxPermissionsUtil {
    public static Observable<Boolean> requestCamera(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.CAMERA);
    }

    public static Observable<Boolean> requestStorage(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static Observable<Boolean> requestCallPhone(Activity activity) {
        return new RxPermissions(activity).request(Manifest.permission.CALL_PHONE);
    }

    public static Observable<Permission> requestMore(Activity activity, String... permissions) {
        return new RxPermissions(activity).requestEach(permissions);
    }

    public static boolean isHasAll(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if(!new RxPermissions(activity).isGranted(permission)){
                return false;
            }
        }
        return true;
    }
}
