package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.CommentResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.ui.view.StartBar;
import com.example.admin.fdm.utils.SPUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/5.
 */

public class DealEvaluateActivity extends BaseActivity {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.broker_icon)
    ImageView broker_icon;
    @BindView(R.id.broker_name)
    TextView broker_name;
    @BindView(R.id.broker_company_name)
    TextView broker_company_name;
    @BindView(R.id.broker_start)
    StartBar broker_start;
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.broker_flowlayout)
    TagFlowLayout broker_flowlayout;
    @BindView(R.id.tv_broker_content)
    TextView tv_broker_content;

    @BindView(R.id.house_name)
    TextView house_name;
    @BindView(R.id.house_detail)
    TextView house_detail;
    @BindView(R.id.house_start)
    StartBar house_start;
    @BindView(R.id.tv_house_content)
    TextView tv_house_content;
    @BindView(R.id.fl_pic)
    TagFlowLayout fl_pic;

    @BindView(R.id.company_icon)
    ImageView company_icon;
    @BindView(R.id.company_name)
    TextView company_name;
    @BindView(R.id.company_start)
    StartBar company_start;
    @BindView(R.id.tv_company_content)
    TextView tv_company_content;
    @BindView(R.id.house_flowlayout)
    TagFlowLayout house_flowlayout;






    @BindView(R.id.company_flowlayout)
    TagFlowLayout company_flowlayout;

    @BindString(R.string.user_evaluation)
    String str_user_evaluation;
    @BindString(R.string.see_evaluation)
    String str_see_evaluation;

    private int state;
    private int method;//带看评价标签
    private int member_id;
    private int id;

    private CommentResponse.DataBean data;

    @Override
    public int setLayout() {
        return R.layout.activity_deal_evaluate;
    }

    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(mActivity,"member_id",-1);
        setDisplayView(empty,ll_linearlayout);

        Intent intent = getIntent(); //得到传过来的bundle
        state = intent.getIntExtra("state",-1);
        method = intent.getIntExtra("method",-1);
        id  = intent.getIntExtra("id",-1);

        setNetworkLoading();
        setEvaluateView(state);
    }


    @Override
    public void initData() {

    }

    private void setTakeLookView(CommentResponse.DataBean data){
        setHideLayout();
        Glide.with(DealEvaluateActivity.this).load(data.getAvatar()).into(broker_icon);
        broker_name.setText(data.getUsername());
        broker_company_name.setText(data.getCompany_name());
        broker_start.setRating(data.getMember_score());

        broker_flowlayout.setAdapter(new TagAdapter<String>(data.getMember_label()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(DealEvaluateActivity.this).inflate(R.layout.tagtextview,
                        broker_flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        tv_broker_content.setText(data.getMember_conent());

        house_name.setText(data.getHouse_title());
        house_detail.setText(data.getHouse_area());
        house_start.setRating(data.getHouse_score());

        house_flowlayout.setAdapter(new TagAdapter<String>(data.getHouse_label()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(DealEvaluateActivity.this).inflate(R.layout.tagtextview,
                        broker_flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });

        fl_pic.setAdapter(new TagAdapter<String>(data.getAlbum()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(DealEvaluateActivity.this).inflate(R.layout.item_deal_evaluate_pic, parent, false);
                Glide.with(DealEvaluateActivity.this).load(s).into((ImageView) view);
                return view;
            }
        });
        if(state == Constant.DEAL){
            if(data.getLogo() != null){
                Glide.with(DealEvaluateActivity.this).load(data.getLogo()).into(company_icon);
            }
            company_flowlayout.setAdapter(new TagAdapter<String>(data.getCompany_label()) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(DealEvaluateActivity.this).inflate(R.layout.tagtextview,
                            broker_flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }
    }


    private void getCommentData(){
        RetrofitHelper.getApiWithUid().getComment(member_id,id,method)
                .compose(RxScheduler.<CommentResponse>defaultScheduler())
                .subscribe(new BaseObserver<CommentResponse>(DealEvaluateActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if (data == null) {
                            setNoDataView("暂无数据", R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if (data == null) {
                            setNetworkError();
                        }
                    }
                    @Override
                    public void next(CommentResponse commentResponse) {
                        data = commentResponse.getData();
                        setTakeLookView(data);
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void setEvaluateView(int state){
        if(state == Constant.DEAL){
            tv_title.setText(str_user_evaluation);
            ll_company.setVisibility(View.VISIBLE);
        }else{
            tv_title.setText(str_see_evaluation);
            ll_company.setVisibility(View.GONE);
        }
        getCommentData();

    }

    @OnClick({R.id.iv_left,R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.empty:
                setNetworkLoading();
                getCommentData();
                break;
        }
    }
}