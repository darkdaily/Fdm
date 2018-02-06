package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/15.
 */

public class PostResponse extends BaseResponse<PostResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {

        private String bill_num;

        private int verificationCode;

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(int verificationCode) {
            this.verificationCode = verificationCode;
        }

        public String getBill_num() {
            return bill_num;
        }

        public void setBill_num(String bill_num) {
            this.bill_num = bill_num;
        }
    }

}
