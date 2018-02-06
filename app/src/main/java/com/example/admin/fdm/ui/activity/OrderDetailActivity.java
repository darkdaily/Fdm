package com.example.admin.fdm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.OrderDetailResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.example.admin.fdm.utils.system.SystemUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/4.
 */

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;

    @BindView(R.id.order_state_title)
    TextView order_state_title;
    @BindView(R.id.iv_userhead)
    ImageView iv_userhead;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_tel)
    TextView tv_user_tel;
    @BindView(R.id.iv_house)
    ImageView iv_house;
    @BindView(R.id.tv_house_name)
    TextView tv_house_name;
    @BindView(R.id.tv_house_info)
    TextView tv_house_info;
    @BindView(R.id.tv_house_location)
    TextView tv_house_location;
    @BindView(R.id.tv_house_rent)
    TextView tv_house_rent;
    @BindView(R.id.rent_month)
    TextView rent_month;
    @BindView(R.id.pay_type)
    TextView pay_type;
    @BindView(R.id.room_no)
    TextView room_no;
    @BindView(R.id.order_user_name)
    TextView order_user_name;
    @BindView(R.id.order_user_tel)
    TextView order_user_tel;

    @BindView(R.id.tv_green)
    TextView tv_green;
    @BindView(R.id.tv_black)
    TextView tv_black;

    @BindView(R.id.zhagen_discount)
    LinearLayout zhagen_discount;
    @BindView(R.id.zhagen_discount_number)
    TextView zhagen_discount_number;

    @BindView(R.id.brokerage_discount)
    LinearLayout brokerage_discount;
    @BindView(R.id.brokerage_discount_number)
    TextView brokerage_discount_number;

    @BindView(R.id.promotion_discount)
    LinearLayout promotion_discount;
    @BindView(R.id.promotion_discount_number)
    TextView promotion_discount_number;

    @BindView(R.id.rent_unit_price)
    TextView rent_unit_price;
    @BindView(R.id.tv_rent_count)
    TextView tv_rent_count;
    @BindView(R.id.rent_money)
    TextView rent_money;

    @BindView(R.id.tv_deposit_count)
    TextView tv_deposit_count;
    @BindView(R.id.deposit_money)
    TextView deposit_money;

    @BindView(R.id.middle_count)
    TextView middle_count;
    @BindView(R.id.middle_money)
    TextView middle_money;


    @BindView(R.id.service_count)
    TextView service_count;
    @BindView(R.id.service_money)
    TextView service_money;

    @BindView(R.id.tv_total)
    TextView tv_total;


    @BindString(R.string.wait_pay)
    String str_user_wait_pay;
    @BindString(R.string.complete_pay)
    String str_user_complete_pay;
    @BindString(R.string.cancel_pay)
    String str_user_cancel_pay;

    @BindString(R.string.delete)
    String str_delete;
    @BindString(R.string.view_evaluation)
    String str_view_evaluation;
    @BindString(R.string.price_detail)
    String str_price_detail;
    @BindString(R.string.revise_price)
    String str_revise_price;

    @BindView(R.id.order_detail_layout)
    LinearLayout order_detail_layout;
    @BindView(R.id.price_detail_layout)
    LinearLayout price_detail_layout;

    @BindView(R.id.order_number)
    TextView order_number;
    @BindView(R.id.pay_tima)
    TextView pay_tima;
    @BindView(R.id.received_money)
    TextView received_money;
    @BindView(R.id.agent)
    TextView agent;
    @BindView(R.id.tenant_name)
    TextView tenant_name;
    @BindView(R.id.tenant_tel)
    TextView tenant_tel;
    @BindView(R.id.agent_name)
    TextView agent_name;
    @BindView(R.id.agent_tel)
    TextView agent_tel;

    private OrderDetailResponse.DataBean.ListBean bean;

    //请求参数
    private String state;
    private int member_id;
    private int uid;
    private int order_id;

    @Override
    protected void onResume() {
        super.onResume();
        getDetailData();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initEvent() {

        setDisplayView(empty,ll_linearlayout);

        member_id = (Integer) SPUtil.get(mActivity,"member_id",-1);

        Intent intent = getIntent();

        uid = intent.getIntExtra("uid",-1);
        order_id = intent.getIntExtra("order_id",-1);
        Log.d("1111111111111111","order_id-o"+Integer.valueOf(order_id)+"uid+o"+Integer.valueOf(uid));

        setNetworkLoading();
//        getDetailData();

    }

    @Override
    public void initData() {

    }

    public void setView(String state) {
        setHideLayout();
        if(bean.getUser_avatar() != null){
            Glide.with(this).load(bean.getUser_avatar()).into((ImageView) iv_userhead);
        }
        tv_user_name.setText(bean.getReal_name());
        if(bean.getHouse_photo() != null && !bean.getHouse_photo().equals("")){
            Glide.with(this).load(bean.getHouse_photo()).into((ImageView) iv_house);
        }
        tv_house_name.setText(bean.getHouse_title());
        tv_house_info.setText(bean.getHouse_area());
        tv_house_location.setText(bean.getAddress());
        tv_house_rent.setText(bean.getRent_money()+"");

        rent_month.setText(bean.getRent_month());
        pay_type.setText(bean.getPay_type());
        room_no.setText(bean.getRoom_num());

        if(state.equals(Constant.USER_COMPLETE_PAY)){
            tv_user_tel.setText(bean.getTelephones());
            order_detail_layout.setVisibility(View.VISIBLE);
            price_detail_layout.setVisibility(View.GONE);
            order_number.setText(bean.getOrder());
            pay_tima.setText(TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getPay_time()));
            received_money.setText("¥"+bean.getPayment());
            agent.setText(bean.getNet());
            tenant_name.setText(bean.getReal_name());
            tenant_tel.setText(bean.getPhone_number());
            agent_name.setText(bean.getAgent());
            agent_tel.setText(bean.getTelephones());
        }else{
            tv_user_tel.setText(bean.getTelephone());
            order_detail_layout.setVisibility(View.GONE);
            price_detail_layout.setVisibility(View.VISIBLE);
            order_user_name.setText(bean.getReal_name());
            order_user_tel.setText(bean.getPhone_number());
            rent_unit_price.setText(bean.getRent_money()+"");
            tv_rent_count.setText("×"+bean.getRent_pay());
            rent_money.setText("¥"+bean.getR_money());
            tv_deposit_count.setText("×"+bean.getDeposit());
            deposit_money.setText("¥"+bean.getPay());
            middle_count.setText("×"+bean.getMiddle_count());
            middle_money.setText("¥"+bean.getMiddle_money());
            String str = bean.getService_count();
            String s = str.replaceAll("\\*", "×");
            service_count.setText(s);
            service_money.setText("¥"+bean.getService_money());
            tv_total.setText("¥"+bean.getPayment());
        }
        switch (state){
            case Constant.USER_WAIT_PAY:
                order_state_title.setText(str_user_wait_pay);
                tv_green.setText(str_revise_price);
                tv_black.setVisibility(View.GONE);
                break;
            case Constant.USER_COMPLETE_PAY:
                order_state_title.setText(str_user_complete_pay);
                tv_green.setText(str_view_evaluation);
                tv_black.setText(str_price_detail);
                break;
            case Constant.USER_CANCEL_PAY:
                order_state_title.setText(str_user_cancel_pay);
                tv_green.setVisibility(View.GONE);
                tv_black.setText(str_delete);
                break;
        }

    }

    private void getDetailData(){
        RetrofitHelper.getApiWithUid().getOrderDetail(member_id,order_id,uid)
//                RetrofitHelper.getApiWithUid().getOrderDetail(237,611,39)
                .compose(RxScheduler.<OrderDetailResponse>defaultScheduler())
                .subscribe(new BaseObserver<OrderDetailResponse>(this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if(bean == null){
                            setNoDataView("暂时无数据",R.drawable.no_order);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(bean == null){
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(OrderDetailResponse orderDetailResponse) {
                        if(orderDetailResponse.getData().getList() != null){
                            bean = orderDetailResponse.getData().getList();
                            state = orderDetailResponse.getData().getStatus();
                            setView(state);
                        }else{
                            setNoDataView("暂时无数据",R.drawable.no_order);
                        }
                    }


                    @Override
                    public void complete() {

                    }
                });
    }

    public void setDeleteOrder(){
        RetrofitHelper.getApiWithUid().setDeleteOrder(order_id,member_id,uid)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(OrderDetailActivity.this) {
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
                    public void next(PostResponse postResponse) {
                        if(postResponse.getCode() == 0){
                            Toast.makeText(OrderDetailActivity.this,"删除完成",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }


    @OnClick({R.id.iv_left, R.id.contact_user, R.id.tv_green, R.id.tv_black,R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.contact_user:
                //联系用户
                SystemUtil.callPhone(OrderDetailActivity.this,bean.getPhone_number());
                break;
            case R.id.tv_green:
                switch (state){
                    case Constant.USER_WAIT_PAY:
                        Intent rpintent = new Intent(this, RevisePriceActivity.class);
                        rpintent.putExtra("uid", bean.getUid());
                        rpintent.putExtra("order_id", bean.getOrder_id());
                        startActivity(rpintent);
                        break;
                    case Constant.USER_COMPLETE_PAY:
                        //查看评论
                        Intent intent = new Intent(mActivity, DealEvaluateActivity.class);
                        intent.putExtra("state", Constant.DEAL);
                        intent.putExtra("id",bean.getOrder_id());
                        intent.putExtra("method",1);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.tv_black:
                switch (state){
                    case Constant.USER_CANCEL_PAY:
                        //删除
                        setDeleteOrder();
                        break;
                    case Constant.USER_COMPLETE_PAY:
                        Intent priceIntent = new Intent(OrderDetailActivity.this,PriceDetailActivity.class);
                        priceIntent.putExtra("state","OrderDetail");
                        priceIntent.putExtra("rent_pay",bean.getRent_pay());
                        priceIntent.putExtra("rent_money",bean.getR_money());
                        priceIntent.putExtra("deposit",bean.getDeposit());
                        priceIntent.putExtra("deposit_money",bean.getPay());
                        priceIntent.putExtra("middle",bean.getMiddle_count());
                        priceIntent.putExtra("middle_money",bean.getMiddle_money());
                        priceIntent.putExtra("service",bean.getService_count());
                        priceIntent.putExtra("service_money",bean.getService_money());
                        priceIntent.putExtra("payment",bean.getPayment());
                        startActivity(priceIntent);
                        break;
                }
                break;
            case R.id.empty:
                setNetworkLoading();
                getDetailData();
                break;

        }

    }
}
