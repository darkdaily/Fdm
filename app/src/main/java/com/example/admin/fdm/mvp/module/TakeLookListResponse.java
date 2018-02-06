package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/10.
 */

public class TakeLookListResponse extends BaseResponse<TakeLookListResponse.DataBean> {


    public static class DataBean extends BaseResponse.DataBean{

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean{
            /**
             * id : 2
             * room_id : 5
             * house_id : 5
             * status : 5
             * expect_time : 1515480019
             * type : 2
             * nick_name : gg
             * phone_number : 186****3577
             * avatar :
             * uid : 33
             * company_name : 爱家公寓
             * house_photo : http://zy.zhagen.com/uploads/images/20171113/151053973598ec51b107940ebd.jpg
             * house_title : 北京太阳城9号楼-太阳A户型
             * house_info : 一室一厅零卫-60.00㎡
             * house_address : 北京市昌平区北七家
             * house_rental : 1500
             * house_label : ["可以养宠物","全新墙纸","阳台视野广阔"]
             * telephone   : 用户真实电话
             */

            private String id;
            private String room_id;
            private String house_id;
            private String status;
            private long expect_time;
            private String type;
            private String nick_name;
            private String phone_number;
            private String avatar;
            private String uid;
            private String company_name;
            private String house_photo;
            private String house_title;
            private String house_info;
            private String house_address;
            private String house_rental;
            private List<String> house_label;

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            private String telephone;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getHouse_id() {
                return house_id;
            }

            public void setHouse_id(String house_id) {
                this.house_id = house_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getExpect_time() {
                return expect_time;
            }

            public void setExpect_time(long expect_time) {
                this.expect_time = expect_time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getHouse_photo() {
                return house_photo;
            }

            public void setHouse_photo(String house_photo) {
                this.house_photo = house_photo;
            }

            public String getHouse_title() {
                return house_title;
            }

            public void setHouse_title(String house_title) {
                this.house_title = house_title;
            }

            public String getHouse_info() {
                return house_info;
            }

            public void setHouse_info(String house_info) {
                this.house_info = house_info;
            }

            public String getHouse_address() {
                return house_address;
            }

            public void setHouse_address(String house_address) {
                this.house_address = house_address;
            }

            public String getHouse_rental() {
                return house_rental;
            }

            public void setHouse_rental(String house_rental) {
                this.house_rental = house_rental;
            }

            public List<String> getHouse_label() {
                return house_label;
            }

            public void setHouse_label(List<String> house_label) {
                this.house_label = house_label;
            }
        }
    }

}
