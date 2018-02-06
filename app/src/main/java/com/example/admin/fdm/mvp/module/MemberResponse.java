package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/27.
 */

public class MemberResponse extends BaseResponse<MemberResponse.DataBean>{


    public static class DataBean extends BaseResponse.DataBean {

        /**
         * id : 238
         * enterprise_code : zhagen
         * username : 扎根-王思康
         * telephone : 18515312205
         * password : a43cd4b171e795be3d874fe11c892540:138
         * hx_username : agent18515312205
         * hx_password : 5fc54f01ea3eeb5d7eec62fe49013d34
         * district : null
         * avatar : http://zy.zhagen.com/default/default_avatar.png
         * idcard : 0
         * is_system_add : 0
         * create_at : 1516847262
         * update_at : 1516847262
         * deleted : 0
         */

        private int id;
        private String enterprise_code;
        private String username;
        private String telephone;
        private String password;
        private String hx_username;
        private String hx_password;
        private String district;
        private String avatar;
        private String idcard;
        private int is_system_add;
        private int create_at;
        private int update_at;
        private int deleted;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnterprise_code() {
            return enterprise_code;
        }

        public void setEnterprise_code(String enterprise_code) {
            this.enterprise_code = enterprise_code;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHx_username() {
            return hx_username;
        }

        public void setHx_username(String hx_username) {
            this.hx_username = hx_username;
        }

        public String getHx_password() {
            return hx_password;
        }

        public void setHx_password(String hx_password) {
            this.hx_password = hx_password;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public int getIs_system_add() {
            return is_system_add;
        }

        public void setIs_system_add(int is_system_add) {
            this.is_system_add = is_system_add;
        }

        public int getCreate_at() {
            return create_at;
        }

        public void setCreate_at(int create_at) {
            this.create_at = create_at;
        }

        public int getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(int update_at) {
            this.update_at = update_at;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }
    }
}
