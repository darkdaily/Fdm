package com.example.admin.fdm.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.ClaimHouseListParameter;
import com.example.admin.fdm.mvp.module.ClaimHouseListResponse;
import com.example.admin.fdm.mvp.module.TradingAreaResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.easeui.EaseConstant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by test on 2018/1/30.
 */

public class ChatHouseListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_recyclerView)
    LinearLayout ll_recyclerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;

    @BindView(R.id.rb_jizhongshi)
    RadioButton rb_jizhongshi;

    @BindView(R.id.rb_fensanshi)
    RadioButton rb_fensanshi;

    //经纪人id
    private int member_id;

    //页数
    public Integer currentPager = 1;

    //房源类型 1分散式 2集中式
    private int houseType = 2;

    private CommonAdapter<TradingAreaResponse.DataBean.ListBean> adapter;

    //房源列表数据
    private ArrayList<ClaimHouseListResponse.DataBean.ListBean> jlist = new ArrayList<>();

    private ArrayList<ClaimHouseListResponse.DataBean.ListBean> flist = new ArrayList<>();

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_fensanshi) {
            houseType = 1;
            if (flist.size() == 0) {
                getAlreadyClaimHouseData(setInitData(houseType));
            } else {
                setRecyclerView(flist);
            }
        } else {
            houseType = 2;
            if (jlist.size() == 0) {
                getAlreadyClaimHouseData(setInitData(houseType));
            } else {
                setRecyclerView(jlist);
            }
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_chat_house;
    }

    @Override
    public void initEvent() {
        setDisplayView(empty,ll_recyclerView);

        member_id = (Integer) SPUtil.get(this, "member_id", -1);

        radio_group.setOnCheckedChangeListener(this);

        setNetworkLoading();

        getAlreadyClaimHouseData(setInitData(houseType));
    }

    @Override
    public void initData() {

    }

    private void getAlreadyClaimHouseData(final ClaimHouseListParameter chp) {
        RetrofitHelper.getApiWithUid().getAlreadyClaimHouseList(member_id,
                chp.getRent_start(),
                chp.getRent_end(),
                chp.getRoom(),
                chp.getHouse_name(),
                chp.getOrder(),
                chp.getSort(),
                chp.getAreaid(),
                currentPager,
                chp.getHouse_type())
                .compose(RxScheduler.<ClaimHouseListResponse>defaultScheduler())
                .doOnNext(new FinishLoadConsumer<ClaimHouseListResponse>(refreshLayout, currentPager))
                .subscribe(new BaseObserver<ClaimHouseListResponse>(ChatHouseListActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        //房源类型 1分散式 2集中式
                        if ((chp.getHouse_type() == 1 && flist.size() <= 1) || (chp.getHouse_type() == 2 && jlist.size() <= 1)) {
                            setNoDataView("先去认领公司房源吧，认领后有福利哦", R.drawable.no_house);
                        }
                    }

                    @Override
                    public void NoNetwork() {
                        //房源类型 1分散式 2集中式
                        if ((chp.getHouse_type() == 1 && flist.size() == 0) || (chp.getHouse_type() == 2 && jlist.size() == 0)) {
                            setNetworkError();
                        }
                    }

                    @Override
                    public void next(ClaimHouseListResponse claimHouseListResponse) {
                        if (chp.getHouse_type() == 1 ) {
                            if (claimHouseListResponse.getData().getList().size() == 0 && currentPager == 1) {
                                setNoDataView("先去认领公司房源吧，认领后有福利哦", R.drawable.no_house);
                            } else {
                                if (currentPager == 1) {
                                    flist.clear();
                                    flist.addAll(claimHouseListResponse.getData().getList());
                                } else {
                                    flist.addAll(claimHouseListResponse.getData().getList());
                                }
                                setRecyclerView(flist);
                            }
                        } else {
                            if (claimHouseListResponse.getData().getList().size() == 0 && currentPager == 1) {
                                setNoDataView("先去认领公司房源吧，认领后有福利哦", R.drawable.no_house);
                            } else {
                                if (currentPager == 1) {
                                    jlist.clear();
                                    jlist.addAll(claimHouseListResponse.getData().getList());
                                } else {
                                    jlist.addAll(claimHouseListResponse.getData().getList());
                                }
                                setRecyclerView(jlist);
                            }
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


    private void setRecyclerView(final List<ClaimHouseListResponse.DataBean.ListBean> list){
        setHideLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonAdapter<ClaimHouseListResponse.DataBean.ListBean> adapter = new CommonAdapter<ClaimHouseListResponse.DataBean.ListBean>(this, R.layout.item_recommend_house, list) {
            @Override
            protected void convert(ViewHolder holder, ClaimHouseListResponse.DataBean.ListBean bean, int position) {
                holder.setText(R.id.tv_house_name, bean.getHouse_title());
                Glide.with(mContext).load(bean.getHouse_photo()).into((ImageView) holder.getView(R.id.iv_house));
                holder.setText(R.id.tv_house_info, bean.getHouse_info());
                holder.setText(R.id.tv_house_location, bean.getHouse_address());
                holder.setText(R.id.tv_house_rent, bean.getHouse_rental() + "");
                showThreeTag(bean.getHouse_label(), (LinearLayout) holder.getView(R.id.ll_label));
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatHouseListActivity.this);
                builder.setTitle("是否确认分享该房源").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent chatHouse = new Intent();
                        chatHouse.putExtra(Constant.HOUSE_ID,list.get(position).getHouse_id());
                        chatHouse.putExtra(Constant.ROOM_ID,list.get(position).getRoom_id());
                        chatHouse.putExtra(Constant.HOUSE_TITLE,list.get(position).getHouse_title());
                        chatHouse.putExtra(Constant.HOUSE_PHOTO,list.get(position).getHouse_photo());
                        chatHouse.putExtra(Constant.HOUSE_RENTAL,list.get(position).getHouse_rental());
                        chatHouse.putExtra(Constant.HOUSE_TYPE,list.get(position).getType());
                        int resultCode = Activity.RESULT_OK;
                        setResult(resultCode, chatHouse);
                        finish();

                    }
                }).show();
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
                getAlreadyClaimHouseData(setInitData(2));

            }
        });
    }


    private ClaimHouseListParameter setInitData(int type) {
        ClaimHouseListParameter chp = new ClaimHouseListParameter();
        chp.setHouse_type(type);
        return chp;
    }
}
