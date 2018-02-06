package com.example.admin.fdm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.mvp.module.CrabListResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.activity.ChatActivity;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/22.
 */

public class CrabListFragment extends BaseSupportFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    //抢单列表数据
    private List<CrabListResponse.DataBean.ListBean> list = new ArrayList<>();

    //recyclerView适配器
    private CommonAdapter<CrabListResponse.DataBean.ListBean> adapter;

    //经纪人id
    public Integer member_id;

    //页数
    public Integer currentPager = 1;

    //界面状态  待抢单|1，已抢单|2
    public Integer state;

    //设置布局
    @Override
    protected int setLayout() {
        return R.layout.list_fragment;
    }

    //初始化
    @Override
    protected void init() {
        member_id = (Integer) SPUtil.get(mActivity,"member_id",-1);
        setDisplayView(empty,ll_recyclerView);
        state = getArguments().getInt("state");
        setNetworkLoading();
        getGrabListData(mActivity,member_id,currentPager,state);

    }

    //设置布局
    private void setFragmentView() {
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CommonAdapter<CrabListResponse.DataBean.ListBean>(mActivity,R.layout.item_crab_list,list) {
            @Override
            protected void convert(ViewHolder holder, final CrabListResponse.DataBean.ListBean listBean, int position) {
                    Glide.with(mActivity).load(listBean.getAvatar()).into((ImageView) holder.getView(R.id.iv_user_icon));
                    holder.setText(R.id.tv_user_name,listBean.getNick_name());
                    holder.setText(R.id.tv_user_tel,listBean.getPhone_number());
                    holder.setText(R.id.tv_crab_date, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, listBean.getCreate_at()));
                    holder.setText(R.id.qiuzu_region,listBean.getDistrict());
                    holder.setText(R.id.qiuzu_price,listBean.getPrice());
                    holder.setText(R.id.qiuzu_mode,listBean.getMethod());
                    holder.setText(R.id.qiuzu_house_type,listBean.getRoom());
                    TextView textView = holder.getView(R.id.tv_crab_order);

                if(state == 1){
                        if(listBean.getExpired() == 1){
                            textView.setText("已过期");
                            textView.setTextColor(getResources().getColor(R.color.black));
                            textView.setBackgroundResource(R.drawable.gray_solid_bg);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }else{
                            textView.setText("立即抢单");
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setCrab(listBean);
                                }
                            });
                        }
                    }else{
                    textView.setText("与TA聊一聊");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, ChatActivity.class);
                            intent.putExtra(EaseConstant.USER_HEAD,listBean.getAvatar());
                            intent.putExtra(EaseConstant.USER_ID,listBean.getUid());


                            intent.putExtra(EaseConstant.EXTRA_USER_ID,listBean.getHx_username());
                            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                            startActivity(intent);
                        }
                    });
                    }
                    holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteCrab(listBean);
                        }
                    });
            }
        };
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPager++;
                getGrabListData(mActivity,member_id,currentPager,state);

            }
        });
    }

    //请求抢单数据
    private void setCrab(final CrabListResponse.DataBean.ListBean bean){
        RetrofitHelper.getApiWithUid().setCrab(member_id,bean.getId())
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(mActivity) {
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
                            Toast.makeText(mActivity,"抢单成功",Toast.LENGTH_LONG).show();
                            localListDelete(bean.getId());
                        }
                        getGrabListData(mActivity,member_id,currentPager,state);
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    //删除订单
    private void deleteCrab(final CrabListResponse.DataBean.ListBean bean){
        RetrofitHelper.getApiWithUid().deleteCrab(member_id,bean.getId())
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(mActivity) {
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
                            Toast.makeText(mActivity,"删除成功",Toast.LENGTH_LONG).show();
                            localListDelete(bean.getId());
                        }
                        getGrabListData(mActivity,member_id,currentPager,state);
                    }

                    @Override
                    public void complete() {

                    }
                });

    }

    public  void getGrabListData(Activity activity, int member_id, final int currentPager, int status){
        RetrofitHelper.getApiWithUid().getGrabList(member_id,currentPager,status)
                .compose(RxScheduler.<CrabListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<CrabListResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<CrabListResponse>(activity) {
                    @Override
                    public void error(Throwable e) {
                    }
                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            if(state == 1 ){
                                setNoDataView("用户还没有发单呢，请耐心等待一下",R.drawable.no_crab);
                            }else if(state == 2){
                                setNoDataView("你最近是不是偷懒了，抢个单炫耀一下吧",R.drawable.no_crab);
                            }
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                           setNetworkError();
                        }
                    }
                    @Override
                    public void next(CrabListResponse grabListResponse) {
                        if(grabListResponse.getData().getList().size() == 0 && list.size() == 0){
                            if(state == 1 ){
                                setNoDataView("用户还没有发单呢，请耐心等待一下",R.drawable.no_crab);
                            }else if(state == 2){
                                setNoDataView("你最近是不是偷懒了，抢个单炫耀一下吧",R.drawable.no_crab);
                            }
                        }else{
                            if(currentPager == 1){
                                list.clear();
                                list.addAll(grabListResponse.getData().getList());
                            }else{
                                list.addAll(grabListResponse.getData().getList());
                            }
                            setFragmentView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void localListDelete(String id){
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getId().equals(id)){
                list.remove(i);
            }
        }
    }


    @OnClick({R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.empty:
                setNetworkLoading();
                currentPager = 1;
                getGrabListData(mActivity,member_id,currentPager,state);
                break;
        }
    }
}
