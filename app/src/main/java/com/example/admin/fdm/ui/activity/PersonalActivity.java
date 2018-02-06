package com.example.admin.fdm.ui.activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.MainActivity;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.mvp.module.PersonalResponse;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.view.RoundImageView;
import com.example.admin.fdm.utils.ImageUtils;
import com.example.admin.fdm.utils.SPUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.internal.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by test on 2017/12/26.
 */

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.tv_notice)
    TextView tv_notice;
    @BindView(R.id.iv_delete_notice)
    ImageView iv_delete_notice;
    @BindView(R.id.img_user_icon)
    RoundImageView img_user_icon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_tel)
    TextView tv_tel;
    @BindView(R.id.trading_area_butten)
    TextView trading_area_butten;

    //经纪人id
    private int member_id;

    //个人中心数据
    private PersonalResponse.DataBean data;

    //照相路径
    protected static Uri tempUri;

    //图片管理
    protected static final int CHOOSE_PICTURE = 0;

    //拍照
    protected static final int TAKE_PICTURE = 1;

    //修图
    private static final int CROP_SMALL_PICTURE = 2;

    private String Key;

    @Override
    public int setLayout() {
        return R.layout.activity_personal;
    }

    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        Key = getIntent().getExtras().getString("key");
        showProgressDialog();
        getPersonalData();
    }

    @Override
    public void initData() {


    }

    private void getPersonalData() {
        RetrofitHelper.getApiWithUid().getPersonal(member_id)
                .compose(RxScheduler.<PersonalResponse>defaultScheduler())
                .subscribe(new BaseObserver<PersonalResponse>(PersonalActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        finish();
                    }

                    @Override
                    public void NoNetwork() {
                        finish();
                    }
                    @Override
                    public void next(PersonalResponse personalResponse) {
                        if(personalResponse.getData() != null){
                            data = personalResponse.getData();
                            setView();
                        }else{
                            finish();
                        }

                    }

                    @Override
                    public void complete() {
                        dismissProgressDialog();
                    }
                });
    }

    private void setView() {
        //公告内容
//        tv_notice.setText("");
        Glide.with(mActivity).load(data.getAvatar()).into(img_user_icon);
        tv_name.setText(data.getUsername());
        tv_company_name.setText(data.getCompany_name());
        tv_tel.setText(data.getTelephone());
        if(!data.getDistrict().equals("")){
            trading_area_butten.setText(data.getDistrict());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //回调参数
        switch (resultCode) {
            case 1:
                trading_area_butten.setText(intent.getStringExtra("district"));
                data.setDistrict(intent.getStringExtra("district"));
                break;
        }
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(intent.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(intent);
                    }
                    break;
            }

        }
    }

    @OnClick({R.id.trading_area_butten, R.id.iv_left, R.id.iv_delete_notice, R.id.img_user_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trading_area_butten:
                Intent mIntent = new Intent();
                mIntent.setClass(PersonalActivity.this,
                        TradingAreaActivity.class);
                int requestCode = 0;
                mIntent.putExtra("district", data.getDistrict());
                if(data.getDistrictId() == null){
                    data.setDistrictId("");
                }
                mIntent.putExtra("districtId", data.getDistrictId());
                startActivityForResult(mIntent, requestCode);
                break;
            case R.id.iv_left:
                if(!trading_area_butten.getText().toString().equals("")){
                    if(Key.equals("")){
                        Intent nIntent = new Intent();
                        int resultCode = 1;
                        setResult(resultCode, nIntent);
                        finish();
                    }else{
                        startActivity(MainActivity.class);
                        finish();
                    }
                }else{
                    Toast.makeText(PersonalActivity.this,"请设置商圈",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_delete_notice:
                break;
            case R.id.img_user_icon:
                showChoosePicDialog();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(!trading_area_butten.getText().toString().equals("")){
                if(Key.equals("")){
                    Intent nIntent = new Intent();
                    int resultCode = 1;
                    setResult(resultCode, nIntent);
                    finish();
                    return false;
                }else{
                    startActivity(MainActivity.class);
                    finish();
                    return false;
                }
            }else{
                Toast.makeText(PersonalActivity.this,"请设置商圈",Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            img_user_icon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }


    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            // 拿着imagePath上传
            File mFile = new File(imagePath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), mFile);

            // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);

            RetrofitHelper.getApiWithUid().setUserHead(member_id, body)
                    .enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if(response.body().getCode() == 0){
                                SPUtil.put(PersonalActivity.this,"avatar",response.body().getData().getAvatar());
                                Toast.makeText(PersonalActivity.this,response.body().getMsg(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {

                        }
                    });


        }
    }

    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void takePicture() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(PersonalActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PersonalActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                return;
            } else {
                openCamra();//调用具体方法
            }
        } else {
            openCamra();//调用具体方法
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    openCamra();
                } else {
                    // Permission Denied
                    Toast.makeText(PersonalActivity.this, "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void openCamra() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(PersonalActivity.this, "com.example.admin.fdm.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
}
