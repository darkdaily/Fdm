package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.mvp.module.StateSerializable;
import com.example.admin.fdm.mvp.module.TradingAreaResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.MainListAdapter;
import com.example.admin.fdm.ui.adapter.MoreListAdapter;
import com.example.admin.fdm.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import it.sephiroth.android.library.easing.Linear;

/**
 * Created by test on 2017/12/27.
 */

public class TradingAreaActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.list_1)
    ListView list1;
    @BindView(R.id.list_2)
    ListView list2;
    @BindView(R.id.select_num)
    TextView select_num;
    @BindView(R.id.select_one)
    TextView data_one;
    @BindView(R.id.select_two)
    TextView data_two;
    @BindView(R.id.delete_one)
    LinearLayout delete_one;
    @BindView(R.id.delete_two)
    LinearLayout delete_two;

    private MainListAdapter malAdapter;
    private MoreListAdapter molAdapter;


    private int member_id;
    private String district;
    private String address [];
    private String addressId [];
    private List<StateSerializable> tradingAreaData = new ArrayList<>();
    private List<StateSerializable> zhuangtai;
    private String districtId;

    private StateSerializable stateSerializable = new StateSerializable();
    private ArrayList<TradingAreaResponse.DataBean.ListBean> list = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_trading_area;
    }

    private void setTradingArea(){
        if(tradingAreaData.size() == 2){
            districtId = tradingAreaData.get(0).getId()+","+tradingAreaData.get(1).getId();
            district = tradingAreaData.get(0).getCityName()+","+tradingAreaData.get(1).getCityName();
        }else{
            districtId = tradingAreaData.get(0).getId();
            district = tradingAreaData.get(0).getCityName();
        }
        RetrofitHelper.getApiWithUid().setTradingArea(member_id,districtId)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(TradingAreaActivity.this) {
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
                        if(baseResponse.getCode() == 0){
                            Toast.makeText(TradingAreaActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                            Intent mIntent = new Intent();
                            int resultCode = 1;
                            mIntent.putExtra("district",district);
                            setResult(resultCode, mIntent);
                            finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void getTradingArea(){
        RetrofitHelper.getApiWithUid().getTradingArea(member_id)
                .compose(RxScheduler.<TradingAreaResponse>defaultScheduler())
                .subscribe(new BaseObserver<TradingAreaResponse>(TradingAreaActivity.this) {
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
                            list.clear();
                            list.addAll(tradingAreaResponse.getData().getList());
                            setListView();
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void setListView(){
        dismissProgressDialog();
        select_num.setText(tradingAreaData.size() + "");
        switch (tradingAreaData.size()){
            case 1:
                data_one.setText(tradingAreaData.get(0).getCityName());
                delete_one.setVisibility(View.VISIBLE);
                break;
            case 2:
                data_one.setText(tradingAreaData.get(0).getCityName());
                delete_one.setVisibility(View.VISIBLE);
                data_two.setText(tradingAreaData.get(1).getCityName());
                delete_two.setVisibility(View.VISIBLE);
                break;
        }


        malAdapter = new MainListAdapter(this, list);
        list1.setAdapter(malAdapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                malAdapter.setNowSelectedIndex(position);
                zhuangtai = new ArrayList<>();
                for(int i = 0; i<list.get(position).getSub().size();i++) {
                    StateSerializable state = new StateSerializable();
                    state.setId(list.get(position).getSub().get(i).getId());
                    state.setCityName(list.get(position).getSub().get(i).getName());
                    switch (tradingAreaData.size()){
                        case 0:
                            state.setState(false);
                            break;
                        case 1:
                            if(state.getCityName().equals(tradingAreaData.get(0).getCityName())){
                                state.setState(true);
                            }else{
                                state.setState(false);
                            }
                            break;
                        case 2:
                            if(state.getCityName().equals(tradingAreaData.get(0).getCityName()) || state.getCityName().equals(tradingAreaData.get(1).getCityName())){
                                state.setState(true);
                            }else{
                                state.setState(false);
                            }
                            break;
                    }
                    zhuangtai.add(state);
                }

                initMoreBaseAdapter(position,zhuangtai);

            }
        });
    }

    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        district = getIntent().getStringExtra("district");
        districtId = getIntent().getStringExtra("districtId");
        address = district.split(",");
        addressId = districtId.split(",");

        for(int i = 0;i < address.length;i++){
            if(!address[i].equals("")){
                StateSerializable serializable = new StateSerializable();
                serializable.setCityName(address[i]);
                serializable.setId(addressId[i]);
                tradingAreaData.add(serializable);
            }
        }
        showProgressDialog();
        getTradingArea();
    }

    @Override
    public void initData() {

    }

    private void initMoreBaseAdapter(int position, final List<StateSerializable> sublist) {
        molAdapter = new MoreListAdapter(this, sublist);
        list2.setAdapter(molAdapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_list_item = (TextView) view.findViewById(R.id.more_item_tv);
                ImageView more_item_iv = (ImageView) view.findViewById(R.id.more_item_iv);
                if (zhuangtai.get(position).getState() == false && tradingAreaData.size() < 2) {
                    StateSerializable serializable = new StateSerializable();
                    serializable.setCityName(sublist.get(position).getCityName());
                    serializable.setId(sublist.get(position).getId());
                    tradingAreaData.add(serializable);
                    MoreListItemChange(false, tv_list_item, more_item_iv);
                    zhuangtai.get(position).setState(true);
                    setLabel();
                } else if (zhuangtai.get(position).getState() == true) {
                    LabelChange(position);
                    MoreListItemChange(true, tv_list_item, more_item_iv);
                    zhuangtai.get(position).setState(false);
                }
                select_num.setText(tradingAreaData.size() + "");
            }
        });
        molAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.delete_one, R.id.delete_two, R.id.iv_left,R.id.tv_green})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                Intent mIntent = new Intent();
                int resultCode = 2;
                setResult(resultCode, mIntent);
                finish();
                break;
            case R.id.delete_one:
                LablChange(data_one.getText().toString());
                if(zhuangtai != null){
                    for (int i = 0; i < zhuangtai.size(); i++) {
                        if (data_one.getText().equals(zhuangtai.get(i).getCityName()))
                            if (zhuangtai.get(i).getState() == true) {
                                zhuangtai.get(i).setState(false);
                            } else {
                                zhuangtai.get(i).setState(true);
                            }
                    }
                }
                select_num.setText(tradingAreaData.size() + "");
                if(molAdapter != null){
                    molAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete_two:
                LablChange(data_two.getText().toString());
                if(zhuangtai != null){
                    for (int i = 0; i < zhuangtai.size(); i++) {
                        if (data_two.getText().equals(zhuangtai.get(i).getCityName()))
                            if (zhuangtai.get(i).getState() == true) {
                                zhuangtai.get(i).setState(false);
                            } else {
                                zhuangtai.get(i).setState(true);
                            }
                    }
                }
                select_num.setText(tradingAreaData.size() + "");
                if(molAdapter != null){
                    molAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_green:
                if(tradingAreaData.size() > 0){
                    setTradingArea();
                }else{
                    Toast.makeText(TradingAreaActivity.this,"请选择商圈",Toast.LENGTH_LONG).show();
                }
                break;
        }

    }


    private void MoreListItemChange(boolean zhuangtai, TextView textView, ImageView imageView) {
        if (!zhuangtai) {
            textView.setTextColor(0xFF68b62e);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.duihao));
        } else {
            textView.setTextColor(0xFF2a2e32);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.huise));
        }
    }

    private void setLabel() {
        if (tradingAreaData.size() == 1) {
            data_one.setText(tradingAreaData.get(0).getCityName());
            delete_one.setVisibility(View.VISIBLE);
        } else if (tradingAreaData.size() == 2) {
            data_one.setText(tradingAreaData.get(0).getCityName());
            delete_one.setVisibility(View.VISIBLE);
            data_two.setText(tradingAreaData.get(1).getCityName());
            delete_two.setVisibility(View.VISIBLE);
        }
    }

    private  void LablChange(String dataName){
        for (int i = 0; i < tradingAreaData.size(); i++) {
            if (tradingAreaData.get(i).getCityName().equals(dataName)) {
                tradingAreaData.remove(i);
            }
            if (data_one.getText().equals(dataName) && tradingAreaData.size() >1) {
                data_one.setText(tradingAreaData.get(0).getCityName());
                delete_two.setVisibility(View.GONE);
            } else if (data_two.getText().equals(dataName)) {
                delete_two.setVisibility(View.GONE);
            }else{
                delete_one.setVisibility(View.GONE);
            }
        }
    }

    private void LabelChange(int position) {
        for (int i = 0; i < tradingAreaData.size(); i++) {
            Log.d("tradingAreaData.size+0",i+""+tradingAreaData.get(i));
            if (tradingAreaData.get(i).getCityName().equals(zhuangtai.get(position).getCityName())) {
                tradingAreaData.remove(i);
                Log.d("tradingAreaData.size+1",""+tradingAreaData.size());
            }
            if (data_one.getText().equals(zhuangtai.get(position).getCityName()) && tradingAreaData.size() > 1) {
                Log.d("tradingAreaData.size+2",""+tradingAreaData.size());
                data_one.setText(tradingAreaData.get(0).getCityName());
                delete_two.setVisibility(View.GONE);
            } else if (data_two.getText().equals(zhuangtai.get(position).getCityName())) {
                delete_two.setVisibility(View.GONE);
            }else{
                delete_one.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent mIntent = new Intent();
            int resultCode = 2;
            setResult(resultCode, mIntent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
