package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/22.
 */

public class ClaimSearchResponse extends BaseResponse<ClaimSearchResponse.DataBean> {


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
             * house_id : 326
             * room_id : 0
             * type : 2
             * code_id : 77
             * house_photo : http://zy.zhagen.com/uploads/images/20171204/15123721509c30f665e9326c8b.jpg
             * house_title : 百子湾西里102号楼-大户型
             * house_info : 一室零厅一卫-32.00
             * house_address : 北京市朝阳区百子湾
             * house_rental : 1900
             * house_label : ["阳台视野广阔"]
             */

            private String house_id;
            private String room_id;
            private String type;
            private String code_id;
            private String house_photo;
            private String house_title;
            private String house_info;
            private String house_address;
            private String house_rental;
            private List<String> house_label;

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

            public String getCode_id() {
                return code_id;
            }

            public void setCode_id(String code_id) {
                this.code_id = code_id;
            }

            public String getHouse_photo() {
                return house_photo;
            }

            public void setHouse_photo(String house_photo) {
                this.house_photo = house_photo;
            }

            public String getHouse_title() {
                return house_title;
            }

            public void setHouse_title(String house_title) {
                this.house_title = house_title;
            }

            public String getHouse_info() {
                return house_info;
            }

            public void setHouse_info(String house_info) {
                this.house_info = house_info;
            }

            public String getHouse_address() {
                return house_address;
            }

            public void setHouse_address(String house_address) {
                this.house_address = house_address;
            }

            public String getHouse_rental() {
                return house_rental;
            }

            public void setHouse_rental(String house_rental) {
                this.house_rental = house_rental;
            }

            public List<String> getHouse_label() {
                return house_label;
            }

            public void setHouse_label(List<String> house_label) {
                this.house_label = house_label;
            }
        }
    }
}
