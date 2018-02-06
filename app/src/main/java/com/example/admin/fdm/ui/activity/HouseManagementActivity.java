package com.example.admin.fdm.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.admin.fdm.ui.view.BidirectionalSeekBar;
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
 * Created by test on 2017/12/29.
 */

public class HouseManagementActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

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

    @BindView(R.id.ll_region)
    LinearLayout ll_region;

    @BindView(R.id.ll_rent)
    LinearLayout ll_rent;

    @BindView(R.id.ll_sort)
    LinearLayout ll_sort;

    @BindView(R.id.ll_filter)
    LinearLayout ll_filter;

    @BindView(R.id.tv_region)
    TextView tv_region;

    private PopupWindow region_panel;
    private PopupWindow rent_panel;
    private PopupWindow sort_panel;
    private PopupWindow filter_panel;

    //经纪人id
    private int member_id;

    //页数
    public Integer currentPager = 1;

    //房源类型 1分散式 2集中式
    private int houseType = 2;

    //最低价
    private int Left = 0;

    //最高价
    private int Right = 9000;

    //房屋模式 整租/合租
    private String house_code;

    //居室  如 1居室 2居室
    private String room;

    //排序依据(租金rental、面积space_area)
    private String order;

    //升序降序(ASC、DESC)
    private String sort;

    //商圈id
    private String areaid;

    private CommonAdapter<TradingAreaResponse.DataBean.ListBean> adapter;

    //房源列表数据
    private ArrayList<ClaimHouseListResponse.DataBean.ListBean> jlist = new ArrayList<>();

    private ArrayList<ClaimHouseListResponse.DataBean.ListBean> flist = new ArrayList<>();

    //商圈数据
    private ArrayList<TradingAreaResponse.DataBean.ListBean> TAList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        if (houseType == 2 ){
            currentPager = 1;
            getAlreadyClaimHouseData(setInitData(houseType));
        }else if(houseType == 1){
            currentPager = 1;
            getAlreadyClaimHouseData(setInitData(houseType));
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_house_managment;
    }

    @Override
    public void initEvent() {

        setDisplayView(empty,ll_recyclerView);

        member_id = (Integer) SPUtil.get(this, "member_id", -1);

        radio_group.setOnCheckedChangeListener(this);

        getTradingArea();

        initRentPanel();

        initSortPanel();

        initFilterPanel();

        setNetworkLoading();

        getAlreadyClaimHouseData(setInitData(houseType));

    }

    private void initFilterPanel() {
        final View rent_pick_panel = LayoutInflater.from(this).inflate(R.layout.filter_pick_panel, null, false);
        filter_panel = new PopupWindow(rent_pick_panel, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        filter_panel.getContentView().measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED);
        final LinearLayout ll_rentcode = (LinearLayout) rent_pick_panel.findViewById(R.id.ll_rentcode);
        final LinearLayout ll_room = (LinearLayout) rent_pick_panel.findViewById(R.id.ll_room);
        final LinearLayout ll_room_two = (LinearLayout) rent_pick_panel.findViewById(R.id.ll_room_two);

        rent_pick_panel.findViewById(R.id.tv_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder t = new StringBuilder();
                for(int i = 0;i<ll_rentcode.getChildCount();i++){
                    CheckBox childAt = ((CheckBox) ll_rentcode.getChildAt(i));
                    if(childAt.isChecked()){
                        t.append(i+",");
                    }
                }
                if(t.length() > 0){
                    house_code = t.substring(0, t.length() - 1);
                }else{
                    house_code = "";
                }
                if(house_code.equals("")){
                    house_code = "无选中";
                }else if(house_code.equals("1")){
                    house_code = "合租";
                }else if(house_code.equals("0")){
                    house_code = "整租";
                }else{
                    house_code = "";
                }
                StringBuilder s = new StringBuilder();
                for(int i = 1; i<ll_room.getChildCount()+1;i++){
                    CheckBox childAt = ((CheckBox) ll_room.getChildAt(i-1));
                    if(childAt.isChecked()){
                        s.append(i+",");
                    }
                }
                for(int i = 4; i<ll_room_two.getChildCount()+4;i++){
                    CheckBox childAt = ((CheckBox) ll_room_two.getChildAt(i-4));
                    if(childAt.isChecked()){
                        s.append(i+",");
                    }
                }
                if(s.length() > 0){
                    room = s.substring(0, s.length() - 1);
                }else{
                    room = "";
                }
                if(!room.equals("") && !house_code.equals("无选中")){
                    getAlreadyClaimHouseData(setSearchData(Left + "", Right + "", room, house_code, order, sort, areaid, houseType));
                    filter_panel.dismiss();
                }else if(house_code.equals("无选中")){
                    Toast.makeText(HouseManagementActivity.this,"请选择出租方式！", Toast.LENGTH_LONG).show();
                }else if(room.equals("")){
                    Toast.makeText(HouseManagementActivity.this,"请选居室类型！", Toast.LENGTH_LONG).show();
                }
            }
        });

        rent_pick_panel.findViewById(R.id.tv_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(ll_rentcode);
                setChecked(ll_room);
                setChecked(ll_room_two);
            }
        });
    }


    private void initRentPanel() {
        View rent_pick_panel = LayoutInflater.from(this).inflate(R.layout.rent_pick_panel, null, false);
        rent_panel = new PopupWindow(rent_pick_panel, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        rent_panel.getContentView().measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED);
        final TextView tv_start = (TextView) rent_pick_panel.findViewById(R.id.tv_start);
        final TextView tv_end = (TextView) rent_pick_panel.findViewById(R.id.tv_end);
        final BidirectionalSeekBar seekBar = (BidirectionalSeekBar) rent_pick_panel.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new BidirectionalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(int leftProgress, int rightProgress) {
                Left = leftProgress;
                Right = rightProgress;
                tv_start.setText("¥" + leftProgress);
                tv_end.setText("¥" + rightProgress);
            }
        });

        TextView tv_reset = (TextView) rent_pick_panel.findViewById(R.id.tv_reset);
        TextView tv_ok = (TextView) rent_pick_panel.findViewById(R.id.tv_ok);
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rent_panel.dismiss();
                initRentPanel();
                rent_panel.showAsDropDown(ll_rent);
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlreadyClaimHouseData(setSearchData(Left + "", Right + "", room, house_code, order, sort,areaid, houseType));
                rent_panel.dismiss();
            }
        });
    }


    private void initSortPanel() {
        View sort_pick_panel = LayoutInflater.from(this).inflate(R.layout.sort_pick_panel, null, false);
        sort_panel = new PopupWindow(sort_pick_panel, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        sort_panel.getContentView().measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED);
        LinearLayout ll_sort_pick_panel = (LinearLayout) sort_pick_panel.findViewById(R.id.ll_sort_pick_panel);
        int sum[] = {0, 2, 4, 6, 8};
        for (int i = 0; i < sum.length; i++) {
            TextView childAt = ((TextView) ll_sort_pick_panel.getChildAt(sum[i]));
            final int finalI = i;
            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI) {
                        case 0:
                            order = "";
                            sort = "";
                            break;
                        case 1:
                            order = "rental";
                            sort = "ASC";
                            break;
                        case 2:
                            order = "rental";
                            sort = "DESC";
                            break;
                        case 3:
                            order = "space_area";
                            sort = "ASC";
                            break;
                        case 4:
                            order = "space_area";
                            sort = "DESC";
                            break;
                    }
                    Log.d("111111111111111","house_code"+house_code);
                    getAlreadyClaimHouseData(setSearchData(Left + "", Right + "", room, house_code, order, sort, areaid, houseType));
                    sort_panel.dismiss();
                }
            });
        }
    }


    private void initRegionPanel() {
        View region_pick_panel = LayoutInflater.from(this).inflate(R.layout.region_pick_panel, null, false);
        region_panel = new PopupWindow(region_pick_panel, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        region_panel.getContentView().measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);

        RecyclerView rv_region1 = (RecyclerView) region_pick_panel.findViewById(R.id.region1);
        final RecyclerView rv_region2 = (RecyclerView) region_pick_panel.findViewById(R.id.region2);

        rv_region1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_region2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

         adapter = new CommonAdapter<TradingAreaResponse.DataBean.ListBean>(this, R.layout.item_region_pick_panel, TAList) {
            @Override
            protected void convert(ViewHolder holder, TradingAreaResponse.DataBean.ListBean listBean, int position) {
                ((TextView) holder.getView(R.id.region_name)).setText(listBean.getName());

            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final  int position) {

                CommonAdapter<TradingAreaResponse.DataBean.ListBean.SubBean> adapter2 = new CommonAdapter<TradingAreaResponse.DataBean.ListBean.SubBean>(HouseManagementActivity.this, R.layout.item_region_pick_panel, TAList.get(position).getSub()) {
                    @Override
                    protected void convert(ViewHolder holder, TradingAreaResponse.DataBean.ListBean.SubBean subBean, int position) {
                        ((TextView) holder.getView(R.id.region_name)).setText(subBean.getName());
                    }
                };

                adapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int positions) {
                        areaid = TAList.get(position).getSub().get(positions).getId();
                        if(areaid.equals("0")){
                            areaid = "";
                        }
                        tv_region.setText(TAList.get(position).getSub().get(positions).getName());
                        getAlreadyClaimHouseData(setSearchData(Left + "", Right + "", room, house_code, order, sort, areaid, houseType));
                        region_panel.dismiss();

                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });

                rv_region2.setAdapter(adapter2);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        rv_region1.setAdapter(adapter);
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
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle mBundle = new Bundle();
                if(houseType == 1){ //分散式
                    mBundle.putInt("state", Constant.CANCEL_CANCEL_HOUSING_RESOURCES);
                }else if(houseType == 2){//集中式
                    mBundle.putInt("state", Constant.CANCEL_ROOM);
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
                getAlreadyClaimHouseData(setInitData(2));

            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.ll_region, R.id.ll_rent, R.id.ll_sort, R.id.ll_filter, R.id.iv_left, R.id.home_find,R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                    finish();
                break;
            case R.id.ll_region:
                if (region_panel.isShowing()) {
                    region_panel.dismiss();
                } else {
                    region_panel.showAsDropDown(ll_region);
                }
                break;
            case R.id.ll_rent:
                if (rent_panel.isShowing()) {
                    rent_panel.dismiss();
                } else {
                    initRentPanel();
                    rent_panel.showAsDropDown(ll_rent);
                }
                break;

            case R.id.ll_sort:
                if (sort_panel.isShowing()) {
                    sort_panel.dismiss();
                } else {
                    sort_panel.showAsDropDown(ll_sort);
                }
                break;
            case R.id.ll_filter:
                if (filter_panel.isShowing()) {
                    filter_panel.dismiss();
                } else {
                    filter_panel.showAsDropDown(ll_filter);
                }
                break;
            case R.id.home_find:
                Bundle search = new Bundle();
                search.putString("type","房源管理");
                startActivity(SearchActivity.class,search);
                break;
            case R.id.empty:
                setNetworkLoading();
                currentPager = 1;
                getAlreadyClaimHouseData(setInitData(houseType));
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        tv_region.setText("区域");
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

    private ClaimHouseListParameter setInitData(int type) {
        ClaimHouseListParameter chp = new ClaimHouseListParameter();
        chp.setHouse_type(type);
        return chp;
    }


    private ClaimHouseListParameter setSearchData(String rent_start, String rent_end, String room, String house_neme, String order, String sort, String areaid,int type) {
        ClaimHouseListParameter chp = new ClaimHouseListParameter();
        chp.setRent_start(rent_start);
        chp.setRent_end(rent_end);
        chp.setRoom(room);
        chp.setHouse_name(house_neme);
        chp.setOrder(order);
        chp.setSort(sort);
        chp.setAreaid(areaid);
        chp.setHouse_type(type);
        return chp;
    }

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }

    private void getTradingArea() {
        RetrofitHelper.getApiWithUid().getClaimTradingArea(member_id)
                .compose(RxScheduler.<TradingAreaResponse>defaultScheduler())
                .subscribe(new BaseObserver<TradingAreaResponse>(HouseManagementActivity.this) {
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
                    public void next(TradingAreaResponse tradingAreaResponse) {
                        TAList.clear();
                        TAList.addAll(tradingAreaResponse.getData().getList());
                    }

                    @Override
                    public void complete() {
                        initRegionPanel();
                    }
                });
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
                .subscribe(new BaseObserver<ClaimHouseListResponse>(HouseManagementActivity.this) {
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

    private void setChecked(LinearLayout ll_house_config) {
        for (int i = 0; i < ll_house_config.getChildCount(); i++) {
            CheckBox childAt = ((CheckBox) ll_house_config.getChildAt(i));
            childAt.setChecked(true);
        }
    }
}
