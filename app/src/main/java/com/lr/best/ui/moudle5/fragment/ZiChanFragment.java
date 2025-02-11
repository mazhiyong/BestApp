package com.lr.best.ui.moudle5.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.xtablayout.XTabLayout;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicFragment;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.ReLoadingData;
import com.lr.best.listener.SelectBackListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.dialog.KindSelectDialog;
import com.lr.best.mywidget.pulltozoomview.PullToZoomScrollViewEx;
import com.lr.best.mywidget.view.LoadingWindow;
import com.lr.best.mywidget.view.PageView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.ui.moudle5.activity.ChoseBiTypeActivity;
import com.lr.best.ui.moudle5.activity.HuaZhuanActivity;
import com.lr.best.ui.moudle5.activity.MyProfitListActivity;
import com.lr.best.ui.moudle5.adapter.ZiChanListAdapter;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.SelectDataUtil;
import com.lr.best.utils.tool.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ZiChanFragment extends BasicFragment implements ReLoadingData, RequestView , SelectBackListener {


    @BindView(R.id.person_scroll_view)
    PullToZoomScrollViewEx mPersonScrollView;


    @BindView(R.id.back_view)
    ImageView mBackView;
    @BindView(R.id.titleText)
    TextView mTitleText;
    @BindView(R.id.personal_more_button)
    ImageView mPersonalMoreButton;

    @BindView(R.id.index_top_layout_person)
    LinearLayout mIndexTopLayoutPerson;
    Unbinder unbinder;
    @BindView(R.id.bank_money_tv)
    TextView bankMoneyTv;
    @BindView(R.id.bank_card_tv)
    TextView bankCardTv;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.money_tv2)
    TextView moneyTv2;
    @BindView(R.id.refresh_list_view)
    LRecyclerView refreshListView;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.page_view)
    PageView pageView;
    @BindView(R.id.chongbi_tv)
    TextView chongbiTv;
    @BindView(R.id.tibi_tv)
    TextView tibiTv;
    @BindView(R.id.huazhuan_tv)
    TextView huazhuanTv;
    @BindView(R.id.zichanAcccountLay)
    LinearLayout zichanAcccountLay;
    @BindView(R.id.duihuanAcountlay)
    LinearLayout duihuanAcountlay;
    @BindView(R.id.duihuanLay)
    LinearLayout duihuanLay;
    @BindView(R.id.tv)
    TextView mTextView;
    @BindView(R.id.from_tv)
    TextView fromTv;
    @BindView(R.id.to_tv)
    TextView toTv;


    private LoadingWindow mLoadingWindow;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ZiChanListAdapter mAdapter;


    private String mRequestTag = "";
    private List<Map<String, Object>> mDataList = new ArrayList<>();

    //1币币，3发布，4奖励
    private String mType = "1";

    private KindSelectDialog mDialog;


    public ZiChanFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MbsConstans.BroadcastReceiverAction.USER_INFO_UPDATE);
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
        mLoadingWindow = new LoadingWindow(getActivity(), R.style.Dialog);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen.title_item_height) + UtilTools.getStatusHeight2(getActivity()));
        mIndexTopLayoutPerson.setLayoutParams(layoutParams);
        mIndexTopLayoutPerson.setPadding(0, UtilTools.getStatusHeight2(getActivity()), 0, 0);
        mTitleText.setVisibility(View.GONE);


        mTextView.setText("温馨提示：\n" +
                "任意币种兑换best实行1：3兑换\n" +
                "兑换一次消耗1奖励金");

        tabLayout.addTab(tabLayout.newTab().setText("币币账户"));
        tabLayout.addTab(tabLayout.newTab().setText("法币账户"));
        //tabLayout.addTab(tabLayout.newTab().setText("奖励金"));
        tabLayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                //mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0: //币币账户
                        mType = "1";
                        pageView.setVisibility(View.VISIBLE);
                        zichanAcccountLay.setVisibility(View.VISIBLE);
                        duihuanLay.setVisibility(View.GONE);
                        duihuanAcountlay.setVisibility(View.GONE);

                        getAccountDataAction();
                        break;
                    case 1: //法币账户
                        mType = "3";
                        pageView.setVisibility(View.VISIBLE);
                        zichanAcccountLay.setVisibility(View.VISIBLE);
                        duihuanLay.setVisibility(View.GONE);
                        duihuanAcountlay.setVisibility(View.GONE);

                        getAccountDataAction();
                        break;
                /*    case 2: //奖励金
                        mType = "4";
                        pageView.setVisibility(View.GONE);
                        zichanAcccountLay.setVisibility(View.GONE);
                        duihuanLay.setVisibility(View.VISIBLE);
                        duihuanAcountlay.setVisibility(View.VISIBLE);

                        //getAccountDataAction();
                        break;*/
                }


            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        pageView.setContentView(content);
        pageView.showLoading();
        pageView.setReLoadingData(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        refreshListView.setLayoutManager(linearLayoutManager);

        refreshListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取各个账户资产信息
                getAccountDataAction();
            }
        });
        setBarTextColor();

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取总资产
        getZiChanDataAction();

        //获取各个账户资产信息
        getAccountDataAction();

    }


    public void setBarTextColor() {
        StatusBarUtil.setDarkMode(getActivity());
    }


    @OnClick({R.id.chongbi_tv, R.id.tibi_tv, R.id.huazhuan_tv, R.id.duihuan_tv,R.id.from_tv,R.id.to_tv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.chongbi_tv: //充币
                intent = new Intent(getActivity(), ChoseBiTypeActivity.class);
                intent.putExtra("TYPE", "1");
                startActivity(intent);
                break;
            case R.id.tibi_tv:   //提币
                intent = new Intent(getActivity(), ChoseBiTypeActivity.class);
                intent.putExtra("TYPE", "2");
                startActivity(intent);
                break;
            case R.id.huazhuan_tv://划转
                intent = new Intent(getActivity(), HuaZhuanActivity.class);
                startActivity(intent);
                break;
            case R.id.duihuan_tv://分红
                intent = new Intent(getActivity(), MyProfitListActivity.class);
                startActivity(intent);
                break;
            case R.id.from_tv:
                List<Map<String,Object>> mDataList = SelectDataUtil.getAccoutType();
                mDialog=new KindSelectDialog(getActivity(),true,mDataList,10);
                mDialog.setSelectBackListener(this);
                mDialog.showAtLocation(Gravity.BOTTOM,0,0);
                break;
            case R.id.to_tv:
                List<Map<String,Object>> mDataList1 = SelectDataUtil.getAccoutType();
                mDialog=new KindSelectDialog(getActivity(),true,mDataList1,20);
                mDialog.setSelectBackListener(this);
                mDialog.showAtLocation(Gravity.BOTTOM,0,0);
                break;
        }
    }


    /**
     * 获取总资产
     */
    public void getZiChanDataAction() {
        mRequestTag = MethodUrl.ZICHAN_ALL;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.ZICHAN_ALL, map);
    }

    /**
     * 获取个账户信息
     */
    public void getAccountDataAction() {
        mRequestTag = MethodUrl.ZICHAN_ACCOUNT;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("type", mType);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.ZICHAN_ACCOUNT, map);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MbsConstans.BroadcastReceiverAction.USER_INFO_UPDATE)) {
                //getUserInfoAction();
            } else if (action.equals(MbsConstans.BroadcastReceiverAction.MONEY_UPDATE)) {
            }
        }
    };


    @Override
    public void showProgress() {
        mLoadingWindow.showView();
    }

    @Override
    public void disimissProgress() {
        mLoadingWindow.cancleView();
    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {
        Intent intent;
        switch (mType) {
            case MethodUrl.ZICHAN_ALL:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        if (!UtilTools.empty(tData.get("data") + "")) {
                            Map<String, Object> map = (Map<String, Object>) tData.get("data");
                            //bankMoneyTv.setText(UtilTools.getNormalMoney(map.get("total") + ""));
                            //bankCardTv.setText("≈" + UtilTools.getNormalMoney(map.get("cny") + "") + "CNY");
                            bankMoneyTv.setText(UtilTools.formatDecimal(map.get("total") + "",8));
                            bankCardTv.setText("≈" + UtilTools.formatDecimal(map.get("cny") + "",2) + "CNY");
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
            case MethodUrl.ZICHAN_ACCOUNT:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        if (UtilTools.empty(tData.get("data") + "")) {
                            pageView.showEmpty();
                        } else {
                            Map<String, Object> mapData = (Map<String, Object>) tData.get("data");
                            if (!UtilTools.empty(mapData)) {
                                Map<String, Object> moneyMap = (Map<String, Object>) mapData.get("account");
                                if (!UtilTools.empty(moneyMap)) {
                                    //moneyTv.setText(UtilTools.getNormalMoney(moneyMap.get("btc") + ""));
                                    //moneyTv2.setText("≈" + UtilTools.getNormalMoney(moneyMap.get("cny") + "") + "CNY");
                                    moneyTv.setText(UtilTools.formatDecimal(moneyMap.get("btc") + "",8));
                                    moneyTv2.setText("≈" + UtilTools.formatDecimal(moneyMap.get("cny") + "",2)  + "CNY");
                                }

                                if (UtilTools.empty(mapData.get("coin") + "")) {
                                    pageView.showEmpty();
                                } else {
                                    mDataList = (List<Map<String, Object>>) mapData.get("coin");
                                    if (UtilTools.empty(mDataList)) {
                                        pageView.showEmpty();
                                    } else {
                                        pageView.showContent();
                                        responseData();
                                        refreshListView.refreshComplete(10);
                                    }
                                }

                            } else {
                                pageView.showEmpty();
                            }
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        pageView.showNetworkError();
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

            case MethodUrl.REFRESH_TOKEN://获取refreshToken返回结果
                MbsConstans.REFRESH_TOKEN = tData.get("refresh_token") + "";
                mIsRefreshToken = false;

                break;
        }
    }

    private void responseData() {
        if (mAdapter == null) {
            mAdapter = new ZiChanListAdapter(getActivity());
            mAdapter.addAll(mDataList);

            AnimationAdapter adapter1 = new ScaleInAnimationAdapter(mAdapter);
            adapter1.setFirstOnly(false);
            adapter1.setDuration(400);
            adapter1.setInterpolator(new OvershootInterpolator(0.8f));


            AnimationAdapter adapter = new AlphaInAnimationAdapter(adapter1);
            adapter.setFirstOnly(false);
            adapter.setDuration(500);
            adapter.setInterpolator(new OvershootInterpolator(1f));


            mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
            //SampleHeader headerView = new SampleHeader(getActivity(), R.layout.fragment_home_head_view);
            //mLRecyclerViewAdapter.addHeaderView(headerView);
            refreshListView.setAdapter(mLRecyclerViewAdapter);
            refreshListView.setItemAnimator(new DefaultItemAnimator());
            refreshListView.setHasFixedSize(true);
            refreshListView.setNestedScrollingEnabled(false);

            refreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
            refreshListView.setPullRefreshEnabled(true);
            refreshListView.setLoadMoreEnabled(false);

            refreshListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            refreshListView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

            //int spacing = getResources().getDimensionPixelSize(R.dimen.divide_hight);
            //mRefreshListView.addItemDecoration(SpacesItemDecoration.newInstance(spacing, spacing, gridLayoutManager.getSpanCount(), Color.GRAY));
            //根据需要选择使用GridItemDecoration还是SpacesItemDecoration
            GridItemDecoration divider = new GridItemDecoration.Builder(getActivity())
                    .setHorizontal(R.dimen.divide_hight)
                    .setVertical(R.dimen.divide_hight)
                    .setColorResource(R.color.divide_line)
                    .build();
            //mRefreshListView.addItemDecoration(divider);

//            DividerDecoration divider2 = new DividerDecoration.Builder(getActivity())
//                    .setHeight(R.dimen.dp_10)
//                    .setPadding(R.dimen.dp_10)
//                    .setColorResource(R.color.body_bg)
//                    .build();
//            mRefreshListView.addItemDecoration(divider2);

            mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Map<String, Object> item = mAdapter.getDataList().get(position);
                   /* Intent intent = new Intent(getActivity(), ShowDetailPictrue.class);
                    intent.putExtra("jsonData",item.get("url")+"");
                    startActivity(intent);*/
                }

            });


        } else {

           /* if (mPage == 1) {
                mRepaymentAdapter.clear();
            }*/
          /*  mAdapter.clear();
            mAdapter.addAll(mDataList);
            refreshListView.setAdapter(mLRecyclerViewAdapter);*/
            mAdapter.clear();
            mAdapter.addAll(mDataList);
            mAdapter.notifyDataSetChanged();
            mLRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
        }

        refreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        if (mDataList.size() < 10) {
            refreshListView.setNoMore(true);
        } else {
            refreshListView.setNoMore(false);
        }

        if (mAdapter.getDataList().size() <= 0) {
            pageView.showEmpty();
        } else {
            pageView.showContent();
        }
    }


    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        dealFailInfo(map, mType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void reLoadingData() {
        //获取各个账户资产信息
        getAccountDataAction();
    }


    @Override
    public void onSelectBackListener(Map<String, Object> map, int type) {
        switch (type){
            case 10:
                String s= (String) map.get("name"); //选择账户
                fromTv.setText(s);
               // huzhuanTv.setEnabled(true);
                break;
            case 20: //选择币种
                String str= (String) map.get("name"); //选择账户
                toTv.setText(str);
                break;
        }
    }
}
