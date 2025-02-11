package com.lr.best.ui.moudle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.SelectBackListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.dialog.KindSelectDialog;
import com.lr.best.mywidget.dialog.SureOrNoDialog;
import com.lr.best.mywidget.dialog.UpdateDialog;
import com.lr.best.service.DownloadService;
import com.lr.best.ui.moudle1.activity.HelpDetialActivity;
import com.lr.best.utils.permission.PermissionsUtils;
import com.lr.best.utils.permission.RePermissionResultBack;
import com.lr.best.utils.share.ShareUtil;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.SelectDataUtil;
import com.lr.best.utils.tool.UtilTools;
import com.yanzhenjie.permission.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置 界面
 */
public class SettingActivity extends BasicActivity implements RequestView, SelectBackListener {
    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    @BindView(R.id.iv_welcome)
    ImageView mImageViewPhone;
    @BindView(R.id.iv_about_pihuibao)
    ImageView mImageViewAbout;
    @BindView(R.id.tv_phone_coutomer)
    TextView mTextView;
    @BindView(R.id.head_image)
    ImageView mHeadImage;
    @BindView(R.id.version_check_lay)
    LinearLayout mVersionCheckLay;
    @BindView(R.id.puhuibao_jieshao_lay)
    LinearLayout mJieShaoLay;
    @BindView(R.id.version_tv)
    TextView mVersionTv;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit)
    EditText mEdit;
    @BindView(R.id.shared_lay)
    LinearLayout mSharedLay;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.help_lay)
    LinearLayout helpLay;
    @BindView(R.id.exit_tv)
    TextView exitTv;
    @BindView(R.id.language_lay)
    LinearLayout languageLay;

    private String mRequestTag = "";
    private String mTempToken = "";
    private String mAuthCode = "";
    private String mSmsToken = "";


    private Map<String, Object> mShareMap;

    private KindSelectDialog mDialog;

    private SureOrNoDialog sureOrNoDialog;

    @Override
    public int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);


        mTitleText.setText(getResources().getString(R.string.setting));
        mTitleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);
        mBackText.setText("");

        mVersionTv.setText(MbsConstans.UpdateAppConstans.VERSION_APP_NAME);
        //getTempToken();

        List<Map<String, Object>> mDataList2 = SelectDataUtil.getSetType();
        mDialog = new KindSelectDialog(SettingActivity.this, true, mDataList2, 30);
        mDialog.setSelectBackListener(this);
        //getZiChanDataAction();


        //0 红跌绿涨   1红涨绿跌
        String colorType = SPUtils.get(SettingActivity.this, MbsConstans.SharedInfoConstans.COLOR_TYPE, "0").toString();
        if (colorType.equals("0")) {
            MbsConstans.COLOR_LOW = MbsConstans.COLOR_RED;
            MbsConstans.COLOR_TOP = MbsConstans.COLOR_GREEN;
            typeTv.setText("红跌绿涨");

        } else {
            MbsConstans.COLOR_LOW = MbsConstans.COLOR_GREEN;
            MbsConstans.COLOR_TOP = MbsConstans.COLOR_RED;

            typeTv.setText("红涨绿跌");
        }

    }


    @OnClick({R.id.back_img, R.id.version_check_lay, R.id.iv_about_pihuibao, R.id.welcome_lay, R.id.tv_phone_coutomer,
            R.id.left_back_lay, R.id.head_image, R.id.puhuibao_jieshao_lay, R.id.shared_lay, R.id.about_us_lay, R.id.help_lay, R.id.exit_tv,R.id.language_lay})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.language_lay:

                break;
            case R.id.puhuibao_jieshao_lay:
                mDialog.showAtLocation(Gravity.BOTTOM, 0, 0);
                break;
            case R.id.welcome_lay:
                //checkImageCode();
                break;
            case R.id.head_image:
                // getImageCode();
                break;
            case R.id.version_check_lay:
                //REGIST_ACTION();
                //getAppVersion();
                break;
            case R.id.tv_phone_coutomer:
                /*Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + mTextView.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                UtilTools.startTel(this, mTextView.getText().toString());
                break;

            case R.id.shared_lay: //分享
                if (mShareMap != null) {
                    String url = mShareMap.get("codeurl") + "";
                    LogUtilDebug.i("SettingActivity", mShareMap + "--------------------------           " + url);
                    ShareUtil.showShare(SettingActivity.this, "来自币友在线", "分享注册", url);
                } else {
                }
                break;
            case R.id.about_us_lay:
                intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.help_lay:
                intent = new Intent(SettingActivity.this, HelpDetialActivity.class);
                startActivity(intent);

                break;
            case R.id.exit_tv:
                sureOrNoDialog = new SureOrNoDialog(SettingActivity.this, true);
                sureOrNoDialog.initValue("提示", "确定要退出登录吗？");
                sureOrNoDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.cancel:
                                sureOrNoDialog.dismiss();
                                break;
                            case R.id.confirm:
                                /*ChatManagerHolder.gChatManager.disconnect(true);
                                SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                                sp.edit().clear().apply();*/

                                closeAllActivity();
                                MbsConstans.USER_MAP = null;
                                MbsConstans.RONGYUN_MAP = null;
                                MbsConstans.ACCESS_TOKEN = "";
                                SPUtils.put(SettingActivity.this, MbsConstans.SharedInfoConstans.LOGIN_OUT, true);
                                SPUtils.put(SettingActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "");
                                SPUtils.put(SettingActivity.this, MbsConstans.SharedInfoConstans.COLOR_TYPE, "0");
                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(intent);

                                break;
                        }
                    }
                });
                sureOrNoDialog.show();
                sureOrNoDialog.setCanceledOnTouchOutside(false);
                sureOrNoDialog.setCancelable(true);
                break;
        }
    }

    /**
     * 获取分享内容
     */
    public void getShareData() {
        mRequestTag = MethodUrl.shareUrl;
        Map<String, String> map = new HashMap<>();
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestGetToMap(mHeaderMap, MethodUrl.shareUrl, map);
    }


    /**
     * 获取app更新信息
     */
    public void getAppVersion() {
        mRequestTag = MethodUrl.appVersion;
        Map<String, String> map = new HashMap<>();
        map.put("appCode", MbsConstans.UPDATE_CODE);
        map.put("osType", "android");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestGetToMap(mHeaderMap, MethodUrl.appVersion, map);
    }


    private UpdateDialog mUpdateDialog;

    private void showUpdateDialog() {
        if (MbsConstans.UpdateAppConstans.VERSION_NET_CODE > MbsConstans.UpdateAppConstans.VERSION_APP_CODE) {
            mUpdateDialog = new UpdateDialog(this, true);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cancel:
                            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                                showToastMsg("本次升级为必须更新，请您更新！");
                            } else {
                                mUpdateDialog.dismiss();
                            }
                            break;
                        case R.id.confirm:
                            PermissionsUtils.requsetRunPermission(SettingActivity.this, new RePermissionResultBack() {
                                @Override
                                public void requestSuccess() {
                                    mUpdateDialog.getProgressLay().setVisibility(View.VISIBLE);
                                    DownloadService.downNewFile(MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL, 1003,
                                            MbsConstans.UpdateAppConstans.VERSION_NET_APK_NAME, MbsConstans.UpdateAppConstans.VERSION_MD5_CODE, SettingActivity.this);

                                }

                                @Override
                                public void requestFailer() {

                                }
                            }, Permission.Group.STORAGE);

                            if (!MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                                mUpdateDialog.dismiss();
                            }
                            break;
                    }
                }
            };
            mUpdateDialog.setCanceledOnTouchOutside(false);
            mUpdateDialog.setCancelable(false);
            String ss = "";
            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                ss = "必须更新";
            } else {
                ss = "建议更新";
            }
            mUpdateDialog.setOnClickListener(onClickListener);
            mUpdateDialog.initValue("检查新版本" + "(" + ss + ")", "更新内容:\n" + MbsConstans.UpdateAppConstans.VERSION_NET_UPDATE_MSG);
            mUpdateDialog.show();

            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                mUpdateDialog.setCancelable(false);
                mUpdateDialog.tv_cancel.setEnabled(false);
                mUpdateDialog.tv_cancel.setVisibility(View.GONE);
            } else {
                mUpdateDialog.setCancelable(true);
                mUpdateDialog.tv_cancel.setEnabled(true);
                mUpdateDialog.tv_cancel.setVisibility(View.VISIBLE);
            }
            mUpdateDialog.getProgressLay().setVisibility(View.GONE);
            DownloadService.mProgressBar = mUpdateDialog.getProgressBar();
            DownloadService.mTextView = mUpdateDialog.getPrgText();
        } else {
            showToastMsg("已经是最新版本");
        }
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
        switch (mType) {
            case MethodUrl.shareUrl:
                mShareMap = tData;
                break;
            case MethodUrl.appVersion:

                if (tData != null && !tData.isEmpty()) {
                    //网络版本号
                    MbsConstans.UpdateAppConstans.VERSION_NET_CODE = UtilTools.getIntFromStr(tData.get("verCode") + "");
                    //MbsConstans.UpdateAppConstans.VERSION_NET_CODE = 3;
                    //网络下载url
                    MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL = tData.get("downUrl") + "";
                    //MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                    //网络版本名称
                    MbsConstans.UpdateAppConstans.VERSION_NET_APK_NAME = tData.get("verName") + "";
                    //网络MD5值
                    MbsConstans.UpdateAppConstans.VERSION_MD5_CODE = tData.get("md5Code") + "";
                    //本次更新内容
                    MbsConstans.UpdateAppConstans.VERSION_NET_UPDATE_MSG = tData.get("verUpdateMsg") + "";

                    String mustUpdate = tData.get("isMust") + "";
                    if (mustUpdate.equals("0")) {
                        MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE = false;
                    } else {
                        MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE = true;
                    }
                    showUpdateDialog();
                } else {
                    showToastMsg("已经是最新版本");
                }
                break;
            case MethodUrl.REFRESH_TOKEN://获取refreshToken返回结果
                MbsConstans.REFRESH_TOKEN = tData.get("refresh_token") + "";
                mIsRefreshToken = false;
                switch (mRequestTag) {
                    case MethodUrl.shareUrl:
                        //getZiChanDataAction();
                        break;
                }
                break;
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        dealFailInfo(map, mType);
    }


    /**---------------------------------------------------------------------以下代码申请权限---------------------------------------------
     * Request permissions.
     */


    /**
     * activity销毁前触发的方法
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * activity恢复时触发的方法
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.divide_line)
    public void onViewClicked() {
    }


//    public class MyPlatformActionListener implements PlatformActionListener {
//        @Override
//        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//            SettingActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String name = platform.getName();
//                   /* if (name.equals(Wechat.NAME)){
//
//                    }else {
//                        Toast.makeText(MobSDK.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
//                    }*/
//                }
//            });
//        }
//
//        @Override
//        public void onError(Platform platform, int i, Throwable throwable) {
//            throwable.printStackTrace();
//            final String error = throwable.toString();
//            Log.d("show", "onError ---->  失败" + throwable.getStackTrace());
//            Log.d("show", "onError ---->  失败" + throwable.getMessage());
//            Log.d("show", "onError ---->  失败" + i);
//            Log.d("show", "onError ---->  失败" + platform.getName());
//            SettingActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (platform.getName().equals("Wechat")) {
//                        if (!platform.isClientValid()) {
//                            Toast.makeText(MobSDK.getContext(), "目前您的微信版本过低或未安装微信，需要安装微信才能使用", Toast.LENGTH_SHORT).show();
//                        } else if (throwable.getMessage().contains("-6")) {
//                            Toast.makeText(MobSDK.getContext(), "微信签名授权失败", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(MobSDK.getContext(), "微信分享失败，错误信息：" + error, Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(MobSDK.getContext(), "分享失败,错误信息：" + error, Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
//        }
//
//        @Override
//        public void onCancel(Platform platform, int i) {
//            SettingActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(MobSDK.getContext(), "分享取消", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    @Override
    public void onSelectBackListener(Map<String, Object> map, int type) {
        switch (type) {
            case 30:
                String str = (String) map.get("name");
                typeTv.setText(str);
                SPUtils.put(SettingActivity.this, MbsConstans.SharedInfoConstans.COLOR_TYPE, map.get("code") + "");

                if ((map.get("code") + "").equals("0")) {
                    MbsConstans.COLOR_LOW = MbsConstans.COLOR_RED;
                    MbsConstans.COLOR_TOP = MbsConstans.COLOR_GREEN;
                } else {
                    MbsConstans.COLOR_LOW = MbsConstans.COLOR_GREEN;
                    MbsConstans.COLOR_TOP = MbsConstans.COLOR_RED;
                }
                break;

        }
    }


}
