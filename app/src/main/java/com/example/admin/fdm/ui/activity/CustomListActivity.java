package com.example.admin.fdm.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    @BindView(R.id.tv_right)
    TextView tv_right;

    //请求数据
    public Integer member_id;//经纪人id

    public Integer currentPager = 1 ;//页数

    //租房状态：无状态|unstatus|0,已租房|rent|1,求租中|renting|2,暂不租房|unrent|3 （全部就传-1）
    public Integer status = -1;//用户状态

    //用户列表数据
    private ArrayList<CustomListResponse.DataBean.ListBean> list = new ArrayList<>();

    //recyclerView适配器
    private CommonAdapter<CustomListResponse.DataBean.ListBean> adapter;

    //设置状态组件
    private PopupWindow mPopWindow;

    @Override
    protected void onResume() {
        super.onResume();
        getCustomListData(status,currentPager);
    }

    //设置布局
    @Override
    public int setLayout() {
        return R.layout.activity_custom_list;
    }


    //界面初始化
    @Override
    public void initEvent() {
        //获取member_id
        member_id = (Integer) SPUtil.get(this,"member_id",-1);
        setDisplayView(empty,ll_recyclerView);
        setNetworkLoading();
    }

    @Override
    public void initData() {

    }

    private void localListDelete(String id){
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getId().equals(id)){
                list.remove(i);
            }
        }
    }

    //删除用户请求
    private void deleteFriend(final String user_id){
        RetrofitHelper.getApiWithUid().DeleteChat(member_id,user_id)
                .compose(RxScheduler.<BaseResponse>defaultScheduler())
                .subscribe(new BaseObserver<BaseResponse>(CustomListActivity.this) {
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
                        if(baseResponse.getCode() == 0){
                            Toast.makeText(CustomListActivity.this,"删除用户成功",Toast.LENGTH_SHORT);
                            localListDelete(user_id);
                            getCustomListData(status,currentPager);
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    //用户列表请求
    private void getCustomListData(int status, final int currentPager){
        RetrofitHelper.getApiWithUid().getCustomList(member_id,currentPager,status)
                .compose(RxScheduler.<CustomListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<CustomListResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<CustomListResponse>(CustomListActivity.this) {
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
        adapter = new CommonAdapter<CustomListResponse.DataBean.ListBean>(this,R.layout.item_custom_list,list) {
            @Override
            protected void convert(ViewHolder holder, final CustomListResponse.DataBean.ListBean bean, int position) {
                holder.setText(R.id.tv_user_name,bean.getNick_name());
                holder.setText(R.id.tv_user_tel,bean.getPhone_number());
                if(bean.getAvatar() != null){
                    Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) holder.getView(R.id.iv_user_icon));
                }

                holder.setText(R.id.tv_time, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getCreate_at()));
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
                holder.getView(R.id.iv_talk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chatIntent = new Intent(CustomListActivity.this, ChatActivity.class);
                        chatIntent.putExtra(EaseConstant.USER_HEAD,bean.getAvatar());
                        chatIntent.putExtra(EaseConstant.USER_ID,bean.getUid());
                        chatIntent.putExtra(EaseConstant.USER_NAME,bean.getNick_name());
                        chatIntent.putExtra(EaseConstant.EXTRA_USER_ID,bean.getHx_username());

                        chatIntent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                        startActivity(chatIntent);
                    }
                });
                holder.getView(R.id.iv_take_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle takelook = new Bundle();
                        takelook.putString("uid",bean.getUid());
                        takelook.putString("user_name",bean.getNick_name());
                        takelook.putString("user_tel",bean.getPhone_number());
                        startActivity(BespeakTakeLookActivity.class,takelook);
                    }
                });

                holder.getView(R.id.tv_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CustomListActivity.this,UserDetailActivity.class);
                        intent.putExtra("Uid",bean.getUid());
                        startActivity(intent);
                    }
                });

                holder.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFriend(bean.getId());
                    }
                });
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(CustomListActivity.this, UserDetailActivity.class);
                intent.putExtra("Uid",list.get(position).getUid());
                startActivity(intent);
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
                getCustomListData(status,currentPager);
            }
        });
    }

    //修改状态事件处理
    View.OnClickListener listener = new View.OnClickListener(){//创建监听对象
        public void onClick(View v){
            switch (v.getId()){
                case R.id.yizufang:
                    status = 1;
                    currentPager = 1;
                    getCustomListData(status,currentPager);
                    break;
                case R.id.qiuzu:
                    status = 2;
                    currentPager = 1;
                    getCustomListData(status,currentPager);
                    break;
                case R.id.buzufang:
                    status = 3;
                    currentPager = 1;
                    getCustomListData(status,currentPager);
                    break;
                case R.id.wuzhuangtai:
                    status = 0;
                    currentPager = 1;
                    getCustomListData(status,currentPager);
                    break;
            }
        }
    };

    //点击事件
    @SuppressLint("Range")
    @OnClick({R.id.tv_right,R.id.iv_left,R.id.empty})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_right:
                View contentView = LayoutInflater.from(CustomListActivity.this).inflate(R.layout.tag_choose_panel, null);
                contentView.getBackground().setAlpha(5000);
                contentView.findViewById(R.id.yizufang).setOnClickListener(listener);
                contentView.findViewById(R.id.qiuzu).setOnClickListener(listener);
                contentView.findViewById(R.id.buzufang).setOnClickListener(listener);
                contentView.findViewById(R.id.wuzhuangtai).setOnClickListener(listener);
                mPopWindow = new PopupWindow(contentView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                mPopWindow.setContentView(contentView);
                mPopWindow.showAsDropDown(tv_right,-200,0);
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.empty:
                setNetworkLoading();
                status = -1;
                currentPager = 1;
                getCustomListData(status,currentPager);
                break;
        }
    }
}
