package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/13.
 */

public class OrderRevisePriceResponse extends BaseResponse<OrderRevisePriceResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean{


        /**
         * uid : 33
         * order_id : 1336
         * member_id : 2
         * rent_month : 12
         * deposit : 1
         * pay : 3
         * real_name : 问问
         * user_avatar :
         * telephone : 186****3577
         * phone_number : 18662033577
         * house_photo : http://zy.zhagen.com/uploads/images/20171116/1510835458dfe72487a81924e3.jpg
         * house_title : 韩家胡同102号楼-整租
         * house_area : 一室一厅一卫-100.00
         * address : 北京市西城区大栅栏
         * rent_money : 30000
         * deposit_price : 200
         * middle : 1.00
         * service : 2.00
         */

        private String uid;
        private String order_id;
        private String member_id;
        private int rent_month;
        private int deposit;
        private int pay;
        private String real_name;
        private String user_avatar;
        private String telephone;
        private String phone_number;
        private String house_photo;
        private String house_title;
        private String house_area;
        private String address;
        private int rent_money;
        private int deposit_price;
        private String middle;
        private String service;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public int getRent_month() {
            return rent_month;
        }

        public void setRent_month(int rent_month) {
            this.rent_month = rent_month;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
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

        public String getHouse_area() {
            return house_area;
        }

        public void setHouse_area(String house_area) {
            this.house_area = house_area;
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

        public int getDeposit_price() {
            return deposit_price;
        }

        public void setDeposit_price(int deposit_price) {
            this.deposit_price = deposit_price;
        }

        public String getMiddle() {
            return middle;
        }

        public void setMiddle(String middle) {
            this.middle = middle;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }


}
