package com.example.admin.fdm.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.OrderRevisePriceResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/5.
 */

public class RevisePriceActivity extends BaseActivity {

    @BindView(R.id.iv_userhead)
    ImageView iv_userhead;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_tel)
    TextView tv_user_tel;
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
    @BindView(R.id.tv_rent_number)
    TextView tv_rent_number;
    @BindView(R.id.et_rent_number)
    EditText et_rent_number;
    @BindView(R.id.tv_deposit_number)
    TextView tv_deposit_number;
    @BindView(R.id.et_deposit_number)
    EditText et_deposit_number;
    @BindView(R.id.tv_agency_fee_number)
    TextView tv_agency_fee_number;
    @BindView(R.id.et_agency_fee_number)
    EditText et_agency_fee_number;
    @BindView(R.id.tv_service_charge_number)
    TextView tv_service_charge_number;
    @BindView(R.id.et_service_charge_number)
    EditText et_service_charge_number;

    ModificationHintsDialog dialog;

    private int uid;
    private int order_id;

    private OrderRevisePriceResponse.DataBean data;


    @Override
    public int setLayout() {
        return R.layout.activity_revise_price;
    }

    @Override
    public void initEvent() {
        getData();
    }

    @Override
    public void initData() {

    }

    public void setView(){

        Glide.with(this).load(data.getUser_avatar()).into((ImageView) iv_userhead);
        tv_user_name.setText(data.getReal_name());
        tv_user_tel.setText(data.getTelephone());
        tv_rent_number.setText(""+data.getRent_money());
        tv_deposit_number.setText(""+data.getDeposit_price());
        tv_agency_fee_number.setText(""+data.getMiddle());
        tv_service_charge_number.setText(data.getService()+"%");
        Glide.with(this).load(data.getHouse_photo()).into(iv_house);
        tv_house_name.setText(data.getHouse_title());
        tv_house_info.setText(data.getHouse_area());
        tv_house_location.setText(data.getAddress());
        tv_house_rent.setText(data.getRent_money()+"");

        et_rent_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER ){

                }
                return false;
            }
        });
        setTextChangedListener(et_rent_number);
//        setTextChangedListener(et_agency_fee_number);
//        setTextChangedListener(et_service_charge_number);


    }


    public void setTextChangedListener(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            boolean hint;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() == 0) {
//                    // no text, hint is visible
//                    hint = true;
//                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                } else {
//                    // no hint, text is visible
//                    hint = false;
//                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//                }
                if(editText.getId() == R.id.et_rent_number){
                    et_deposit_number.setText(s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getData(){
        Intent intent = getmActivity().getIntent();
        uid = intent.getIntExtra("uid",-1);
        order_id = intent.getIntExtra("order_id",-1);
        RetrofitHelper.getApiWithUid().getRevisePrice(order_id,uid)
               .compose(RxScheduler.<OrderRevisePriceResponse>defaultScheduler())
                .subscribe(new BaseObserver<OrderRevisePriceResponse>(this) {
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
                    }
                    @Override
                    public void next(OrderRevisePriceResponse orderRevisePriceResponse) {
                        if(orderRevisePriceResponse.getData() != null){
                            data = orderRevisePriceResponse.getData();
                            setView();
                        }else{
                            finish();
                        }

                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void setRevisePrice(){
        int rent_money;
        if(et_rent_number.getText().toString().equals("")){
            rent_money = 0;
        }else{
            rent_money = Integer.parseInt(et_rent_number.getText().toString());
        }
        int deposit;
        if(et_deposit_number.getText().toString().equals("")){
            deposit = 0;
        }else{
            deposit = Integer.parseInt(et_deposit_number.getText().toString());
        }
        double middle;
        if(et_agency_fee_number.getText().toString().equals("")){
            middle = Double.parseDouble(data.getMiddle());
        }else{
            middle = Double.parseDouble(et_agency_fee_number.getText().toString());
        }
        double service;
        if(et_service_charge_number.getText().toString().equals("")){
            service = Double.parseDouble(data.getService());
        }else{
            service = Double.parseDouble(et_service_charge_number.getText().toString());
        }
        RetrofitHelper.getApiWithUid().exRevisePrice(data.getOrder_id(),data.getMember_id(),rent_money,deposit,middle,service)
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(this) {
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
                            Toast.makeText(RevisePriceActivity.this,"修改完成",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }



    private boolean EditTextVerification(EditText editText){
        if("".equals(editText.getText().toString().trim())) {
            return false;
        }
        return true;
    }





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.iv_left, R.id.tv_green, R.id.tv_black, R.id.question_button, R.id.contact_user})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_black:
                Intent priceIntent = new Intent(RevisePriceActivity.this,PriceDetailActivity.class);
                priceIntent.putExtra("state","RevisePrice");
                priceIntent.putExtra("rent_month",data.getRent_month());
                priceIntent.putExtra("deposit",data.getDeposit());
                priceIntent.putExtra("pay",data.getPay());
                priceIntent.putExtra("rent_money",data.getRent_money());
                priceIntent.putExtra("deposit_price",data.getDeposit_price());
                priceIntent.putExtra("middle",data.getMiddle());
                priceIntent.putExtra("service",data.getService());
                startActivity(priceIntent);
                break;
            case R.id.tv_green:
                if(EditTextVerification(et_rent_number)|| EditTextVerification(et_deposit_number)||
                        EditTextVerification(et_agency_fee_number)||EditTextVerification(et_service_charge_number)){
                    setRevisePrice();

                }else{
                    Toast.makeText(this,"请输入修改内容",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.question_button:
                dialog = new ModificationHintsDialog(RevisePriceActivity.this);
                dialog.setMessage("内容暂定");
                dialog.show();
                break;
            case R.id.contact_user:
                //联系用户
                break;
        }
    }
}