package com.lr.best.ui.moudle1.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.utils.imageload.GlideUtils;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.TextViewUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请  界面
 */
public class YaoQingActivity extends BasicActivity implements RequestView {
    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.tip_iv)
    TextView tipIv;
    @BindView(R.id.yqcode_tv)
    TextView yqcodeTv;
    @BindView(R.id.yqcode_copy_tv)
    TextView yqcodeCopyTv;
    @BindView(R.id.yqlink_tv)
    TextView yqlinkTv;
    @BindView(R.id.yqlink_copy_tv)
    TextView yqlinkCopyTv;
    @BindView(R.id.erweicode_iv)
    ImageView erweicodeIv;
    @BindView(R.id.top_iv)
    ImageView topIv;


    private String mRequestTag = "";
    private String mTempToken = "";
    private String mAuthCode = "";
    private String mSmsToken = "";


    private Map<String, Object> mShareMap;
    private String yqCode = "";
    private String link = "";
    private ClipboardManager mClipboardManager;
    private ClipData clipData;
    private String imgUrl = "";

    @Override
    public int getContentView() {
        return R.layout.activity_yaoqing;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);


        mTitleText.setText("邀请好友");
        mTitleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);
        mBackText.setText("");

        String content = tipIv.getText().toString().trim();
        TextViewUtils textViewUtils = new TextViewUtils();
        textViewUtils.init(content, tipIv);
        textViewUtils.setTextColor(content.indexOf("的") + 1, content.length(), ContextCompat.getColor(this, R.color.yellow1));
        textViewUtils.setTextSize(content.indexOf("的") + 1, content.length(), 2.0f);
        textViewUtils.build();

        getInvitAction();

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //长按保存
        erweicodeIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!UtilTools.empty(imgUrl)){
                    GlideUtils.downloadImage(YaoQingActivity.this,imgUrl);
                }
                return true;
            }
        });
    }

    private void getInvitAction() {
        mRequestTag = MethodUrl.INVIAT_ATION;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(YaoQingActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN,"").toString();
        }
        map.put("token",MbsConstans.ACCESS_TOKEN);

        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.INVIAT_ATION, map);
    }


    @OnClick({R.id.left_back_lay,R.id.yqcode_copy_tv, R.id.yqlink_copy_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.yqcode_copy_tv:
                clipData = ClipData.newPlainText("币友",yqCode);
                mClipboardManager.setPrimaryClip(clipData);
                showToastMsg("复制成功");
                break;
            case R.id.yqlink_copy_tv:
                clipData = ClipData.newPlainText("币友",link);
                mClipboardManager.setPrimaryClip(clipData);
                showToastMsg("复制成功");

                break;
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
            case MethodUrl.INVIAT_ATION:
                switch (tData.get("code")+""){
                    case "0": //请求成功
                        Map<String,Object> map= (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(map)){
                            yqCode = map.get("invitation_code")+"";
                            yqcodeTv.setText("邀请码 "+yqCode);
                            link = map.get("invitation_url")+"";
                            yqlinkTv.setText(link);
                            imgUrl = map.get("invitation_img")+"";
                            GlideUtils.loadImage(YaoQingActivity.this,imgUrl,erweicodeIv);
                            GlideUtils.loadImage(YaoQingActivity.this,map.get("invitation_show")+"",topIv);
                        }else {
                            showToastMsg("暂无相关数据");
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg")+"");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        Intent intent = new Intent(YaoQingActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;

                }



                break;
            case MethodUrl.shareUrl:
                mShareMap = tData;
                break;
            case MethodUrl.appVersion:


                break;
            case MethodUrl.REFRESH_TOKEN://获取refreshToken返回结果
                MbsConstans.REFRESH_TOKEN = tData.get("refresh_token") + "";
                mIsRefreshToken = false;
                switch (mRequestTag) {
                    case MethodUrl.shareUrl:
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


}
