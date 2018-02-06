package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/11.
 */

public class CommentResponse extends BaseResponse<CommentResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean{


        /**
         * id : 5
         * house_id : 5
         * room_id : 5
         * type : 2
         * member_id : 2
         * username : 李四-链家公寓
         * avatar :
         * company_id : 2
         * company_name : 链家公寓
         * house_title : 北京太阳城9号楼-太阳A户型
         * house_area : 一室一厅零卫-60.00m²
         * house_score : 5
         * house_conent : asdsaszadsdd
         * album :
         * label : 2,3,4
         * house_label : ["环境很nice","适合居住"]
         * member_score : 4
         * member_conent : 嗯嗯呃
         * member_label : ["准时到达","服务很nice","态度很好"]
         */

        private String id;
        private String house_id;
        private String room_id;
        private String type;
        private String member_id;
        private String username;
        private String avatar;
        private String company_id;
        private String company_name;
        private String house_title;
        private String house_area;
        private int house_score;
        private String house_conent;
        private List<String> album;
        private String label;
        private int member_score;
        private String member_conent;
        private List<String> house_label;
        private List<String> member_label;
        /**
         * logo : null
         * company_score : 4.0
         * company_conent : 应从容度日。
         * company_label : []
         */

        private String logo;
        private int company_score;
        private String company_conent;
        private List<String> company_label;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
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

        public int getHouse_score() {
            return house_score;
        }

        public void setHouse_score(int house_score) {
            this.house_score = house_score;
        }

        public String getHouse_conent() {
            return house_conent;
        }

        public void setHouse_conent(String house_conent) {
            this.house_conent = house_conent;
        }

        public List<String> getAlbum() {
            return album;
        }

        public void setAlbum(List<String> album) {
            this.album = album;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getMember_score() {
            return member_score;
        }

        public void setMember_score(int member_score) {
            this.member_score = member_score;
        }

        public String getMember_conent() {
            return member_conent;
        }

        public void setMember_conent(String member_conent) {
            this.member_conent = member_conent;
        }

        public List<String> getHouse_label() {
            return house_label;
        }

        public void setHouse_label(List<String> house_label) {
            this.house_label = house_label;
        }

        public List<String> getMember_label() {
            return member_label;
        }

        public void setMember_label(List<String> member_label) {
            this.member_label = member_label;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getCompany_score() {
            return company_score;
        }

        public void setCompany_score(int company_score) {
            this.company_score = company_score;
        }

        public String getCompany_conent() {
            return company_conent;
        }

        public void setCompany_conent(String company_conent) {
            this.company_conent = company_conent;
        }

        public List<String> getCompany_label() {
            return company_label;
        }

        public void setCompany_label(List<String> company_label) {
            this.company_label = company_label;
        }
    }
}
