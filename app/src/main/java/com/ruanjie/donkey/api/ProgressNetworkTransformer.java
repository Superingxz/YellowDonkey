package com.ruanjie.donkey.api;

import com.ruanjie.donkey.ui.unlock.contract.WaitUnlockContract;
import com.softgarden.baselibrary.network.ApiException;
import com.softgarden.baselibrary.network.BaseBean;
import com.softgarden.baselibrary.network.RxJava2NullException;
import com.softgarden.baselibrary.utils.NetworkUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.api
 * 文件名:   ProgressNetworkTransformer
 * 创建者:    QJM
 * 创建时间: 2019/8/12 16:41
 * 描述:     TODO
 */
public class ProgressNetworkTransformer <T> implements ObservableTransformer<BaseBean<T>, T> {

    private WaitUnlockContract.View view;
    private boolean showProgress;

    public  ProgressNetworkTransformer(WaitUnlockContract.View view) {
        this(view, true);
    }

    public  ProgressNetworkTransformer(WaitUnlockContract.View view, boolean showProgress) {
        if (view == null) throw new RuntimeException("WaitUnlockContract.View is not NULL");
        this.view = view;
        this.showProgress = showProgress;
    }

    @Override
    public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    //请求前检测网络
                    if (!NetworkUtil.isConnected(view.getContext())) {
                        NetworkUtil.showNoNetWorkDialog(view.getContext());
                        view.onRequestFinish();
                        disposable.dispose();
                    } else {
                        if (showProgress) view.showProgress();
                    }
                })
                .doFinally(() -> {
                    if (showProgress) view.showProgress();
                    view.onRequestFinish();
                })
                .map(filterData())
                .map(checkInnerData())
                .doOnError(throwable -> {
                    //RxJava2NullException 交给RxCallback处理
                    if (!(throwable instanceof RxJava2NullException)) {
                        view.showError(throwable);
                    }
                })
                .compose(view.bindToLifecycle());

    }

    /**
     * 过滤异常
     *
     * @return
     */
    public Function<? super BaseBean<T>, BaseBean<T>> filterData() {
        return (Function<BaseBean<T>, BaseBean<T>>) baseBean -> {
            if (baseBean.status == 1) {
                return baseBean;
            } else {
//                if (baseBean.status == -1) {
//                    mView.showReLoginDialog();
//                }
                throw new ApiException(baseBean.status, baseBean.info);
            }
        };
    }

    /**
     * 提取内部真正数据，并检测数据是否为空
     *
     * @return
     */
    public Function<? super BaseBean<T>, T> checkInnerData() {
        return (Function<BaseBean<T>, T>) baseBean -> {
            if (baseBean.data != null) {
                return baseBean.data;
            }else{
                return (T)"";
            }
            //返回空数据时 抛出一个异常让CallBack处理
//            throw new RxJava2NullException();
        };
    }
}
