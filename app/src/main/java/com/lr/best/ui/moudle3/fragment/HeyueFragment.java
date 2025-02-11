package com.lr.best.ui.moudle3.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.dialog.utils.CornerUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicFragment;
import com.lr.best.basic.BasicRecycleViewAdapter;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.OnMyItemClickListener;
import com.lr.best.listener.ReLoadingData;
import com.lr.best.listener.SelectBackListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.behavior.WeiboHeaderPagerBehavior;
import com.lr.best.mywidget.dialog.KindSelectDialog;
import com.lr.best.mywidget.view.LoadingWindow;
import com.lr.best.mywidget.view.PageView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.ui.moudle2.adapter.MyViewPagerAdapter;
import com.lr.best.ui.moudle3.activity.CoinInfoDetailActivity;
import com.lr.best.ui.moudle3.adapter.BuyAdapter;
import com.lr.best.ui.moudle3.adapter.SellAdapter;
import com.lr.best.ui.moudle3.adapter.ShouMoneyListAdapter;
import com.lr.best.ui.moudle3.adapter.TypeSelectAdapter;
import com.lr.best.utils.tool.AnimUtil;
import com.lr.best.utils.tool.JSONUtil;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.SelectDataUtil;
import com.lr.best.utils.tool.UtilTools;
import com.xw.repo.BubbleSeekBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class HeyueFragment extends BasicFragment implements RequestView, ReLoadingData, SelectBackListener,WeiboHeaderPagerBehavior.OnPagerStateListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    /*@BindView(R.id.top_layout_toolbar)
    ConstraintLayout mTitleBarView;*/
    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.back_text)
    TextView mBackText;
    @BindView(R.id.left_back_lay)
    LinearLayout mLeftBackLay;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.right_text_tv)
    TextView rightTextTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.rbOpen)
    RadioButton rbOpen;
    @BindView(R.id.rbClose)
    RadioButton rbClose;
    @BindView(R.id.rgOpenClose)
    RadioGroup rgOpenClose;
    @BindView(R.id.tvLimitPrice)
    TextView tvLimitPrice;
    @BindView(R.id.tvLessChoose)
    TextView tvLessChoose;
    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.ivLess)
    ImageView ivLess;
    @BindView(R.id.view18)
    View view18;
    @BindView(R.id.ivPlus)
    ImageView ivPlus;
    @BindView(R.id.clNumber)
    LinearLayout clNumber;
    @BindView(R.id.tvMarketPrice)
    TextView tvMarketPrice;
    @BindView(R.id.tvCnyPrice)
    TextView tvCnyPrice;
    @BindView(R.id.etHand)
    EditText etHand;
    @BindView(R.id.textView99)
    TextView textView99;
    @BindView(R.id.clHand)
    LinearLayout clHand;
    @BindView(R.id.tvCloseMore)
    TextView tvCloseMore;
    @BindView(R.id.llAvailableCloseMore)
    LinearLayout llAvailableCloseMore;
    @BindView(R.id.tvCloseEmpty)
    TextView tvCloseEmpty;
    @BindView(R.id.llAvailableCloseEmpty)
    LinearLayout llAvailableCloseEmpty;
    @BindView(R.id.textView100)
    TextView textView100;
    @BindView(R.id.etStopProfit)
    EditText etStopProfit;
    @BindView(R.id.llProfit)
    LinearLayout llProfit;
    @BindView(R.id.textView101)
    TextView textView101;
    @BindView(R.id.etStopLess)
    EditText etStopLess;
    @BindView(R.id.llLoss)
    LinearLayout llLoss;
    @BindView(R.id.tvMoreOperate)
    TextView tvMoreOperate;
    @BindView(R.id.tvEmptyOperate)
    TextView tvEmptyOperate;
    @BindView(R.id.constraintLayout7)
    LinearLayout constraintLayout7;
    @BindView(R.id.textView59)
    TextView textView59;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.rvSell)
    RecyclerView rvSell;
    @BindView(R.id.tvCurrentPrice)
    TextView tvCurrentPrice;
    @BindView(R.id.tvCurrentCny)
    TextView tvCurrentCny;
    @BindView(R.id.rvBuy)
    RecyclerView rvBuy;
    @BindView(R.id.ivError)
    ImageView ivError;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.ivEmptyContent)
    ImageView ivEmptyContent;
    @BindView(R.id.tvEmptyContent)
    TextView tvEmptyContent;
    @BindView(R.id.tv_loading_content)
    TextView tvLoadingContent;
    @BindView(R.id.rb_number1)
    RadioButton rbNumber1;
    @BindView(R.id.rb_number2)
    RadioButton rbNumber2;
    @BindView(R.id.rb_number3)
    RadioButton rbNumber3;
    @BindView(R.id.rb_number4)
    RadioButton rbNumber4;
    @BindView(R.id.seekBar)
    RadioGroup seekBar;
    @BindView(R.id.tvShouyi)
    TextView tvShouyi;
    @BindView(R.id.buy_more_tv)
    TextView buyMoreTv;
    @BindView(R.id.tlTradeList)
    TabLayout tlTradeList;
    //@BindView(R.id.tlTradeList2)
    //TabLayout tlTradeList2;
    @BindView(R.id.refresh_list_view)
    LRecyclerView mRefreshListView;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.page_view)
    PageView mPageView;
    /* @BindView(R.id.nestScrollView)
     NestedScrollView nestScrollView;*/
    @BindView(R.id.iv_toCoinInfo)
    ImageView ivToCoinInfo;
    @BindView(R.id.lay)
    LinearLayout lay;


    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_cny_number)
    TextView tvCnyNumber;
    @BindView(R.id.lay22)
    LinearLayout lay22;
    @BindView(R.id.bundle_seekBar)
    BubbleSeekBar bundleSeekBar;
    @BindView(R.id.zhiying_et)
    EditText etZhiYing;
    @BindView(R.id.zhisun_et)
    EditText etZhiSun;
    @BindView(R.id.xianjia_lay)
    LinearLayout xianjiaLay;
    @BindView(R.id.etchufa)
    EditText etchufa;
    @BindView(R.id.et_jihua_price)
    EditText etJihuaPrice;
    @BindView(R.id.jihua_lay)
    LinearLayout jihuaLay;
    @BindView(R.id.lay_chufan)
    LinearLayout layChufan;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.coordintaor_lay)
    CoordinatorLayout coordintaorLay;


    private LoadingWindow mLoadingWindow;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private ShouMoneyListAdapter shouMoneyListAdapter;
    private SellAdapter sellAdapter;
    private BuyAdapter buyAdapter;
    private TypeSelectAdapter mAdapter;

    private String mRequestTag = "";
    private LinearLayoutManager manager;


    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private List<List<String>> mBuyDataList = new ArrayList<>();
    private List<List<String>> mSellDataList = new ArrayList<>();
    private List<Map<String, Object>> mDatas = new ArrayList<>();
    private List<Map<String, Object>> mLeverData = new ArrayList<>();
    private List<Map<String, Object>> mLeverDataChild = new ArrayList<>();


    private String mSelectType = "0"; //0 限价  1 计划委托

    private AnimUtil mAnimUtil;
    private int mPage = 1;
    private int maxPage = 1;

    private int precision = 2;
    private String precisionStr = "0.01";
    private String symbol = "BTC";
    private String kind = "0";
    private int type = 0;

    private String BTC_Account = "0";
    private String USDT_Account = "0";


    private float bilv;


    private KindSelectDialog mDialog;
    private KindSelectDialog mDialog2;
    private String cnyRatio = "1";

    private final int QUEST_CODE = 120;

    private Handler handler = new Handler();

    private List<Fragment> mFragments = new ArrayList<>();
    private ChiCangFragment chiCangFragment;
    private WeituoFragment weituoFragment;
    private ChengJiaoFragment chengJiaoFragment;

    private WeiboHeaderPagerBehavior mHeaderPagerBehavior;

    //HTTP请求  轮询
    private Runnable cnyRunnable = new Runnable() {
        @Override
        public void run() {
            //获取合约价格以及深度信息
            getPairDepthAction();
            handler.postDelayed(this, MbsConstans.SECOND_TIME_5000);
        }
    };

    public HeyueFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_heyue;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void init() {
 /*       ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen.title_item_height) + UtilTools.getStatusHeight2(getActivity()));
        mTitleBarView.setLayoutParams(layoutParams);
        mTitleBarView.setPadding(0, UtilTools.getStatusHeight2(getActivity()), 0, 0);*/


        mAnimUtil = new AnimUtil();
        initView();

        mTitleText.setVisibility(View.GONE);
        mBackText.setText("BTC永续合约");
        mBackImg.setImageResource(R.drawable.icon_xuanze);


        mLoadingWindow = new LoadingWindow(getActivity(), R.style.Dialog);
        mLoadingWindow.showView();
        //setBarTextColor();
        //setAvatorChange();

        rgOpenClose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbOpen:
                        type = 0;
                        buyMoreTv.setText("买入开多");
                        bundleSeekBar.setProgress(0);
                        etHand.setText("");
                        buyMoreTv.setBackgroundResource(R.drawable.btn_next_green);
                        rbNumber1.setBackgroundResource(R.drawable.selector_open_close_house2);
                        rbNumber2.setBackgroundResource(R.drawable.selector_open_close_house2);
                        rbNumber3.setBackgroundResource(R.drawable.selector_open_close_house2);
                        rbNumber4.setBackgroundResource(R.drawable.selector_open_close_house2);

                        updateLever(type);
                        break;

                    case R.id.rbClose:
                        type = 1;
                        buyMoreTv.setText("卖出开空");
                        bundleSeekBar.setProgress(0);
                        etHand.setText("");
                        buyMoreTv.setBackgroundResource(R.drawable.btn_next_red);
                        rbNumber1.setBackgroundResource(R.drawable.selector_open_close_house3);
                        rbNumber2.setBackgroundResource(R.drawable.selector_open_close_house3);
                        rbNumber3.setBackgroundResource(R.drawable.selector_open_close_house3);
                        rbNumber4.setBackgroundResource(R.drawable.selector_open_close_house3);

                        updateLever(type);
                        break;
                }
            }

        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()> 0){
                    tvCnyPrice.setText("≈" + bilv * Float.parseFloat(s.toString()) + "CNY");
                    float maxNumber = 0;
                    int number = 0;
                    if (rbNumber1.isChecked()){
                        if (mSelectType.equals("0")) { //限价
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                            LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.10f);
                            etHand.setText(number + "");
                        }

                    }

                    if (rbNumber2.isChecked()){
                        if (mSelectType.equals("0")) { //限价
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                            LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.20f);
                            etHand.setText(number + "");
                        }
                    }

                    if (rbNumber3.isChecked()){
                        if (mSelectType.equals("0")) { //限价
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                            LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.50f);
                            etHand.setText(number + "");
                        }
                    }

                    if (rbNumber4.isChecked()){
                        if (mSelectType.equals("0")) { //限价
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                            LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 1);
                            etHand.setText(number + "");
                        }
                    }
                }else {
                    tvCnyPrice.setText("≈0 CNY");
                    etHand.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etJihuaPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0){
                    float maxNumber = 0;
                    int number = 0;
                    if (rbNumber1.isChecked()){
                        if (mSelectType.equals("1")) { //计划
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                            LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.10f);
                            etHand.setText(number + "");
                        }
                    }

                    if (rbNumber2.isChecked()){
                        if (mSelectType.equals("1")) { //计划
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                            LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.20f);
                            etHand.setText(number + "");
                        }
                    }

                    if (rbNumber3.isChecked()){
                        if (mSelectType.equals("1")) { //计划
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                            LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 0.50f);
                            etHand.setText(number + "");
                        }
                    }

                    if (rbNumber4.isChecked()){
                        if (mSelectType.equals("1")) { //计划
                            maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                            LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                            number = (int) (maxNumber * 1);
                            etHand.setText(number + "");
                        }
                    }
                }else {
                    etHand.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                float maxNumber = 0;
                int number = 0;
                switch (checkedId){
                    case R.id.rb_number1: //10
                        switch (type) {
                            case 0: //开多买入
                                if (mSelectType.equals("0")) { //限价
                                    if (UtilTools.empty(etPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                                    LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.10f);
                                    etHand.setText(number + "");
                                } else {
                                    if (UtilTools.empty(etJihuaPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                                    LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.10f);
                                    etHand.setText(number + "");
                                }

                                break;

                            case 1: //开出卖空   可用BTC 乘以 百分比率
                                number = (int) (Float.parseFloat(BTC_Account) * 0.10f);
                                etHand.setText(number + "");
                                break;
                        }
                        break;
                    case R.id.rb_number2: //20
                        switch (type) {
                            case 0: //开多买入
                                if (mSelectType.equals("0")) { //限价
                                    if (UtilTools.empty(etPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                                    LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.20f);
                                    etHand.setText(number + "");
                                } else {
                                    if (UtilTools.empty(etJihuaPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                                    LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.20f);
                                    etHand.setText(number + "");
                                }

                                break;

                            case 1: //开出卖空   可用BTC 乘以 百分比率
                                number = (int) (Float.parseFloat(BTC_Account) * 0.20f);
                                etHand.setText(number + "");
                                break;
                        }
                        break;

                    case R.id.rb_number3: //50
                        switch (type) {
                            case 0: //开多买入
                                if (mSelectType.equals("0")) { //限价
                                    if (UtilTools.empty(etPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                                    LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.50f);
                                    etHand.setText(number + "");
                                } else {
                                    if ( UtilTools.empty(etJihuaPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                                    LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 0.50f);
                                    etHand.setText(number + "");
                                }

                                break;

                            case 1: //开出卖空   可用BTC 乘以 百分比率
                                number = (int) (Float.parseFloat(BTC_Account) * 0.50f);
                                etHand.setText(number + "");
                                break;
                        }
                        break;

                    case R.id.rb_number4: //100
                        switch (type) {
                            case 0: //开多买入
                                if (mSelectType.equals("0")) { //限价
                                    if (UtilTools.empty(etPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                                    LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 1);
                                    etHand.setText(number + "");
                                } else {
                                    if (UtilTools.empty(etJihuaPrice.getText().toString())) {
                                        showToastMsg("请输入价格");
                                        etHand.setText("");
                                        return;
                                    }
                                    maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                                    LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                                    number = (int) (maxNumber * 1);
                                    etHand.setText(number + "");
                                }

                                break;

                            case 1: //开出卖空   可用BTC 乘以 百分比率
                                number = (int) (Float.parseFloat(BTC_Account) * 1);
                                etHand.setText(number + "");
                                break;
                        }
                        break;
                }
            }
        });




        bundleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (UtilTools.empty(etPrice.getText().toString()) && UtilTools.empty(etJihuaPrice.getText().toString())) {
                    showToastMsg("请输入价格");
                    return;
                }
                if (progress > 0) {
                    //etHand.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                    float maxNumber = 0;
                    int number = 0;
                    switch (type) {
                        case 0: //开多买入
                            if (mSelectType.equals("0")) { //限价
                                maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                                LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                                number = (int) (maxNumber * progress / 100);
                                etHand.setText(number + "");
                            } else {
                                maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                                LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                                number = (int) (maxNumber * progress / 100);
                                etHand.setText(number + "");
                            }

                            break;

                        case 1: //开出卖空   可用BTC 乘以 百分比率
                            number = (int) (Float.parseFloat(BTC_Account) * progress / 100);
                            etHand.setText(number + "");
                            break;
                    }

                   /* if ( number > 0 ){
                        etHand.setEnabled(false);
                    }else {
                        etHand.setEnabled(true);
                    }*/
                } else {
                    /*etHand.setHint(R.string.number);
                    etHand.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.colorGray));*/
                    etHand.setText("");
                }
            }
        });

        List<Map<String, Object>> mDataList2 = SelectDataUtil.getPriceType();
        mDialog = new KindSelectDialog(getActivity(), true, mDataList2, 10);
        mDialog.setSelectBackListener(this);


        //获取保证金
        getAvaiableMoneyAction();

        //获取合约价格以及深度信息
        //getPairDepthAction();
        //handler.post(cnyRunnable);

        //获取杠杆信息
        getContractLeverAction();

        //获取交易区信息
        getAreaAction();

        //获取用户合约币、USDT资产
        getCoinAccountAction();


       /* List<Map<String, Object>> maps = SelectDataUtil.getTabValues2();
        for (Map<String, Object> map : maps) {
            tlTradeList.addTab(tlTradeList.newTab().setText(map.get("name") + ""));
            //tlTradeList2.addTab(tlTradeList2.newTab().setText(map.get("name") + ""));
        }*/

        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //折叠时顶部
                if (verticalOffset == -appBarLayout.getTotalScrollRange()) {
                    //mPageView.setEnabled(true);
                    //mPageView.setNestedScrollingEnabled(false);
                    //coordintaorLay.setNestedScrollingEnabled(false);
                    //LogUtilDebug.i("show","&&&&&&&&&&&&Nestscroll:"+coordintaorLay.onStartNestedScroll());
                }else {
                    LogUtilDebug.i("show","************Nestscroll:"+coordintaorLay.isNestedScrollingEnabled());
                }
            }
        });


       /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppbarLayout.getLayoutParams();
        mHeaderPagerBehavior = (WeiboHeaderPagerBehavior) params.getBehavior();
        if (mHeaderPagerBehavior != null){
            mHeaderPagerBehavior.setPagerStateListener(this);
            LogUtilDebug.i("show","mAppbarLayoutHeight:"+mAppbarLayout.getHeight());
        }else {
            LogUtilDebug.i("show","mheadpagerBehavior is null");
        }*/

       /* AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        assert behavior != null;
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return true;
            }
        });*/


        tlTradeList.addTab(tlTradeList.newTab().setText("持仓"));
        tlTradeList.addTab(tlTradeList.newTab().setText("委托"));
        tlTradeList.addTab(tlTradeList.newTab().setText("成交"));

        chiCangFragment = new ChiCangFragment();
        weituoFragment = new WeituoFragment();
        chengJiaoFragment = new ChengJiaoFragment();

        mFragments.add(chiCangFragment);
        mFragments.add(weituoFragment);
        mFragments.add(chengJiaoFragment);

        mViewpager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), mFragments));

        mViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlTradeList));

        tlTradeList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


     /*   tlTradeList2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        kind = "0";
                        getChicangListAction();
                        break;
                    case 1:
                        kind = "1";
                        getWeituoListAction();
                        break;
                    case 2:
                        kind = "2";
                        getChengjiaoListAction();
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
*/

        /*nestScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
              *//*  if (scrollY > tlTradeList.getTop() || scrollY == tlTradeList.getTop()) {

                } else {
                    if (oldScrollY > scrollY) {
                        LogUtilDebug.i("show", "下滑");
                        Objects.requireNonNull(tlTradeList.getTabAt(tlTradeList2.getSelectedTabPosition())).select();
                    } else {
                        LogUtilDebug.i("show", "上滑");
                    }
                }*//*

                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    //滑动到底部
                    LogUtilDebug.i("show","滑动到底部");
                    LogUtilDebug.i("show","maxPage:"+maxPage);
                    LogUtilDebug.i("show","currentgage:"+mPage);
                    if (mPage < maxPage){
                        mPage = mPage + 1;
                        switch (mRequestTag){
                            case MethodUrl.CHICANG_LIST:
                                getChicangListAction();
                                LogUtilDebug.i("show","持仓加载更多");
                                break;
                            case MethodUrl.WEITUO_LIST:
                                getWeituoListAction();
                                LogUtilDebug.i("show","委托加载更多");
                                break;
                            case MethodUrl.CHENGJIAO_LIST:
                                LogUtilDebug.i("show","成交加载更多");
                                getChengjiaoListAction();
                                break;
                        }
                    }

                }



            }
        });*/

      /*  etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (input.length() > 0) {
                    tvCnyPrice.setText("≈" + bilv * Float.parseFloat(input) + "CNY");

                    if (mSelectType.equals("0")) { //限价
                        float maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                        LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                        int number = (int) (maxNumber * bundleSeekBar.getProgress() / 100);
                        etHand.setText(number + "");
                    } else {
                        float maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                        LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                        int number = (int) (maxNumber * bundleSeekBar.getProgress() / 100);
                        etHand.setText(number + "");
                    }

                } else {
                    tvCnyPrice.setText("≈0 CNY");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 4) {
                    s.delete(posDot + 5, posDot + 6);
                }
            }
        });
*/
       /* etJihuaPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String input = s.toString();
                if (input.length() > 0) {
                    if (mSelectType.equals("0")) { //限价
                        float maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etPrice.getText().toString());
                        LogUtilDebug.i("show", "限价maxNumber:" + maxNumber);
                        int number = (int) (maxNumber * bundleSeekBar.getProgress() / 100);
                        etHand.setText(number + "");
                    } else {
                        float maxNumber = Float.parseFloat(USDT_Account) / Float.parseFloat(etJihuaPrice.getText().toString());
                        LogUtilDebug.i("show", "计划委托maxNumber:" + maxNumber);
                        int number = (int) (maxNumber * bundleSeekBar.getProgress() / 100);
                        etHand.setText(number + "");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


    }

    private void updateLever(int i) {
        switch (i) {
            case 0:
                if (mLeverData != null && mLeverData.size() > 0) {
                    mLeverDataChild.clear();
                    for (Map<String, Object> map : mLeverData) {
                        if ((map.get("type") + "").equals("0")) {
                            mLeverDataChild.add(map);

                        }
                    }
                    mDialog2 = new KindSelectDialog(getActivity(), true, mLeverDataChild, 20);
                    mDialog2.setSelectBackListener(this::onSelectBackListener);
                }
                break;
            case 1:
                if (mLeverData != null && mLeverData.size() > 0) {
                    mLeverDataChild.clear();
                    for (Map<String, Object> map : mLeverData) {
                        if ((map.get("type") + "").equals("1")) {
                            mLeverDataChild.add(map);

                        }
                    }
                    mDialog2 = new KindSelectDialog(getActivity(), true, mLeverDataChild, 20);
                    mDialog2.setSelectBackListener(this::onSelectBackListener);
                }
                break;
        }
    }


    @OnClick({R.id.left_back_lay, R.id.iv_toCoinInfo, R.id.rb_number1, R.id.rb_number2, R.id.rb_number3, R.id.rb_number4, R.id.seekBar, R.id.buy_more_tv,
            R.id.tvLimitPrice, R.id.tvLessChoose, R.id.ivPlus, R.id.ivLess})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.left_back_lay:
                showDialog();
                break;
            case R.id.iv_toCoinInfo:
                Intent intent1 = new Intent(getActivity(), CoinInfoDetailActivity.class);
                intent1.putExtra("symbol", symbol);
                intent1.putExtra("area", "USDT");
                intent1.putExtra("from", "2");
                startActivityForResult(intent1, QUEST_CODE);
                break;
            case R.id.rb_number1:
                break;
            case R.id.rb_number2:
                break;
            case R.id.rb_number3:
                break;
            case R.id.rb_number4:
                break;
            case R.id.seekBar:
                break;
            case R.id.buy_more_tv:
                buyAndSellAction();
                break;

            case R.id.tvLimitPrice:
                mDialog.showAtLocation(Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tvLessChoose:
                if (mDialog2 != null) {
                    mDialog2.showAtLocation(Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.ivPlus:
                // 加价格
                BigDecimal bigDecima1;
                BigDecimal bigDecima2 = new BigDecimal(precisionStr);
                if (!UtilTools.empty(etPrice.getText() + "")) {
                    bigDecima1 = new BigDecimal(etPrice.getText() + "");
                } else {
                    bigDecima1 = new BigDecimal("0");
                }
                etPrice.setText(bigDecima1.add(bigDecima2).toString());
                break;

            case R.id.ivLess:
                try {
                    // 减价格
                    if (!UtilTools.empty(etPrice.getText() + "")) {
                        BigDecimal bigDecimal1 = new BigDecimal(etPrice.getText() + "");
                        BigDecimal bigDecimal2 = new BigDecimal(precisionStr);
                        if (bigDecimal1.doubleValue() > 0) {
                            etPrice.setText(bigDecimal1.subtract(bigDecimal2).toString());
                        } else {
                            etPrice.setText(0);
                        }
//                        double priceDouble = Double.parseDouble(textPrice);
//                        if (priceDouble > 0) {
//                            etPrice.setText(UiTools.formatNumber(priceDouble - Double.parseDouble(precision), pattern));
//                        } else {
//                            etPrice.setText(UiTools.formatNumber(0, pattern));
//                        }
                    } else {
                        etPrice.setHint("请输入价格");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }


    private void initView() {
        rvSell.setHasFixedSize(true);
        rvSell.setNestedScrollingEnabled(false);
        sellAdapter = new SellAdapter(getActivity());
        rvSell.setAdapter(sellAdapter);
        sellAdapter.setOnItemClickListener(new BasicRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (sellAdapter.getBuySell().size() - 1 < position) {
                    etPrice.setText("0");
                } else {
                    List<String> strings = sellAdapter.getBuySell().get(position);
                    etPrice.setText(strings.get(0));
                }
            }
        });


        rvBuy.setHasFixedSize(true);
        rvBuy.setNestedScrollingEnabled(false);
        buyAdapter = new BuyAdapter(getActivity());
        rvBuy.setAdapter(buyAdapter);

        buyAdapter.setOnItemClickListener(new BasicRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (buyAdapter.getBuySell().size() - 1 < position) {
                    etPrice.setText("0");
                } else {
                    List<String> strings = buyAdapter.getBuySell().get(position);
                    etPrice.setText(strings.get(0));
                }
            }
        });

        buyAdapter.setBuyTradeInfo(mBuyDataList, precision);

        mPageView.setContentView(mContent);
        mPageView.setReLoadingData(this);
        mPageView.showLoading();
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        mRefreshListView.setLayoutManager(manager);
        mRefreshListView.setNestedScrollingEnabled(false);

    }


    /**
     * 渐变toolbar背景
     */
    private void setAvatorChange() {
        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());

                mToolbar.setBackgroundColor(changeAlpha(Color.WHITE, percent));
            }
        });
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }


    public void setBarTextColor() {
        StatusBarUtil.setLightMode(Objects.requireNonNull(getActivity()));
    }

    private void getAvaiableMoneyAction() {
        //mRequestTag = MethodUrl.AVIABLE_MONEY;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(Objects.requireNonNull(getActivity()), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("symbol", symbol);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.AVIABLE_MONEY, map);
    }


    private void getPairDepthAction() {
        //mRequestTag = MethodUrl.PAIR_DEPTH;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("symbol", symbol);
        map.put("depth", "1");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.PAIR_DEPTH, map);
    }


    private void getContractLeverAction() {
        //mRequestTag = MethodUrl.CONTRACT_LEVER;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        //map.put("token",MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.CONTRACT_LEVER, map);
    }


    private void getAreaAction() {
        //mRequestTag = MethodUrl.AREA_ALL;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        //map.put("token",MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.AREA_ALL, map);
    }


    private void getCoinAccountAction() {
        //mRequestTag = MethodUrl.COIN_ACCOUNT;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("symbol", symbol);
        map.put("area", "USDT");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.COIN_ACCOUNT, map);
    }


    private void buyAndSellAction() {
        //mRequestTag = MethodUrl.CONTRACT_TRADE;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("symbol", symbol);
        map.put("mode", type);
        map.put("entrustType", mSelectType);
        switch (mSelectType) {
            case "0"://限价
                map.put("price", etPrice.getText() + "");
                break;
            case "1"://计划委托
                map.put("price", etJihuaPrice.getText() + "");
                map.put("trigger", etchufa.getText() + "");
                break;
            case "2"://市价单
                map.put("price", etJihuaPrice.getText() + "");
                break;
        }
        map.put("number", etHand.getText() + "");
        map.put("stop_profit", etZhiYing.getText() + "");
        map.put("loss_limit", etZhiSun.getText() + "");
        map.put("ratio", cnyRatio);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.CONTRACT_TRADE, map);
    }


    @Override
    public void showProgress() {
        //mLoadingWindow.show();
    }

    @Override
    public void disimissProgress() {
        //mLoadingWindow.cancleView();
    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {
        mLoadingWindow.cancleView();
        Intent intent;
        switch (mType) {
            case MethodUrl.AVIABLE_MONEY:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        Map<String, Object> map = (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(map)) {
                            //可用
                            tvCloseMore.setText(UtilTools.getNormalMoney(map.get("balance") + ""));
                            //保证金
                            tvCloseEmpty.setText(UtilTools.getNormalMoney(map.get("bond") + ""));
                            //收益
                            tvShouyi.setText(UtilTools.getNormalMoney(map.get("profit") + ""));
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }

                break;


            case MethodUrl.PAIR_DEPTH:
                switch ((tData.get("code") + "")) {
                    case "0":
                        Map<String, Object> mapData = (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(mapData)) {
                            Map<String, Objects> pairMap = (Map<String, Objects>) mapData.get("pair");
                            Map<String, Objects> depthMap = (Map<String, Objects>) mapData.get("depth");
                            if (!UtilTools.empty(pairMap)) {
                                String pri = pairMap.get("price") + "";
                                tvPrice.setText(UtilTools.getNormalMoney(pri));
                                String cny = pairMap.get("cny_number") + "";
                                tvCnyNumber.setText("≈" + UtilTools.getNormalMoney(cny) + "CNY");
                                bilv = Float.parseFloat(cny) / Float.parseFloat(pri);
                            }

                            if (!UtilTools.empty(depthMap)) {
                                String strSell = depthMap.get("sell") + "";
                                String strBuy = depthMap.get("buy") + "";
                                mSellDataList = JSONUtil.getInstance().jsonToListStr2(strSell);
                                mBuyDataList = JSONUtil.getInstance().jsonToListStr2(strBuy);

                                if (mSellDataList != null && mSellDataList.size() > 0) {
                                    sellAdapter.setSellTradeInfos(mSellDataList, precision);

                                }

                                if (mBuyDataList != null && mBuyDataList.size() > 0) {
                                    buyAdapter.setBuyTradeInfo(mBuyDataList, precision);

                                }


                            }

                        }


                        break;
                    case "1":
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");
                        break;
                }
                break;
            case MethodUrl.CONTRACT_LEVER:
                switch ((tData.get("code") + "")) {
                    case "0":
                        mLeverData = (List<Map<String, Object>>) tData.get("data");
                        if (mLeverData != null && mLeverData.size() > 0) {
                            mLeverDataChild.clear();
                            for (Map<String, Object> map : mLeverData) {
                                if ((map.get("type") + "").equals("0")) { //0 开多 1//卖出
                                    mLeverDataChild.add(map);
                                }
                            }
                            mDialog2 = new KindSelectDialog(getActivity(), true, mLeverDataChild, 20);
                            mDialog2.setSelectBackListener(this);
                        }
                        break;
                    case "1":
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");
                        break;
                }
                break;

            case MethodUrl.AREA_ALL:
                switch ((tData.get("code") + "")) {
                    case "0":
                        mDatas = (List<Map<String, Object>>) tData.get("data");
                        break;
                    case "1":
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");

                        break;
                }
                break;

            case MethodUrl.COIN_ACCOUNT:
                switch ((tData.get("code") + "")) {
                    case "0":
                        Map<String, Object> mapData = (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(mapData)) {
                            BTC_Account = mapData.get("symbol") + "";
                            USDT_Account = mapData.get("area") + "";
                            LogUtilDebug.i("show", "USDT_Account:" + USDT_Account);
                        }
                        break;
                    case "1":
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");
                        break;
                }
                break;

            case MethodUrl.CONTRACT_TRADE:
                showToastMsg(tData.get("msg") + "");
                switch ((tData.get("code") + "")) {
                    case "0":
                       /* switch (kind) {
                            case "0":
                                getChicangListAction();
                                break;
                            case "1":
                                getWeituoListAction();
                                break;
                            case "2":
                                getChengjiaoListAction();
                                break;
                        }*/
                        break;
                    case "1":
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");
                        break;
                }
                break;

        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        mLoadingWindow.cancleView();
        dealFailInfo(map, mType);
    }

    @Override
    public void reLoadingData() {
        mLoadingWindow.showView();
        //getAvaiableMoneyAction();

    }


    private void responseData() {
        if (shouMoneyListAdapter == null) {
            shouMoneyListAdapter = new ShouMoneyListAdapter(getActivity());
            shouMoneyListAdapter.addAll(mDataList);

            /*AnimationAdapter adapter = new ScaleInAnimationAdapter(mDataAdapter);
            adapter.setFirstOnly(false);
            adapter.setDuration(500);
            adapter.setInterpolator(new OvershootInterpolator(.5f));*/

            mLRecyclerViewAdapter = new LRecyclerViewAdapter(shouMoneyListAdapter);

//            SampleHeader headerView = new SampleHeader(BankCardActivity.this, R.layout.item_bank_bind);
//            mLRecyclerViewAdapter.addHeaderView(headerView);

            mRefreshListView.setAdapter(mLRecyclerViewAdapter);
            mRefreshListView.setItemAnimator(new DefaultItemAnimator());
            mRefreshListView.setHasFixedSize(true);
            mRefreshListView.setNestedScrollingEnabled(false);


            mRefreshListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRefreshListView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

            mRefreshListView.setPullRefreshEnabled(true);
            mRefreshListView.setLoadMoreEnabled(false);

        } else {
            if (mPage == 1) {
                shouMoneyListAdapter.clear();
            }
            LogUtilDebug.i("show", "mDataList:" + mDataList.size());
            shouMoneyListAdapter.addAll(mDataList);
            shouMoneyListAdapter.notifyDataSetChanged();
            mLRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
        }


        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });
     /*   //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOut); //设置下拉刷新Progress的样式
        //mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);  //设置下拉刷新箭头
        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.red ,android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.red ,android.R.color.white);*/

        mRefreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        /*if (mDataList.size() < 10) {
            mRefreshListView.setNoMore(true);
        } else {
            mRefreshListView.setNoMore(false);
        }*/

        mRefreshListView.refreshComplete(10);
        shouMoneyListAdapter.notifyDataSetChanged();
        if (shouMoneyListAdapter.getDataList().size() <= 0) {
            mPageView.showEmpty();
        } else {
            mPageView.showContent();
        }

    }


    private View popView;
    private PopupWindow mConditionDialog;
    private boolean bright = false;

    private XTabLayout tabLayout;
    private RecyclerView rcv;


    public void showDialog() {
        initPopupWindow();
    }


    private void initPopupWindow() {
        if (mConditionDialog == null) {

            popView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_heyue_type, null);
            mConditionDialog = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            initConditionDialog(popView);
            mConditionDialog.setClippingEnabled(false);

            int screenWidth = UtilTools.getScreenWidth(getActivity());
            int screenHeight = UtilTools.getScreenHeight(getActivity());
            mConditionDialog.setWidth((int) (screenWidth * 0.8));
            mConditionDialog.setHeight(WindowManager.LayoutParams.MATCH_PARENT);

            //设置background后在外点击才会消失
            mConditionDialog.setBackgroundDrawable(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), UtilTools.dip2px(getActivity(),5)));
            mConditionDialog.setOutsideTouchable(true);// 设置可允许在外点击消失
            //自定义动画
            mConditionDialog.setAnimationStyle(R.style.PopupAnimation);
            //mConditionDialog.setAnimationStyle(android.R.style.Animation_Activity);//使用系统动画
            mConditionDialog.update();
            mConditionDialog.setTouchable(true);
            mConditionDialog.setFocusable(true);
            //popView.requestFocus();//pop设置不setBackgroundDrawable情况，把焦点给popView，添加popView.setOnKeyListener。可实现点击外部不消失，点击反键才消失
            //			mConditionDialog.showAtLocation(mCityTv, Gravity.TOP|Gravity.RIGHT, 0, 0); //设置layout在PopupWindow中显示的位置
            mConditionDialog.showAtLocation(getActivity().getWindow().getDecorView(),
                    Gravity.TOP | Gravity.LEFT, 0, 0);
            toggleBright();
            mConditionDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    toggleBright();
                }
            });
        } else {
            mConditionDialog.showAtLocation(getActivity().getWindow().getDecorView(),
                    Gravity.TOP | Gravity.LEFT, 0, 0);
            toggleBright();
        }
    }


    private void toggleBright() {
        //三个参数分别为： 起始值 结束值 时长 那么整个动画回调过来的值就是从0.5f--1f的
        mAnimUtil.setValueAnimator(0.7f, 1f, 300);
        mAnimUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                //此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                float bgAlpha = bright ? progress : (1.7f - progress);//三目运算，应该挺好懂的。
                //bgAlpha = progress;//三目运算，应该挺好懂的。
                bgAlpha(bgAlpha);//在此处改变背景，这样就不用通过Handler去刷新了。
            }
        });
        mAnimUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                //在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        mAnimUtil.startAnimator();
    }

    private void bgAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) getActivity()).getWindow().getAttributes();
        lp.alpha = alpha;// 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void initConditionDialog(View view) {

        rcv = view.findViewById(R.id.rcv);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        rcv.setLayoutManager(manager);
        if (mDatas != null && mDatas.size() > 0) {
            mAdapter = new TypeSelectAdapter(getActivity(), mDatas, 10);
            rcv.setAdapter(mAdapter);

            mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
                @Override
                public void OnMyItemClickListener(View view, int position) {
                    mConditionDialog.dismiss();
                    mBackText.setText(mDatas.get(position).get("name") + "");
                    symbol = mDatas.get(position).get("symbol") + "";
                    //获取保证金
                    getAvaiableMoneyAction();


                    //获取合约价格以及深度信息
                    getPairDepthAction();

                    //获取杠杆信息
                    getContractLeverAction();
                }
            });
        } else {
            showToastMsg("暂无更多信息");
        }


    }


    @Override
    public void onSelectBackListener(Map<String, Object> map, int type) {
        switch (type) {
            case 10: //限价
                tvLimitPrice.setText(map.get("name") + "");
                switch (map.get("code") + "") {
                    case "0"://市价
                        mSelectType = "2";
                        xianjiaLay.setVisibility(View.GONE);
                        jihuaLay.setVisibility(View.VISIBLE);
                        layChufan.setVisibility(View.GONE);
                        break;
                    case "1"://限价
                        mSelectType = "0";
                        xianjiaLay.setVisibility(View.VISIBLE);
                        jihuaLay.setVisibility(View.GONE);
                        break;
                    case "2"://计划委托
                        mSelectType = "1";
                        xianjiaLay.setVisibility(View.GONE);
                        jihuaLay.setVisibility(View.VISIBLE);
                        layChufan.setVisibility(View.VISIBLE);
                        break;
                }
                break;

            case 20://杠杆
                cnyRatio = map.get("multiple") + "";
                tvLessChoose.setText(cnyRatio + "X");
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUEST_CODE) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    String buySell = bundle.getString("buySell");
                    if (buySell.equals("1")) { //买入
                        rbOpen.setChecked(true);
                    } else {  //卖出
                        rbClose.setChecked(true);
                    }
                }

            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (handler != null && cnyRunnable != null) {
            handler.removeCallbacks(cnyRunnable);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (handler != null && cnyRunnable != null) {
                handler.post(cnyRunnable);
                LogUtilDebug.i("show", "&&&&&&&&HeyueFragment visible");
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (handler != null && cnyRunnable != null) {
                handler.removeCallbacks(cnyRunnable);
            }
            setUserVisibleHint(false);
            LogUtilDebug.i("show", "onHiddenChanged()*******HeYueFragment不可见");
        } else {
            if (handler != null && cnyRunnable != null) {
                handler.post(cnyRunnable);
            }
            setUserVisibleHint(true);
            LogUtilDebug.i("show", "onHiddenChanged()*******HeYueFragment可见");
        }
    }

    @Override
    public void onPagerClosed() {
        Toast.makeText(getActivity(), "关闭了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPagerOpened() {
        Toast.makeText(getActivity(), "打开了", Toast.LENGTH_SHORT).show();
    }
}
