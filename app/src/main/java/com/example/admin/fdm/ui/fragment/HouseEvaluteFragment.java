package com.example.admin.fdm.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.mvp.module.HouseEvaluteBaseResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.ui.view.StartBar;
import com.example.admin.fdm.utils.date.TimeUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseEvaluteFragment extends BaseSupportFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    private int type;
    private String house_id;
    private String room_id;
    private int state;
    public Integer currentPager = 1;
    private List<HouseEvaluteBaseResponse.DataBean.ListBean> list = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.fragment_house_evalute;
    }

    @Override
    protected void init() {

        setDisplayView(empty, ll_recyclerView);

        house_id = getArguments().getString("house_id");
        room_id = getArguments().getString("room_id");
        state = getArguments().getInt("state");
        if (state == 1 || state == 4) {
            type = 1;
        } else if (state == 2 || state == 3) {
            type = 2;
        }
        setNetworkLoading();
        getHouseEvaluteData();
    }

    private void setRecyclerView() {
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CommonAdapter<HouseEvaluteBaseResponse.DataBean.ListBean>(getContext(), R.layout.item_evaluate, list) {

            @Override
            protected void convert(ViewHolder holder, HouseEvaluteBaseResponse.DataBean.ListBean bean, int position) {
                Glide.with(mActivity).load(bean.getAvatar()).into((ImageView) holder.getView(R.id.user_icon));
                if (bean.getHide().equals("1")) {
                    holder.setText(R.id.tv_user_name, "匿名评价");
                } else {
                    holder.setText(R.id.tv_user_name, bean.getReal_name());
                }
                holder.setText(R.id.tv_user_tel, bean.getPhone_number());
                holder.setText(R.id.tv_publish_time, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, bean.getCreate_at()));
                StartBar startbar = holder.getView(R.id.startbar);
                startbar.setRating(bean.getScore());

                holder.setText(R.id.tv_evaluate_content, bean.getContent());


                final TagFlowLayout flowLayout = (TagFlowLayout) holder.getView(R.id.flow_layout);
                flowLayout.setAdapter(new TagAdapter<String>(bean.getLabel()) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tagtextview,
                                flowLayout, false);
                        tv.setText(s);
                        return tv;
                    }
                });


                TagFlowLayout flpic = (TagFlowLayout) holder.getView(R.id.fl_pic);
                flpic.setAdapter(new TagAdapter<String>(bean.getAlbum()) {
                    @Override
                    public View getView(FlowLayout parent, int position, String o) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_flowlayout_evaluation_pic, parent, false);
                        Glide.with(mActivity).load(o).into((ImageView) view);
                        return view;
                    }
                });
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPager++;
                getHouseEvaluteData();

            }
        });
    }

    private void getHouseEvaluteData() {
        RetrofitHelper.getApiWithUid().getHouseEvalute(type, house_id, room_id, currentPager)
                .compose(RxScheduler.<HouseEvaluteBaseResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<HouseEvaluteBaseResponse>(refreshLayout, currentPager))
                .subscribe(new BaseObserver<HouseEvaluteBaseResponse>(mActivity) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        if (list.size() == 0) {
                            setNoDataView("暂无数据", R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if (list.size() == 0) {
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(HouseEvaluteBaseResponse houseEvaluteBaseResponse) {
                        if (houseEvaluteBaseResponse.getData().getList().size() == 0 && currentPager == 1) {
                            setNoDataView("暂无数据", R.drawable.no_house);
                        } else {
                            if (currentPager == 1) {
                                list.clear();
                                list.addAll(houseEvaluteBaseResponse.getData().getList());
                            } else {
                                list.addAll(houseEvaluteBaseResponse.getData().getList());
                            }
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    @OnClick({R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty:
                currentPager = 1;
                setNetworkLoading();
                getHouseEvaluteData();
                break;
        }
    }
}
