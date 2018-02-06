package com.example.admin.fdm.mvp.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by test on 2018/1/20.
 */

public class HouseDetailResponse extends BaseResponse<HouseDetailResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean {


        /**
         * house_id : 274
         * room_id :
         * company_id : 2
         * type : 2
         * house_photo : ["http://zy.zhagen.com/uploads/images/20171113/1510542838dfe72487a81924e3.jpg","http://zy.zhagen.com/uploads/images/20171113/15105428396ed550d7ab5f2d8e.jpg","http://zy.zhagen.com/uploads/images/20171113/1510542839fc8b17a8afa13056.jpg","http://zy.zhagen.com/uploads/images/20171113/151054283959f8e5b4b4aca154.jpg","http://zy.zhagen.com/uploads/images/20171113/151054283901f30b2b3659e27c.jpg","http://zy.zhagen.com/uploads/images/20171113/15105428406d4dffaaf8242888.jpg","http://zy.zhagen.com/uploads/images/20171113/15105428406d88ca2177940d50.jpg","http://zy.zhagen.com/uploads/images/20171113/1510542840820bb345dd97c324.jpg","http://zy.zhagen.com/uploads/images/20171113/15105428419fa5d0368b439548.jpg","http://zy.zhagen.com/uploads/images/20171113/1510542841e18a7e9cdd7e5e0d.jpg"]
         * house_title : 和谐家园二区一号院101号楼-两居室
         * house_kind : 二室一厅一卫-100.00
         * rental : 1300
         * labels : ["简单大方","装修地中海风","可以养宠物","全新墙纸","阳台视野广阔"]
         * service_charge : 1.0
         * intermediary_fee : 1.0
         * pay_method : [{"method_name":"押1付3","pay":"3","pledge":"1"}]
         * room_n : [{"room_deposit":"1300","room_code":"BJ0002B49296","room_number":"402","rental":"1300","room_id":"369"}]
         * date : ["1","2","3","4","5","6","7","8","9","10","11","12"]
         * seven : 0
         * all : 0
         * collect : 3
         * house_code : BJ0002B49296
         * house_type : 二室一厅一卫
         * space_area : 100.00
         * floor : 4/12
         * room : [1,0,0,1,1,0,1,0,1,1,1,1,1,0,0,1]
         * public : []
         * house_address : 北京-昌平-回龙观;昌平回龙观东区旺角回龙观东大街
         * house_desc : 很HAO

         * logo : http://zy.zhagen.com/
         * company_name : 链家公寓
         * on_rent : 44
         * count_order : 111
         * agent_sum : 5
         * lng 经度
         * lat 纬度
         */

        private double lng;
        private double lat;
        private String house_id;
        private String room_id;
        private String company_id;
        private String type;
        private String house_title;
        private String house_kind;
        private String rental;
        private String service_charge;
        private String intermediary_fee;
        private String seven;
        private String all;
        private String collect;
        private String house_code;
        private String house_type;
        private String space_area;
        private String floor;
        private String house_address;
        private String house_desc;
        private String logo;
        private String company_name;
        private String on_rent;
        private String count_order;
        private String agent_sum;
        private List<String> house_photo;
        private List<String> labels;
        private List<PayMethodBean> pay_method;
        private List<RoomNBean> room_n;
        private List<String> date;
        private List<Integer> room;
        private List<String> link;
        private List<SameBean> same;
        private String share;

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lag) {
            this.lng = lag;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public List<String> getLink() {
            return link;
        }

        public void setLink(List<String> link) {
            this.link = link;
        }

        @SerializedName("public")
        private List<Integer> publicX;

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

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHouse_title() {
            return house_title;
        }

        public void setHouse_title(String house_title) {
            this.house_title = house_title;
        }

        public String getHouse_kind() {
            return house_kind;
        }

        public void setHouse_kind(String house_kind) {
            this.house_kind = house_kind;
        }

        public String getRental() {
            return rental;
        }

        public void setRental(String rental) {
            this.rental = rental;
        }

        public String getService_charge() {
            return service_charge;
        }

        public void setService_charge(String service_charge) {
            this.service_charge = service_charge;
        }

        public String getIntermediary_fee() {
            return intermediary_fee;
        }

        public void setIntermediary_fee(String intermediary_fee) {
            this.intermediary_fee = intermediary_fee;
        }

        public String getSeven() {
            return seven;
        }

        public void setSeven(String seven) {
            this.seven = seven;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public String getHouse_code() {
            return house_code;
        }

        public void setHouse_code(String house_code) {
            this.house_code = house_code;
        }

        public String getHouse_type() {
            return house_type;
        }

        public void setHouse_type(String house_type) {
            this.house_type = house_type;
        }

        public String getSpace_area() {
            return space_area;
        }

        public void setSpace_area(String space_area) {
            this.space_area = space_area;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getHouse_address() {
            return house_address;
        }

        public void setHouse_address(String house_address) {
            this.house_address = house_address;
        }

        public String getHouse_desc() {
            return house_desc;
        }

        public void setHouse_desc(String house_desc) {
            this.house_desc = house_desc;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getOn_rent() {
            return on_rent;
        }

        public void setOn_rent(String on_rent) {
            this.on_rent = on_rent;
        }

        public String getCount_order() {
            return count_order;
        }

        public void setCount_order(String count_order) {
            this.count_order = count_order;
        }

        public String getAgent_sum() {
            return agent_sum;
        }

        public void setAgent_sum(String agent_sum) {
            this.agent_sum = agent_sum;
        }

        public List<String> getHouse_photo() {
            return house_photo;
        }

        public void setHouse_photo(List<String> house_photo) {
            this.house_photo = house_photo;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        public List<PayMethodBean> getPay_method() {
            return pay_method;
        }

        public void setPay_method(List<PayMethodBean> pay_method) {
            this.pay_method = pay_method;
        }

        public List<RoomNBean> getRoom_n() {
            return room_n;
        }

        public void setRoom_n(List<RoomNBean> room_n) {
            this.room_n = room_n;
        }

        public List<String> getDate() {
            return date;
        }

        public void setDate(List<String> date) {
            this.date = date;
        }

        public List<Integer> getRoom() {
            return room;
        }

        public void setRoom(List<Integer> room) {
            this.room = room;
        }

        public List<Integer> getPublicX() {
            return publicX;
        }

        public void setPublicX(List<Integer> publicX) {
            this.publicX = publicX;
        }

        public List<SameBean> getSame() {
            return same;
        }

        public void setSame(List<SameBean> same) {
            this.same = same;
        }

        public static class PayMethodBean {
            /**
             * method_name : 押1付3
             * pay : 3
             * pledge : 1
             */

            private String method_name;
            private String pay;
            private String pledge;

            public String getMethod_name() {
                return method_name;
            }

            public void setMethod_name(String method_name) {
                this.method_name = method_name;
            }

            public String getPay() {
                return pay;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public String getPledge() {
                return pledge;
            }

            public void setPledge(String pledge) {
                this.pledge = pledge;
            }
        }

        public static class RoomNBean {
            /**
             * room_deposit : 1300
             * room_code : BJ0002B49296
             * room_number : 402
             * rental : 1300
             * room_id : 369
             */

            private String room_deposit;
            private String room_code;
            private String room_number;
            private String rental;
            private String room_id;

            public String getRoom_deposit() {
                return room_deposit;
            }

            public void setRoom_deposit(String room_deposit) {
                this.room_deposit = room_deposit;
            }

            public String getRoom_code() {
                return room_code;
            }

            public void setRoom_code(String room_code) {
                this.room_code = room_code;
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

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }
        }

        public static class SameBean {
            /**
             * house_id : 170
             * room_id : 0
             * type : 2
             * house_photo : http://zy.zhagen.com/uploads/images/20171120/15111429314fb44972f92cdcfe.jpg
             * house_title : 北苑32号院205号楼-双人间
             * rental : 1234
             */

            @SerializedName("house_id")
            private String house_idX;
            @SerializedName("room_id")
            private String room_idX;
            @SerializedName("type")
            private String typeX;
            @SerializedName("house_photo")
            private String house_photoX;
            @SerializedName("house_title")
            private String house_titleX;
            @SerializedName("rental")
            private int rentalX;

            public String getHouse_idX() {
                return house_idX;
            }

            public void setHouse_idX(String house_idX) {
                this.house_idX = house_idX;
            }

            public String getRoom_idX() {
                return room_idX;
            }

            public void setRoom_idX(String room_idX) {
                this.room_idX = room_idX;
            }

            public String getTypeX() {
                return typeX;
            }

            public void setTypeX(String typeX) {
                this.typeX = typeX;
            }

            public String getHouse_photoX() {
                return house_photoX;
            }

            public void setHouse_photoX(String house_photoX) {
                this.house_photoX = house_photoX;
            }

            public String getHouse_titleX() {
                return house_titleX;
            }

            public void setHouse_titleX(String house_titleX) {
                this.house_titleX = house_titleX;
            }

            public int getRentalX() {
                return rentalX;
            }

            public void setRentalX(int rentalX) {
                this.rentalX = rentalX;
            }
        }
    }
}
