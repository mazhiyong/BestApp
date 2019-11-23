package com.lr.best.ui.moudle.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.utils.tool.JSONUtil;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 重置支付密码  界面
 */
public class ResetPayPassActivity extends BasicActivity implements RequestView {

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.right_img)
    ImageView mRightImg;
    @BindView(R.id.top_layout)
    LinearLayout mTitleBarView;
    @BindView(R.id.but_sure)
    Button mButNext;
    @BindView(R.id.new_pass_edit)
    EditText mNewPassEdit;
    @BindView(R.id.new_pass_again_edit)
    EditText mNewPassAgainEdit;
    @BindView(R.id.togglePwd1)
    ToggleButton mTogglePwd1;
    @BindView(R.id.togglePwd2)
    ToggleButton mTogglePwd2;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.old_lay)
    LinearLayout oldLay;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;



    private String mType = "";

    private String mRequestTag = "";

    private  String paycode = "";

    private TimeCount mTimeCount;

    @Override
    public int getContentView() {
        return R.layout.activity_reset_pay_pass;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);

        mTitleText.setText("设置支付密码");
        mTimeCount = new TimeCount(1 * 60 * 1000, 1000);

        mTitleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);

        if (UtilTools.empty(MbsConstans.USER_MAP)) {
            String s = SPUtils.get(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.LOGIN_INFO, "").toString();
            MbsConstans.USER_MAP = JSONUtil.getInstance().jsonMap(s);

        }
        etPhone.setText(MbsConstans.USER_MAP.get("account") + "");
        etPhone.setEnabled(false);


        if (!UtilTools.empty(MbsConstans.PAY_CODE)){
            paycode = MbsConstans.PAY_CODE;
        }else {
            paycode = SPUtils.get(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.PAY_CODE, "").toString();
        }

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0
                        && !UtilTools.empty(etPassword.getText()+"")
                        && !UtilTools.empty(etCode.getText()+"")
                        && !UtilTools.empty(etPasswordAgain.getText()+"")){
                    btSure.setEnabled(true);
                }else {
                    btSure.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0
                        && !UtilTools.empty(etPhone.getText()+"")
                        && !UtilTools.empty(etPassword.getText()+"")
                        && !UtilTools.empty(etPasswordAgain.getText()+"")){
                    btSure.setEnabled(true);
                }else {
                    btSure.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0
                        && !UtilTools.empty(etPhone.getText()+"")
                        && !UtilTools.empty(etCode.getText()+"")
                        && !UtilTools.empty(etPassword.getText()+"")){
                    btSure.setEnabled(true);
                }else {
                    btSure.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0
                        && !UtilTools.empty(etPhone.getText()+"")
                        && !UtilTools.empty(etCode.getText()+"")
                        && !UtilTools.empty(etPasswordAgain.getText()+"")){
                    btSure.setEnabled(true);
                }else {
                    btSure.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       /* if (UtilTools.empty(paycode)){ //设置支付密码
            mType = "0";
            oldLay.setVisibility(View.GONE);



        }else {
            mType = "1";
            oldLay.setVisibility(View.VISIBLE); //重置支付密码
            etOldPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length()>0 && !UtilTools.empty(etPasswordAgain.getText()+"") && !UtilTools.empty(etPassword.getText()+"")){
                        btSure.setEnabled(true);
                    }else {
                        btSure.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etPasswordAgain.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length()>0 && !UtilTools.empty(etPassword.getText()+"") && !UtilTools.empty(etOldPassword.getText()+"")){
                        btSure.setEnabled(true);
                    }else {
                        btSure.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            etPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length()>0 && !UtilTools.empty(etPasswordAgain.getText()+"") && !UtilTools.empty(etOldPassword.getText()+"")){
                        btSure.setEnabled(true);
                    }else {
                        btSure.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }*/


        mTogglePwd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示密码
                    mNewPassEdit.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    mNewPassEdit.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());
                }
            }
        });
        mTogglePwd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //显示密码
                    mNewPassAgainEdit.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    mNewPassAgainEdit.setTransformationMethod(
                            PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    /**
     * 获取用户信息
     */
    private void getUserInfoAction() {
        mRequestTag = MethodUrl.USER_INFO;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN,"").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.USER_INFO, map);
    }


    private void submitAction() {
        mRequestTag = MethodUrl.UPDATE_PAYCODE;

        String oldpassword =etOldPassword.getText().toString();
        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();
        /*if (mType.equals("1") && UtilTools.empty(oldpassword)) {
            showToastMsg("请输入原支付密码");
            return;
        }
*/
        if (UtilTools.empty(password)) {
            showToastMsg("请输入支付密码");
            return;
        }


        if (UtilTools.empty(passwordAgain)) {
            showToastMsg("请再次输入支付密码");
            return;
        }
        if (!password.equals(passwordAgain)) {
            showToastMsg("两次输入密码不一样，请重新输入");
            return;
        }

        /*int s = RegexUtil.isLetterDigit(password);
        switch (s) {
            case 0:
                break;
            case 1:
                showToastMsg(getResources().getString(R.string.must_has_shuzi));
                return;
            case 2:
                showToastMsg(getResources().getString(R.string.must_has_zimu));
                return;
            case 3:
                showToastMsg(getResources().getString(R.string.set_new_pass_tip));
                return;
        }*/

        // String pass = AESHelper.encrypt(password, AESHelper.password);
        //String pass = RSAUtils.encryptContent(password, RSAUtils.publicKey);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN,"").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        //map.put("old_password",oldpassword);
        map.put("code",etCode.getText()+"");
        map.put("payment_password",password);
        map.put("repayment_password",passwordAgain);
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.UPDATE_PAYCODE, map);

        paycode = password;
        btSure.setEnabled(false);

    }

    @OnClick({R.id.back_img, R.id.left_back_lay,R.id.tv_code, R.id.bt_sure})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.tv_code:
                if (UtilTools.empty(etPhone.getText().toString())) {
                    showToastMsg("手机号或邮箱地址不能为空");
                    return;
                }
                mTimeCount.start();
                getMsgCodeAction();
                break;
            case R.id.bt_sure:
                submitAction();
                break;

        }
    }
    private void getMsgCodeAction() {

        mRequestTag = MethodUrl.REGIST_SMSCODE;
        Map<String, Object> map = new HashMap<>();
        map.put("account",  etPhone.getText() + "");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.REGIST_SMSCODE, map);
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
            case MethodUrl.REGIST_SMSCODE:
                switch (tData.get("code")+""){
                    case "0":
                        showToastMsg("获取验证码成功");
                        etCode.setText(tData.get("data")+"");
                        break;

                    case "-1":
                        showToastMsg(tData.get("msg")+"");
                        break;

                    case "1":
                        closeAllActivity();
                        intent = new Intent(ResetPayPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                break;
            case MethodUrl.UPDATE_PAYCODE:
                switch (tData.get("code")+""){
                    case "0": //请求成功
                        showToastMsg("设置成功");

                        MbsConstans.PAY_CODE = paycode;
                        SPUtils.put(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.PAY_CODE, paycode);

                        finish();
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg")+"");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(ResetPayPassActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;

                }
                btSure.setEnabled(true);
                break;
            case MethodUrl.USER_INFO:
                switch (tData.get("code")+""){
                    case "0": //请求成功
                        MbsConstans.USER_MAP = (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(MbsConstans.USER_MAP)){
                            SPUtils.put(ResetPayPassActivity.this, MbsConstans.SharedInfoConstans.LOGIN_INFO, JSONUtil.getInstance().objectToJson(MbsConstans.USER_MAP));
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg")+"");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(ResetPayPassActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;

                }
                break;

        }

    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        btSure.setEnabled(true);
        dealFailInfo(map, mType);
    }

    /* 定义一个倒计时的内部类 */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tvCode.setText(getResources().getString(R.string.msg_code_again));
            tvCode.setClickable(true);
            MbsConstans.CURRENT_TIME = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tvCode.setClickable(false);
            tvCode.setText(millisUntilFinished / 1000 + "秒");
            MbsConstans.CURRENT_TIME = (int) (millisUntilFinished / 1000);
        }
    }


}
