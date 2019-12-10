package com.lr.best.ui.moudle1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.ReLoadingData;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.view.PageView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.ui.moudle1.adapter.TreamListAdapter;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的团队  界面
 */
public class MyTreamActivity extends BasicActivity implements RequestView, ReLoadingData {
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

    @BindView(R.id.refresh_list_view)
    LRecyclerView mRefreshListView;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.page_view)
    PageView mPageView;
    @BindView(R.id.right_text_tv)
    TextView rightTextTv;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.right_img)
    ImageView rightImg;


    private String mRequestTag = "";
    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private TreamListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Override
    public int getContentView() {
        return R.layout.activity_my_tream;
    }

    @Override
    public void init() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);


        mTitleText.setText("我的团队");
        mTitleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        rightTextTv.setVisibility(View.VISIBLE);
        rightTextTv.setText("我的收益");

        mPageView.setContentView(mContent);
        mPageView.setReLoadingData(this);
        mPageView.showLoading();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRefreshListView.setLayoutManager(linearLayoutManager);
        mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyTreamInfoAction();
            }
        });

        mRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRefreshListView.setNoMore(true);
            }
        });

       /* for (int i = 0; i <6 ; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("title","1281928192");
            map.put("title1","222");
            map.put("title2","6");
            mDataList.add(map);

        }

        responseData();*/

        getMyTreamInfoAction();

    }


    @OnClick({R.id.back_img, R.id.left_back_lay, R.id.right_lay})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_img:
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.right_lay:
                intent = new Intent(MyTreamActivity.this, TeamProfitListActivity.class);
                startActivity(intent);
                break;


        }
    }


    private void getMyTreamInfoAction() {
        mRequestTag = MethodUrl.MY_TREAM;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(MyTreamActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.MY_TREAM, map);

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
            case MethodUrl.MY_TREAM:
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        if (UtilTools.empty(tData.get("data") + "")) {
                            mPageView.showEmpty();
                        } else {
                            mDataList = (List<Map<String, Object>>) tData.get("data");
                            if (!UtilTools.empty(mDataList) && mDataList.size() > 0) {
                                mPageView.showContent();
                                responseData();
                                mRefreshListView.refreshComplete(10);
                            } else {
                                mPageView.showEmpty();
                            }
                        }
                   /* List<Map<String,Object>> list = (List<Map<String, Object>>) tData.get("data");
                    if (UtilTools.empty(list)){
                        mPageView.showEmpty();
                    }else {
                        mPageView.showContent();
                        mDataList.clear();
                        mDataList.addAll(list);
                        responseData();

                    }
                    mRefreshListView.refreshComplete(10);*/

                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        Intent intent = new Intent(MyTreamActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;


                }
                break;
        }

    }


    private void responseData() {
        if (mListAdapter == null) {
            mListAdapter = new TreamListAdapter(MyTreamActivity.this);
            mListAdapter.addAll(mDataList);

            /*AnimationAdapter adapter = new ScaleInAnimationAdapter(mDataAdapter);
            adapter.setFirstOnly(false);
            adapter.setDuration(500);
            adapter.setInterpolator(new OvershootInterpolator(.5f));*/

            View view = LayoutInflater.from(this).inflate(R.layout.item_tream_header, mRefreshListView, false);
            //View view = LayoutInflater.from(this).inflate(R.layout.item_bank_bind, null);
            mListAdapter.addHeaderView(view);


            mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);

//            SampleHeader headerView = new SampleHeader(BankCardActivity.this, R.layout.item_bank_bind);
//            mLRecyclerViewAdapter.addHeaderView(headerView);

            mRefreshListView.setAdapter(mLRecyclerViewAdapter);
            mRefreshListView.setItemAnimator(new DefaultItemAnimator());
            mRefreshListView.setHasFixedSize(true);
            mRefreshListView.setNestedScrollingEnabled(false);

            mRefreshListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRefreshListView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

            mRefreshListView.setPullRefreshEnabled(true);
            mRefreshListView.setLoadMoreEnabled(true);


            mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });


        } else {
            mListAdapter.clear();
            mListAdapter.addAll(mDataList);
            mListAdapter.notifyDataSetChanged();
            mLRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
        }
     /*   //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOut); //设置下拉刷新Progress的样式
        //mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);  //设置下拉刷新箭头
        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.red ,android.R.color.white);
//设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.red ,android.R.color.white);*/

        mRefreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        mRefreshListView.refreshComplete(10);
        mListAdapter.notifyDataSetChanged();
        if (mListAdapter.getDataList().size() <= 0) {
            mPageView.showEmpty();
        } else {
            mPageView.showContent();
        }

    }


    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        dealFailInfo(map, mType);
    }


    @Override
    public void reLoadingData() {
        getMyTreamInfoAction();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
