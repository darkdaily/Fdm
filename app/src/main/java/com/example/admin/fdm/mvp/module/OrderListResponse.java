package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/12.
 */

public class OrderListResponse extends BaseResponse<OrderListResponse.DataBean> {

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
             * order_id : 1336
             * uid : 33
             * member_id : 2
             * real_name : 问问
             * avatar :
             * phone_number : 186****3577
             * real_number
             * status : 待付款
             * house_photo : http://zy.zhagen.com/uploads/images/20171116/1510835458dfe72487a81924e3.jpg
             * house_title : 韩家胡同102号楼-整租
             * pay_type : 押1付3、55、3
             * address : 北京市西城区大栅栏
             * rent_money : 30000
             * label : []
             * payment : 840200.00
             */

            private int order_id;
            private int uid;
            private int member_id;
            private String real_name;
            private String avatar;
            private String phone_number;
            private String real_number;
            private String status;
            private String house_photo;
            private String house_title;
            private String pay_type;
            private String address;
            private int rent_money;
            private String payment;
            private List<String> label;

            public String getReal_number() {
                return real_number;
            }

            public void setReal_number(String real_number) {
                this.real_number = real_number;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getRent_money() {
                return rent_money;
            }

            public void setRent_money(int rent_money) {
                this.rent_money = rent_money;
            }

            public String getPayment() {
                return payment;
            }

            public void setPayment(String payment) {
                this.payment = payment;
            }

            public List<String> getLabel() {
                return label;
            }

            public void setLabel(List<String> label) {
                this.label = label;
            }
        }
    }
}
