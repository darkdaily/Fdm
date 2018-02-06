package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/17.
 */

public class CreatTakeLookResponse extends BaseResponse<CreatTakeLookResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {


        /**
         * nick_name : 啊
         * phone_number : 18310846756
         * uid : 35
         */

        private String nick_name;
        private String phone_number;
        private String uid;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

}
