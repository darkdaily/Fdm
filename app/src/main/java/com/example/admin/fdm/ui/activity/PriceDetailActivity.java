package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/5.
 */

public class PriceDetailActivity extends BaseActivity {


    @BindView(R.id.tv_rent_count)
    TextView tv_rent_count;
    @BindView(R.id.tv_rent_money)
    TextView tv_rent_money;

    @BindView(R.id.tv_deposit_count)
    TextView tv_deposit_count;
    @BindView(R.id.deposit_money)
    TextView tv_deposit_money;

    @BindView(R.id.middle_count)
    TextView tv_middle_count;
    @BindView(R.id.middle_money)
    TextView tv_middle_money;

    @BindView(R.id.service_count)
    TextView tv_service_count;
    @BindView(R.id.service_money)
    TextView tv_service_money;

    @BindView(R.id.promotion_discount)
    LinearLayout promotion_discount;
    @BindView(R.id.promotion_discount_number)
    TextView promotion_discount_number;

    @BindView(R.id.brokerage_discount)
    LinearLayout brokerage_discount;
    @BindView(R.id.brokerage_discount_number)
    TextView brokerage_discount_number;

    @BindView(R.id.zhagen_discount)
    LinearLayout zhagen_discount;
    @BindView(R.id.zhagen_discount_number)
    TextView zhagen_discount_number;

    @BindView(R.id.tv_total)
    TextView tv_total;

    //付租金月数
    private int rent_month;

    //押金数
    private int deposit;

    //付租金数
    private int pay;

    //租金
    private int rent_money;

    //押金
    private int deposit_price;

//    //中介费月数
//    private double middle;
        private String middles;

//
//    //服务费率
//    private double service;
        private String services;


    @Override
    public int setLayout() {
        return R.layout.activity_price_detail;
    }

    @Override
    public void initEvent() {
        Intent intent = getIntent();
        switch (intent.getStringExtra("state")){
            case "OrderDetail":
                tv_rent_count.setText(""+intent.getStringExtra("rent_pay"));
                tv_rent_money.setText("¥"+intent.getIntExtra("rent_money",-1));
                tv_deposit_count.setText(""+intent.getStringExtra("deposit"));
                tv_deposit_money.setText("¥"+intent.getIntExtra("deposit_money",-1));
                tv_middle_count.setText("");
                tv_middle_money.setText("¥"+intent.getIntExtra("middle_money",-1));
                String str = intent.getStringExtra("service").replaceAll("\\*", "×");
                tv_service_count.setText(""+str);
                tv_service_money.setText("¥"+intent.getIntExtra("service_money",-1));
                tv_total.setText("¥"+(intent.getStringExtra("payment")));
                break;
            case "RevisePrice":
                rent_month = intent.getIntExtra("rent_month",-1);
                deposit = intent.getIntExtra("deposit",-1);
                pay = intent.getIntExtra("pay",-1);
                rent_money = intent.getIntExtra("rent_money",-1);
                deposit_price = intent.getIntExtra("deposit_price",-1);
                middles = intent.getStringExtra("middle");
                services = intent.getStringExtra("service");

                tv_rent_count.setText(""+pay);
                int rent_money_Sum = rent_money*pay;
                tv_rent_money.setText("¥"+rent_money_Sum);

                tv_deposit_count.setText(""+deposit);
                int deposit_mony_Sum = deposit_price*deposit;
                tv_deposit_money.setText("¥"+deposit_mony_Sum);

                tv_middle_count.setText("");
                double middle = Double.parseDouble(middles);
                int middle_mony_Sum = (int) ((double)middle*rent_money);
                tv_middle_money.setText("¥"+middle_mony_Sum);
                tv_service_count.setText("×"+rent_month+"×"+services+"%");

                double service = Double.parseDouble(services);
                int service_mony_Sum = (int) (rent_money*rent_month*(service/100));
                tv_service_money.setText("¥"+service_mony_Sum);
                tv_total.setText("¥"+(rent_money_Sum+service_mony_Sum+middle_mony_Sum+deposit_mony_Sum));

                break;
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.back_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }

}
