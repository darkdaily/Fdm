package com.example.admin.fdm.mvp.module;

/**
 * Created by fushuang on 2017/12/20.
 */

public class BaseResponse<T extends BaseResponse.DataBean>{

    private String msg;
    private int code;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {

        //分页数据
        private int page;
        private int sum_page;

        //状态标志位
        private String status;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSum_page() {
            return sum_page;
        }

        public void setSum_page(int sum_page) {
            this.sum_page = sum_page;
        }
    }

}
