package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/20.
 */

public class ClaimChooseResponse extends BaseResponse<ClaimChooseResponse.DataBean> {


    public static class DataBean extends BaseResponse.DataBean {

        /**
         * house_photo : http://zy.zhagen.com/uploads/images/20171113/1510542838dfe72487a81924e3.jpg
         * house_title : 嘉禾园A区1101号楼-两居室
         * house_info : 二室一厅一卫-100.00
         * house_address : 北京市朝阳区潘家园
         * house_rental : 2000
         * house_label : ["简单大方","装修地中海风","可以养宠物","全新墙纸","阳台视野广阔"]
         * rooms : [{"id":"371","house_code":"BJ0002B71966","room_number":"222","rental":"2000","deposit":"2000"}]
         * member_id : 2
         * type : 2
         */

        private String house_photo;
        private String house_title;
        private String house_info;
        private String house_address;
        private String house_rental;
        private String member_id;
        private int type;
        private List<String> house_label;
        private List<RoomsBean> rooms;
        private String share;

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
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

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getHouse_label() {
            return house_label;
        }

        public void setHouse_label(List<String> house_label) {
            this.house_label = house_label;
        }

        public List<RoomsBean> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomsBean> rooms) {
            this.rooms = rooms;
        }

        public static class RoomsBean {
            /**
             * id : 371
             * house_code : BJ0002B71966
             * room_number : 222
             * rental : 2000
             * deposit : 2000
             */

            private String id;
            private String house_code;
            private String room_number;
            private String rental;
            private String deposit;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHouse_code() {
                return house_code;
            }

            public void setHouse_code(String house_code) {
                this.house_code = house_code;
            }

            public String getRoom_number() {
                return room_number;
            }

            public void setRoom_number(String room_number) {
                this.room_number = room_number;
            }

            public String getRental() {
                return rental;
            }

            public void setRental(String rental) {
                this.rental = rental;
            }

            public String getDeposit() {
                return deposit;
            }

            public void setDeposit(String deposit) {
                this.deposit = deposit;
            }
        }
    }

}
