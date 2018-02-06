package com.example.admin.fdm.retrofit;

import com.example.admin.fdm.mvp.module.AboutResponse;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.ClaimChooseResponse;
import com.example.admin.fdm.mvp.module.ClaimHouseListResponse;
import com.example.admin.fdm.mvp.module.ClaimSearchResponse;
import com.example.admin.fdm.mvp.module.CreatTakeLookResponse;
import com.example.admin.fdm.mvp.module.CustomListResponse;
import com.example.admin.fdm.mvp.module.CutomDetailResponse;
import com.example.admin.fdm.mvp.module.CrabListResponse;
import com.example.admin.fdm.mvp.module.HouseDetailResponse;
import com.example.admin.fdm.mvp.module.HouseEvaluteBaseResponse;
import com.example.admin.fdm.mvp.module.MainEvaluateResponse;
import com.example.admin.fdm.mvp.module.MainResponse;
import com.example.admin.fdm.mvp.module.MemberResponse;
import com.example.admin.fdm.mvp.module.OrderDetailResponse;
import com.example.admin.fdm.mvp.module.OrderListResponse;
import com.example.admin.fdm.mvp.module.OrderRevisePriceResponse;
import com.example.admin.fdm.mvp.module.PersonalResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.mvp.module.CommentResponse;
import com.example.admin.fdm.mvp.module.TakeLookDetailResponse;
import com.example.admin.fdm.mvp.module.TakeLookHouseResponse;
import com.example.admin.fdm.mvp.module.TakeLookListResponse;
import com.example.admin.fdm.mvp.module.TradingAreaResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Serenade on 17/6/10.
 */

public interface RetrofitApi {
    String Host = "http://api.zhagen.com/";

    //登录接口
    //http://api.zhagen.com/agent/login
    @FormUrlEncoded
    @POST("agent/login")
    Observable<MemberResponse> exLogin(@Field("phone_number") String phone_number, @Field("password") String password);


    //添加好友
    //http://api.zhagen.com/agent/chat/add
    @FormUrlEncoded
    @POST("agent/chat/add")
    Observable<PostResponse> addFriend(@Field("member_id") Integer member_id,@Field("uid") String uid);

    //用户带看列表
    @GET("agent/visit-house/index")
    Observable<TakeLookListResponse> getTakeLookList(@Query("member_id") Integer member_id, @Query("page") Integer page, @Query("status") Integer status);

    //带看详情
    @GET("agent/visit-house/detail")
    Observable<TakeLookDetailResponse> getTakeLookDetail(@Query("id") String id, @Query("member_id") Integer member_id);

    //确认带看
    //http://api.zhagen.com/agent/visit-house/success
    @GET("agent/visit-house/success")
    Observable<BaseResponse> setTakeLookSuccess(@Query("member_id") Integer member_id, @Query("id") String id);

    //完成带看
    //http://api.zhagen.com/agent/visit-house/finish
    @GET("agent/visit-house/finish")
    Observable<BaseResponse> setTakeLookFinish(@Query("member_id") Integer member_id, @Query("id") String id);

    //取消带看
    //http://api.zhagen.com/agent/visit-house/cancel
    @GET("agent/visit-house/cancel")
    Observable<BaseResponse> setTakeLookCancel(@Query("member_id") Integer member_id, @Query("id") String id);

    //删除带看
    //http://api.zhagen.com/agent/visit-house/del
    @GET("agent/visit-house/del")
    Observable<BaseResponse> setTakeLookDelete(@Query("member_id") Integer member_id, @Query("id") String id);

    //查看评论
//    http://api.zhagen.com/agent/visit-house/getinfo
    @GET("agent/visit-house/getinfo")
    Observable<CommentResponse> getComment(@Query("member_id") Integer member_id, @Query("id") Integer id, @Query("method") Integer method);

    //订单列表
//    api.zhagen.com/agent/order/list
    @GET("agent/order/list")
    Observable<OrderListResponse> getOrderList(@Query("member_id") Integer member_id, @Query("page") Integer page, @Query("status") Integer status);

    //订单详情
//   api.zhagen.com/agent/order/detail
    @GET("agent/order/detail")
    Observable<OrderDetailResponse> getOrderDetail(@Query("member_id") Integer member_id, @Query("order_id")
            Integer order_id, @Query("uid") Integer uid);

    //修改价格
//  api.zhagen.com/agent/order/save
    @GET("agent/order/save")
    Observable<OrderRevisePriceResponse> getRevisePrice(@Query("order_id") Integer order_id, @Query("uid") Integer uid);

    //确认修改价格
//  api.zhagen.com/agent/order/saving
    @FormUrlEncoded
    @POST("agent/order/saving")
    Observable<PostResponse> exRevisePrice(@Field("order_id") String order_id, @Field("member_id") String member_id, @Field("rent_money") Integer rent_money, @Field("deposit") Integer deposit
            , @Field("middle") double middle, @Field("service") double service);

    //删除订单
//api.zhagen.com/agent/order/del
    @FormUrlEncoded
    @POST("agent/order/del")
    Observable<PostResponse> setDeleteOrder(@Field("order_id") Integer order_id, @Field("member_id") Integer member_id, @Field("uid") Integer uid);

    //  用户列表
    //http://api.zhagen.com/agent/chat/index
    @GET("agent/chat/index")
    Observable<CustomListResponse> getCustomList(@Query("member_id") Integer member_id, @Query("page") Integer page, @Query("status") Integer status);

    //  用户列表
    //http://api.zhagen.com/agent/chat/index
    @GET("agent/chat/index")
    Observable<CustomListResponse> getForwardList(@Query("member_id") Integer member_id, @Query("status") Integer status,@Query("keyword") String keyword,@Query("page") Integer page);


    // 用户详情
    //http://api.zhagen.com/agent/chat/detail
    @GET("agent/chat/detail")
    Observable<CutomDetailResponse> getCustomDetail(@Query("member_id") Integer member_id, @Query("uid") Integer id, @Query("page") Integer page);

    //修改用户状态
    //http://api.zhagen.com/agent/chat/status
    @GET("agent/chat/status")
    Observable<BaseResponse> setChatStatus(@Query("member_id") Integer member_id, @Query("uid") Integer uid, @Query("status") Integer status);

    //删除好友
    //http://api.zhagen.com/agent/chat/del
    @GET("agent/chat/del")
    Observable<BaseResponse> DeleteChat(@Query("member_id") Integer member_id, @Query("id") String id);

    //预约带看
    //http://api.zhagen.com/agent/visit-house/create
    @GET("agent/visit-house/create")
    Observable<CreatTakeLookResponse> getCreatTakeLook(@Query("member_id") Integer member_id, @Query("uid") String uid);

    //预约房源搜索
    //http://api.zhagen.com/agent/visit-house/house
    @GET("agent/visit-house/house")
    Observable<TakeLookHouseResponse> getTakeLookHouseList(@Query("member_id") Integer member_id, @Query("keyword") String keyword);

    //提交帶看
    //http://api.zhagen.com/agent/visit-house/add
    @FormUrlEncoded
    @POST("agent/visit-house/add")
    Observable<PostResponse> exTakeLook(@Field("uid") String uid, @Field("member_id") Integer member_id, @Field("room_id") Integer room_id, @Field("type") String type
            , @Field("house_id") String house_id, @Field("name") String name, @Field("expect_time") String expect_time);

    //个人资料
    //http://api.zhagen.com/agent/member/index
    @GET("agent/member/index")
    Observable<PersonalResponse> getPersonal(@Query("member_id") Integer member_id);

    //获得商圈
    //http://api.zhagen.com/agent/member/create
    @GET("agent/member/create")
    Observable<TradingAreaResponse> getTradingArea(@Query("member_id") Integer member_id);

    //获得商圈
    //http://api.zhagen.com/agent/claim/create
    @GET("agent/claim/create")
    Observable<TradingAreaResponse> getClaimTradingArea(@Query("member_id") Integer member_id);

    //修改商圈
    //http://api.zhagen.com/agent/member/save
    @GET("agent/member/save")
    Observable<PostResponse> setTradingArea(@Query("member_id") Integer member_id, @Query("districtId") String districtId);

    //修改头像
    //http://api.zhagen.com/agent/member/saves
    @Multipart
    @POST("agent/member/saves")
    Call<PostResponse> setUserHead(@Part("member_id") int member_id,
                                   @Part MultipartBody.Part file);

    //主页数据
    //http://api.zhagen.com/agent/member/show
    @GET("agent/member/show")
    Observable<MainResponse> getMainData(@Query("member_id") Integer member_id);


    //经纪人评价
    //http://api.zhagen.com/agent/member/detail
    @GET("agent/member/detail")
    Observable<MainEvaluateResponse> getMainEvaluate(@Query("member_id") Integer member_id);

    //认领房源列表
    //api.zhagen.com/agent/claim/index
    @GET("agent/claim/index")
    Observable<ClaimHouseListResponse> getClaimHouseList(@Query("member_id") Integer member_id,
                                                         @Query("rent_start") String rent_start,
                                                         @Query("rent_end") String rent_end,
                                                         @Query("room") String room,
                                                         @Query("house_name") String house_name,
                                                         @Query("order") String order,
                                                         @Query("sort") String sort,
                                                         @Query("areaid") String areaid,
                                                         @Query("page") Integer page,
                                                         @Query("type") Integer house_type);

    //已认领房源列表
    //api.zhagen.com/agent/manage/index
    //api.zhagen.com/agent/manage/index
    @GET("agent/manage/index")
    Observable<ClaimHouseListResponse> getAlreadyClaimHouseList(@Query("member_id") Integer member_id,
                                                         @Query("rent_start") String rent_start,
                                                         @Query("rent_end") String rent_end,
                                                         @Query("room") String room,
                                                         @Query("house_name") String house_name,
                                                         @Query("order") String order,
                                                         @Query("sort") String sort,
                                                         @Query("areaid") String areaid,
                                                         @Query("page") Integer page,
                                                         @Query("type") Integer house_type);



    //确认认领
    //api.zhagen.com/agent/claim/house
    @FormUrlEncoded
    @POST("agent/claim/house")
    Observable<PostResponse>exClaimHouse(@Field("member_id") Integer member_id, @Field("type") Integer type
            , @Field("house_id") String house_id, @Field("room_id") String room_id,@Field("source") Integer source);

    //取消认领
    //api.zhagen.com/agent/manage/sure
    @FormUrlEncoded
    @POST("agent/manage/sure")
    Observable<PostResponse>exCleanHouse(@Field("member_id") Integer member_id,@Field("id") String room_id);

    //认领房源详情
    //agent/claim/all
    //api.zhagen.com/agent/index/all
//    @GET("agent/index/all")
//    Observable<HouseDetailResponse>getHouseDetail(@Query("type") Integer type
//            , @Query("house_id") String house_id, @Query("room_id") String room_id);
    @GET("agent/claim/all")
    Observable<HouseDetailResponse>getHouseDetail(@Query("type") Integer type
            , @Query("house_id") String house_id, @Query("room_id") String room_id,@Query("member_id") Integer member_id);

    //已领取房源详情
    //api.zhagen.com/agent/manage/house-detail
    @GET("agent/manage/house-detail")
    Observable<HouseDetailResponse>getAlreadyHouseDetail(@Query("type") Integer type
            , @Query("house_id") String house_id, @Query("room_id") String room_id);

    //认领房源
    //api.zhagen.com/agent/claim/choose
    @FormUrlEncoded
    @POST("agent/claim/choose")
    Observable<ClaimChooseResponse>exClaimChoose(@Field("member_id") Integer member_id, @Field("type") Integer type
            ,@Field("house_id") String house_id, @Field("room_id") String room_id);

    //取消房源
    //api.zhagen.com/agent/manage/cancel-house
    @FormUrlEncoded
    @POST("agent/manage/cancel-house")
    Observable<ClaimChooseResponse>exCleanChoose(@Field("type") Integer type
            ,@Field("house_id") String house_id, @Field("room_id") String room_id,@Field("member_id") Integer member_id);

    //房源评价
    //api.zhagen.com/agent/claim/house-review
    @GET("agent/claim/house-review")
    Observable<HouseEvaluteBaseResponse>getHouseEvalute(@Query("type") Integer type
            , @Query("house_id") String house_id, @Query("room_id") String room_id,@Query("page") Integer page);

    //抢单列表
    //http://api.com/agent/grab/index
    @GET("agent/grab/index")
    Observable<CrabListResponse>getGrabList(@Query("member_id") Integer member_id, @Query("page") Integer page, @Query("status") Integer status);

    //抢单
    //http://api.zhagen.com/agent/grab/save
    @GET("agent/grab/save")
    Observable<PostResponse>setCrab(@Query("member_id") Integer member_id, @Query("id") String id);

    //删除抢单
    //http://api.zhagen.com/agent/grab/del
    @GET("agent/grab/del")
    Observable<PostResponse>deleteCrab(@Query("member_id") Integer member_id, @Query("id") String id);


    //认领房源搜索
    //api.zhagen.com/agent/claim/search
    @GET("agent/claim/search")
    Observable<ClaimSearchResponse>getClaimSearch(@Query("keyword") String keyword);

    //房源管理搜索
    //pi.zhagen.com/agent/manage/search-house
    @GET("agent/manage/search-house")
    Observable<ClaimSearchResponse>getHouseMeangementSearch(@Query("keyword") String keyword,@Query("member_id") int member_id);

    //关于我们
    //http://api.zhagen.com/district/aboutus
    @GET("district/aboutus")
    Observable<AboutResponse>getAbout();

    //短信接口
    //http://api.zhagen.com/sms/code
    @FormUrlEncoded
    @POST("sms/code")
    Observable<PostResponse>getSms(@Field("phone_number") String phone_number);

    //修改密码
    //http://api.zhagen.com/agent/member/modify
    @FormUrlEncoded
    @POST("agent/member/modify")
    Observable<PostResponse>exChangePassWord(@Field("phone_number") String phone_number,@Field("password") String password,@Field("code") String code);
}
