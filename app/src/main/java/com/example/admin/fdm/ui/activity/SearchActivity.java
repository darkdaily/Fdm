package com.example.admin.fdm.ui.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.ClaimSearchResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.fl_history)
    TagFlowLayout fl_history;

    @BindView(R.id.et_input)
    EditText et_input;

    private TagAdapter adapter;

    @BindView(R.id.ll_history)
    LinearLayout ll_history;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;

    private List<String> list = new ArrayList<>();
    private String keyword;
    private int member_id;
    public Integer currentPager = 1;
    private String state;
    private List<ClaimSearchResponse.DataBean.ListBean> Slist = new ArrayList<>();


    @OnClick({R.id.iv_del,R.id.search_btn,R.id.empty})
    public void onClick(View view){
        switch (view.getId()) {
            case  R.id.iv_del:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认删除全部搜索历史").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtil.remove(mActivity, Constant.SEARCH_HISTORY);
                        list.clear();
                        adapter.notifyDataChanged();
                    }
                }).show();
                break;
            case R.id.search_btn:
                if(et_input.getText().length() > 0){
                    et_input.setText("");
                    if(ll_recyclerView.getVisibility() == View.VISIBLE){
                        ll_recyclerView.setVisibility(View.GONE);
                        ll_history.setVisibility(View.VISIBLE);
                    }
                    if(empty.getVisibility() == View.VISIBLE){
                        empty.setVisibility(View.GONE);
                        ll_history.setVisibility(View.VISIBLE);
                    }
                }else{
                    finish();
                }
                break;
            case R.id.empty:
                if(!et_input.getText().toString().equals("")){
                    setNetworkLoading();
                    currentPager = 1;
                    if (state.equals("房源管理")) {
                        getHouseManageSearchData(et_input.getText().toString());
                    } else {
                        getClaimSearchData(et_input.getText().toString());
                    }
                }
                break;
        }
    }


    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }

    private void  getHouseManageSearchData(String keyword){
        this.keyword = keyword;
        RetrofitHelper.getApiWithUid().getHouseMeangementSearch(keyword ,member_id)
                .compose(RxScheduler.<ClaimSearchResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<ClaimSearchResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<ClaimSearchResponse>(SearchActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if(Slist.size() == 0){
                            ll_history.setVisibility(View.GONE);
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(Slist.size() == 0){
                            ll_history.setVisibility(View.GONE);
                            setNetworkError();
                        }
                    }
                    @Override
                    public void next(ClaimSearchResponse claimSearchResponse) {
                        if(claimSearchResponse.getData().getList().size() == 0 && currentPager == 1){
                            ll_history.setVisibility(View.GONE);
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }else{
                            if(currentPager == 1){
                                Slist.clear();
                                Slist.addAll(claimSearchResponse.getData().getList());
                            }else{
                                Slist.addAll(claimSearchResponse.getData().getList());
                            }
                            setRecyclerView(Slist);
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void  getClaimSearchData(String keyword){
        this.keyword = keyword;
        RetrofitHelper.getApiWithUid().getClaimSearch(keyword)
                .compose(RxScheduler.<ClaimSearchResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<ClaimSearchResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<ClaimSearchResponse>(SearchActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if(Slist.size() == 0){
                            ll_history.setVisibility(View.GONE);
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(Slist.size() == 0){
                            ll_history.setVisibility(View.GONE);
                            setNetworkError();
                        }
                    }
                    @Override
                    public void next(ClaimSearchResponse claimSearchResponse) {
                        if(claimSearchResponse.getData().getList().size() == 0 && currentPager == 1){
                            ll_history.setVisibility(View.GONE);
                            setNoDataView("暂无数据",R.drawable.no_house);
                        }else{
                            if(currentPager == 1){
                                Slist.clear();
                                Slist.addAll(claimSearchResponse.getData().getList());
                            }else{
                                Slist.addAll(claimSearchResponse.getData().getList());
                            }
                            setRecyclerView(Slist);
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }



    @Override
    public void initEvent() {
        setDisplayView(empty,ll_recyclerView);

        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        state = getIntent().getExtras().getString("type");

        setSearchView();

        ll_history.setVisibility(View.VISIBLE);

        setHistoryView();

    }


    private void setHistoryView() {
        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    setNetworkLoading();
                    ll_history.setVisibility(View.GONE);
                    if(et_input.getText().toString().equals("")){

                    }else{
                        if (state.equals("房源管理")) {
                            getHouseManageSearchData(et_input.getText().toString());
                        } else {
                            getClaimSearchData(et_input.getText().toString());
                        }
                        String s = (String) SPUtil.get(mActivity, Constant.SEARCH_HISTORY, "");
                        if (s.equals("")) {
                            s = et_input.getText().toString();
                        } else {
                            s += "," + et_input.getText();
                        }
                        SPUtil.put(mActivity, Constant.SEARCH_HISTORY, s);
                        list.add(et_input.getText().toString());
                        adapter.notifyDataChanged();
                    }


                    return true;
                }
                return false;
            }
        });

        String s = (String) SPUtil.get(mActivity, Constant.SEARCH_HISTORY, "");
        String[] split = s.split(",");
        for (String s1 : split) {
            if (!s1.equals("")) {
                list.add(s1);
            }
        }

        adapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView textView = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.item_history_search, null, false);
                textView.setText(o);
                return textView;
            }
        };

        fl_history.setAdapter(adapter);
        fl_history.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                et_input.setText(list.get(position));
                Toast.makeText(SearchActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                ArrayList<String> hot = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    hot.add(list.get(position));
                }
                if (state.equals("房源管理")) {
                    getHouseManageSearchData(list.get(position));
                } else {
                    getClaimSearchData(list.get(position));
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }


    public void setRecyclerView(final List<ClaimSearchResponse.DataBean.ListBean> list){
        setHideLayout();
        ll_history.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        CommonAdapter<ClaimSearchResponse.DataBean.ListBean> adapter = new CommonAdapter<ClaimSearchResponse.DataBean.ListBean>(SearchActivity.this, R.layout.item_recommend_house, list) {
            @Override
            protected void convert(ViewHolder holder, ClaimSearchResponse.DataBean.ListBean bean, int position) {
                Glide.with(mActivity).load(bean.getHouse_photo()).into((ImageView) holder.getView(R.id.iv_house));
                holder.setText(R.id.tv_house_name,bean.getHouse_title());
                holder.setText(R.id.tv_house_info,bean.getHouse_info());
                holder.setText(R.id.tv_house_location,bean.getHouse_address());
                LinearLayout ll_lable = holder.getView(R.id.ll_label);
                showThreeTag(bean.getHouse_label(),ll_lable);
                holder.setText(R.id.tv_house_rent,bean.getHouse_rental());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle mBundle = new Bundle();
                if(list.get(position).getType().equals("1")){ //分散式
                    mBundle.putInt("state", Constant.CLAIM_HOUSING_RESOURCES);
                }else{//集中式
                    mBundle.putInt("state", Constant.CLAIM_ROOM);
                }
                mBundle.putString("house_id",list.get(position).getHouse_id());
                mBundle.putString("room_id",list.get(position).getRoom_id());
                startActivity(HouseDetailActivity.class, mBundle);
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
                if(state.equals("房源管理")){
                    getHouseManageSearchData(keyword);
                }else{
                    getClaimSearchData(keyword);
                }
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
}
