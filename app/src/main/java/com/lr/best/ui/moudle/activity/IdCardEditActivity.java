package com.lr.best.ui.moudle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.utils.permission.PermissionsUtils;
import com.lr.best.utils.permission.RePermissionResultBack;
import com.lr.best.utils.tool.AppUtil;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.TextViewUtils;
import com.lr.best.utils.tool.UtilTools;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动输入身份证界面   界面
 */
public class IdCardEditActivity extends BasicActivity implements RequestView {


    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.idcard_edit)
    EditText idcardEdit;
    @BindView(R.id.check_lay)
    LinearLayout checkLay;
    @BindView(R.id.result_check_lay)
    LinearLayout resultCheckLay;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.but_next)
    Button butNext;
    @BindView(R.id.frontIv)
    ImageView frontIv;
    @BindView(R.id.backIv)
    ImageView backIv;
    @BindView(R.id.cardIv)
    ImageView cardIv;
    @BindView(R.id.checkIV)
    ImageView checkIV;
    @BindView(R.id.checkTV)
    TextView checkTV;

    private String mOpType = "0";

    private String mRequestTag = "";
    private String mIdNum = "";
    private String mName = "";

    private String frontPhoto = "";
    private String backPhoto = "";
    private String cardPhoto = "";
    private String imageType = "-1";

    @Override
    public int getContentView() {
        return R.layout.activity_idcard_edit;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mOpType = bundle.getString("status");
                //0 未提交 1 提交待审核 2 审核通过 3拒绝
                switch (mOpType){
                    case "0":
                        checkLay.setVisibility(View.VISIBLE);
                        resultCheckLay.setVisibility(View.GONE);
                        break;
                    case "1":
                        checkLay.setVisibility(View.GONE);
                        resultCheckLay.setVisibility(View.VISIBLE);
                        checkIV.setImageResource(R.drawable.wait_check);
                        checkTV.setText("已提交,请等待系统审核");
                        break;
                    case "2":
                        checkLay.setVisibility(View.GONE);
                        resultCheckLay.setVisibility(View.VISIBLE);
                        checkIV.setImageResource(R.drawable.icon1_renzheng1);
                        checkTV.setText("您的身份认证已通过审核");
                        break;
                    case "3":
                        checkLay.setVisibility(View.GONE);
                        resultCheckLay.setVisibility(View.VISIBLE);
                        checkIV.setImageResource(R.drawable.refuse_check);
                        checkTV.setText("身份认证未通过审核,重新提交");
                        String content = checkTV.getText().toString().trim();
                        TextViewUtils textViewUtils = new TextViewUtils();
                        textViewUtils.init(content, checkTV);
                        textViewUtils.setTextColor(content.indexOf("重新提交") , content.length(), ContextCompat.getColor(this, R.color.blue1));
                        textViewUtils.setTextClick(content.indexOf("重新提交"), content.length(), new TextViewUtils.ClickCallBack() {
                            @Override
                            public void onClick() {
                                checkLay.setVisibility(View.VISIBLE);
                                resultCheckLay.setVisibility(View.GONE);
                            }
                        });
                        textViewUtils.build();
                        break;
                }
                if (!UtilTools.empty(mOpType) && mOpType.equals("1")) {
                    resultCheckLay.setVisibility(View.VISIBLE);

                }
            }
        }


        mTitleText.setText("身份认证");
        mTitleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);
        mBackText.setText("");

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 && !UtilTools.empty(idcardEdit.getText() + "")) {
                    butNext.setEnabled(true);
                } else {
                    butNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        idcardEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 && !UtilTools.empty(nameEdit.getText() + "")) {
                    butNext.setEnabled(true);
                } else {
                    butNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @OnClick({R.id.back_img, R.id.left_back_lay, R.id.but_next, R.id.frontIv, R.id.backIv, R.id.cardIv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.frontIv:
                imageType = "0";
                ActionSheetDialogNoTitle();
                break;

            case R.id.backIv:
                imageType = "1";
                ActionSheetDialogNoTitle();
                break;
            case R.id.cardIv:
                imageType = "2";
                ActionSheetDialogNoTitle();
                break;
            case R.id.but_next:
                mName = nameEdit.getText() + "";
                if (UtilTools.isEmpty(nameEdit, "姓名")) {
                    showToastMsg("姓名不能为空");
                    return;
                }
                mIdNum = idcardEdit.getText() + "";
                if (UtilTools.isEmpty(idcardEdit, "身份证号")) {
                    showToastMsg("身份证号不能为空");
                    return;
                }
                if (UtilTools.empty(frontPhoto)) {
                    showToastMsg("请上传身份证正面");
                    return;
                }

                if (UtilTools.empty(backPhoto)) {
                    showToastMsg("请上传身份证反面");
                    return;
                }
                if (UtilTools.empty(cardPhoto)) {
                    showToastMsg("请上传手持身份证正面");
                    return;
                }
                //身份认证
                identityActiveAction();
                butNext.setEnabled(false);

                break;
        }
    }

    private void identityActiveAction() {

        mRequestTag = MethodUrl.IDENTITY_ACTIVE;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(IdCardEditActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("real_name", mName);
        map.put("identity", mIdNum);
        map.put("front_photo", frontPhoto);
        map.put("back_photo", backPhoto);
        map.put("card_photo", cardPhoto);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.IDENTITY_ACTIVE, map);
    }


    @Override
    public void showProgress() {
        showProgressDialog();
    }


    @Override
    public void disimissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {
        Intent intent;
        switch (mType) {
            case MethodUrl.IDENTITY_ACTIVE:
                butNext.setEnabled(true);
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        checkLay.setVisibility(View.GONE);
                        resultCheckLay.setVisibility(View.VISIBLE);
                        checkIV.setImageResource(R.drawable.wait_check);
                        checkTV.setText("已提交,请等待系统审核");
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(IdCardEditActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                butNext.setEnabled(true);
                break;
            case MethodUrl.UPLOAD_FILE: //上传二维码图片成功
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        String imgUrl = tData.get("data") + "";
                        if (!UtilTools.empty(imgUrl)) {
                            switch (imageType) {
                                case "0":
                                    frontPhoto = imgUrl;
                                    break;
                                case "1":
                                    backPhoto = imgUrl;
                                    break;
                                case "2":
                                    cardPhoto = imgUrl;
                                    break;
                            }
                            showToastMsg("图片上传成功");
                        } else {
                            showToastMsg("图片上传失败");
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(IdCardEditActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }


                break;
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {

        butNext.setEnabled(true);
        dealFailInfo(map, mType);
    }

    private void ActionSheetDialogNoTitle() {
        final String[] stringItems = {"从相册选择", "拍照"};
        final ActionSheetDialog dialog = new ActionSheetDialog(IdCardEditActivity.this, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                switch (position) {
                    case 0: //从相册选择

                        PermissionsUtils.requsetRunPermission(IdCardEditActivity.this, new RePermissionResultBack() {
                            @Override
                            public void requestSuccess() {
                                //showToastMsg(R.string.successfully);
                                localPic();
                            }

                            @Override
                            public void requestFailer() {
                                showToastMsg(R.string.failure);
                            }
                        }, Permission.Group.STORAGE);
                        break;
                    case 1: //拍照

                        PermissionsUtils.requsetRunPermission(IdCardEditActivity.this, new RePermissionResultBack() {
                            @Override
                            public void requestSuccess() {
                                //showToastMsg(R.string.successfully);
                                photoPic();
                            }

                            @Override
                            public void requestFailer() {
                                showToastMsg(R.string.failure);
                            }
                        }, Permission.Group.STORAGE, Permission.Group.CAMERA);
                        break;
                }

            }
        });
    }

    private void localPic() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /**
         * 下面这句话，与其它方式写是一样的效果，如果：
         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         * intent.setType(""image/*");设置数据类型
         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
         * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
         */
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }

    private void photoPic() {
        /**
         * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
         * 文档，you_sdk_path/docs/guide/topics/media/camera.html
         * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
         * 官方文档太长了就不看了，其实是错的，这个地方也错了，必须改正
         */
        Uri uri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(IdCardEditActivity.this, AppUtil.getAppProcessName(this) + ".FileProvider", new File(Environment.getExternalStorageDirectory(), "xiaoma.jpg"));
        } else {
            uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "xiaoma.jpg"));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 2);
    }

    private Intent dataIntent = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory() + "/xiaoma.jpg");
                if (temp.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(IdCardEditActivity.this, AppUtil.getAppProcessName(IdCardEditActivity.this) + ".FileProvider", temp);
                    } else {
                        uri = Uri.fromFile(temp);
                    }
                    startPhotoZoom(uri);
                }
                break;
            // 取得裁剪后的图片
            case 3:

                // 将Uri图片转换为Bitmap
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                    // TODO，将裁剪的bitmap显示在imageview控件上
                    Drawable dr = new BitmapDrawable(getResources(), bitmap);
                    switch (imageType) {
                        case "0":
                            frontIv.setImageDrawable(dr);
                            break;
                        case "1":
                            backIv.setImageDrawable(dr);
                            break;
                        case "2":
                            cardIv.setImageDrawable(dr);
                            break;
                    }
                    saveCroppedImage(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                //ImageLoader.getmContext().displayImage(MbsConstans.Pic_Path+MbsConstans.memberUser.getHeadPath(),mHeadImage);
                // UtilTools.showImage(MbsConstans.Pic_Path+MbsConstans.memberUser.getHeadPath(),mHeadImage, R.drawable.no_def);

                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String imgName = "";
    private Uri uritempFile;

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        File file = new File(MbsConstans.BASE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.putExtra("crop", "true");
        // intent.putExtra("noFaceDetection", true);
        // 宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);

        // intent.putExtra("scale", true);
        // intent.putExtra("return-data", true);
        // this.startActivityForResult(intent, AppFinal.RESULT_CODE_PHOTO_CUT);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        imgName = System.currentTimeMillis() + ".jpg";
        uritempFile = Uri.parse("file:///" + MbsConstans.BASE_PATH + "/" + imgName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 3);

    }

    private String mHeadImgPath = "";

    private void saveCroppedImage(Bitmap bmp) {

        try {
            File saveFile = new File(MbsConstans.IMAGE_CODE_PATH);

            mHeadImgPath = MbsConstans.IMAGE_CODE_PATH + new Date().getTime() + ".png";
            File file = new File(mHeadImgPath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            File saveFile2 = new File(mHeadImgPath);

            FileOutputStream fos = new FileOutputStream(saveFile2);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();

            // uploadAliPic(new Date().getTime()+".png",filepath);

            //上传图片信息
            uploadPicAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadPicAction() {
        mRequestTag = MethodUrl.UPLOAD_FILE;
        Map<String, Object> signMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(IdCardEditActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("file", mHeadImgPath);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.postFileToMap(mHeaderMap, MethodUrl.UPLOAD_FILE, signMap, map, fileMap);
    }



}
