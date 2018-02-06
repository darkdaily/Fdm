package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/22.
 */

public class CrabListResponse extends BaseResponse<CrabListResponse.DataBean> {

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
             * id : 118
             * expired 1为过期 0为可抢单状态
             * expired_time : 1515740725
             * method : 不限
             * create_at : 1515740425
             * district : 回龙观
             * room : 不限
             * nick_name : gg
             * phone_number : 186****3577
             * hx_username 环信账号
             * avatar : http://zy.zhagen.com/origin/20180118/c81e728d9d4c2f636f067f89cc14862c_1516253968.png
             * uid : 33
             * price : 不限
             */

            private String id;
            private int expired;
            private String method;
            private long create_at;
            private String district;
            private String room;
            private String nick_name;
            private String phone_number;
            private String avatar;
            private String uid;
            private String price;
            private String hx_username;

            public String getHx_username() {
                return hx_username;
            }

            public void setHx_username(String hx_username) {
                this.hx_username = hx_username;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getExpired() {
                return expired;
            }

            public void setExpired(int expired) {
                this.expired = expired;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public long getCreate_at() {
                return create_at;
            }

            public void setCreate_at(long create_at) {
                this.create_at = create_at;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
