package com.lr.best.ui.moudle5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.listener.OnChildClickListener;
import com.lr.best.ui.moudle.adapter.ListBaseAdapter;
import com.lr.best.ui.moudle5.activity.JiaoGeActivity;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.UtilTools;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ZiChanListAdapter extends ListBaseAdapter {

    private LayoutInflater mLayoutInflater;
    private int mW = 0;
    private OnChildClickListener mCallBack;

    public void setmCallBack(OnChildClickListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public ZiChanListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        LogUtilDebug.i("show", "init adapter");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_zichan_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Map<String, Object> item = mDataList.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
//        map.put("","USDT");
//        map.put("money1","100000");
//        map.put("money2","909010");
//        map.put("money3","608109291");
        viewHolder.typeTv.setText(item.get("symbol") + "");
        if (!UtilTools.empty(item.get("symbol")) && (item.get("symbol") + "").equals("Best")){
            viewHolder.jiaogeActionTv.setVisibility(View.VISIBLE);
            viewHolder.jiaogeTv.setText("交割数量");
        }else {
            viewHolder.jiaogeActionTv.setVisibility(View.GONE);
            viewHolder.jiaogeTv.setText("冻结");
        }
        viewHolder.moneyAvaivable.setText(UtilTools.formatDecimal(item.get("balance")+"",8));
        //viewHolder.moneyAvaivable.setText(item.get("balance")+"");
        if (UtilTools.empty(item.get("frozen"))){
            viewHolder.dongjieLay.setVisibility(View.GONE);
        }else {

            if (!UtilTools.empty(item.get("symbol")) && (item.get("symbol") + "").equals("Best")){

                viewHolder.moneyLimit.setText(UtilTools.formatDecimal(item.get("JGBEST")+"",8));
            }else {

                viewHolder.moneyLimit.setText(UtilTools.formatDecimal(item.get("frozen")+"",8));
            }
        }
        viewHolder.moneyZhehe.setText(UtilTools.formatDecimal(item.get("cny")+"",2));
        //viewHolder.moneyZhehe.setText(item.get("cny")+"");

        viewHolder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onChildClickListener(viewHolder.itemLay, position, item);
                }
            }
        });
        viewHolder.jiaogeActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JiaoGeActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type_tv)
        TextView typeTv;
        @BindView(R.id.money_avaivable)
        TextView moneyAvaivable;
        @BindView(R.id.money_limit)
        TextView moneyLimit;
        @BindView(R.id.money_zhehe)
        TextView moneyZhehe;
        @BindView(R.id.jiaoge_tv)
        TextView jiaogeTv;
        @BindView(R.id.jiaoge_action_tv)
        TextView jiaogeActionTv;
        @BindView(R.id.item_lay)
        CardView itemLay;
        @BindView(R.id.dongjie_lay)
        LinearLayout dongjieLay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
