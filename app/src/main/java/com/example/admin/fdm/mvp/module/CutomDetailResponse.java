package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/16.
 */

public class CutomDetailResponse extends BaseResponse<CutomDetailResponse.DataBean> {


    public static class DataBean extends BaseResponse.DataBean {

        /**
         * nick_name : 你好
         * phone_number : 18662033577
         * avatar : http://zy.zhagen.com/origin/20180118/c81e728d9d4c2f636f067f89cc14862c_1516253968.png
         * uid : 33
         * hx_username :
         * list : [{"outset":"0","cutoff":"0","method":"不限","create_at":"1514342596","district":"回龙观","room":"1/2/3居室","price":"不限"},{"outset":"0","cutoff":"0","method":"不限","create_at":"1514342643","district":"龙泽","room":"1/2/3居室","price":"不限"},{"outset":"1000","cutoff":"8000","method":"不限","create_at":"1514342651","district":"龙泽","room":"1/2/3居室","price":"1000-8000元"},{"outset":"2000","cutoff":"4000","method":"整租","create_at":"1514342703","district":"龙泽","room":"1/2/3居室","price":"2000-4000元"},{"outset":"2000","cutoff":"4000","method":"整租","create_at":"1514342720","district":"北苑","room":"1/2/3居室","price":"2000-4000元"},{"outset":"0","cutoff":"0","method":"不限","create_at":"1514449252","district":"回龙观","room":"1/2/3居室","price":"不限"},{"outset":"2000","cutoff":"4000","method":"整租","create_at":"1514452711","district":"北苑","room":"不限","price":"2000-4000元"},{"outset":"2000","cutoff":"4000","method":"整租","create_at":"1514452727","district":"北苑","room":"1/2/3居室","price":"2000-4000元"},{"outset":"0","cutoff":"0","method":"不限","create_at":"1514453676","district":"回龙观","room":"不限","price":"不限"},{"outset":"0","cutoff":"0","method":"不限","create_at":"1514884668","district":"下瓦房","room":"不限","price":"不限"}]
         * page : 1
         */

        private String nick_name;
        private String phone_number;
        private String avatar;
        private String uid;
        private String hx_username;
        private List<ListBean> list;

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

        public String getHx_username() {
            return hx_username;
        }

        public void setHx_username(String hx_username) {
            this.hx_username = hx_username;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * outset : 0
             * cutoff : 0
             * method : 不限
             * create_at : 1514342596
             * district : 回龙观
             * room : 1/2/3居室
             * price : 不限
             */

            private String outset;
            private String cutoff;
            private String method;
            private long create_at;
            private String district;
            private String room;
            private String price;

            public String getOutset() {
                return outset;
            }

            public void setOutset(String outset) {
                this.outset = outset;
            }

            public String getCutoff() {
                return cutoff;
            }

            public void setCutoff(String cutoff) {
                this.cutoff = cutoff;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
