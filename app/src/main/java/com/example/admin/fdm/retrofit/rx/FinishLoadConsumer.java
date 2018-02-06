package com.example.admin.fdm.retrofit.rx;

import com.example.admin.fdm.mvp.module.BaseResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by fushuang on 2017/12/22.
 */

public class  FinishLoadConsumer<T extends BaseResponse> implements Consumer<T> {

    private final RefreshLayout refreshLayout;
    private Integer currentPage;
    private List<Object> list;

    public FinishLoadConsumer(RefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public FinishLoadConsumer(RefreshLayout refreshLayout, Integer currentPage) {
        this.refreshLayout = refreshLayout;
        this.currentPage = currentPage;
    }


    @Override
    public void accept(T t) throws Exception {

        if (currentPage!=null&&currentPage>=t.getData().getSum_page()){
            refreshLayout.setEnableLoadmore(false);
        }else {
            refreshLayout.setEnableLoadmore(true);
        }
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefresh();
    }
}
