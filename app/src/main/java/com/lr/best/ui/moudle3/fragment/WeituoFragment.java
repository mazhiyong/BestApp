package com.lr.best.ui.moudle3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicFragment;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.OnChildClickListener;
import com.lr.best.listener.ReLoadingData;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.dialog.SureOrNoDialog;
import com.lr.best.mywidget.view.LoadingWindow;
import com.lr.best.mywidget.view.PageView;
import com.lr.best.ui.moudle.activity.LoginActivity;
import com.lr.best.ui.moudle3.adapter.ShouMoneyListAdapter;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 委托
 */
public class WeituoFragment extends BasicFragment implements RequestView, ReLoadingData {

    @BindView(R.id.refresh_list_view)
    LRecyclerView mRefreshListView;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.page_view)
    PageView mPageView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int TYPE = 0;
    public LoadingWindow mLoadingWindow;

    private LRecyclerViewAdapter mLRecyclerViewAdapter1 = null;

    private ShouMoneyListAdapter shouMoneyListAdapter;


    private String mRequestTag = "";

    private List<Map<String, Object>> mDataList = new ArrayList<>();


    private int maxPage = 1;
    private int mPage = 1;
    private String id = "";


    public WeituoFragment() {
        // Required empty public constructor
    }


    /**
     * -------------------------------------懒加载  start
     */
    boolean isViewInitiated;
    boolean isVisibleToUser;
    boolean isDataInitiated;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(true);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            //showProgressDialog();
//            if (getUserVisibleHint()) {
//                setWebsocketListener();
//                handler.post(runnable);
//            }
            //请求数据 只在进入时加载数据，不进行预加载数据
            //mLoadingWindow.showView();
            mPage = 1;
            //获取持仓列表
            getWeituoListAction();


            LogUtilDebug.i("show", "委托懒加载数据");
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    /**
     * --------------------------------------懒加载     end
     */


    @Override
    public int getLayoutId() {
        return R.layout.fragment_weituo;
    }

    @Override
    public void init() {
        mLoadingWindow = new LoadingWindow(getActivity(), R.style.Dialog);

        mPageView.setContentView(mContent);
        mPageView.setReLoadingData(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //mRefreshListView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(linearLayoutManager);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPage = 1;
                getWeituoListAction();
            }
        });
        refreshLayout.setOnLoadMoreListener(new com.scwang.smartrefresh.layout.listener.OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mDataList == null || mDataList.size() < 10) {
                    //refreshLayout.finishLoadMore();
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    getWeituoListAction();
                }
            }
        });


        //刷新
        mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getWeituoListAction();

            }
        });


        //加载更多
        mRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //mPage = mPage + 1;
                if (mDataList == null || mDataList.size() < 10) {
                    mRefreshListView.setNoMore(true);
                } else {
                    getWeituoListAction();
                }
            }
        });

        setBarTextColor();

    }



    private void getWeituoListAction() {
        mRequestTag = MethodUrl.WEITUO_LIST;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("symbol", "");
        map.put("size", "10");
        map.put("page", mPage);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.WEITUO_LIST, map);
    }


    private void cheXiaoAction(Map<String, Object> mParentMap) {
        //mRequestTag = MethodUrl.CHE_XIAO;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(getActivity(), MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        map.put("id", mParentMap.get("id") + "");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.CHE_XIAO, map);
    }



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
        mLoadingWindow.cancleView();
        Intent intent;
        switch (mType) {
            case MethodUrl.WEITUO_LIST:
                switch (tData.get("code") + "") {
                    case "0":
                        if (!UtilTools.empty(tData.get("data")+"")){
                            Map<String, Object> mapData = (Map<String, Object>) tData.get("data");
                            if (!UtilTools.empty(mapData)) {
                                mDataList = (List<Map<String, Object>>) mapData.get("list");
                                if (!UtilTools.empty(mapData.get("page_mum")+"")){
                                    maxPage = Integer.parseInt(mapData.get("page_mum")+"");
                                }

                                if (!UtilTools.empty(mDataList) && mDataList.size() > 0) {
                                    for (Map<String, Object> map : mDataList) {
                                        map.put("kind", "1");
                                    }
                                    mPageView.showContent();
                                    responseData1();
                                    refreshLayout.finishRefresh();
                                    refreshLayout.finishLoadMore();
                                    //mRefreshListView.refreshComplete(10);
                                } else {
                                    mPageView.showEmpty();
                                }

                            } else {
                                mPageView.showEmpty();
                            }
                        }

                        break;
                    case "1":
                        if (getParentFragment().getActivity() != null) {
                            getParentFragment().getActivity().finish();
                        }
                        intent = new Intent(getParentFragment().getActivity(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case "-1":
                        showToastMsg(tData.get("msg") + "");
                        break;

                }
                break;
            case MethodUrl.CHE_XIAO:
                switch ((tData.get("code") + "")) {
                    case "0":
                        showToastMsg("撤销成功");
                        if (shouMoneyListAdapter.getDataList().size()%10 == 1){
                            //撤销之前为11,21...撤销成功后,为10,20,分页加载需要减去1
                            if (mPage > 1){
                                mPage = mPage -1;
                            }

                        }
                        getWeituoListAction();
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


            case MethodUrl.REFRESH_TOKEN://获取refreshToken返回结果
                MbsConstans.REFRESH_TOKEN = tData.get("refresh_token") + "";
                mIsRefreshToken = false;
                switch (mRequestTag) {
                    case MethodUrl.shoumoneyList:
                        //getShouMoneyInfoList();
                        break;
                }
                break;
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        switch (mType) {
            case MethodUrl.borrowList:
                if (shouMoneyListAdapter != null) {
                    if (shouMoneyListAdapter.getDataList().size() <= 0) {
                        mPageView.showNetworkError();
                    } else {
                        mPageView.showContent();
                    }
                    mRefreshListView.refreshComplete(10);
                    mRefreshListView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {

                        }
                    });
                } else {
                    mPageView.showNetworkError();
                }

                break;
        }


        mLoadingWindow.cancleView();
        dealFailInfo(map, mType);
    }

    @Override
    public void reLoadingData() {

        mLoadingWindow.showView();
        getWeituoListAction();

    }


    public void setBarTextColor() {
        StatusBarUtil.setLightMode(getActivity());
    }


    private void responseData1() {
        if (shouMoneyListAdapter == null) {
            shouMoneyListAdapter = new ShouMoneyListAdapter(getActivity());
            shouMoneyListAdapter.addAll(mDataList);

           /* AnimationAdapter adapter1 = new ScaleInAnimationAdapter(shouMoneyListAdapter);
            adapter1.setFirstOnly(false);
            adapter1.setDuration(400);
            adapter1.setInterpolator(new OvershootInterpolator(0.8f));


            AnimationAdapter adapter = new AlphaInAnimationAdapter(adapter1);
            adapter.setFirstOnly(false);
            adapter.setDuration(500);
            adapter.setInterpolator(new OvershootInterpolator(1f));


            mLRecyclerViewAdapter1 = new LRecyclerViewAdapter(adapter);
            //SampleHeader headerView = new SampleHeader(getActivity(), R.layout.fragment_home_head_view);
            //mLRecyclerViewAdapter1.addHeaderView(headerView);
            mRefreshListView.setAdapter(mLRecyclerViewAdapter1);
            mRefreshListView.setItemAnimator(new DefaultItemAnimator());
            mRefreshListView.setHasFixedSize(true);
            mRefreshListView.setNestedScrollingEnabled(false);

            mRefreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
            mRefreshListView.setPullRefreshEnabled(true);
            mRefreshListView.setLoadMoreEnabled(true);

            mRefreshListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRefreshListView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);*/

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
        } else {
            if (mPage == 1) {
                shouMoneyListAdapter.clear();
            }
            shouMoneyListAdapter.addAll(mDataList);

            shouMoneyListAdapter.notifyDataSetChanged();
            //mLRecyclerViewAdapter1.notifyDataSetChanged();

        }

        //mRefreshListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        //mRefreshListView.refreshComplete(10);

        if (mDataList==null  || mDataList.size() < 10) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (mPage < maxPage ){
                mPage++;
            }
        }

        recyclerView.setAdapter(shouMoneyListAdapter);

        if (shouMoneyListAdapter.getDataList().size() <= 0) {
            mPageView.showEmpty();
            LogUtilDebug.i("show","******************** mPageView.showEmpty()");

        } else {
            mPageView.showContent();
            LogUtilDebug.i("show","******************** mPageView.showContent()");
        }
     /*   if (mDataList.size() < 10) {
            mRefreshListView.setNoMore(true);
        } else {
            mRefreshListView.setNoMore(false);
        }

        if (shouMoneyListAdapter.getDataList().size() <= 0) {
            mPageView.showEmpty();
        } else {
            mPageView.showContent();
        }*/

        shouMoneyListAdapter.setmCallBack(new OnChildClickListener() {
            @Override
            public void onChildClickListener(View view, int position, Map<String, Object> mParentMap) {
                LogUtilDebug.i("show", "itemClick()");
                // mRefreshListView.smoothScrollToPosition(0);
                // mRefreshListView.scrollTo(0,0);
                // nestScrollView.scrollTo(0, tlTradeList.getTop());
                SureOrNoDialog sureOrNoDialog;
                switch (mParentMap.get("kind") + "") {
                    case "1": //撤销
                        sureOrNoDialog = new SureOrNoDialog(getActivity(), true);
                        sureOrNoDialog.initValue("提示", "是否确定撤销？");
                        sureOrNoDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.cancel:
                                        sureOrNoDialog.dismiss();
                                        break;
                                    case R.id.confirm:
                                        sureOrNoDialog.dismiss();
                                        cheXiaoAction(mParentMap);
                                        break;
                                }
                            }
                        });
                        sureOrNoDialog.show();
                        sureOrNoDialog.setCanceledOnTouchOutside(false);
                        sureOrNoDialog.setCancelable(true);

                        break;
                    case "2":
                        break;

                }


            }
        });

    }



}
