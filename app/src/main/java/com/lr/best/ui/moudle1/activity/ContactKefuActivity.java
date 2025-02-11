package com.lr.best.ui.moudle1.activity;

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
import com.lr.best.listener.SelectBackListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.utils.imageload.GlideUtils;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系客服  界面
 */
public class ContactKefuActivity extends BasicActivity implements RequestView, SelectBackListener {
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
    @BindView(R.id.code_iv)
    ImageView codeIv;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.shangwuTv)
    TextView shangwuTv;
    @BindView(R.id.ruanjianTv)
    TextView ruanjianTv;


    @Override
    public int getContentView() {
        return R.layout.activity_contact_kefu;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);


        mTitleText.setText("联系客服");
        mTitleText.setCompoundDrawables(null, null, null, null);

        //getContactUsAction();

    }

    private void getContactUsAction() {
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(ContactKefuActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.CONTACT_US, map);
    }


    @OnClick({R.id.back_img, R.id.left_back_lay, R.id.code_iv,R.id.shangwuTv,R.id.ruanjianTv})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.code_iv:
                getContactUsAction();
                break;
            case R.id.shangwuTv:
                intent = new Intent(ContactKefuActivity.this, ChatActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.ruanjianTv:
                intent = new Intent(ContactKefuActivity.this, ChatActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;


        }
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {
        switch (mType) {
            case MethodUrl.CHAT_RECORD:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        GlideUtils.loadImage(ContactKefuActivity.this, tData.get("data") + "", codeIv);
                        tvTitle.setText("扫一扫上面的二维码图案，加我微信");
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        codeIv.setImageResource(R.drawable.icon4_er2);
                        tvTitle.setText("二维码信息错误，请点击刷新");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        Intent intent = new Intent(ContactKefuActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;

                }

                break;
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        codeIv.setImageResource(R.drawable.icon4_er2);
        tvTitle.setText("二维码信息错误，请点击刷新");
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


    @Override
    public void onSelectBackListener(Map<String, Object> map, int type) {
        switch (type) {
            case 30:
                String str = (String) map.get("name");

                break;

        }
    }


}
