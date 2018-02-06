package com.example.admin.fdm.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.CustomListResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/31.
 */

public class ForwardActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    private int ClickItemNum = -1;

    //请求数据
    public Integer member_id;//经纪人id

    public Integer currentPager = 1 ;//页数

    //租房状态：无状态|unstatus|0,已租房|rent|1,求租中|renting|2,暂不租房|unrent|3 （全部就传-1）
    public Integer status = -1;//用户状态

    //用户列表数据
    private ArrayList<CustomListResponse.DataBean.ListBean> list = new ArrayList<>();

    //recyclerView适配器
    private CommonAdapter<CustomListResponse.DataBean.ListBean> adapter;

    private Intent chatIntent;

    private Bundle forwardBundle;


    @Override
    public int setLayout() {
        return R.layout.activity_forward;
    }

    @Override
    public void initEvent() {
        setDisplayView(empty,ll_recyclerView);

        member_id = (Integer) SPUtil.get(this,"member_id",-1);
//        chatIntent = new Intent(ForwardActivity.this, ChatActivity.class);
        forwardBundle = getIntent().getExtras();
        getCustomListData();
    }

    @Override
    public void initData() {

    }

    private void getCustomListData(){
        RetrofitHelper.getApiWithUid().getForwardList(member_id,-1,"",currentPager)
                .compose(RxScheduler.<CustomListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<CustomListResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<CustomListResponse>(ForwardActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            setNoDataView("别偷懒了，赶紧找用户吧",R.drawable.no_user);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(CustomListResponse customListResponse) {
                        if (customListResponse.getData().getList().size() == 0 && list.size() == 0){
                            setNoDataView("别偷懒了，赶紧找用户吧",R.drawable.no_user);
                        }else{
                            if(currentPager == 1){
                                list.clear();
                                list.addAll(customListResponse.getData().getList());
                            }else{
                                list.addAll(customListResponse.getData().getList());
                            }
                            setListView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }
    private void setListView(){
        setHideLayout();
        ll_recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter<CustomListResponse.DataBean.ListBean>(this,R.layout.item_forward,list) {
            @Override
            protected void convert(ViewHolder holder, final CustomListResponse.DataBean.ListBean bean, final int position) {

                holder.setText(R.id.tv_user_name,bean.getNick_name());
                holder.setText(R.id.tv_user_tel,bean.getPhone_number());
                if(bean.getAvatar() != null){
                    Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) holder.getView(R.id.iv_user_icon));
                }

                final CheckBox checkBox = (CheckBox) holder.getView(R.id.select_checkbox);
                checkBox.setEnabled(false);

                if(ClickItemNum == position){
                    checkBox.setChecked(true);
                }else{
                    checkBox.setChecked(false);
                }

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!checkBox.isChecked()){
                            ClickItemNum = position;
                            checkBox.setChecked(true);
                            setForwardBundle(position);
                        }else{
                            ClickItemNum = -1;
                            checkBox.setChecked(false);
                            forwardBundle.putString(EaseConstant.USER_ID,"");
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                if(bean.getStatus() != null){
                    Resources resources = getBaseContext().getResources();
                    Drawable drawable = null;
                    switch (bean.getStatus()){
                        case "1":
                            holder.setText(R.id.tv_user_state,"已租房");
                            drawable = resources.getDrawable(R.drawable.house_tag_green);
                            holder.getView(R.id.tv_user_state).setBackground(drawable);
                            holder.setTextColor(R.id.tv_user_state,getResources().getColor(R.color.text_greens));
                            holder.getView(R.id.tv_user_state).setVisibility(View.VISIBLE);
                            break;
                        case "2":
                            holder.setText(R.id.tv_user_state,"求租中");
                            drawable = resources.getDrawable(R.drawable.house_tag_red);
                            holder.getView(R.id.tv_user_state).setBackground(drawable);
                            holder.getView(R.id.tv_user_state).setVisibility(View.VISIBLE);
                            holder.setTextColor(R.id.tv_user_state,getResources().getColor(R.color.text_red));
                            break;
                        case "3":
                            holder.setText(R.id.tv_user_state,"暂不租房");
                            drawable = resources.getDrawable(R.drawable.house_tag_blue);
                            holder.getView(R.id.tv_user_state).setBackground(drawable);
                            holder.getView(R.id.tv_user_state).setVisibility(View.VISIBLE);
                            holder.setTextColor(R.id.tv_user_state,getResources().getColor(R.color.text_blue));
                            break;
                    }
                }
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                CheckBox checkBox = (CheckBox) holder.itemView.findViewById(R.id.select_checkbox);
                if(!checkBox.isChecked()){
                    ClickItemNum = position;
                    checkBox.setChecked(true);
                    setForwardBundle(position);
                }else{
                    ClickItemNum = -1;
                    checkBox.setChecked(false);
                    forwardBundle.putString(EaseConstant.USER_ID,"");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(adapter);

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPager++;
                getCustomListData();
            }
        });
    }

    private void setForwardBundle(int position){
        forwardBundle.putString(EaseConstant.USER_HEAD,list.get(position).getAvatar());
        forwardBundle.putString(EaseConstant.USER_ID, list.get(position).getUid());
        forwardBundle.putString(EaseConstant.USER_NAME, list.get(position).getNick_name());
        forwardBundle.putString(EaseConstant.EXTRA_USER_ID, list.get(position).getHx_username());
        forwardBundle.putSerializable(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
    }


    //点击事件
    @SuppressLint("Range")
    @OnClick({R.id.tv_right,R.id.iv_left,R.id.empty})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_right:
                if(forwardBundle.getString(EaseConstant.USER_ID).equals("")){
                    Toast.makeText(ForwardActivity.this,"请选择好友",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForwardActivity.this);
                    builder.setTitle("是否确认分享该房源").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(ChatActivity.class,forwardBundle);
                            finish();
                        }
                    }).show();
                }
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.empty:
                setNetworkLoading();
                status = -1;
                currentPager = 1;
                getCustomListData();
                break;
        }
    }
}
