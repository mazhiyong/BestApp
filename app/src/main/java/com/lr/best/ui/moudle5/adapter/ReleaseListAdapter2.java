package com.lr.best.ui.moudle5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.ui.moudle.adapter.ListBaseAdapter;
import com.lr.best.ui.moudle5.activity.ReleaseListActivity;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReleaseListAdapter2 extends ListBaseAdapter {



    private LayoutInflater mLayoutInflater;

    public ReleaseListAdapter2(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_jiaoge, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Map<String, Object> item = mDataList.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.typeTv.setText("流水号:" + item.get("id"));
        viewHolder.timeTv.setText(item.get("num_all") + "");
        viewHolder.numberTv.setText(item.get("time") + "");
        viewHolder.fromtoTv.setText(item.get("num") + "");
        viewHolder.jifenTv.setText(Float.parseFloat(item.get("rate") + "")*100+"%");
        viewHolder.jianglijinTv.setText(item.get("release_num") + "");



        viewHolder.tradeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((item.get("type") + "").equals("1")) {
                    Intent intent = new Intent(mContext, ReleaseListActivity.class);
                    intent.putExtra("DATA", (Serializable) item);
                    mContext.startActivity(intent);
                }

            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.type_tv)
        TextView typeTv;
        @BindView(R.id.number_tv)
        TextView numberTv;
        @BindView(R.id.fromto_tv)
        TextView fromtoTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.jifen_tv)
        TextView jifenTv;
        @BindView(R.id.jianglijin_tv)
        TextView jianglijinTv;
        @BindView(R.id.trade_lay)
        CardView tradeLay;
        @BindView(R.id.to_iv)
        ImageView toIv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
