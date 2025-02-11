package com.lr.best.ui.moudle2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.listener.OnChildClickListener;
import com.lr.best.ui.moudle.adapter.ListBaseAdapter;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.TextViewUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class WeiTuoListAdapter extends ListBaseAdapter {



    private LayoutInflater mLayoutInflater;
    private int mW = 0;
    private OnChildClickListener mCallBack;

    public void setmCallBack(OnChildClickListener mCallBack) {
        this.mCallBack = mCallBack;
    }

    public WeiTuoListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_repayment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Map<String, Object> item = mDataList.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;


        //String money = UtilTools.getRMBMoney(item.get("backbejn") + "");
        //String time = UtilTools.getStringFromSting2(item.get("creatime") + "", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
        //手续费
        if (UtilTools.empty(item.get("fee"))){
            viewHolder.moneyLay.setVisibility(View.GONE);
        }else {
            viewHolder.moneyLay.setVisibility(View.VISIBLE);
            viewHolder.moneyTv.setText(UtilTools.getNormalMoney(item.get("fee")+""));
        }
        if ((item.get("status")+"").equals("1")){
            viewHolder.dealTv.setText("撤销");
            viewHolder.dealTv.setTextColor(ContextCompat.getColor(mContext,R.color.blue1));
        }else {
            viewHolder.dealTv.setText("已完成");
            viewHolder.dealTv.setTextColor(ContextCompat.getColor(mContext,R.color.gray));
        }


        String kind = "";
        if ((item.get("direction") + "").equals("卖出")) {
            kind = "卖出";
            viewHolder.nameTv.setText(kind + item.get("symbol")+"/"+item.get("area"));
            TextViewUtils textViewUtils = new TextViewUtils();
            String s = viewHolder.nameTv.getText().toString();
            textViewUtils.init(s, viewHolder.nameTv);
            textViewUtils.setTextColor(0, 2, ContextCompat.getColor(mContext, R.color.red_btn));
            textViewUtils.build();
        } else {
            kind = "买入";
            viewHolder.nameTv.setText(kind + item.get("symbol")+"/"+item.get("area"));
            TextViewUtils textViewUtils = new TextViewUtils();
            String s = viewHolder.nameTv.getText().toString();
            textViewUtils.init(s, viewHolder.nameTv);
            textViewUtils.setTextColor(0, 2, ContextCompat.getColor(mContext, R.color.green_btn));
            textViewUtils.build();
        }

        viewHolder.areaTv.setText("价格("+item.get("area")+")");
        viewHolder.symbolTv.setText("数量("+item.get("symbol")+")");
        viewHolder.symbolTv2.setText("实际成交("+item.get("symbol")+")");

        viewHolder.timeTv.setText(item.get("time") + "");
        viewHolder.priceTv.setText(UtilTools.getNormalMoney(item.get("price") + ""));
        viewHolder.numberTv.setText(UtilTools.getNormalMoney(item.get("total") + ""));
        viewHolder.stateTv.setText(UtilTools.getNormalMoney(item.get("finish")+""));

        viewHolder.dealTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //撤销
                //status
                LogUtilDebug.i("show","status&&&&:"+item.get("status")+"");
                if ((item.get("status")+"").equals("1") && mCallBack != null){
                    mCallBack.onChildClickListener(viewHolder.itemLay, position, item);
                }
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.deal_tv)
        TextView dealTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.number_tv)
        TextView numberTv;
        @BindView(R.id.state_tv)
        TextView stateTv;
        @BindView(R.id.money_tv)
        TextView moneyTv;
        @BindView(R.id.areaTv)
        TextView areaTv;
        @BindView(R.id.symbolTv)
        TextView symbolTv;
        @BindView(R.id.symbolTv2)
        TextView symbolTv2;
        @BindView(R.id.money_lay)
        LinearLayout moneyLay;
        @BindView(R.id.item_lay)
        CardView itemLay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
