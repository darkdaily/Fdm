package com.example.admin.fdm.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.ClaimChooseResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.fragment.HouseDetailFragment;
import com.example.admin.fdm.ui.fragment.HouseEvaluteFragment;
import com.example.admin.fdm.ui.view.RoomPickDialog;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class HouseDetailActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.iv_share)
    ImageView iv_share;

    private HouseEvaluteFragment evaluteFragment;

    private HouseDetailFragment detailFragment;

    @Override
    public int setLayout() {
        return R.layout.activity_house_detail;
    }

    @Override
    public void initEvent() {

        final Bundle bundle = getIntent().getExtras(); //得到传过来的bundle

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case "详情":

                        getSupportFragmentManager().beginTransaction().hide(evaluteFragment)
                                .show(detailFragment).commit();
                        break;

                    case "评价":
                        getSupportFragmentManager().beginTransaction().hide(detailFragment)
                                .show(evaluteFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        evaluteFragment = new HouseEvaluteFragment();
        evaluteFragment.setArguments(bundle);
        detailFragment = new HouseDetailFragment();
        detailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, detailFragment).add(R.id.fl_content, evaluteFragment)
                .hide(evaluteFragment).commit();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left,R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_share:
                String imageUrl = detailFragment.getHouse_photo();
                UMImage image = new UMImage(HouseDetailActivity.this, detailFragment.getHouse_photo());
                image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.PNG;//网络图片
                UMWeb  web = new UMWeb(detailFragment.getShare());

                web.setTitle(detailFragment.getHouse_name());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(detailFragment.getHouse_info()+"|"+detailFragment.getHouse_rental());//描述
                new ShareAction(this)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.ALIPAY)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {

                            }
                        })
                        .open();


                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
