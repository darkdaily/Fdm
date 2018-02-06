package com.example.admin.fdm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.TakeLookDetailResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.example.admin.fdm.utils.event.TakeLookEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/7.
 */

public class TakeLookDetailActivity extends BaseActivity {
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;

    @BindView(R.id.ll_takelook_button)
    LinearLayout ll_takelook_button;

    @BindView(R.id.takelook_state_title)
    TextView takelook_state_title;

    @BindView(R.id.tv_black)
    TextView tv_black;
    @BindView(R.id.tv_green)
    TextView tv_green;


    @BindView(R.id.iv_house)
    ImageView iv_house;
    @BindView(R.id.tv_house_name)
    TextView tv_house_name;
    @BindView(R.id.tv_house_info)
    TextView tv_house_info;
    @BindView(R.id.tv_house_location)
    TextView tv_house_location;
    @BindView(R.id.ll_label)
    LinearLayout ll_label;
    @BindView(R.id.tv_house_rent)
    TextView tv_house_rent;

    @BindView(R.id.contacts_name)
    TextView contacts_name;
    @BindView(R.id.contacts_tel)
    TextView contacts_tel;
    @BindView(R.id.take_look_date)
    TextView take_look_date;

    @BindView(R.id.iv_userhead)
    ImageView iv_userhead;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_tel)
    TextView tv_user_tel;


    @BindString(R.string.user_wait_confir)
    String str_user_wait_confir;
    @BindString(R.string.fangdami_wait_confir)
    String str_fangdami_wait_confir;
    @BindString(R.string.bespeaking)
    String str_bespeaking;
    @BindString(R.string.already_completed)
    String str_already_completed;
    @BindString(R.string.already_cancel)
    String str_already_cancel;


    private String state;
    private String id;
    private int member_id;

    private TakeLookDetailResponse.DataBean.ListBean bean;

    @Override
    public int setLayout() {
        return R.layout.activity_takelook_detail;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDetailData();
    }


    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(mActivity, "member_id", -1);
        setDisplayView(empty, ll_linearlayout);
        Intent intent = getmActivity().getIntent();
        id = intent.getStringExtra("takelookid");
        setNetworkLoading();
    }

    @Override
    public void initData() {

    }

    public void getDetailData() {
        RetrofitHelper.getApiWithUid().getTakeLookDetail(id, member_id)
                .compose(RxScheduler.<TakeLookDetailResponse>defaultScheduler())
                .subscribe(new BaseObserver<TakeLookDetailResponse>(this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if (bean == null) {
                            setNoDataView("暂时无数据", R.drawable.no_takelook);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if (bean == null) {
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(TakeLookDetailResponse takeLookDetailResponse) {
                        if(takeLookDetailResponse.getData() != null){
                            if (takeLookDetailResponse.getData().getList() != null) {
                                bean = takeLookDetailResponse.getData().getList();
                                state = bean.getStatus();
                                setView(state);
                            } else {
                                setNoDataView("暂时无数据", R.drawable.no_takelook);

                            }
                        }else{
                            setNoDataView("暂时无数据", R.drawable.no_takelook);
                        }

                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }


    public void setView(String state) {
        setHideLayout();
        take_look_date.setText(TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getExpect_time()));
        Glide.with(mActivity).load(bean.getHouse_photo()).into((ImageView) iv_house);
        tv_house_name.setText(bean.getHouse_title());
        tv_house_info.setText(bean.getHouse_info());
        tv_house_location.setText(bean.getHouse_address());
        tv_house_rent.setText(bean.getHouse_rental() + "元/月");
        showThreeTag(bean.getHouse_label(),ll_label);

        contacts_name.setText(bean.getName());
        contacts_tel.setText(bean.getTelephone());
        Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) iv_userhead);
        tv_user_name.setText(bean.getNick_name());
        tv_user_tel.setText(bean.getPhone_number());
        if (state != null) {
            switch (state) {
                case Constant.USER_WAIT_CONFIR:
                    takelook_state_title.setText(str_user_wait_confir);
                    ll_takelook_button.setVisibility(View.GONE);
                    break;
                case Constant.FANGDAMI_WAIT_CONFIR:
                    takelook_state_title.setText(str_fangdami_wait_confir);
                    ll_takelook_button.setVisibility(View.VISIBLE);
                    tv_black.setText("取消带看");
                    tv_green.setText("确认带看");
                    break;
                case Constant.BESPAKING:
                    takelook_state_title.setText(str_bespeaking);
                    ll_takelook_button.setVisibility(View.VISIBLE);
                    tv_black.setText("取消带看");
                    tv_green.setText("完成带看");
                    break;
                case Constant.ALREADY_COMPLETED:
                    ll_takelook_button.setVisibility(View.VISIBLE);
                    takelook_state_title.setText(str_already_completed);
                    tv_black.setVisibility(View.GONE);
                    tv_green.setText("查看评价");
                    break;
                case Constant.ALREADY_CANCEL:
                    ll_takelook_button.setVisibility(View.VISIBLE);
                    takelook_state_title.setText(str_already_cancel);
                    tv_black.setText("刪除");
                    tv_green.setVisibility(View.GONE);
                    break;
            }
        }
    }

    //确认完成
    public void setSuccess(final Activity activity, final String id, final int memberid) {
        RetrofitHelper.getApiWithUid().setTakeLookSuccess(memberid, id)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(activity) {
                    @Override
                    public void error(Throwable e) {
                        Log.d("setSuccess", "error" + e.toString());
                    }

                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }

                    @Override
                    public void next(BaseResponse baseResponse) {
                        Log.d("setSuccess", "next" + baseResponse.getCode());
                        if (baseResponse.getCode() == 0) {
                            Intent intent = new Intent(activity, TakeLookDetailActivity.class);
                            intent.putExtra("takelookid", id);
                            activity.startActivity(intent);
                            Toast.makeText(activity, "确认带看", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    //完成带看
    public void setFinish(final Activity activity, final String id, final int memberid) {
        RetrofitHelper.getApiWithUid().setTakeLookFinish(memberid, id)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(activity) {
                    @Override
                    public void error(Throwable e) {
                        Log.d("setSuccess", "error" + e.toString());
                    }

                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }

                    @Override
                    public void next(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            Intent intent = new Intent(activity, TakeLookDetailActivity.class);
                            intent.putExtra("takelookid", id);
                            activity.startActivity(intent);
                            finish();
                        } else {
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    //取消带看
    public void setCancel(final Activity activity, final String id, final int memberid) {
        RetrofitHelper.getApiWithUid().setTakeLookCancel(memberid, id)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(activity) {
                    @Override
                    public void error(Throwable e) {
                        Log.d("setSuccess", "error" + e.toString());
                    }

                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }

                    @Override
                    public void next(BaseResponse baseResponse) {
                        Log.d("setSuccess", "next" + baseResponse.getCode());
                        if (baseResponse.getCode() == 0) {
                            Intent intent = new Intent(activity, TakeLookDetailActivity.class);
                            intent.putExtra("takelookid", id);
                            activity.startActivity(intent);
                            Toast.makeText(activity, "完成取消", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(activity, "失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    //删除带看
    public void setDelete(final Activity activity, final String id, final int memberid) {
        RetrofitHelper.getApiWithUid().setTakeLookDelete(memberid, id)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(activity) {
                    @Override
                    public void error(Throwable e) {
                        Log.d("setSuccess", "error" + e.toString());
                    }

                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }

                    @Override
                    public void next(BaseResponse baseResponse) {
                        Log.d("setSuccess", "next" + baseResponse.getCode());
                        if (baseResponse.getCode() == 0) {
                            finish();
                        } else {
                            Toast.makeText(activity, "失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    @OnClick({R.id.iv_left, R.id.tv_green, R.id.tv_black,R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_black:
                if (state != null) {
                    switch (state) {
                        case Constant.FANGDAMI_WAIT_CONFIR:
                            //取消带看
                            setCancel(TakeLookDetailActivity.this, bean.getId(), member_id);
                            finish();
                            break;
                        case Constant.BESPAKING:
                            //取消带看
                            setCancel(TakeLookDetailActivity.this, bean.getId(), member_id);
                            break;
                        case Constant.ALREADY_CANCEL:
                            //删除
                            setDelete(TakeLookDetailActivity.this, bean.getId(), member_id);
                            break;
                    }
                }
                break;
            case R.id.tv_green:
                if (state != null) {
                    switch (state) {
                        case Constant.FANGDAMI_WAIT_CONFIR:
                            //确认带看
                            setSuccess(TakeLookDetailActivity.this, bean.getId(), member_id);
                            break;
                        case Constant.BESPAKING:
                            //完成带看
                            setFinish(TakeLookDetailActivity.this, bean.getId(), member_id);
                            break;
                        case Constant.ALREADY_COMPLETED:
                            //查看评论
                            Intent intent = new Intent(mActivity, DealEvaluateActivity.class);
                            intent.putExtra("state", Constant.TAKELOOK);
                            intent.putExtra("id", bean.getId());
                            intent.putExtra("method", 2);
                            startActivity(intent);
                            break;
                    }
                }
                break;
            case R.id.empty:
                setNetworkLoading();
                getDetailData();
                break;

        }
    }
}
