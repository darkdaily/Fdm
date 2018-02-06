package com.example.admin.fdm.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.ClaimChooseResponse;
import com.example.admin.fdm.mvp.module.ClaimHouseListResponse;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2018/1/3.
 */

public class SelectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private ArrayList<String> mList = new ArrayList<>();

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    private boolean mIsSelectable = false;

    protected Context mContext;
    protected int mLayoutId;
    protected List<ClaimHouseListResponse.DataBean.ListBean> mDatas;
    protected List<ClaimChooseResponse.DataBean.RoomsBean> mData;

    protected int type;
    private TextView housr_num;
    private TextView room_num;
    private int roomsum = 0;
    private int housesum = 0;
    private int room = 0;
    private int house = 0;

    private List<String> house_id;
    private List<String> room_id;

    public List<String> getHouse_id() {
        return house_id;
    }

    public void setHouse_id(List<String> house_id) {
        this.house_id = house_id;
    }

    public List<String> getRoom_id() {
        return room_id;
    }

    public void setRoom_id(List<String> room_id) {
        this.room_id = room_id;
    }

    public SelectListAdapter(Context mContext, int layoutId, List<ClaimChooseResponse.DataBean.RoomsBean> datas, int type) {
        this.mLayoutId = layoutId;
        this.mContext = mContext;
        if (datas == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        this.mData = datas;
        this.type = type;
        room_id = new ArrayList<>();
    }

    public SelectListAdapter(Context mContext,int layoutId, List<ClaimHouseListResponse.DataBean.ListBean> datas,int type,TextView housr_num,TextView room_num) {
        this.mLayoutId = layoutId;
        this.mContext = mContext;
        if (datas == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        this.mDatas = datas;
        this.type = type;
        this.housr_num = housr_num;
        this.room_num = room_num;
        for(int i = 0; i<datas.size();i++){
            room +=  datas.get(i).getRoom_count();
        }
        this.house = datas.size();
        house_id = new ArrayList<>();
        room_id = new ArrayList<>();
    }

    //更新adpter的数据和选择状态
    public void updateDataSet(List list) {
        this.mDatas = list;
        mSelectedPositions = new SparseBooleanArray();
    }

    //获得选中条目的结果
    public List getSelectedItem() {
        List selectList = new ArrayList<>();
//        ArrayList<String> selectList = new ArrayList<>();
        if(type == 0){
            for (int i = 0; i < mDatas.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mDatas.get(i));
                }
            }
        }else{
            for (int i = 0; i < mData.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mDatas.get(i));
                }
            }
        }
        return selectList;
    }

    /**
     * 获取选中的items
     */
    public List<Integer> getSelectedItems(){
        List<Integer> items = new ArrayList<>(mSelectedPositions.size());
        for(int i = 0; i < mSelectedPositions.size(); i++){
            items.add(mSelectedPositions.keyAt(i));
        }
        return items;
    }

    public List<Integer> getRoomSelectedItems(){
        List<Integer> items = new ArrayList<>(mSelectedPositions.size());
        for(int i = 0; i < mSelectedPositions.size(); i++){
            items.add(mSelectedPositions.keyAt(i));
        }
        return items;
    }


    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    //根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }

    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }

    /**
     * 清除所有选中item的标记
     */
    public void clearSelectedState(){
        List<Integer> selection = getSelectedItems();
        mSelectedPositions.clear();
        for(Integer i : selection){
            notifyItemChanged(i);
        }
        if(type == 0){
            housesum = 0;
            roomsum = 0;
            house_id.clear();
            room_id.clear();
        }else{
            room_id.clear();
        }

    }

    /**
     * 全选
     */
    public void selectAllItems(){
        clearSelectedState();
        for(int i = 0; i < getItemCount(); i++){
            mSelectedPositions.put(i,true);
            notifyItemChanged(i);
        }
        if(type == 0){
            if(housr_num != null &room_num != null){
                housr_num.setText(""+house);
                room_num.setText(""+room);
                if(mDatas != null){
                    for(int j = 0; j< mDatas.size(); j++){
                        house_id.add(mDatas.get(j).getHouse_id());
                        room_id.add(mDatas.get(j).getRoom_id());
                    }
                }

            }
        }else{
            if(mData != null){
                for(int j = 0; j< mData.size(); j++){
                    room_id.add(mData.get(j).getId());
                }
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
            return new ListItemViewHolder(itemView);
    }

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(type == 0){
            final ClaimHouseListResponse.DataBean.ListBean bean = mDatas.get(position);
            ((ListItemViewHolder) holder).tv_name.setText(""+bean.getHouse_title());
            Glide.with(mContext).load(bean.getHouse_photo()).into((ImageView) ((ListItemViewHolder) holder).iv_house);
            ((ListItemViewHolder) holder).tv_house_info.setText(bean.getHouse_info());
            ((ListItemViewHolder) holder).tv_house_location.setText(bean.getHouse_address());
            ((ListItemViewHolder) holder).tv_house_rent.setText(bean.getHouse_rental()+"");
            showThreeTag(bean.getHouse_label(),((ListItemViewHolder) holder).ll_label);

            ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(position));
            ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(position)) {
                        ((ListItemViewHolder) holder).checkBox.setChecked(false);
                        housr_num.setText(""+(--housesum));
                        setItemChecked(position, false);
                        roomsum-=bean.getRoom_count();
                        room_num.setText(""+roomsum);
                        for(int i = 0; i< house_id.size();i++){
                            if(house_id.get(i).equals(bean.getHouse_id())){
                                house_id.remove(i);
                            }
                        }
                        for(int i = 0; i< room_id.size();i++){
                            if(room_id.get(i).equals(bean.getRoom_id())){
                                room_id.remove(i);
                            }
                        }
                    } else {
                        house_id.add(bean.getHouse_id());
                        room_id.add(bean.getRoom_id());
                        ((ListItemViewHolder) holder).checkBox.setChecked(true);
                        housr_num.setText(""+(++housesum));
                        setItemChecked(position, true);
                        roomsum+=bean.getRoom_count();
                        room_num.setText(""+roomsum);
                    }
                }
            });
            ((ListItemViewHolder) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(position)) {
                        ((ListItemViewHolder) holder).checkBox.setChecked(false);
                        housr_num.setText(""+(--housesum));
                        setItemChecked(position, false);
                        roomsum-=bean.getRoom_count();
                        room_num.setText(""+roomsum);
                        for(int i = 0; i< house_id.size();i++){
                            if(house_id.get(i).equals(bean.getHouse_id())){
                                house_id.remove(i);
                            }
                        }
                        for(int i = 0; i< room_id.size();i++){
                            if(room_id.get(i).equals(bean.getRoom_id())){
                                room_id.remove(i);
                            }
                        }
                    } else {
                        house_id.add(bean.getHouse_id());
                        room_id.add(bean.getRoom_id());
                        ((ListItemViewHolder) holder).checkBox.setChecked(true);
                        housr_num.setText(""+(++housesum));
                        setItemChecked(position, true);
                        roomsum+=bean.getRoom_count();
                        room_num.setText(""+roomsum);
                    }
                }
            });
        }else{
            final ClaimChooseResponse.DataBean.RoomsBean bean = mData.get(position);
            ((ListItemViewHolder) holder).tv_code.setText(""+bean.getHouse_code());
            ((ListItemViewHolder) holder).tv_rental.setText(""+bean.getRental());
            ((ListItemViewHolder) holder).tv_deposit.setText(""+bean.getDeposit());
            ((ListItemViewHolder) holder).tv_room_number.setText(""+bean.getRoom_number());
            ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(position));
            ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(position)) {
                        ((ListItemViewHolder) holder).checkBox.setChecked(false);
                        setItemChecked(position, false);
                        for(int i = 0; i< room_id.size();i++){
                            if(room_id.get(i).equals(bean.getId())){
                                room_id.remove(i);
                            }
                        }
                    }else{
                        ((ListItemViewHolder) holder).checkBox.setChecked(true);
                        room_id.add(bean.getId());
                        setItemChecked(position, true);
                    }
                }
            });
            ((ListItemViewHolder) holder).ll_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(position)) {
                        ((ListItemViewHolder) holder).checkBox.setChecked(false);
                        setItemChecked(position, false);
                        for(int i = 0; i< room_id.size();i++){
                            if(room_id.get(i).equals(bean.getId())){
                                room_id.remove(i);
                            }
                        }
                    }else{
                        ((ListItemViewHolder) holder).checkBox.setChecked(true);
                        room_id.add(bean.getId());
                        setItemChecked(position, true);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        if(type == 0 ){
            return mDatas == null ? 0 : mDatas.size();
        }else{
            return mData == null ? 0 : mData.size();
        }
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder
        CheckBox checkBox;
        TextView tv_name;
        ImageView iv_house;
        TextView tv_house_info;
        TextView tv_house_location;
        LinearLayout ll_label;
        TextView tv_house_rent;
        RelativeLayout relativeLayout;
        //
        LinearLayout ll_room;
        TextView tv_code;
        TextView tv_rental;
        TextView tv_deposit;
        TextView tv_room_number;


        ListItemViewHolder(View view) {
            super(view);
            if(type == 0){
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                iv_house = (ImageView)view.findViewById(R.id.iv_house);
                tv_house_info = (TextView)view.findViewById(R.id.tv_house_info);
                tv_house_location = (TextView)view.findViewById(R.id.tv_house_location);
                ll_label = (LinearLayout)view.findViewById(R.id.ll_label);
                tv_house_rent = (TextView)view.findViewById(R.id.tv_house_rent);
                relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout);
            }else{
                tv_code = (TextView)view.findViewById(R.id.tv_code);
                tv_rental = (TextView)view.findViewById(R.id.tv_rental);
                tv_deposit = (TextView)view.findViewById(R.id.tv_deposit);
                tv_room_number = (TextView)view.findViewById(R.id.tv_room_number);
                ll_room = (LinearLayout)view.findViewById(R.id.ll_room);
            }
            this.checkBox = (CheckBox) view.findViewById(R.id.select_checkbox);

        }
    }


}
