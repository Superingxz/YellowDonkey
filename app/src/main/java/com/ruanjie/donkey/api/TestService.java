package com.ruanjie.donkey.api;


import com.ruanjie.donkey.bean.AgreementBean;
import com.ruanjie.donkey.bean.AliLoginBean;
import com.ruanjie.donkey.bean.ConfigBean;
import com.ruanjie.donkey.bean.CouponBean;
import com.ruanjie.donkey.bean.CurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExCurrentTodayHeaderBean;
import com.ruanjie.donkey.bean.ExRealNameBean;
import com.ruanjie.donkey.bean.ExTodayDatasBean;
import com.ruanjie.donkey.bean.FenceListBean;
import com.ruanjie.donkey.bean.GankBaseBean;
import com.ruanjie.donkey.bean.ImageBean;
import com.ruanjie.donkey.bean.IndexBean;
import com.ruanjie.donkey.bean.JoinAreaInfoBean;
import com.ruanjie.donkey.bean.JoinCityInfoBean;
import com.ruanjie.donkey.bean.LockBean;
import com.ruanjie.donkey.bean.LoginBean;
import com.ruanjie.donkey.bean.MainTainBean;
import com.ruanjie.donkey.bean.MessageListBean;
import com.ruanjie.donkey.bean.NotifyMessageBean;
import com.ruanjie.donkey.bean.ParkingAreaBean;
import com.ruanjie.donkey.bean.ParkingListBean;
import com.ruanjie.donkey.bean.PriceBean;
import com.ruanjie.donkey.bean.RechargeBean;
import com.ruanjie.donkey.bean.RegisterBean;
import com.ruanjie.donkey.bean.TodayDatasBean;
import com.ruanjie.donkey.bean.TodayDatasDetailBean;
import com.ruanjie.donkey.bean.TokenBean;
import com.ruanjie.donkey.bean.TravelDetailBean;
import com.ruanjie.donkey.bean.TravelStatisticsBean;
import com.ruanjie.donkey.bean.UnReadBean;
import com.ruanjie.donkey.bean.UnlockBean;
import com.ruanjie.donkey.bean.UploadBean;
import com.ruanjie.donkey.bean.UseHelpBean;
import com.ruanjie.donkey.bean.VehicleDetailBean;
import com.ruanjie.donkey.bean.VehicleListBean;
import com.ruanjie.donkey.bean.VersionBean;
import com.ruanjie.donkey.bean.WalletDetailBean;
import com.softgarden.baselibrary.network.BaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface TestService {


    /**
     * 测试接口
     *
     * @return
     */

    //接口有参数时 要写上该注解
    // @FormUrlEncoded
    @POST(HostUrl.HOST_Gank_Image)
    Observable<BaseBean<List<ImageBean>>> getData(/*@Field("is_new") int is_new*/);

    /**
     * gank 图片列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET(HostUrl.HOST_Gank_Image)
    Observable<GankBaseBean<List<ImageBean>>> getImages(@Path("page") int page, @Path("pageSize") int pageSize);

    @POST
    Observable<BaseBean<List<ImageBean>>> getData(@Url String url/*@Field("is_new") int is_new*/);

    @FormUrlEncoded
    @POST(HostUrl.LOGIN_URL)
    Observable<BaseBean<LoginBean>> getLoginData(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST(HostUrl.REGISTER_URL)
    Observable<BaseBean<RegisterBean>> getRegisterData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.SEND_VERIFICATION_CODE_URL)
    Observable<BaseBean<String>> getVerificationCodeData(@Field("phone") String phone, @Field("type") int type);

    @FormUrlEncoded
    @POST(HostUrl.SERVICE_AGREEMENT_URL)
    Observable<BaseBean<AgreementBean>> getAgreementData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.CHECK_VERIFICATION_CODE_URL)
    Observable<BaseBean<String>> checkVerificationCodeData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.GET_COUPON_URL)
    Observable<BaseBean<String>> getCouponData(@Field("message_id") int message_id, @Field("coupon_id") int coupon_id);

    @FormUrlEncoded
    @POST(HostUrl.UPLOAD_IMAGES_BASE64)
    Observable<BaseBean<UploadBean>> uploadImage(
            @Field("content") String content
    );

    @Multipart
    @POST(HostUrl.UPLOAD_IMAGES_STREAM)
    Observable<BaseBean<String>> uploadImage2(
            @Part() List<MultipartBody.Part> parts
//            @Part("type") RequestBody type,
//            @Part("code") RequestBody code
    );

    @FormUrlEncoded
    @POST(HostUrl.CHANGE_HEAD)
    Observable<BaseBean<UploadBean>> changeHead(
            @Field("avatar") String avatar
    );

    @FormUrlEncoded
    @POST(HostUrl.CHANGE_USERINFO)
    Observable<BaseBean<String>> changeUserInfo(
            @Field("sex") int sex,
            @Field("nickname") String nickname,
            @Field("birthday") String birthday
    );

    @POST(HostUrl.GET_USERINFO)
    Observable<BaseBean<LoginBean>> getUserInfo(
    );

    @FormUrlEncoded
    @POST(HostUrl.CHANGE_PHONE)
    Observable<BaseBean<String>> changePhone(
            @Field("new_phone") String new_phone,
            @Field("new_code") String new_code
    );

    @FormUrlEncoded
    @POST(HostUrl.SEND_VERIFICATION_CODE_URL)
    Observable<BaseBean<String>> getCode(
            @Field("phone") String phone,
            @Field("type") int type
    );

    @FormUrlEncoded
    @POST(HostUrl.REAL_NAME_APPLY)
    Observable<BaseBean<String>> realNameApply(
            @Field("username") String username,
            @Field("idcard") String idcard,
            @Field("idcard_font") String idcard_font,
            @Field("idcard_back") String idcard_back,
            @Field("idcard_hand") String idcard_hand
    );

    @FormUrlEncoded
    @POST(HostUrl.CHANGE_PWD)
    Observable<BaseBean<TokenBean>> changePwd(
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("re_password") String re_password
    );

    //重置密码
    @FormUrlEncoded
    @POST(HostUrl.CHANGE_RESET_PASSWORD)
    Observable<BaseBean<String>> resetPwd(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("password") String password,
            @Field("repassword") String repassword
    );

    @FormUrlEncoded
    @POST(HostUrl.JOIN_AREA)
    Observable<BaseBean<String>> joinArea(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("area_code") String area_code,
            @Field("area_id") String area_id
    );

    @FormUrlEncoded
    @POST(HostUrl.JOIN_CITY)
    Observable<BaseBean<String>> joinCity(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("num") String num
    );

    @POST(HostUrl.GET_JOIN_AREA_INFO)
    Observable<BaseBean<JoinAreaInfoBean>> getJoinAreaInfo();

    @POST(HostUrl.GET_JOIN_CITY_INFO)
    Observable<BaseBean<JoinCityInfoBean>> getJoinCityInfo();

    @FormUrlEncoded
    @POST(HostUrl.GET_CURRENT_TODAY_HEADER)
    Observable<BaseBean<CurrentTodayHeaderBean>> getCurrentTodayHeader(
            @Field("date") String date
    );
    @FormUrlEncoded
    @POST(HostUrl.GET_EX_CURRENT_TODAY_HEADER)
    Observable<BaseBean<ExCurrentTodayHeaderBean>> getExCurrentTodayHeader(
            @Field("date") String date
    );
    @FormUrlEncoded
    @POST(HostUrl.GET_CURRENT_TODAY_DATAS)
    Observable<BaseBean<List<TodayDatasBean>>> getCurrentTodayDatas(
            @Field("date") String date,
            @Field("page") String page,
            @Field("pageSize") String pageSize
    );
    @FormUrlEncoded
    @POST(HostUrl.GET_EX_CURRENT_TODAY_DATAS)
    Observable<BaseBean<List<ExTodayDatasBean>>> getExCurrentTodayDatas(
            @Field("date") String date,
            @Field("page") String page,
            @Field("pageSize") String pageSize
    );
    @FormUrlEncoded
    @POST(HostUrl.GET_CONFIG_INFO)
    Observable<BaseBean<ConfigBean>> getConfigInfo(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST(HostUrl.DELETE_TODAY_DATAS)
    Observable<BaseBean<String>> deleteTodayDatas(
            @Field("id") int id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_TODAY_DATAS)
    Observable<BaseBean<List<TodayDatasDetailBean>>> getTodayDatas(
            @Field("id") int id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST(HostUrl.INDEX_URL)
    Observable<BaseBean<IndexBean>> getIndexData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.VEHICLE_LIST_URL)
    Observable<BaseBean<List<VehicleListBean>>> getVehicleListData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.SCAN_UNLOCK_URL)
    Observable<BaseBean<UnlockBean>> getScanUnlockData(@Field("code") String code);

    @FormUrlEncoded
    @POST(HostUrl.VEHICLE_DETAIL_URL)
    Observable<BaseBean<VehicleDetailBean>> getVehicleDetailData(@Field("code") String code);

    @FormUrlEncoded
    @POST(HostUrl.MESSAGE_LIST_URL)
    Observable<BaseBean<List<MessageListBean>>> getMessageListData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.MESSAGE_LIST_URL)
    Observable<BaseBean<List<NotifyMessageBean>>> getNotifyMessageData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.USE_HELP_URL)
    Observable<BaseBean<List<UseHelpBean>>> getUseHelpData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(HostUrl.SUBMIT_BREAKDOWN_URL)
    Observable<BaseBean<String>> uploadFault(@Field("content") String content, @Field("images") String images);

    @FormUrlEncoded
    @POST(HostUrl.ILLEGAL_DECLARATION_URL)
    Observable<BaseBean<String>> uploadIllegal(@Field("content") String content, @Field("images") String images);

    @GET(HostUrl.PAUSE_USE_VEHICLE_URL)
    Observable<BaseBean<String>> getPauseUseVehicleData();

    @GET(HostUrl.CONTINUE_USE_VEHICLE_URL)
    Observable<BaseBean<String>> getContinueUseVehicleData();

    @FormUrlEncoded
    @POST(HostUrl.TRANS_VEHICLE_URL)
    Observable<BaseBean<String>> getTransVehicleData(@Field("code")String code,@Field("lng")String lng,@Field("lat")String lat);

    @FormUrlEncoded
    @POST(HostUrl.LOCK_VENHICLE_URL)
    Observable<BaseBean<LockBean>> getLockVehicleData(@Field("lng")String lng,@Field("lat")String lat);

    @FormUrlEncoded
    @POST(HostUrl.PAY_ORDER_URL)
    Observable<BaseBean<RechargeBean>> getPayOrderData(@Field("pay_type") int pay_type);


    @GET(HostUrl.SHOW_PRICE_URL)
    Observable<BaseBean<String>> getShowPriceData();

    @FormUrlEncoded
    @POST(HostUrl.PARKING_LIST_URL)
    Observable<BaseBean<List<ParkingListBean>>> getParkingListData(@Field("lng")String lng,@Field("lat")String lat,@Field("distance")int distance);

    @FormUrlEncoded
    @POST(HostUrl.IS_PARKING_AREA_URL)
    Observable<BaseBean<ParkingAreaBean>> getIsParkingAreaData(@FieldMap Map<String, Object> params);


    @GET(HostUrl.FENCE_LIST_URL)
    Observable<BaseBean<List<FenceListBean>>> getFenceListData();

    @FormUrlEncoded
    @POST(HostUrl.RECHARGE)
    Observable<BaseBean<RechargeBean>> recharge(
            @Field("money") String money,
            @Field("pay_type") int pay_type,
            @Field("coupon_id") int coupon_id
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_COUPONS)
    Observable<BaseBean<List<CouponBean>>> getCoupons(
            @Field("type") int type,
            @Field("tab_type") int tab_type,
            @Field("status") int status,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );
    @FormUrlEncoded
    @POST(HostUrl.DELETE_COUPONS)
    Observable<BaseBean<String>> deleteCoupon(
            @Field("coupon_id") int coupon_id
    );
    @FormUrlEncoded
    @POST(HostUrl.WITH_DRAW)
    Observable<BaseBean<String>> withDraw(
            @Field("money") String money,
            @Field("bankcard") String bankcard,
            @Field("bank") String bank
    );

    @FormUrlEncoded
    @POST(HostUrl.WALLET_DETAIL)
    Observable<BaseBean<List<WalletDetailBean>>> walletDetail(
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(HostUrl.PAY_DEPOSIT)
    Observable<BaseBean<RechargeBean>> payDeposit(
            @Field("pay_type") int pay_type
    );

    @FormUrlEncoded
    @POST(HostUrl.CONFIG_URL)
    Observable<BaseBean<PriceBean>> getPrice(@Field("name") String name);

    @POST(HostUrl.MY_TRAVEL_STATISTICS)
    Observable<BaseBean<TravelStatisticsBean>> getMyTravelStatistics(

    );

    @FormUrlEncoded
    @POST(HostUrl.MY_TRAVEL_STATISTICS_DATAS)
    Observable<BaseBean<List<TravelDetailBean>>> getMyTravelDatas(
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(HostUrl.TRAVEL_DETAIL)
    Observable<BaseBean<TravelDetailBean>> getTravelDetail(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_ALI_LOGIN_DATAS)
    Observable<BaseBean<AliLoginBean>> getAliLoginData(
            @Field("pid") String pid
    );

    @FormUrlEncoded
    @POST(HostUrl.ALI_LOGIN)
    Observable<BaseBean<LoginBean>> alipayLogin(
            @Field("authCode") String authCode
    );

    @FormUrlEncoded
    @POST(HostUrl.WECHAT_LOGIN)
    Observable<BaseBean<LoginBean>> wechatLogin(
            @Field("type") String type,
            @Field("access_token") String access_token,
            @Field("openid") String openid
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_MAINTAIN_HISTORY)
    Observable<BaseBean<List<MainTainBean>>> getMainHistorys(
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(HostUrl.UPLOAD_MAINTAIN)
    Observable<BaseBean<String>> uploadMaintain(
            @Field("content") String content,
            @Field("images") String images
    );

    @FormUrlEncoded
    @POST(HostUrl.EX_REAL_NAME_APPLY)
    Observable<BaseBean<String>> exRealNameApply(
            @Field("real_name") String real_name,
            @Field("id_card") String id_card,
            @Field("id_card_photo") String id_card_photo,
            @Field("id_card_photo2") String id_card_photo2,
            @Field("id_card_photo3") String id_card_photo3
    );

    @POST(HostUrl.GET_EX_REAL_NAME_DATA)
    Observable<BaseBean<ExRealNameBean>> getExRealNameApplyData(
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_EX_CHANGE_TASK)
    Observable<BaseBean<String>> getExChangeTask(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST(HostUrl.SCAN_EX_CHANGE)
    Observable<BaseBean<String>> scanExChange(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST(HostUrl.BACK_DEPOSIT)
    Observable<BaseBean<String>> backDeposit(
            @Field("refund_reason") String refund_reason
    );
    @FormUrlEncoded
    @POST(HostUrl.BIND_PHONE)
    Observable<BaseBean<LoginBean>> bindPhone(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("password") String password,
            @Field("repassword") String repassword
    );

    @FormUrlEncoded
    @POST(HostUrl.MESSAGE_LIST_URL)
    Observable<BaseBean<List<MessageListBean>>> getMessageList(
            @Field("type") int type,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(HostUrl.DELTETE_MESSAGE)
    Observable<BaseBean<String>> deleteMessage(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST(HostUrl.GET_MESSAGE_DETAIL)
    Observable<BaseBean<MessageListBean>> getMessageDetail(
            @Field("id") int id
    );

    @POST(HostUrl.GET_UN_READ_COUNT)
    Observable<BaseBean<UnReadBean>> getUnReadCount(
    );

    @POST(HostUrl.CHECK_UPLOAD)
    Observable<BaseBean<VersionBean>> checkUpload(
    );
}
