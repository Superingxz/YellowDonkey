package com.ruanjie.donkey.api;

/**
 * @author by DELL
 * @date on 2017/12/22
 * @describe
 */

public interface HostUrl {

//    String HOST_URL_TEST = "http://www.xhlbike.cn/";

    String HOST_URL_TEST = "http://xiaohuanglv.test3.ruanjiekeji.com/";

    String HOST_URL = "http://www.xhlbike.cn/";

    /**
     * app接口Host
     */
    String GANK_URL = "https://gank.io/";
    /*** 模块 */
    String HOST_Gank_Image = "api/data/福利/{pageSize}/{page}";
    //登录
    String LOGIN_URL = "api/Login/login";
    //微信登录
    String WECHAT_LOGIN_URL = "api/Login/loginSocialite";
    //支付宝授权请求参数
    String ALI_LOGIN_REQUEST_PARAMS_URL = "api/Login/aliLoginRequestParams";
    //支付宝登录
    String ALI_LOGIN_URL = "api/Login/aliLogin";
    //退出登录
    String LOGIN_OUT_URL = "api/Login/logout";
    //微信小程序登录
    String WX_APP_LOGIN_URL = "api/Login/wxappLogin";
    //注册
    String REGISTER_URL = "api/Login/register";
    //发送短信验证码
    String SEND_VERIFICATION_CODE_URL = "api/Assistant/sendSms";
    //检测验证码
    String CHECK_VERIFICATION_CODE_URL = "api/Assistant/checkCode";
    //获取小程序openid
    String GET_OPENID_URL = "api/Login/getOpenid";
    //重置密码
    String RESET_PASSWORD_URL = "api/Login/resetPassword";
    //所有地区数据
    String ALL_AREA_URL = "api/Assistant/allArea";
    //服务协议
    String SERVICE_AGREEMENT_URL = "api/Assistant/service";
    //配置信息
    String CONFIG_URL = "api/Assistant/config";
    //使用帮助
    String USE_HELP_URL = "api/Assistant/helpList";
    //意见反馈
    String FEEDBACK_URL = "api/Assistant/feedback";
    //提交车辆故障
    String SUBMIT_BREAKDOWN_URL = "api/Assistant/breakdown";
    //违规申报
    String ILLEGAL_DECLARATION_URL = "api/Assistant/illegal";
    //获取微信小程序码
    String GET_WX_QRCODE_URL = "api/Assistant/getWxQrcode";
    //我的优惠券列表
    String MY_COUPON_LIST_URL = "api/Coupon/couponList";
    //领取优惠券
    String GET_COUPON_URL = "api/Coupon/couponGet";
    //我的行驶统计
    String MY_CAR_HISTORY_INDEX_URL = "api/History/carHistoryIndex";
    //我的行驶记录
    String MY_CAR_HISTORY_LIST_URL = "api/History/carHistoryList";
    //我的行驶记录详情
    String MY_CAR_HISTORY_DETAIL_URL = "api/History/carHistoryDetail";
    //消息列表
    String MESSAGE_LIST_URL = "api/Message/messageList";
    //消息详情
    String MESSAGE_DETAIL_URL = "api/Message/messageDetail";
    //未读消息数量
    String UNREAD_MESSAGE_NUM_URL = "api/Message/unreadNum";
    //余额记录列表
    String MONEY_LOG_LIST_URL = "api/Money/moneyLog";
    //余额充值
    String MONEY_RECHARGE_URL = "api/Money/moneyRecharge";
    //提现申请
    String WITHDRAW_URL = "api/Money/withdraw";
    //支付押金
    String PAY_DEPOSIT_URL = "api/Money/depositPay";
    //退押金
    String REFUND_DEPOSIT_URL = "api/Money/depositRefund";
    //退押金原因选项
    String REFUND_DEPOSIT_REASON_URL = "api/Money/depositRefundReason";
    //合伙人申请信息
    String PARTNER_APPLY_MESSAGE_URL = "api/Partner/partner";
    //合伙人申请
    String PARTNER_ADD_URL = "api/Partner/partnerAdd";
    //合伙人统计
    String PARTNER_STATISTICS_URL = "api/Partner/partnerIndex";
    //合伙人订单列表
    String PARTNER_ORDER_LIST_URL = "api/Partner/partnerOrderList";
    //合伙人订单详情
    String PARTNER_ORDER_DETAIL_URL = "api/Partner/partnerOrderDetail";
    //合伙人订单删除
    String PARTNER_ORDER_DEL_URL = "api/Partner/partnerOrderDel";
    //代理申请信息
    String AGENT_APPLY_MESSAGE_URL = "api/Partner/agent";
    //外协换电申请信息
    String ASSIST_CHANGE_POWER_APPLY_MESSAGE_URL = "api/Partner/assist";
    //外协换电申请
    String ASSIST_CHANGE_POWER_ADD_URL = "api/Partner/assistAdd";
    //首页
    String INDEX_URL = "api/Vehicle/index";
    //车辆列表
    String VEHICLE_LIST_URL = "api/Vehicle/carList";
    //车辆详情
    String VEHICLE_DETAIL_URL = "api/Vehicle/carDetail";
    //扫码开锁
    String SCAN_UNLOCK_URL = "api/Vehicle/unlock";
    //暂停用车
    String PAUSE_USE_VEHICLE_URL = "api/Vehicle/stop";
    //继续用车
    String CONTINUE_USE_VEHICLE_URL = "api/Vehicle/goAhead";
    //换车
    String TRANS_VEHICLE_URL = "api/Vehicle/trans";
    //结束用车
    String LOCK_VENHICLE_URL = "api/Vehicle/lock";
    //支付费用
    String PAY_ORDER_URL = "api/Vehicle/orderPay";
    //维护上传
    String MAINTAIN_UPLOAD_URL = "api/Vehicle/maintenanceUpload";
    //维护记录
    String MAINTAIN_LIST_URL = "api/Vehicle/maintenanceList";
    //换电统计
    String CHANGE_POWER_STATISTICS_URL = "api/Vehicle/exchangeIndex";
    //换电记录
    String CHANGE_POWER_LIST_URL = "api/Vehicle/exchangeList";
    //领取换电任务
    String ADD_CHANGE_POWER_TASK_URL = "api/Vehicle/pwoerExchangeAdd";
    //换电扫码开锁
    String CHANGE_POWER_SCAN_UNLOCK_URL = "api/Vehicle/pwoerExchange";
    //停车点列表
    String PARKING_LIST_URL = "api/Vehicle/parkingList";
    //是否在停车区域
    String IS_PARKING_AREA_URL = "api/Vehicle/isInside";
    //电子围栏列表
    String FENCE_LIST_URL = "api/Vehicle/fenceList";
    //确定显示过收费页面
    String SHOW_PRICE_URL = "api/Vehicle/showPrice";

    String UPLOAD_IMAGES_BASE64 = "api/Upload/base64Upload";

    String UPLOAD_IMAGES_STREAM = "api/Upload/fileUpload";

    String CHANGE_HEAD = "api/User/setAvatar";

    String CHANGE_USERINFO = "api/User/infoEdit";

    String CHANGE_PHONE = "api/User/changePhone";

    String REAL_NAME_APPLY = "api/User/realnameCheck";

    String GET_USERINFO = "api/User/userInfo";

    String CHANGE_PWD = "api/User/changePwd";
    String CHANGE_RESET_PASSWORD = "api/Login/resetPassword";

    String JOIN_AREA = "api/Partner/agentAdd";
    String JOIN_CITY = "api/Partner/partnerAdd";
    String GET_JOIN_AREA_INFO = "api/Partner/agent";
    String GET_JOIN_CITY_INFO = "api/Partner/partner";
    String GET_CURRENT_TODAY_HEADER = "api/Partner/partnerIndex";
    String GET_EX_CURRENT_TODAY_HEADER = "api/Vehicle/exchangeIndex";
    String GET_CURRENT_TODAY_DATAS = "api/Partner/partnerOrderList";
    String GET_EX_CURRENT_TODAY_DATAS = "api/Vehicle/exchangeList";
    String GET_CONFIG_INFO = "api/Assistant/config";
    String DELETE_TODAY_DATAS = "api/Partner/partnerOrderDel";
    String GET_TODAY_DATAS = "api/Partner/partnerOrderDetail";
    String RECHARGE = "api/Money/moneyRecharge";
    String GET_COUPONS = "api/Coupon/couponList";
    String DELETE_COUPONS = "api/Coupon/couponDel";
    String WITH_DRAW = "api/Money/withdraw";
    String WALLET_DETAIL = "api/Money/moneyLog";
    String PAY_DEPOSIT = "api/Money/depositPay";
    String MY_TRAVEL_STATISTICS = "api/History/carHistoryIndex";
    String MY_TRAVEL_STATISTICS_DATAS = "api/History/carHistoryList";
    String TRAVEL_DETAIL = "api/History/carHistoryDetail";
    String GET_ALI_LOGIN_DATAS = "api/Login/aliLoginRequestParams";
    String ALI_LOGIN = "api/Login/aliLogin";
    String WECHAT_LOGIN = "api/Login/loginSocialite";
    String GET_MAINTAIN_HISTORY = "api/Vehicle/maintenanceList";
    String UPLOAD_MAINTAIN = "api/Vehicle/maintenanceUpload";
    String EX_REAL_NAME_APPLY = "api/Partner/assistAdd";
    String GET_EX_REAL_NAME_DATA = "api/Partner/assist";
    String GET_EX_CHANGE_TASK = "api/Vehicle/pwoerExchangeAdd";
    String SCAN_EX_CHANGE = "api/Vehicle/pwoerExchange";
    String BACK_DEPOSIT = "api/Money/depositRefund";
    String BIND_PHONE = "api/User/bindPhone";
    String DELTETE_MESSAGE = "api/Message/messageDel";
    String GET_MESSAGE_DETAIL = "api/Message/messageDetail";
    String GET_UN_READ_COUNT = "api/Message/unreadNum";
    String CHECK_UPLOAD = "api/Assistant/version";


    String GET_SHOP_BANNERS = "api/Shop/banners";
    String GET_SHOP_SORTS = "api/Shop/storeCategoryList";
    String GET_SHOP_LIST = "api/Shop/storeList";
    String GET_SHOP_DETAIL = "api/Shop/storeDetail";
    String BUY_GOODS = "api/Shop/orderAdd";
    String GET_COUPON_LIST = "api/Shop/couponList";
    String EXCHANGE_COUPON = "api/Shop/couponBuy";
    String GET_GOODS_DETAIL = "api/Shop/goodsDetail";
    String GET_APPRAISE_LIST = "api/Shop/commentList";
    String GET_SHOP_ORDER_LIST = "api/Shop/orderList";
    String SUBMIT_ORDER = "api/Shop/orderDone";
    String DELETE_ORDER = "api/Shop/orderDel";
    String APPRAICE_GOODS = "api/Shop/comment";
    String GET_COIN_LIST = "api/Shop/scoreLog";
    String UPLOAD_IMAGES_BASE64_2 = "api/Upload/base64Upload2";

}
