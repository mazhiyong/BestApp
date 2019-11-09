package com.lr.best.mywidget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.SlideEnter.SlideBottomEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.lr.best.R;
import com.lr.best.listener.OnMyItemClickListener;
import com.lr.best.listener.SelectBackListener;
import com.lr.best.ui.moudle.adapter.CompanyAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 */
public class CompanySelectDialog extends BaseDialog {
    private Context mContext;
    private Map<String,Object>  mSelectedMap;
    private List<Map<String,Object>> mDatas= new ArrayList<>();
    private int mType = 0;

    private CompanyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private SelectBackListener mSelectBackListener;

    public SelectBackListener getSelectBackListener() {
        return mSelectBackListener;
    }

    public void setSelectBackListener(SelectBackListener selectBackListener) {
        mSelectBackListener = selectBackListener;
    }

    public CompanySelectDialog(Context context) {
        super(context);
        mContext=context;
    }

    public CompanySelectDialog(Context context, boolean isPopupStyle, List<Map<String, Object>> datas, int type) {
        super(context, isPopupStyle);
        mContext =context;
        mDatas = datas;
        mType = type;
    }

    @Override
    public View onCreateView() {
        View view=View.inflate(mContext, R.layout.dialong_kind_layout,null);
        mRecyclerView=view.findViewById(R.id.rcv_bankcard);
        init();

        return view;
    }

    @SuppressLint("WrongConstant")
    private void init() {
        if(mAdapter==null){
            mAdapter=new CompanyAdapter(mContext,mDatas);
            LinearLayoutManager manager=new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayout.VERTICAL);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
                @Override
                public void OnMyItemClickListener(View view, int position) {
                    mSelectedMap=mAdapter.getDatas().get(position);
                    if(mSelectBackListener!=null){
                        mSelectBackListener.onSelectBackListener(mSelectedMap,mType);
                    }
                    dismiss();
                }
            });
        }else {
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setUiBeforShow() {

        BaseAnimatorSet bas_in;
        BaseAnimatorSet bas_out;
        bas_in = new SlideBottomEnter();
        bas_in.duration(400);
        bas_out = new SlideBottomExit();
        bas_out.duration(200);

        widthScale(1f);
        dimEnabled(true);
        // baseDialog.heightScale(1f);
        showAnim(bas_in)
                .dismissAnim(bas_out);
    }
}
