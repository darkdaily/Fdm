package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/18.
 */

public class MainResponse extends BaseResponse<MainResponse.DataBean> {


    public static class DataBean extends BaseResponse.DataBean {


        /**
         * username : 李四-链家公寓
         * avatar : http://zy.zhagen.com/origin/20180118/a144e5b7220a417648d274de8c727d1f_1516269637.png
         * company_name : 链家公寓
         * count : 0
         * sum : 0
         * score : 4.0
         */

        private String username;
        private String avatar;
        private String company_name;
        private String count;
        private String sum;
        private String score;
        private int grab_num;

        public int getGrab_num() {
            return grab_num;
        }

        public void setGrab_num(int grab_num) {
            this.grab_num = grab_num;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

}
