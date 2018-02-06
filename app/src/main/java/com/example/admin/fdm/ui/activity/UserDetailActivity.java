package com.example.admin.fdm.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.CutomDetailResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.CustomDatePicker;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/8.
 */

public class UserDetailActivity extends BaseActivity {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;
    @BindView(R.id.tv_right)
    TextView tv_right;

    private List<String> strings;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_tel)
    TextView tv_user_tel;
    @BindView(R.id.tv_user_state)
    TextView tv_user_state;

    private int member_id;
    private String id;
    private int uid;
    private String user_name;
    private String user_tel;
    private String user_state;
    private int currentPager = 1;


    private ArrayList<CutomDetailResponse.DataBean.ListBean> list = new ArrayList<>();

    private CutomDetailResponse.DataBean data;


    private PopupWindow mPopWindow;


    @Override
    public int setLayout() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void initEvent() {
        setDisplayView(empty,ll_linearlayout);

        member_id = (Integer) SPUtil.get(this,"member_id",-1);
        uid = Integer.parseInt(getIntent().getStringExtra("Uid"));
        setNetworkLoading();

        getDetailData();

    }

    @Override
    public void initData() {

    }

    private void setUserStatus(final int state) {
        mPopWindow.dismiss();
        RetrofitHelper.getApiWithUid().setChatStatus(member_id, uid, state)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(UserDetailActivity.this) {
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
                    public void next(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            setStateView("" + state);
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void setStateView(String state) {
        tv_user_name.setText(data.getNick_name());
        tv_user_tel.setText(data.getPhone_number());
        Resources resources = getBaseContext().getResources();
        Drawable drawable = null;
        switch (state) {
            case "0":
                tv_user_state.setVisibility(View.GONE);
                break;
            case "1":
                tv_user_state.setVisibility(View.VISIBLE);
                tv_user_state.setText("已租房");
                drawable = resources.getDrawable(R.drawable.house_tag_green);
                tv_user_state.setBackground(drawable);
                tv_user_state.setTextColor(getResources().getColor(R.color.text_greens));
                break;
            case "2":
                tv_user_state.setVisibility(View.VISIBLE);
                tv_user_state.setText("求租中");
                drawable = resources.getDrawable(R.drawable.house_tag_red);
                tv_user_state.setBackground(drawable);
                tv_user_state.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case "3":
                tv_user_state.setVisibility(View.VISIBLE);
                tv_user_state.setText("暂不租房");
                drawable = resources.getDrawable(R.drawable.house_tag_blue);
                tv_user_state.setBackground(drawable);
                tv_user_state.setTextColor(getResources().getColor(R.color.text_blue));
                break;
        }
    }


    private void getDetailData() {
        RetrofitHelper.getApiWithUid().getCustomDetail(member_id, uid, currentPager)
                .compose(RxScheduler.<CutomDetailResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<CutomDetailResponse>(refreshLayout, currentPager))
                .subscribe(new BaseObserver<CutomDetailResponse>(UserDetailActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            setNoDataView("暂无数据",R.drawable.no_user);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(CutomDetailResponse cutomDetailResponse) {
                            if(cutomDetailResponse.getData() == null){
                                setNoDataView("暂无数据",R.drawable.no_user);
                            }else{
                                data = cutomDetailResponse.getData();
                                setStateView(data.getStatus());
                                if (currentPager == 1) {
                                    list.clear();
                                    list.addAll(cutomDetailResponse.getData().getList());
                                } else {
                                    list.addAll(cutomDetailResponse.getData().getList());
                                }
                                setRecyclerView();
                            }


                    }

                    @Override
                    public void complete() {

                    }
                });
    }


    private void setRecyclerView() {
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<CutomDetailResponse.DataBean.ListBean>(this, R.layout.item_user_intention, list) {
            @Override
            protected void convert(ViewHolder holder, CutomDetailResponse.DataBean.ListBean bean, int position) {
                holder.setText(R.id.intent_date, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getCreate_at()));
                holder.setText(R.id.qiuzu_region, bean.getDistrict());
                holder.setText(R.id.qiuzu_price, bean.getPrice() + "元");
                holder.setText(R.id.qiuzu_mode, bean.getMethod());
                holder.setText(R.id.qiuzu_house_type, bean.getRoom());
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPager++;
                getDetailData();
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {//创建监听对象
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yizufang:
                        setUserStatus(1);
                        Toast.makeText(UserDetailActivity.this, "已租房", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.qiuzu:
                        setUserStatus(2);
                        Toast.makeText(UserDetailActivity.this, "求租中", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.buzufang:
                        setUserStatus(3);
                        Toast.makeText(UserDetailActivity.this, "暂不租房", Toast.LENGTH_LONG).show();
                        break;
            }
        }
    };


    @SuppressLint("Range")
    @OnClick({R.id.tv_right, R.id.iv_left, R.id.ll_take_look, R.id.ll_chat,R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                if(empty.getVisibility() != View.VISIBLE){
                View contentView = LayoutInflater.from(UserDetailActivity.this).inflate(R.layout.tag_state_panel, null);
                contentView.getBackground().setAlpha(2000);
                contentView.findViewById(R.id.yizufang).setOnClickListener(listener);
                contentView.findViewById(R.id.buzufang).setOnClickListener(listener);
                contentView.findViewById(R.id.qiuzu).setOnClickListener(listener);
                mPopWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mPopWindow.setContentView(contentView);
                mPopWindow.showAsDropDown(tv_right, -200, 0);
            }
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_take_look:
                Bundle takelook = new Bundle();
                takelook.putString("uid",data.getUid());
                takelook.putString("user_name",data.getNick_name());
                takelook.putString("user_tel",data.getPhone_number());
                startActivity(BespeakTakeLookActivity.class,takelook);
                break;
            case R.id.ll_chat:

                Intent chatIntent = new Intent(UserDetailActivity.this, ChatActivity.class);
                chatIntent.putExtra(EaseConstant.USER_HEAD,data.getAvatar());
                chatIntent.putExtra(EaseConstant.USER_ID,data.getUid());
                chatIntent.putExtra(EaseConstant.USER_NAME,data.getNick_name());
                chatIntent.putExtra(EaseConstant.EXTRA_USER_ID,data.getHx_username());

                chatIntent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(chatIntent);
                break;
            case R.id.empty:
                setNetworkLoading();
                setStateView(user_state);
                getDetailData();
                break;
        }

    }
}
