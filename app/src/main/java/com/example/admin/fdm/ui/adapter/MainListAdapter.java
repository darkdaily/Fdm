package com.example.admin.fdm.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.TradingAreaResponse;
import com.example.admin.fdm.ui.activity.TradingAreaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2017/12/27.
 */

public class MainListAdapter extends BaseAdapter {

    private Context context;
    private List<TradingAreaResponse.DataBean.ListBean> cityname;

    public int getNowSelectedIndex() {
        return nowSelectedIndex;
    }

    public void setNowSelectedIndex(int nowSelectedIndex) {
        this.nowSelectedIndex = nowSelectedIndex;
        this.notifyDataSetChanged();//及时通知显示
    }

    private int nowSelectedIndex = -1;

    public MainListAdapter(Context context, List<TradingAreaResponse.DataBean.ListBean> name) {
        this.context = context;
        this.cityname = name;
    }

    @Override
    public int getCount() {
        return cityname.size();
    }

    @Override
    public Object getItem(int position) {
        return cityname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cityname.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main, null);
        }
        TextView tv_list_item = (TextView) convertView.findViewById(R.id.item_main_tv);
        tv_list_item.setText(cityname.get(position).getName());

        if (position == nowSelectedIndex ) {
            tv_list_item.setBackgroundColor(0xF1F0F5);
        } else {
            tv_list_item.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
}
