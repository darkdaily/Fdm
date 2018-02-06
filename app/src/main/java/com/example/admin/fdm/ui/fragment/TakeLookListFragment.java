package com.example.admin.fdm.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.mvp.module.TakeLookListResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.activity.DealEvaluateActivity;
import com.example.admin.fdm.ui.activity.TakeLookDetailActivity;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.example.admin.fdm.utils.system.SystemUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/7.
 */

public class TakeLookListFragment extends BaseSupportFragment {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private CommonAdapter<TakeLookListResponse.DataBean.ListBean> adapter;

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


    //请求参数
    public Integer member_id;
    public Integer currentPager = 1;
    public Integer status;

    private ArrayList<TakeLookListResponse.DataBean.ListBean> list = new ArrayList<>();
    ;

    private TakeLookDetailActivity mTlactivity = new TakeLookDetailActivity();

    @Override
    protected int setLayout() {
        return R.layout.list_fragment;
    }

    @Override
    protected void init() {
        setDisplayView(empty, ll_recyclerView);

        member_id = (Integer) SPUtil.get(mActivity, "member_id", -1);

        status = getArguments().getInt("state");

        setNetworkLoading();
        getListData();
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPager = 1;
        getListData();

    }

    private void getListData() {
        RetrofitHelper.getApiWithUid().getTakeLookList(member_id, currentPager, status)
                .compose(RxScheduler.<TakeLookListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<TakeLookListResponse>(refreshLayout, currentPager))
                .subscribe(new BaseObserver<TakeLookListResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            setNoDataView("还没有任何带看记录呢",R.drawable.no_takelook);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(TakeLookListResponse takeLookListResponse) {
                        if (takeLookListResponse.getData().getList().size() == 0 && currentPager == 1) {
                            setNoDataView("还没有任何带看记录呢",R.drawable.no_takelook);

                        } else {
                            if (currentPager == 1) {
                                list.clear();
                                list.addAll(takeLookListResponse.getData().getList());
                            } else {
                                list.addAll(takeLookListResponse.getData().getList());
                            }
                            setFragmentView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });

    }


    private void setFragmentView() {
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new CommonAdapter<TakeLookListResponse.DataBean.ListBean>(mActivity, R.layout.item_takelook_list, list) {
            @Override
            protected void convert(ViewHolder holder, final TakeLookListResponse.DataBean.ListBean bean, final int position) {
//                状态：用户待确认|user_reply|1,
//                      经纪人待确认|agent_reply|2,
//                      预约成功|success|3,
//                      带看完成|finish|4,
//                      已取消|cancel|5', （全部订单就为0）
                TextView tv_green = holder.getView(R.id.tv_green);
                tv_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (bean.getStatus()) {
                            case Constant.USER_WAIT_CONFIR:
                                SystemUtil.callPhone(mActivity,bean.getTelephone());
                                break;
                            case Constant.FANGDAMI_WAIT_CONFIR:
                                //确认带看
                                mTlactivity.setSuccess(mActivity, bean.getId(), member_id);
                                break;
                            case Constant.BESPAKING:
                                //完成带看
                                mTlactivity.setFinish(mActivity, bean.getId(), member_id);
                                break;
                            case Constant.ALREADY_COMPLETED:
                                //查看评论
                                Intent intent = new Intent(mActivity, DealEvaluateActivity.class);
                                intent.putExtra("state", Constant.TAKELOOK);
                                intent.putExtra("id", bean.getId());
                                intent.putExtra("method", 2);
                                startActivity(intent);
                                break;
                            case Constant.ALREADY_CANCEL:
                                CallTel(bean.getTelephone());
                                break;
                        }
                    }
                });

                TextView tv_black = holder.getView(R.id.tv_black);
                tv_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (bean.getStatus()) {
                            case Constant.FANGDAMI_WAIT_CONFIR:
                                //取消带看
                                mTlactivity.setCancel(mActivity, bean.getId(), member_id);
                                break;
                            case Constant.BESPAKING:
                                //取消带看
                                mTlactivity.setCancel(mActivity, bean.getId(), member_id);
                                break;
                            case Constant.ALREADY_COMPLETED:
                                CallTel(bean.getTelephone());
                                break;
                        }
                    }
                });
                if (bean.getStatus() != null) {
                    switch (bean.getStatus()) {
                        case Constant.USER_WAIT_CONFIR:
                            holder.setText(R.id.tv_takelook_state, str_user_wait_confir);
                            tv_black.setVisibility(View.GONE);
                            tv_green.setText("联系用户");
                            break;
                        case Constant.FANGDAMI_WAIT_CONFIR:
                            holder.setText(R.id.tv_takelook_state, str_fangdami_wait_confir);
                            tv_black.setVisibility(View.VISIBLE);
                            tv_black.setText("取消带看");
                            tv_green.setText("确认带看");
                            break;
                        case Constant.BESPAKING:
                            holder.setText(R.id.tv_takelook_state, str_bespeaking);
                            tv_black.setVisibility(View.VISIBLE);
                            tv_black.setText("取消带看");
                            tv_green.setText("完成带看");
                            break;
                        case Constant.ALREADY_COMPLETED:
                            holder.setText(R.id.tv_takelook_state, str_already_completed);
                            tv_black.setVisibility(View.VISIBLE);
                            tv_black.setText("联系用户");
                            tv_green.setText("查看评价");
                            break;
                        case Constant.ALREADY_CANCEL:
                            holder.setText(R.id.tv_takelook_state, str_already_cancel);
                            tv_black.setVisibility(View.GONE);
                            tv_green.setText("联系用户");
                            break;
                    }
                }
                holder.setText(R.id.takelook_time, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getExpect_time()));
                Glide.with(mActivity).load(bean.getHouse_photo()).into((ImageView) holder.getView(R.id.iv_house));
                holder.setText(R.id.tv_house_name, bean.getHouse_title());
                holder.setText(R.id.tv_house_info, bean.getHouse_info());
                holder.setText(R.id.tv_house_location, bean.getHouse_address());
                holder.setText(R.id.tv_house_rent, bean.getHouse_rental() + "元/月");
                showThreeTag(bean.getHouse_label(), (LinearLayout) holder.getView(R.id.ll_label));
                Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) holder.getView(R.id.iv_company_icon));
                holder.setText(R.id.tv_user_name, bean.getNick_name());
                holder.setText(R.id.tv_user_tel, bean.getPhone_number());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, TakeLookDetailActivity.class);
                intent.putExtra("takelookid", list.get(position).getId());
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

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }

    private void CallTel(String number) {
        Intent tel = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(tel);
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
















