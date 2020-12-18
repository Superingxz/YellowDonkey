package com.ruanjie.donkey.ui.webView.contract;

import com.softgarden.baselibrary.base.IBaseDisplay;
import com.softgarden.baselibrary.base.IBasePresenter;

/**
 * 项目名:   YellowDonkey
 * 包名:     com.ruanjie.donkey.ui.webView.contract
 * 文件名:   WebViewContract
 * 创建者:    QJM
 * 创建时间: 2019/8/22 18:22
 * 描述:     TODO
 */
public interface WebViewContract {

    interface View extends IBaseDisplay {
        void getCouponSuccess();
    }

    interface Model extends IBasePresenter {
        void getCoupon(int messageId,int couponId);
    }
}
