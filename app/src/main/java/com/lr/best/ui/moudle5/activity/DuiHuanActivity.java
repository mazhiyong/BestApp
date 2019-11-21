package com.lr.best.ui.moudle5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.androidkun.xtablayout.XTabLayout;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.SelectBackListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.dialog.KindSelectDialog;
import com.lr.best.mywidget.dialog.TradePassDialog;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换
 */
public class DuiHuanActivity extends BasicActivity implements RequestView, TradePassDialog.PassFullListener, SelectBackListener {
    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    Map<String, Object> mapData = new HashMap<>();
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.from_tv)
    TextView fromTv;
    @BindView(R.id.from_lay)
    LinearLayout fromLay;
    @BindView(R.id.to_tv)
    TextView toTv;
    @BindView(R.id.change_iv)
    TextView changeIv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.type_lay)
    LinearLayout typeLay;
    @BindView(R.id.number_et)
    EditText numberEt;
    @BindView(R.id.type2_tv)
    TextView type2Tv;
    @BindView(R.id.selectall_tv)
    TextView selectallTv;
    @BindView(R.id.aviable_tv)
    TextView aviableTv;
    @BindView(R.id.huzhuan_tv)
    TextView huzhuanTv;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.to_lay)
    LinearLayout toLay;
    @BindView(R.id.jianglijin_tv)
    TextView jianglijinTv;
    @BindView(R.id.jifen_tv)
    TextView jifenTv;
    @BindView(R.id.tipTv)
    TextView tipTv;
    @BindView(R.id.duihuanAcountlay)
    LinearLayout duihuanAcountlay;
    @BindView(R.id.from_iv)
    ImageView fromIv;
    @BindView(R.id.to_iv)
    ImageView toIv;

    private KindSelectDialog mDialog;
    private KindSelectDialog mDialog2;

    private String fromStr;
    private String toStr;
    private String avaiableNumber;
    private String type = "1";


    private Map<String, Object> balanceMap;
    private List<Map<String, Object>> mDataList120;

    @Override
    public int getContentView() {
        return R.layout.activity_dui_huan;
    }


    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mapData = (Map<String, Object>) bundle.getSerializable("DATA");
        }
        divideLine.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(R.drawable.icon6_dingdan);

        getInfoAction();
        //initDialog();

        //禁止截屏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        tabLayout.addTab(tabLayout.newTab().setText("普通兑换"));
        tabLayout.addTab(tabLayout.newTab().setText("奖励金兑换"));
        tabLayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0://普通兑换
                        type = "1";
                        fromLay.setClickable(true);
                        toLay.setClickable(true);
                        tipTv.setText("兑换后系统即不退回，兑换后可释放一次1：3兑换平台币的奖励金");
                        duihuanAcountlay.setVisibility(View.INVISIBLE);
                        numberEt.setText("");
                        fromIv.setVisibility(View.VISIBLE);
                        toIv.setVisibility(View.VISIBLE);
                        break;
                    case 1://奖励金兑换
                        type = "2";
                        fromLay.setClickable(false);
                        toLay.setClickable(false);
                        fromTv.setText("Chip");
                        toTv.setText("Best");
                        tipTv.setText("温馨提示：\n" +
                                "任意币种兑换best实行1：3兑换\n" +
                                "兑换一次消耗1奖励金");
                        avaiableNumber = balanceMap.get("Chip") + "";
                        if (UtilTools.empty(avaiableNumber)) {
                            avaiableNumber = "0";
                        }
                        aviableTv.setText("可用 " + avaiableNumber + " Chip");
                        duihuanAcountlay.setVisibility(View.VISIBLE);
                        numberEt.setText("");
                        fromIv.setVisibility(View.GONE);
                        toIv.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });


        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0){
                    huzhuanTv.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getInfoAction() {
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(DuiHuanActivity.this, MbsConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.DUIHUAN_INFO, map);
    }


    @OnClick({R.id.back_img, R.id.right_lay, R.id.to_lay, R.id.from_lay, R.id.change_iv, R.id.type_lay, R.id.selectall_tv, R.id.huzhuan_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.right_lay: //兑换记录
                intent = new Intent(DuiHuanActivity.this, DuiHuanListActivity.class);
                startActivity(intent);
                break;
            case R.id.from_lay:
                if (!UtilTools.empty(mDialog2)) {
                    mDialog2.showAtLocation(Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.to_lay:
                if (!UtilTools.empty(mDialog)) {
                    mDialog.showAtLocation(Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.change_iv:
                fromStr = fromTv.getText().toString() + "";
                toStr = toTv.getText().toString() + "";
                fromTv.setText(toStr);
                toTv.setText(fromStr);
                break;
            case R.id.type_lay:
                break;
            case R.id.selectall_tv:
                numberEt.setText(avaiableNumber);
                break;
            case R.id.huzhuan_tv:
                huzhuanTv.setEnabled(false);
                huazhuanSumbitAction();
                break;
        }
    }

    private void huazhuanSumbitAction() {
        if (UtilTools.empty(numberEt.getText()) || toTv.getText().toString().equals("请选择")|| fromTv.getText().toString().equals("请选择")){
            showToastMsg("兑换信息不完善");
            huzhuanTv.setEnabled(true);
            return;
        }

        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(DuiHuanActivity.this, MbsConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        if (type.equals("1")){
            if (toTv.getText().toString().equals("Chip")){
                map.put("type", "0");
            }else {
                map.put("type", "1");
            }
        }else {
            map.put("type", "2");
        }
        map.put("from",fromTv.getText()+"");
        map.put("to", toTv.getText()+"");
        map.put("num", numberEt.getText()+"");


        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.DUIHUAN_ACTION, map);

    }

    private void getTypeListAction(String type) {
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(DuiHuanActivity.this, MbsConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("type", type);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.HUANZHUAN_BI_TYPE, map);
    }


    @Override
    public void onPassFullListener(String pass) {
        //mTradePassDialog.mPasswordEditText.setText(null);
        //mTradePass = pass;
        //mNextButton.setEnabled(false);
        //submitData();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {
        Intent intent;
        switch (mType) {
            case MethodUrl.DUIHUAN_INFO:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        if (!UtilTools.empty(tData.get("data") + "")) {
                            Map<String, Object> dataMap = (Map<String, Object>) tData.get("data");
                            mDataList120 = (List<Map<String, Object>>) dataMap.get("120");
                            if (!UtilTools.empty(mDataList120) && mDataList120.size() > 0) {
                                mDialog2 = new KindSelectDialog(this, true, mDataList120, 20);
                                mDialog2.setSelectBackListener(this);
                            }
                            List<Map<String, Object>> mDataList3 = (List<Map<String, Object>>) dataMap.get("platform");
                            if (!UtilTools.empty(mDataList3) && mDataList3.size() > 0) {
                                mDialog = new KindSelectDialog(this, true, mDataList3, 10);
                                mDialog.setSelectBackListener(this);
                            }
                            Map<String, Object> userMap = (Map<String, Object>) dataMap.get("user");
                            jianglijinTv.setText(userMap.get("bounty") + "");
                            jifenTv.setText(userMap.get("integral") + "");

                            balanceMap = (Map<String, Object>) dataMap.get("balance");
                        }

                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(DuiHuanActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                break;
            case MethodUrl.ACCOUNT_AVAIABLE_MONEY:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        if (!UtilTools.empty(tData.get("data") + "")) {
                            avaiableNumber = tData.get("data") + "";
                            aviableTv.setText("可用 " + UtilTools.getNormalMoney(avaiableNumber) + " " + typeTv.getText().toString());
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(DuiHuanActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                break;
            case MethodUrl.DUIHUAN_ACTION:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        showToastMsg(tData.get("msg") + "");
                        getInfoAction();
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        intent = new Intent(DuiHuanActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                huzhuanTv.setEnabled(true);
                break;
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        huzhuanTv.setEnabled(true);
        dealFailInfo(map, mType);
    }


    @Override
    public void onSelectBackListener(Map<String, Object> map, int type) {
        switch (type) {
            case 10: //to
                String s = (String) map.get("name"); //选择账户
                if (s.equals("Chip")) {  //120 ->  chip
                    toTv.setText(s);
                    fromTv.setText(mDataList120.get(0).get("name") + "");
                    type2Tv.setText(mDataList120.get(0).get("name") + "");
                    avaiableNumber = balanceMap.get(mDataList120.get(0).get("name") + "") + "";
                    if (UtilTools.empty(avaiableNumber)) {
                        avaiableNumber = "0";
                    }
                    aviableTv.setText("可用 " + avaiableNumber + " " + mDataList120.get(0).get("name"));
                } else {   //chip ->  best
                    toTv.setText("Best");
                    fromTv.setText("Chip");
                    type2Tv.setText("Chip");
                    avaiableNumber = balanceMap.get("Chip") + "";
                    if (UtilTools.empty(avaiableNumber)) {
                        avaiableNumber = "0";
                    }
                    aviableTv.setText("可用 " + avaiableNumber + " Chip");
                }
                huzhuanTv.setEnabled(true);
                break;
            case 20: //from
                //120 ->  chip
                String str = (String) map.get("name"); //选择账户
                fromTv.setText(str);
                toTv.setText("Chip");
                type2Tv.setText(str);
                avaiableNumber = balanceMap.get(str) + "";
                if (UtilTools.empty(avaiableNumber)) {
                    avaiableNumber = "0";
                }
                aviableTv.setText("可用 " + avaiableNumber + " " + str);
                huzhuanTv.setEnabled(true);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
