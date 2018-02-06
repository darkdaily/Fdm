package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/18.
 */

public class ClaimHouseListResponse extends BaseResponse<ClaimHouseListResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {


            /**
             * house_id : 140
             * type : 1
             * room_id : 212
             * create_at : 1512272826
             * house_photo : http://zy.zhagen.com/uploads/images/20171203/1512272545765af74efd45b1f3.jpg
             * house_title : 白家疃别墅101号楼-整租
             * house_info : 二室一厅一卫-99.00
             * house_address : 北京市海淀区马连洼
             * house_rental : 9000
             * house_label : []
             */

            private int room_count;
            private String house_id;
            private String type;
            private String room_id;
            private String create_at;
            private String house_photo;
            private String house_title;
            private String house_info;
            private String house_address;
            private String house_rental;
            private List<String> house_label;

            public int getRoom_count() {
                return room_count;
            }

            public void setRoom_count(int room_count) {
                this.room_count = room_count;
            }

            public String getHouse_id() {
                return house_id;
            }

            public void setHouse_id(String house_id) {
                this.house_id = house_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
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
