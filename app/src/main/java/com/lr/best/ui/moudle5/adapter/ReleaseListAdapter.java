package com.lr.best.ui.moudle5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.ui.moudle.adapter.ListBaseAdapter;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReleaseListAdapter extends ListBaseAdapter {

    private View mHeaderView;

    private LayoutInflater mLayoutInflater;

    private final int ITEM_TYPE_NORMAL = 0;
    private final int ITEM_TYPE_HEADER = 1;

    public ReleaseListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        if (arg1 == ITEM_TYPE_HEADER) {
            return new ViewHolder(mHeaderView,ITEM_TYPE_HEADER);
        } else {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_tream, arg0, false),ITEM_TYPE_NORMAL);
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
            viewHolder.accontTv.setText(item.get("balance") + "");
            viewHolder.yejiTv.setText(item.get("release_num") + "");
            viewHolder.childTreamTv.setText(item.get("time") + "");

        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.accontTv)
        TextView accontTv;
        @BindView(R.id.yejiTv)
        TextView yejiTv;
        @BindView(R.id.childTreamTv)
        TextView childTreamTv;

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
