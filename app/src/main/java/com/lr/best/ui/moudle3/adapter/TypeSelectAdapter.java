package com.lr.best.ui.moudle3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.listener.OnMyItemClickListener;
import com.lr.best.utils.tool.UtilTools;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypeSelectAdapter extends RecyclerView.Adapter<TypeSelectAdapter.ViewHolder> {


    private OnMyItemClickListener mOnMyItemClickListener;
    private int mSelectItem = 0;
    private Context mContext;
    private List<Map<String, Object>> mDatas;

    private int mType = 0;

    public List<Map<String, Object>> getDatas() {
        return mDatas;
    }

    public void setDatas(List<Map<String, Object>> datas) {
        mDatas = datas;
    }

    public OnMyItemClickListener getOnMyItemClickListener() {
        return mOnMyItemClickListener;
    }

    public int getSelectItem() {
        return mSelectItem;
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        mOnMyItemClickListener = onMyItemClickListener;
    }

    public void setSelectItem(int selectItem) {
        mSelectItem = selectItem;
    }

    public TypeSelectAdapter(Context context, List<Map<String, Object>> datas, int type) {
        mContext = context;
        mDatas = datas;
        mType = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.type_item_layout, parent, false);
        ViewHolder hondler = new ViewHolder(view);
        return hondler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvContent.setText(mDatas.get(position).get("name") + "");

        if (!UtilTools.empty(mDatas.get(position).get("area"))){
            holder.tvType.setText("/"+mDatas.get(position).get("area"));
        }else {
            holder.tvType.setText("  ");
        }
        if ((mDatas.get(position).get("name") + "").equals("Best") && (mDatas.get(position).get("area").equals("Chip"))){
            holder.tvNum.setVisibility(View.VISIBLE);
            holder.tvNum.setText("请勿轻易选择这个交易对!");
        }else {
            holder.tvNum.setVisibility(View.GONE);
        }

        if ((mDatas.get(position).get("name") + "").equals("Chip") && (mDatas.get(position).get("area").equals("Best"))){
            holder.tvTip.setVisibility(View.VISIBLE);
            holder.tvTip.setText("只有在这个市场里的买卖交易才可以获得交易流水,卖的数量+买的数量=您今日的交易流水");
        }else {
            holder.tvTip.setVisibility(View.GONE);
        }


        //holder.tvNum.setText(UtilTools.getNormalMoney(mDatas.get(position).get("price") + ""));
        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMyItemClickListener != null) {
                    mOnMyItemClickListener.OnMyItemClickListener(v, position);
                }
                mSelectItem = position;
                notifyDataSetChanged();
            }
        });

        if (position == mSelectItem) {
            holder.llLayout.setSelected(true);
        } else {
            holder.llLayout.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.ll_layout)
        LinearLayout llLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
