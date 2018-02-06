package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/22.
 */

public class HouseEvaluteBaseResponse extends BaseResponse<HouseEvaluteBaseResponse.DataBean> {


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
             * id : 23
             * avatar : origin/20180118/c81e728d9d4c2f636f067f89cc14862c_1516253968.png
             * real_name : 测试2
             * label : ["热情"]
             * content : 曾几何时，还很懵懂无知，没有烦恼，不作挣扎，朦胧的叩响一颗心，没有习惯，却很执著，热枕想象的期待。风景总是不能回头，错失遗憾，却没有想象中的简单，幻想做一个温情的人，出走半生，相遇并不完美，遇一简单的人，往事随风。
             * album : ["http://zy.zhagen.com/uploads/images/20171113/15105384199c30f665e9326c8b.jpg","http://zy.zhagen.com/uploads/images/20171113/151053841913c13d40df2dc277.jpg","http://zy.zhagen.com/uploads/images/20171113/151053841974092a7c129871c7.jpg","http://zy.zhagen.com/uploads/images/20171113/151053841968b0e3b5da3e6006.jpg","http://zy.zhagen.com/uploads/images/20171113/1510538419aabe078a98674fb1.jpg","http://zy.zhagen.com/uploads/images/20171113/151053842061d25efd2882553d.jpg"]
             * score : 5.0
             * create_at : 1515982567
             * hide : 1
             */

            private String id;
            private String avatar;
            private String real_name;
            private String phone_number;
            private String content;
            private int score;
            private long create_at;
            private String hide;
            private List<String> label;
            private List<String> album;

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
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

            public String getHide() {
                return hide;
            }

            public void setHide(String hide) {
                this.hide = hide;
            }

            public List<String> getLabel() {
                return label;
            }

            public void setLabel(List<String> label) {
                this.label = label;
            }

            public List<String> getAlbum() {
                return album;
            }

            public void setAlbum(List<String> album) {
                this.album = album;
            }
        }
    }
}
