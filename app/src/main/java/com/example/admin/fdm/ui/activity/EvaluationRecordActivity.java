package com.example.admin.fdm.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.MainEvaluateResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.ui.view.StartBar;
import com.example.admin.fdm.utils.SPUtil;
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
 * Created by test on 2018/1/7.
 */

public class EvaluationRecordActivity extends BaseActivity{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private int member_id;
    public Integer currentPager = 1;

    private ArrayList<MainEvaluateResponse.DataBean.ListBean> list = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_evaluation_record;
    }

    private void getEvaluateData(){
        RetrofitHelper.getApiWithUid().getMainEvaluate(member_id)
                .compose(RxScheduler.<MainEvaluateResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<MainEvaluateResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<MainEvaluateResponse>(EvaluationRecordActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if(list.size() == 0){
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
                    public void next(MainEvaluateResponse mainEvaluateResponse) {
                        if (mainEvaluateResponse.getData().getList().size() == 0 && currentPager == 1){
                            setNoDataView("暂无数据", R.drawable.no_house);
                        }else{
                            if(currentPager == 1){
                                list.clear();
                                list.addAll(mainEvaluateResponse.getData().getList());
                            }else{
                                list.addAll(mainEvaluateResponse.getData().getList());
                            }
                            setRecyclerView();
                        }

                    }

                    @Override
                    public void complete() {

                    }
                });

    }

    private void setRecyclerView(){
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(EvaluationRecordActivity.this));

        recyclerView.setAdapter(new CommonAdapter<MainEvaluateResponse.DataBean.ListBean>(EvaluationRecordActivity.this,R.layout.item_evaluate,list) {

            @Override
            protected void convert(ViewHolder holder, MainEvaluateResponse.DataBean.ListBean list, int position) {
                Glide.with(mActivity).load(list.getAvatar()).into((ImageView) holder.getView(R.id.user_icon));
                if(list.getHide() == 1){
                    holder.setText(R.id.tv_user_name,"匿名评价");
                }else{
                    holder.setText(R.id.tv_user_name,list.getNick_name());
                }
                holder.setText(R.id.tv_user_tel,list.getPhone_number());
                holder.setText(R.id.tv_user_tel,list.getPhone_number());
                holder.setText(R.id.tv_publish_time, TimeUtil.formatData(TimeUtil.dateFormatYMDHM, list.getCreate_at()));
                holder.setText(R.id.tv_evaluate_content,list.getContent());
                StartBar startBar = holder.getView(R.id.startbar);
                startBar.setRating(list.getScore());

                final TagFlowLayout flowLayout = (TagFlowLayout) holder.getView(R.id.flow_layout);
                flowLayout.setAdapter(new TagAdapter<String>(list.getLabel()) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv = (TextView) LayoutInflater.from(EvaluationRecordActivity.this).inflate(R.layout.tagtextview,
                                flowLayout, false);
                        tv.setText(s);
                        return tv;
                    }
                });
//                TagFlowLayout flpic = (TagFlowLayout) holder.getView(R.id.fl_pic);
//                flpic.setAdapter(new TagAdapter<String>(strings) {
//                    @Override
//                    public View getView(FlowLayout parent, int position, String o) {
//                        View view = LayoutInflater.from(EvaluationRecordActivity.this).inflate(R.layout.item_flowlayout_evaluation_pic, parent, false);
//                        return view;
//                    }
//                });
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                currentPager++;
                getEvaluateData();
            }
        });
    }

    @Override
    public void initEvent() {
        setDisplayView(empty,ll_recyclerView);

        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        tv_title.setText("评价记录");

        setNetworkLoading();
        getEvaluateData();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left,R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.empty:
                setNetworkLoading();
                getEvaluateData();
                break;

        }

    }


}
