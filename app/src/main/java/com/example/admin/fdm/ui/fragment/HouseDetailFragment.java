package com.example.admin.fdm.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.mvp.module.ClaimChooseResponse;
import com.example.admin.fdm.mvp.module.HouseDetailResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.activity.ForwardActivity;
import com.example.admin.fdm.ui.activity.RoomManagementActivity;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.img.BannerGlideLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class HouseDetailFragment extends BaseSupportFragment {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_house_name)
    TextView tv_house_name;
    @BindView(R.id.tv_house_info)
    TextView tv_house_info;
    @BindView(R.id.tv_house_rent)
    TextView tv_house_rent;
    @BindView(R.id.ll_tag_container)
    LinearLayout ll_tag_container;
    @BindView(R.id.service_count)
    TextView service_count;
    @BindView(R.id.middle_count)
    TextView middle_count;
    @BindView(R.id.tv_house_code)
    TextView tv_house_code;
    @BindView(R.id.tv_house_type)
    TextView tv_house_type;
    @BindView(R.id.tv_space_area)
    TextView tv_space_area;
    @BindView(R.id.tv_floor)
    TextView tv_floor;
    @BindView(R.id.tv_house_address)
    TextView tv_house_address;
    @BindView(R.id.tv_house_content)
    TextView tv_house_content;

    @BindView(R.id.house_configure_layout)
    LinearLayout house_configure_layout;

    @BindView(R.id.room_configure_layout)
    LinearLayout room_configure_layout;

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.r_ll_one)
    LinearLayout r_ll_one;
    @BindView(R.id.r_ll_two)
    LinearLayout r_ll_two;
    @BindView(R.id.r_ll_three)
    LinearLayout r_ll_three;

    @BindView(R.id.h_ll_one)
    LinearLayout h_ll_one;
    @BindView(R.id.h_ll_two)
    LinearLayout h_ll_two;
    @BindView(R.id.h_ll_three)
    LinearLayout h_ll_three;
    @BindView(R.id.h_ll_four)
    LinearLayout h_ll_four;
    @BindView(R.id.h_ll_five)
    LinearLayout h_ll_five;
    @BindView(R.id.h_ll_six)
    LinearLayout h_ll_six;

    private String  house_id;
    private String  room_id;
    private int state;
    private int state_type;
    private int member_id;
    private int Type;//分散式1 集中2
    private String share;

    //分散式配置
    private List<LinearLayout> privhouselinearLayoutsList;//分散式
    private List<LinearLayout> publihouselinearLayoutsList;

    //集中式配置
    private List<LinearLayout> roomlinearLayoutsList;


    private HouseDetailResponse.DataBean data;

    //配置图片
    private Integer[] roomIconOnList={R.drawable.chuang_on,R.drawable.wifi_on,R.drawable.kongtiao_on,
            R.drawable.shuzhuo_on,R.drawable.weishengjian_on,R.drawable.yigui_on,R.drawable.shafa_on,R.drawable.dianshi_on,
            R.drawable.xiyiji_on,R.drawable.bingxiang_on,R.drawable.reshuiqi_on,R.drawable.diancilu_on,R.drawable.weibolu_on,R.drawable.ranqizao_on,R.drawable.youyanji_on,R.drawable.yangtai_on};

    private Integer[] roomIconOffList={R.drawable.chuang_off,R.drawable.wifi_off,R.drawable.kongtiao_off,
            R.drawable.shuzhuo_off,R.drawable.weishengjian_off,R.drawable.yigui_off,R.drawable.shafa_off,R.drawable.dianshi_off,
            R.drawable.xiyiji_off,R.drawable.bingxiang_off,R.drawable.reshuiqi_off,R.drawable.diancilu_off,R.drawable.weibolu_off,R.drawable.ranqizao_off,R.drawable.youyanji_off,R.drawable.yangtai_off};

    private Integer[] number = {1,10,3,8,9,15,12,13,14,11,0,6,2,5,7,4};

    @BindView(R.id.tv_forward)
    LinearLayout tv_forward;

    @BindView(R.id.detail_function_btn)
    TextView detail_function_btn;

    @BindString(R.string.claim_room)
    String str_claim_room;

    @BindString(R.string.claim_housing_resources)
    String str_claim_housing_resources;

    @BindString(R.string.cancel_housing_resources)
    String str_cancel_housing_resources;

    @BindString(R.string.cancel_room)
    String str_cancel_room;


    private BaiduMap mBaiduMap;

    @Override
    protected int setLayout() {
        return R.layout.fragment_house_detail;
    }

    @Override
    protected void init() {
        setDisplayView(empty,ll_linearlayout);

        member_id = (Integer) SPUtil.get(mActivity, "member_id", -1);

        house_id = getArguments().getString("house_id");
        room_id = getArguments().getString("room_id");
        state_type = getArguments().getInt("state");

        setView(state_type);
        mBaiduMap = mapView.getMap();
        setBanner();
        setConfigureView(state);
    }

    private void setBanner(){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new BannerGlideLoader());
        //设置图片集合
//        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    private void setDetailView(){
        setHideLayout();
//        setMap(data.getLng(),data.getLat());
//        setMap(data.getLat(),data.getLng());
        setUserMapCenter(data.getLng(),data.getLat());
        setMarker(data.getLng(),data.getLat());
        banner.setImages(data.getHouse_photo());
        banner.start();
        tv_house_name.setText(data.getHouse_title());
        tv_house_info.setText(data.getHouse_kind());
        tv_house_rent.setText(data.getRental());
        showThreeTag(data.getLabels(),ll_tag_container);
        service_count.setText(data.getService_charge());
        middle_count.setText(data.getIntermediary_fee());
        tv_house_code.setText(data.getHouse_code());
        tv_house_type.setText(data.getHouse_type());
        tv_space_area.setText(data.getSpace_area());
        tv_floor.setText(data.getFloor());
        tv_house_address.setText(data.getHouse_address());
        tv_house_content.setText(data.getHouse_desc());
        if(Type == 1){
            showConfigure(data.getRoom(),privhouselinearLayoutsList);
            showConfigure(data.getPublicX(),publihouselinearLayoutsList);

        }else{
            showConfigure(data.getRoom(),roomlinearLayoutsList);
        }
    }

    private void setConfigureView(int state) {
        setNetworkLoading();
        switch (state) {
            case Constant.CANCEL_ROOM://集中
                Type = 2;
                house_configure_layout.setVisibility(View.GONE);
                room_configure_layout.setVisibility(View.VISIBLE);
                getAlreadyHouseDetail();
                break;
            case Constant.CANCEL_CANCEL_HOUSING_RESOURCES://分散
                Type = 1;
                house_configure_layout.setVisibility(View.VISIBLE);
                room_configure_layout.setVisibility(View.GONE);
                getAlreadyHouseDetail();
                break;
            case Constant.CLAIM_ROOM://集中
                Type = 2;
                house_configure_layout.setVisibility(View.GONE);
                room_configure_layout.setVisibility(View.VISIBLE);
                getHouseDetailData();
                break;
            case Constant.CLAIM_HOUSING_RESOURCES://分散
                Type = 1;
                house_configure_layout.setVisibility(View.VISIBLE);
                room_configure_layout.setVisibility(View.GONE);
                getHouseDetailData();
                break;
        }
        initlinearLyaoutList(Type);
    }

    public void setView(int state) {
        switch (state) {
            case Constant.CANCEL_ROOM:
                this.state = 2;
                tv_forward.setVisibility(View.VISIBLE);
                detail_function_btn.setText(str_cancel_room);
                break;
            case Constant.CANCEL_CANCEL_HOUSING_RESOURCES:
                this.state = 1;
                tv_forward.setVisibility(View.VISIBLE);
                detail_function_btn.setText(str_cancel_housing_resources);
                break;
            case Constant.CLAIM_ROOM:
                this.state = 2;
                tv_forward.setVisibility(View.GONE);
                detail_function_btn.setText(str_claim_room);

                break;
            case Constant.CLAIM_HOUSING_RESOURCES:
                this.state = 1;
                tv_forward.setVisibility(View.GONE);
                detail_function_btn.setText(str_claim_housing_resources);
                break;
        }
    }

    @OnClick({R.id.tv_forward, R.id.detail_function_btn,R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forward://转发
                Intent forward = new Intent(mActivity, ForwardActivity.class);
                forward.putExtra(Constant.HOUSE_ID,data.getHouse_id());
                forward.putExtra(Constant.ROOM_ID,data.getRoom_id());
                forward.putExtra(Constant.HOUSE_TITLE,data.getHouse_title());
                forward.putExtra(Constant.HOUSE_PHOTO,data.getHouse_photo().get(0));
                forward.putExtra(Constant.HOUSE_RENTAL,data.getRental());
                forward.putExtra(Constant.HOUSE_TYPE,data.getType());
                startActivity(forward);
                break;
            case R.id.detail_function_btn://选择或者取消
                switch (state_type){
                    case Constant.CLAIM_ROOM:
                        Intent claimRoom = new Intent(mActivity, RoomManagementActivity.class);
                        claimRoom.putExtra("state",Constant.CHOICE_CLAIM_ROOM);
                        claimRoom.putExtra("house_id",house_id);
                        claimRoom.putExtra("room_id",room_id);
                        startActivity(claimRoom);
                        break;
                    case Constant.CLAIM_HOUSING_RESOURCES:
                        exClaimChoose();
                        break;
                    case Constant.CANCEL_ROOM:
                        Intent cancelRoom = new Intent(mActivity, RoomManagementActivity.class);
                        cancelRoom.putExtra("state",Constant.CANCEL_CLAIM_ROOM);
                        cancelRoom.putExtra("house_id",house_id);
                        cancelRoom.putExtra("room_id",room_id);
                        startActivity(cancelRoom);
                        break;
                    case Constant.CANCEL_CANCEL_HOUSING_RESOURCES:
                        exCleanChoose();
                        break;

                }

                break;
            case R.id.empty:
                setConfigureView(state);
                break;

        }
    }




    private void initlinearLyaoutList(int Type){
        if(Type == 1){//分散
            privhouselinearLayoutsList = new ArrayList<>();
            privhouselinearLayoutsList.add(h_ll_one);
            privhouselinearLayoutsList.add(h_ll_two);
            privhouselinearLayoutsList.add(h_ll_three);
            publihouselinearLayoutsList = new ArrayList<>();
            publihouselinearLayoutsList.add(h_ll_four);
            publihouselinearLayoutsList.add(h_ll_five);
            publihouselinearLayoutsList.add(h_ll_six);
        }else{//集中
            roomlinearLayoutsList = new ArrayList<>();
            roomlinearLayoutsList.add(r_ll_one);
            roomlinearLayoutsList.add(r_ll_two);
            roomlinearLayoutsList.add(r_ll_three);
        }
    }

    private void showConfigure(List<Integer> list,List<LinearLayout> layouts){
        int j = 0;
        int l = 0;
        for(int i = 0;i<list.size() ;i++){
                    if(i>=0 && i<6){
                        LinearLayout ll = ((LinearLayout)layouts.get(0).getChildAt(i));
                        setConfigure(ll,list,i);
                    }
                    else if(i>=6 && i<12){
                        LinearLayout ll = ((LinearLayout)layouts.get(1).getChildAt(j++));
                        setConfigure(ll,list,i);
                    }
                    else if(i >=12 && i <16){
                        LinearLayout ll = ((LinearLayout)layouts.get(2).getChildAt(l++));
                        setConfigure(ll,list,i);
                    }
                }
    }

    private void setConfigure(LinearLayout ll,List list,int i){
        ImageView imageView = ((ImageView) ll.getChildAt(0));
        TextView textView = ((TextView)ll.getChildAt(1));
        if(list.get(number[i]).equals(1)){
            imageView.setBackgroundResource(roomIconOnList[number[i]]);
            textView.setTextColor(getResources().getColor(R.color.black_deep));
        }else{
            imageView.setBackgroundResource(roomIconOffList[number[i]]);
            textView.setTextColor(getResources().getColor(R.color.gray666));
        }
    }

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }

    private void getAlreadyHouseDetail(){
        RetrofitHelper.getApiWithUid().getAlreadyHouseDetail(Type,house_id,room_id)
                .compose(RxScheduler.<HouseDetailResponse>defaultScheduler())
                .subscribe(new BaseObserver<HouseDetailResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        setNoDataView("暂无数据",R.drawable.no_house);
                    }

                    @Override
                    public void NoNetwork() {
                        setNetworkError();
                    }
                    @Override
                    public void next(HouseDetailResponse houseDetailResponse) {
                        if(houseDetailResponse.getData() == null){
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }else{
                            data = houseDetailResponse.getData();
                            setDetailView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }


    private void getHouseDetailData(){
        RetrofitHelper.getApiWithUid().getHouseDetail(Type,house_id,room_id,member_id)
                .compose(RxScheduler.<HouseDetailResponse>defaultScheduler())
                .subscribe(new BaseObserver<HouseDetailResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        setNoDataView("暂无数据",R.drawable.no_house);

                    }

                    @Override
                    public void NoNetwork() {
                        setNetworkError();

                    }
                    @Override
                    public void next(HouseDetailResponse houseDetailResponse) {
                        if(houseDetailResponse.getData() == null){
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }else{
                            data = houseDetailResponse.getData();
                            setShareData();
                            setDetailView();
                        }
                    }

                    @Override
                    public void complete() {
                    }
                });
    }

    //取消房源
    private void exCleanChoose(){
        RetrofitHelper.getApiWithUid().exCleanChoose(state,house_id,room_id,member_id)
                .compose(RxScheduler.<ClaimChooseResponse>defaultScheduler())
                .subscribe(new BaseObserver<ClaimChooseResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }
                    @Override
                    public void next(ClaimChooseResponse claimChooseResponse) {
                        if(claimChooseResponse.getCode() == 0){
                            Toast.makeText(mActivity,"取消成功",Toast.LENGTH_LONG).show();
                            mActivity.finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

//    private void setMap(double lng,double lat){
//        //定义Maker坐标点
//        Log.d("11111111111111","lng"+lng+"lat"+lat);
////        LatLng point = new LatLng(lng, lat);
////
//////构建Marker图标
////
////        BitmapDescriptor bitmap = BitmapDescriptorFactory
////                .fromResource(R.drawable.yellow_start);
////
//////构建MarkerOption，用于在地图上添加Marker
////
////        OverlayOptions option = new MarkerOptions()
////                .position(point)
////                .icon(bitmap);
////
//////在地图上添加Marker，并显示
////
////        mapView.getMap().addOverlay(option);
//        LatLng cenpt = new LatLng(lat,lng); //设定中心点坐标
//        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
//                .target(cenpt)
//                .zoom(18)
//                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        mapView.getMap().setMapStatus(mMapStatusUpdate);
//    }


    //认领房源
    private void exClaimChoose(){
        RetrofitHelper.getApiWithUid().exClaimChoose(member_id,state,house_id,room_id)
                .compose(RxScheduler.<ClaimChooseResponse>defaultScheduler())
                .subscribe(new BaseObserver<ClaimChooseResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }
                    @Override
                    public void next(ClaimChooseResponse claimChooseResponse) {
                        if(claimChooseResponse.getCode() == 0){
                            Toast.makeText(mActivity,"领取成功",Toast.LENGTH_LONG).show();
                            mActivity.finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }

    /**
     * 添加marker
     */
    private void setMarker(double lon,double lat) {
        //定义Maker坐标点
//        LatLng point = new LatLng(lat, lon);
        LatLng point = new LatLng(lat, lon);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.yellow_start);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter(double lon,double lat) {
//        LatLng cenpt = new LatLng(lat,lon);
        LatLng cenpt = new LatLng(lat, lon);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

    }

    private String house_name;
    private String house_photo;
    private String house_info;
    private String house_kind;
    private String house_rental;

    public String getHouse_photo() {
        return house_photo;
    }

    public void setHouse_photo(String house_photo) {
        this.house_photo = house_photo;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getHouse_info() {
        return house_info;
    }

    public void setHouse_info(String house_info) {
        this.house_info = house_info;
    }

    public String getHouse_kind() {
        return house_kind;
    }

    public void setHouse_kind(String house_kind) {
        this.house_kind = house_kind;
    }

    public String getHouse_rental() {
        return house_rental;
    }

    public void setHouse_rental(String house_rental) {
        this.house_rental = house_rental;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    private void setShareData(){
        setHouse_id(data.getHouse_id());
        setRoom_id(data.getRoom_id());
        setHouse_name(data.getHouse_title());
        setHouse_info(data.getHouse_kind());
        setHouse_photo(data.getHouse_photo().get(0));
        setHouse_rental(data.getRental());
        setShare(data.getShare());
    }

}
