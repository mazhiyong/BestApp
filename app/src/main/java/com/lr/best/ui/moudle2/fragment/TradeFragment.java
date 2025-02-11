package com.lr.best.ui.moudle2.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.BasicFragment;
import com.lr.best.ui.moudle2.adapter.MyViewPagerAdapter;
import com.lr.best.utils.tool.LogUtilDebug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

/**
 * OTC
 */
public class TradeFragment extends BasicFragment {

    @BindView(R.id.iv)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    XTabLayout mTabLayout;
    List<Fragment> mFragments=new ArrayList<>();



    private BBTradeFragment bbTradeFragment;
    private FBTradeFragment fbTradeFragment;

    private int Position = 0;
    public int TYPE = 0;
    public String selectArea = "";
    public String selectSymbol = "";
    public String buySell = "1";

    public TradeFragment() {
        // Required empty public constructor
    }





    @Override
    public int getLayoutId() {
        return R.layout.fragment_otc;
    }
    @Override
    public void init() {
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       // StatusBarUtil.setColorForSwipeBack(getActivity(), ContextCompat.getColor(getActivity(), MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);
        setBarTextColor();


        mTabLayout.addTab(mTabLayout.newTab().setText("币币交易"));
        mTabLayout.addTab(mTabLayout.newTab().setText("法币交易"));

        bbTradeFragment=new BBTradeFragment();
        /*bbTradeFragment.symbol = selectSymbol;
        bbTradeFragment.area = selectArea;*/
        mFragments.add(bbTradeFragment);
        fbTradeFragment=new FBTradeFragment();
        mFragments.add(fbTradeFragment);
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),mFragments));
        mViewPager.addOnPageChangeListener(new XTabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                  /*  bbTradeFragment.symbol = selectSymbol;
                    bbTradeFragment.area = selectArea;*/
                    bbTradeFragment.buysell = buySell;
                    bbTradeFragment.restartWs(selectArea,selectSymbol,buySell);

                    if (fbTradeFragment.mLoadingWindow != null){
                        fbTradeFragment.mLoadingWindow.cancleView();
                    }
                }else {
                    bbTradeFragment.stopWs();
                    if (bbTradeFragment.mLoadingWindow != null){
                        bbTradeFragment.mLoadingWindow.cancleView();
                    }
                   LogUtilDebug.i("show","FB可见");
                }
                Position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        if (TYPE == 1){
            Objects.requireNonNull(mTabLayout.getTabAt(1)).select();
        }else {
            Objects.requireNonNull(mTabLayout.getTabAt(0)).select();
        }
    }





    public void setBarTextColor(){
        StatusBarUtil.setLightMode(getActivity());
    }


    @Override
    public void showProgress() {


    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {

    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {

    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtilDebug.i("show","onResume()*******");

        if (getUserVisibleHint() && Position ==0) {
         /*   bbTradeFragment.symbol = selectSymbol;
            bbTradeFragment.area = selectArea;
            bbTradeFragment.buysell = buySell;
            bbTradeFragment.restartWs(selectArea,selectSymbol,buySell);*/
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtilDebug.i("show","onPause()*******");
        bbTradeFragment.stopWs();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            setUserVisibleHint(false);
           LogUtilDebug.i("show","onHiddenChanged()*******OTC不可见" + Position);
            switch (Position){
                case 0:
                    bbTradeFragment.stopWs();
                    break;
                case 1:

                    break;
            }
        }else {
            LogUtilDebug.i("show","onHiddenChanged()*******OTC可见" + Position);
            setUserVisibleHint(true);
            if (TYPE == 1){
                Objects.requireNonNull(mTabLayout.getTabAt(1)).select();
            }else {
                Objects.requireNonNull(mTabLayout.getTabAt(0)).select();
            }
            switch (Position){
                case 0:
                  /*  bbTradeFragment.symbol = selectSymbol;
                    bbTradeFragment.area = selectArea;
                    bbTradeFragment.buysell = buySell;
                    bbTradeFragment.restartWs(selectArea,selectSymbol,buySell);*/
                    break;
                case 1:

                    break;
            }
        }

    }
}
