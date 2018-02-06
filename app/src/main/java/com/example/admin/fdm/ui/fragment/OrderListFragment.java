package com.example.admin.fdm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.mvp.module.OrderListResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.activity.OrderDetailActivity;
import com.example.admin.fdm.ui.activity.RevisePriceActivity;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.system.SystemUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/5.
 */

public class OrderListFragment extends BaseSupportFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    private CommonAdapter<OrderListResponse.DataBean.ListBean> adapter;

    //经纪人id
    public Integer member_id;

    //页数
    public Integer currentPager = 1;

    //类型
    public Integer state;

    //订单数据
    private ArrayList<OrderListResponse.DataBean.ListBean> list = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.list_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPager = 1;
        getListData();
    }

    @Override
    protected void init() {

        member_id = (Integer) SPUtil.get(mActivity,"member_id",-1);

        setDisplayView(empty,ll_recyclerView);

        state = getArguments().getInt("state");

        if(list.size() == 0){
            setNetworkLoading();
        }
//        getListData();
    }

    private boolean setDeleteOrder(final Activity activity, final int order_id, int member_id, int uid){
        RetrofitHelper.getApiWithUid().setDeleteOrder(order_id,member_id,uid)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(activity) {
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
                            Toast.makeText(activity,"删除完成",Toast.LENGTH_LONG).show();
                            localListDelete(order_id);
                            getListData();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
        return false;
    }

    private void localListDelete(int ordid){
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getOrder_id() == ordid){
                list.remove(i);
            }
        }
    }

    private void getListData() {
        RetrofitHelper.getApiWithUid().getOrderList(member_id, currentPager, state)
                .compose(RxScheduler.<OrderListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<OrderListResponse>(refreshLayout, currentPager))
                .subscribe(new BaseObserver<OrderListResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            setNoDataView("不要偷懒找用户下单吧",R.drawable.no_order);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                            setNetworkError();
                        }
                    }
                    @Override
                    public void next(OrderListResponse orderListResponse) {
                        if(orderListResponse.getData().getList().size() == 0 && currentPager == 1){
                            setNoDataView("不要偷懒找用户下单吧",R.drawable.no_order);
                        }else{
                            if(currentPager == 1){
                                list.clear();
                                list.addAll(orderListResponse.getData().getList());
                            }else{
                                list.addAll(orderListResponse.getData().getList());
                            }
                            setFragmentView();
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

    private void setFragmentView() {
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CommonAdapter<OrderListResponse.DataBean.ListBean>(mActivity, R.layout.item_order_list, list) {
            @Override
            protected void convert(ViewHolder holder, final OrderListResponse.DataBean.ListBean bean, final int position) {
                Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) holder.getView(R.id.iv_user_icon));
                holder.setText(R.id.tv_user_name, bean.getReal_name());
                holder.setText(R.id.tv_user_tel, bean.getPhone_number());

                Glide.with(mActivity).load(bean.getHouse_photo()).into((ImageView) holder.getView(R.id.iv_house));
                holder.setText(R.id.tv_house_name, bean.getHouse_title());
                holder.setText(R.id.tv_house_info, bean.getPay_type());
                holder.setText(R.id.tv_house_location, bean.getAddress());
                holder.setText(R.id.tv_house_rent, ""+bean.getRent_money());
                showThreeTag(bean.getLabel(), (LinearLayout) holder.getView(R.id.ll_label));
                TextView tv_green = holder.getView(R.id.tv_green);
                TextView tv_black = holder.getView(R.id.tv_black);
                //订单状态 1待付款 2已完成 3已取消 0全部 (默认值为0)
                if (bean.getStatus() != null) {
                    switch (bean.getStatus()) {
                        case "待付款":
                            holder.setText(R.id.tv_order_state, "用户待支付");
                            tv_green.setText("修改价格");
                            tv_black.setVisibility(View.GONE);
                            break;
                        case "已完成":
                            holder.setText(R.id.tv_order_state, "已完成");
                            tv_green.setText("联系用户");
                            tv_black.setVisibility(View.GONE);
                            break;
                        case "已取消":
                            holder.setText(R.id.tv_order_state, "已取消");
                            tv_green.setText("联系用户");
                            tv_black.setText("删除");
                            break;
                    }
                    tv_green.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(bean.getStatus().equals("待付款")){
                                //修改订单
                                Intent rpintent = new Intent(mActivity, RevisePriceActivity.class);
                                rpintent.putExtra("uid", bean.getUid());
                                rpintent.putExtra("order_id", bean.getOrder_id());
                                rpintent.putExtra("member_id", bean.getMember_id());
                                startActivity(rpintent);
                            }else{
                                //联系用户
                                SystemUtil.callPhone(mActivity,bean.getReal_number());

                            }
                        }
                    });
                    tv_black.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(bean.getStatus().equals("已取消")){
                                //删除
                                setDeleteOrder(mActivity,bean.getOrder_id(),bean.getMember_id(),bean.getUid());
                            }
                        }
                    });
                    holder.setText(R.id.tv_total_price, bean.getPayment());
                }
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                intent.putExtra("order_id", list.get(position).getOrder_id());
                intent.putExtra("uid", list.get(position).getUid());
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
                getListData();
            }
        });
    }

    @OnClick({R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.empty:
                setNetworkLoading();
                currentPager = 1;
                getListData();
                break;
        }
    }
}
