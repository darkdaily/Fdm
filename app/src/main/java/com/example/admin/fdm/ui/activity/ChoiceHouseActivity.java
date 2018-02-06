package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.HouseSerializable;
import com.example.admin.fdm.mvp.module.TakeLookHouseResponse;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/7.
 */

public class ChoiceHouseActivity extends BaseActivity{

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private List<String> strings;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.ed_search)
    EditText ed_search;

    private int resultCode;
    private int member_id;
    public Integer currentPager = 1;

    private ArrayList<TakeLookHouseResponse.DataBean.ListBean> list = new ArrayList<>();

    private CommonAdapter<TakeLookHouseResponse.DataBean.ListBean> adapter;


    @Override
    public int setLayout() {
        return R.layout.activity_choice_house;
    }

    @Override
    public void initEvent() {

        setDisplayView(empty,ll_recyclerView);

        tv_title.setText("选择房源");

        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        setNoDataView("请搜索房源",R.drawable.no_house);

        setView();
    }

    @Override
    public void initData() {

    }


    private void getListData(String keyword){
        RetrofitHelper.getApiWithUid().getTakeLookHouseList(member_id,keyword)
                .compose(RxScheduler.<TakeLookHouseResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<TakeLookHouseResponse>(refreshLayout,currentPager))
                .subscribe(new BaseObserver<TakeLookHouseResponse>(ChoiceHouseActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        if(list.size() == 0){
                            setNoDataView("请搜索房源",R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        if(list.size() == 0){
                            setNetworkError();
                        }
                    }
                    @Override
                    public void next(TakeLookHouseResponse takeLookHouseResponse) {
                        if(takeLookHouseResponse.getData().getList().size() == 0 && currentPager == 1){
                            setNoDataView("请搜索房源",R.drawable.no_house);
                        }else{
                            if(currentPager == 1){
                                list.clear();
                                list.addAll(takeLookHouseResponse.getData().getList());
                            }else{
                                list.addAll(takeLookHouseResponse.getData().getList());
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
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoiceHouseActivity.this));

        adapter = new CommonAdapter<TakeLookHouseResponse.DataBean.ListBean>(ChoiceHouseActivity.this, R.layout.item_takelook_house_red, list) {
            @Override
            protected void convert(ViewHolder holder, TakeLookHouseResponse.DataBean.ListBean bean, final int position) {
                if(bean.getHouse_photo() != null){
                    Glide.with(mActivity).load(bean.getHouse_photo()).into((ImageView) holder.getView(R.id.iv_house));
                }
                holder.setText(R.id.tv_house_name,bean.getHouse_title());
                holder.setText(R.id.tv_house_info,bean.getHouse_info());
                holder.setText(R.id.tv_house_location,bean.getHouse_address());
                showThreeTag(bean.getHouse_label(), (LinearLayout) holder.getView(R.id.ll_label));
                holder.setText(R.id.tv_house_rent,bean.getHouse_rental());
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent mIntent = new Intent();
//                mIntent.putExtra("house_name", strings.get(position));
                TakeLookHouseResponse.DataBean.ListBean bean = list.get(position);
                HouseSerializable house = new HouseSerializable();
                house.setHouse_id(bean.getHouse_id());
                house.setHouse_title(bean.getHouse_title());
                house.setHouse_info(bean.getHouse_info());
                house.setHouse_address(bean.getHouse_address());
                house.setHouse_photo(bean.getHouse_photo());
                house.setHouse_label(bean.getHouse_label());
                house.setHouse_rental(bean.getHouse_rental());
                house.setRoom_id(bean.getRoom_id());
                house.setType(bean.getType());
                // 设置结果，并进行传送
                resultCode = 0;
                mIntent.putExtra("house",house);
                ChoiceHouseActivity.this.setResult(resultCode, mIntent);
                ChoiceHouseActivity.this.finish();
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
                getListData(ed_search.getText().toString());

            }
        });
    }

    private void setView(){
        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    currentPager = 1;
                    setNetworkLoading();
                    getListData(ed_search.getText().toString());
                }
                return false;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            resultCode = 1;
            ChoiceHouseActivity.this.setResult(resultCode);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick({R.id.iv_left,R.id.search_btn,R.id.empty})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                resultCode = 1;
                ChoiceHouseActivity.this.setResult(resultCode);
                finish();
                break;
            case R.id.search_btn:
                break;
            case R.id.empty:
                if(!ed_search.getText().toString().equals("")){
                    currentPager = 1;
                    setNetworkLoading();
                    getListData(ed_search.getText().toString());
                }
                break;
        }

    }
}
