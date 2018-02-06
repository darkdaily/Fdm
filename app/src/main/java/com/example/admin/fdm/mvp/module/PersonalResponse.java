package com.example.admin.fdm.mvp.module;

/**
 * Created by test on 2018/1/18.
 */

public class PersonalResponse extends BaseResponse<PersonalResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {

            /**
             * username : 李四-链家公寓
             * avatar : http://zy.zhagen.com/agent/20180116/96088da73d03299a17c3562f392cdd0f.png
             * telephone : 13261129587
             * district : 天通苑
             * company_name : 链家公寓
             */

            private String username;
            private String avatar;
            private String telephone;
            private String district;
            private String company_name;
            private String districtId;

            public String getDistrictId() {
                return districtId;
            }

            public void setDistrictId(String districtId) {
                this.districtId = districtId;
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

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

    }

}
