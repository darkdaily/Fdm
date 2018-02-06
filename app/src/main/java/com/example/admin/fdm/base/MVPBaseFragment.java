package com.example.admin.fdm.base;


import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by admin on 2016/11/1.
 */
public abstract class MVPBaseFragment<T extends MVPBasePresenter<IBaseView>> extends BaseFragment {
    protected MVPBasePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.attach((IBaseView) this);
        }
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }


    public abstract T createPresenter();
}
