package com.lr.best.ui.moudle1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.ui.moudle.adapter.ListBaseAdapter;
import com.lr.best.utils.tool.UtilTools;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamProfitListAdapter extends ListBaseAdapter {

    private View mHeaderView;

    private LayoutInflater mLayoutInflater;

    private final int ITEM_TYPE_NORMAL = 0;
    private final int ITEM_TYPE_HEADER = 1;

    public TeamProfitListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        if (arg1 == ITEM_TYPE_HEADER) {
            return new ViewHolder(mHeaderView,ITEM_TYPE_HEADER);
        } else {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_profit, arg0, false),ITEM_TYPE_NORMAL);
        }

    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        int itemCount = mDataList.size();
        if (null != mHeaderView) {
            itemCount++;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mHeaderView && position == 0) {
            return ITEM_TYPE_HEADER;
        }else {
            return ITEM_TYPE_NORMAL;
        }
    }


    public void addHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ITEM_TYPE_HEADER) {

        } else {
            final Map<String, Object> item = mDataList.get(position-1);
            ViewHolder viewHolder = (ViewHolder) holder;
            //时间
            viewHolder.accontTv.setText(item.get("time") + "");
            //我的业绩
            if (UtilTools.empty(item.get("team_performance"))){
                viewHolder.yejiTv.setText(item.get("performance_total") + "");
            }else {
                viewHolder.yejiTv.setText(item.get("team_performance") + "");
            }
            //比例
            viewHolder.biliTv.setText(Float.parseFloat(item.get("rate") + "")*100+"%");
            //奖励
            if (UtilTools.empty(item.get("performance"))){
                viewHolder.childTreamTv.setText(item.get("award") + "");
            }else {
                viewHolder.childTreamTv.setText(item.get("performance") + "");
            }


        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.accontTv)
        TextView accontTv;
        @BindView(R.id.yejiTv)
        TextView yejiTv;
        @BindView(R.id.childTreamTv)
        TextView childTreamTv;
        @BindView(R.id.biliTv)
        TextView biliTv;

        public ViewHolder(View itemView,int type) {
            super(itemView);
            switch (type){
                case ITEM_TYPE_HEADER:
                    break;
                case ITEM_TYPE_NORMAL:
                    break;
            }
            ButterKnife.bind(this, itemView);
        }
    }


}
