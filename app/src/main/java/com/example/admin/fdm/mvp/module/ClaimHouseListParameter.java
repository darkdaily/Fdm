package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/19.
 */

public class ClaimHouseListParameter {


    //租金起始价
    private String rent_start;

    //租金最高价
    private String rent_end;

    //几居室
    private String room;

    //整租/合租
    private String house_name;

    //排序依据(租金rental、面积space_area)
    private String order;

    //升序降序(ASC、DESC)
    private String sort;

    //商圈id
    private String areaid;

    private Integer level;

    //房源类型 1分散式 2集中式 0默认
    private int house_type;

    public String getRent_start() {
        return rent_start;
    }

    public void setRent_start(String rent_start) {
        this.rent_start = rent_start;
    }

    public String getRent_end() {
        return rent_end;
    }

    public void setRent_end(String rent_end) {
        this.rent_end = rent_end;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getHouse_type() {
        return house_type;
    }

    public void setHouse_type(int house_type) {
        this.house_type = house_type;
    }
}
