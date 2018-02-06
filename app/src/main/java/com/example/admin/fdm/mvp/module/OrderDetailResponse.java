package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/13.
 */

public class OrderDetailResponse extends BaseResponse<OrderDetailResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {

        private ListBean list;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {


            /**
             * uid : 33
             * order_id : 1336
             * member_id : 2
             * real_name : 问问
             * user_avatar :
             * telephone : 186****3577
             * house_title : 韩家胡同102号楼-整租
             * house_area : 一室一厅一卫-100.00
             * address : 北京市西城区大栅栏
             * rent_money : 30000
             * rent_month : 3
             * pay_type : 押1付3
             * room_num : 55
             * phone_number : 18662033577
             * user_coupon_id : 0
             * user_coupon_money : 0
             * rent_pay : 3
             * r_money : 90000
             * deposit : 1
             * pay : 200
             * middle_count : 1.00
             * middle_money : 30000
             * service_count : *3*2.00
             * service_money : 180000
             * discount : 0
             * payment : 840200.00
             * house_photo : http://zy.zhagen.com/uploads/images/20171116/1510835458dfe72487a81924e3.jpg
             */

            private int uid;
            private int order_id;
            private int member_id;
            private String real_name;
            private String user_avatar;
            private String telephone;
            private String house_title;
            private String house_area;
            private String address;
            private int rent_money;
            private String rent_month;
            private String pay_type;
            private String room_num;
            private String phone_number;
            private String user_coupon_id;
            private String user_coupon_money;
            private String rent_pay;
            private int r_money;
            private String deposit;
            private int pay;
            private String middle_count;
            private int middle_money;
            private String service_count;
            private int service_money;
            private int discount;
            private String payment;
            private String house_photo;
            /**
             * telephones : 132****9586
             * rent_money : 1700.00
             * order : 201711291629344417474735
             * pay_time : 1511944223
             * net : 扎根网
             * agent : 李四-链家公寓
             */

            private String telephones;
            private String order;
            private long pay_time;
            private String net;
            private String agent;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
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

            public String getRent_month() {
                return rent_month;
            }

            public void setRent_month(String rent_month) {
                this.rent_month = rent_month;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getRoom_num() {
                return room_num;
            }

            public void setRoom_num(String room_num) {
                this.room_num = room_num;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getUser_coupon_id() {
                return user_coupon_id;
            }

            public void setUser_coupon_id(String user_coupon_id) {
                this.user_coupon_id = user_coupon_id;
            }

            public String getUser_coupon_money() {
                return user_coupon_money;
            }

            public void setUser_coupon_money(String user_coupon_money) {
                this.user_coupon_money = user_coupon_money;
            }

            public String getRent_pay() {
                return rent_pay;
            }

            public void setRent_pay(String rent_pay) {
                this.rent_pay = rent_pay;
            }

            public int getR_money() {
                return r_money;
            }

            public void setR_money(int r_money) {
                this.r_money = r_money;
            }

            public String getDeposit() {
                return deposit;
            }

            public void setDeposit(String deposit) {
                this.deposit = deposit;
            }

            public int getPay() {
                return pay;
            }

            public void setPay(int pay) {
                this.pay = pay;
            }

            public String getMiddle_count() {
                return middle_count;
            }

            public void setMiddle_count(String middle_count) {
                this.middle_count = middle_count;
            }

            public int getMiddle_money() {
                return middle_money;
            }

            public void setMiddle_money(int middle_money) {
                this.middle_money = middle_money;
            }

            public String getService_count() {
                return service_count;
            }

            public void setService_count(String service_count) {
                this.service_count = service_count;
            }

            public int getService_money() {
                return service_money;
            }

            public void setService_money(int service_money) {
                this.service_money = service_money;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public String getPayment() {
                return payment;
            }

            public void setPayment(String payment) {
                this.payment = payment;
            }

            public String getHouse_photo() {
                return house_photo;
            }

            public void setHouse_photo(String house_photo) {
                this.house_photo = house_photo;
            }

            public String getTelephones() {
                return telephones;
            }

            public void setTelephones(String telephones) {
                this.telephones = telephones;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public long getPay_time() {
                return pay_time;
            }

            public void setPay_time(long pay_time) {
                this.pay_time = pay_time;
            }

            public String getNet() {
                return net;
            }

            public void setNet(String net) {
                this.net = net;
            }

            public String getAgent() {
                return agent;
            }

            public void setAgent(String agent) {
                this.agent = agent;
            }
        }
    }
}