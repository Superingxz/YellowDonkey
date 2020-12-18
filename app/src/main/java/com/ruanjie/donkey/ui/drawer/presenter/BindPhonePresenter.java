package com.ruanjie.donkey.ui.drawer.presenter;

import com.ruanjie.donkey.api.BindPhoneParameterInterceptor;
import com.ruanjie.donkey.api.HostUrl;
import com.ruanjie.donkey.api.RetrofitClient;
import com.ruanjie.donkey.api.Service;
import com.ruanjie.donkey.api.TestService;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.ui.drawer.BindPhoneActivity;
import com.ruanjie.donkey.ui.drawer.contract.BindPhoneContract;
import com.softgarden.baselibrary.base.BasePresenter;
import com.softgarden.baselibrary.network.NetworkTransformer;
import com.softgarden.baselibrary.network.RxCallback;
import com.softgarden.baselibrary.utils.L;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.sign.presenter
 * 文件名:   LoginPresenter
 * 创建者:    QJM
 * 创建时间: 2019/7/29 11:28
 * 描述:     TODO
 */
public class BindPhonePresenter extends BasePresenter implements BindPhoneContract.Model {

    @Override
    public void bindPhone(String user_id, String token, String phone, String code, String password, String repassword) {

        Retrofit build = new Retrofit.Builder().baseUrl(HostUrl.HOST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(user_id,token))
                .build();

        build.create(Service.class)
                .bindPhone(phone, code, password, repassword)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        ((BindPhoneActivity) mView).bindPhone(data);
                    }
                });
//        RetrofitClient.getTestService()
//                .bindPhone(phone, code, password, repassword)
//                .compose(new NetworkTransformer<>(mView))
//                .subscribe(new RxCallback<LoginBean>() {
//                    @Override
//                    public void onSuccess(LoginBean data) {
//                        ((BindPhoneActivity) mView).bindPhone(data);
//                    }
//                });
    }

    @Override
    public void getCode(String phone, int type) {
        RetrofitClient.getService()
                .getCode(phone, type)
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ((BindPhoneActivity) mView).getCode(data);
                    }
                });
    }

    @Override
    public void getUserInfo(String user_id, String token) {

        Retrofit build = new Retrofit.Builder().baseUrl(HostUrl.HOST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(user_id,token))
                .build();

        build.create(Service.class)
                .getUserInfo()
                .compose(new NetworkTransformer<>(mView))
                .subscribe(new RxCallback<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean data) {
                        ((BindPhoneActivity) mView).getUserInfo(data);
                    }
                });
    }

    public static OkHttpClient getOkHttpClient(String user_id, String token) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置超时
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                //需要对请求参数进行处理的时候添加
                .addInterceptor(new BindPhoneParameterInterceptor(user_id,token))// 拦截器
                .addInterceptor(loggingInterceptor);

        return builder.build();
    }

    public static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    L.w("RetrofitLog", message + "");
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);
}
