package com.example.admin.fdm.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.ClaimChooseResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.SelectListAdapter;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/29.
 */

public class RoomManagementActivity<T> extends BaseActivity {

    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.ll_linearlayout)
    LinearLayout ll_linearlayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_house)
    ImageView iv_house;
    @BindView(R.id.tv_house_name)
    TextView tv_house_name;
    @BindView(R.id.tv_house_info)
    TextView tv_house_info;
    @BindView(R.id.tv_house_location)
    TextView tv_house_location;
    @BindView(R.id.tv_house_rent)
    TextView tv_house_rent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.check_all)
    CheckBox check_all;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.ll_label)
    LinearLayout ll_label;

    @BindString(R.string.claim_room)
    String str_claim_room;
    @BindString(R.string.cancel_room)
    String str_cancel_room;
    @BindString(R.string.choice_claim_room)
    String str_choice_claim_room;
    @BindString(R.string.cancel_claim_room)
    String str_cancel_claim_room;
    @BindString(R.string.confir_claim)
    String str_confir_claim;
    @BindString(R.string.confir_cancel)
    String str_confir_cancel;


    private SelectListAdapter selectListAdapter;


    private String house_id;
    private String room_id;
    private int member_id;
    private int state;
    ;

    private List<ClaimChooseResponse.DataBean.RoomsBean> list = new ArrayList<>();
    private ClaimChooseResponse.DataBean data;



    @Override
    public int setLayout() {
        return R.layout.activity_room_management;
    }

    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(this, "member_id", -1);

        setDisplayView(empty, ll_linearlayout);

        Bundle bundle = getIntent().getExtras(); //得到传过来的bundle
        house_id = bundle.getString("house_id");
        room_id = bundle.getString("room_id");
        state = bundle.getInt("state");
        Log.d("11111111111","state"+state);

        setNetworkLoading();
        setView(state);


    }

    private void setRecyclerView() {
        setHideLayout();
        Glide.with(mActivity).load(data.getHouse_photo()).into(iv_house);
        tv_house_name.setText(data.getHouse_title());
        tv_house_info.setText(data.getHouse_info());
        tv_house_location.setText(data.getHouse_address());
        showThreeTag(data.getHouse_label(), ll_label);
        tv_house_rent.setText(data.getHouse_rental());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        selectListAdapter = new SelectListAdapter(this, R.layout.item_cancel_room, list, 1);
        recyclerView.setAdapter(selectListAdapter);
    }


    @Override
    public void initData() {

    }

    public void setView(int view_state) {
        switch (view_state) {
            case Constant.CHOICE_CLAIM_ROOM:
                title.setText(str_choice_claim_room);
                tv_title.setText(str_choice_claim_room);
                confirm.setText(str_confir_claim);
                exClaimChoose();
                break;
            case Constant.CANCEL_CLAIM_ROOM:
                title.setText(str_cancel_claim_room);
                tv_title.setText(str_cancel_claim_room);
                confirm.setText(str_confir_cancel);
                exCleanChoose();
                break;
        }
    }

    @OnClick({R.id.iv_left, R.id.check_all, R.id.confirm,R.id.empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.check_all:
                if (check_all.isChecked()) {
                    selectListAdapter.selectAllItems();
                } else {
                    selectListAdapter.clearSelectedState();
                }
                break;
            case R.id.confirm:
                String room_id;
                switch (state) {
                    case Constant.CHOICE_CLAIM_ROOM://认领
                        if (selectListAdapter.getRoom_id() != null && selectListAdapter.getRoom_id().size() > 0) {
                            room_id = setStringId(selectListAdapter.getRoom_id());
                            setClaimHouse(2, house_id, room_id);
                        }
                        break;
                    case Constant.CANCEL_CLAIM_ROOM://取消
                        if (selectListAdapter.getRoom_id() != null && selectListAdapter.getRoom_id().size() > 0) {
                            room_id = setStringId(selectListAdapter.getRoom_id());
                            setCleanHouse(room_id);
                        }
                        break;
                }
                break;
            case R.id.empty:
                setNetworkLoading();
                setView(state);
                break;
        }
    }

    private String setStringId(List<String> list) {
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i) + ",");
            }
            String s = sb.toString();
            s = s.substring(0, s.length() - 1);
            return s;
        }
        return "";
    }

    private void setCleanHouse(String room) {
        RetrofitHelper.getApiWithUid().exCleanHouse(member_id, room)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(RoomManagementActivity.this) {
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
                    public void next(PostResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            Toast.makeText(RoomManagementActivity.this, "取消成功", Toast.LENGTH_LONG).show();
                            exCleanChoose();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void setClaimHouse(int type, String house, String room) {
        RetrofitHelper.getApiWithUid().exClaimHouse(member_id, type, house, room, 1)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(RoomManagementActivity.this) {
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
                    public void next(PostResponse baseResponse) {
                        if (baseResponse.getCode() == 0) {
                            Toast.makeText(RoomManagementActivity.this, "认领成功", Toast.LENGTH_LONG).show();
                            exClaimChoose();
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


    private void exClaimChoose() {
        RetrofitHelper.getApiWithUid().exClaimChoose(member_id, 2, house_id, room_id)
                .compose(RxScheduler.<ClaimChooseResponse>defaultScheduler())
                .subscribe(new BaseObserver<ClaimChooseResponse>(RoomManagementActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        finish();
                    }

                    @Override
                    public void NoNetwork() {
                        finish();
                        setNetworkError();
                    }

                    @Override
                    public void next(ClaimChooseResponse claimChooseResponse) {
                        if (claimChooseResponse.getData() == null) {
                            finish();
                        } else {
                            if(claimChooseResponse.getData().getRooms().size() == 0){
                                finish();
                            }
                            data = claimChooseResponse.getData();
                            list.clear();
                            list.addAll(data.getRooms());
                            setRecyclerView();
                        }

                    }

                    @Override
                    public void complete() {
                    }
                });
    }

    //取消房源
    private void exCleanChoose() {
        RetrofitHelper.getApiWithUid().exCleanChoose(2, house_id, room_id, member_id)
                .compose(RxScheduler.<ClaimChooseResponse>defaultScheduler())
                .subscribe(new BaseObserver<ClaimChooseResponse>(RoomManagementActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        finish();
                    }

                    @Override
                    public void NoNetwork() {
                        setNetworkError();
                    }

                    @Override
                    public void next(ClaimChooseResponse claimChooseResponse) {
                        if (claimChooseResponse.getData() == null) {
                            finish();
                        } else {
                            if(claimChooseResponse.getData().getRooms().size() == 0){
                                finish();
                            }
                            data = claimChooseResponse.getData();
                            list.clear();
                            list.addAll(data.getRooms());
                            setRecyclerView();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

}
