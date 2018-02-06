package com.example.admin.fdm.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.StateSerializable;
import com.example.admin.fdm.mvp.module.TradingAreaResponse;
import com.example.admin.fdm.ui.activity.TradingAreaActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by test on 2017/12/27.
 */

public class MoreListAdapter extends BaseAdapter {

    private Context context;
    private List<StateSerializable> address;
    private int nowSelectedIndex = 0;


    public MoreListAdapter(Context context, List<StateSerializable> address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int position) {
        return address.get(position);
    }

    @Override
    public long getItemId(int position) {
        return address.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_more, null);
        }
        TextView tv_list_item = (TextView) convertView.findViewById(R.id.more_item_tv);
        ImageView more_item_iv = (ImageView) convertView.findViewById(R.id.more_item_iv);
        tv_list_item.setText(address.get(position).getCityName());

        if(address.get(position).getState() == true){
            tv_list_item.setTextColor(0xFF68b62e);
            more_item_iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.duihao));
        }else{
            tv_list_item.setTextColor(0xFF2a2e32);
            more_item_iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.huise));
        }

//        if (position == nowSelectedIndex) {
//            tv_list_item.setTextColor(0xFFB3EE3A);
//        } else {
//            tv_list_item.setTextColor(0xFF525252);
//        }
        return convertView;
    }

    public int getNowSelectedIndex() {
        return nowSelectedIndex;
    }

    public void setNowSelectedIndex(int nowSelectedIndex,List<StateSerializable> address) {
        this.nowSelectedIndex = nowSelectedIndex;
        this.address = address;
        this.notifyDataSetChanged();//及时通知显示
    }
}
