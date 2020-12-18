package com.ruanjie.donkey.bean;


import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;

public class RechargeBean {


    /**
     * wechat : {"appid":"wxdda72d5a2773ac6a","partnerid":"1548852781","prepayid":"wx09171102224177db055d05411801426300","noncestr":"5d4d38a63c8cd","timestamp":1565341862,"package":"Sign=WXPay","sign":"ECA68FDC34317E45435E772D2D2801FB","packageValue":"Sign=WXPay"}
     * alipay :
     * unionpay :
     * pay_type : 1
     * is_redirect : 1
     */

    private WechatBean wechat;
    private String alipay;
    private String unionpay;
    private String pay_type;
    private int is_redirect;

    public WechatBean getWechat() {
        return wechat;
    }

    public void setWechat(WechatBean wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(String unionpay) {
        this.unionpay = unionpay;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getIs_redirect() {
        return is_redirect;
    }

    public void setIs_redirect(int is_redirect) {
        this.is_redirect = is_redirect;
    }

    public static class WechatBean {
        /**
         * appid : wxdda72d5a2773ac6a
         * partnerid : 1548852781
         * prepayid : wx09171102224177db055d05411801426300
         * noncestr : 5d4d38a63c8cd
         * timestamp : 1565341862
         * package : Sign=WXPay
         * sign : ECA68FDC34317E45435E772D2D2801FB
         * packageValue : Sign=WXPay
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private int timestamp;
        @SerializedName("package")
        private String packageX;
        private String sign;
        private String packageValue;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }
    }
}
