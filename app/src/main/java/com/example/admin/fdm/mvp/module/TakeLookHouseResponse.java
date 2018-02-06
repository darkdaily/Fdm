package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/17.
 */

public class TakeLookHouseResponse extends BaseResponse<TakeLookHouseResponse.DataBean> {


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
             * house_id : 237
             * type : 2
             * room_id : 0
             * house_photo : http://zy.zhagen.com/uploads/images/20171129/151194392474bda974c1b0c879.jpg
             * house_title : 西土城路31号院5号楼-复式
             * house_info : 一室零厅零卫-30.00㎡
             * house_address : 北京市海淀区蓟门桥
             * house_rental : 1800
             * house_label : ["阳台视野广阔"]
             */

            private String house_id;
            private String type;
            private int room_id;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getRoom_id() {
                return room_id;
            }

            public void setRoom_id(int room_id) {
                this.room_id = room_id;
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
