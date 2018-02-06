package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/18.
 */

public class MainEvaluateResponse extends BaseResponse<MainEvaluateResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean{

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        private List<ListBean> list;

        public static class ListBean{


            /**
             * nick_name : 18210919684
             * avatar :
             * phone_number : 182****9684
             * content : 嗯嗯呃
             * label : ["准时到达","服务很nice","态度很好"]
             * score : 4.0
             * create_at : 1515389482
             * hide : 1
             */

            private String nick_name;
            private String avatar;
            private String phone_number;
            private String content;
            private int score;
            private long create_at;
            private int hide;
            private List<String> label;

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public long getCreate_at() {
                return create_at;
            }

            public void setCreate_at(long create_at) {
                this.create_at = create_at;
            }

            public int getHide() {
                return hide;
            }

            public void setHide(int hide) {
                this.hide = hide;
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
