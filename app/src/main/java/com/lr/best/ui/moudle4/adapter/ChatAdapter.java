package com.lr.best.ui.moudle4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lr.best.R;
import com.lr.best.basic.MbsConstans;
import com.lr.best.utils.imageload.GlideUtils;
import com.lr.best.utils.tool.JSONUtil;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :smile
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //文本
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;

    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;

    private List<Map<String,Object>> msgs = new ArrayList<>();
    private Context context;


    public ChatAdapter( Context context,List<Map<String, Object>> msgs) {
        this.msgs = msgs;
        this.context = context;
    }


    public int getCount() {
        return this.msgs == null?0:this.msgs.size();
    }

    public void addMessages(List<Map<String,Object>> messages) {
        msgs.addAll(0, messages);
        notifyDataSetChanged();
    }

    public void addMessage(Map<String,Object> message) {
        msgs.add(message);
        //notifyDataSetChanged();
    }

    /**获取消息
     * @param position
     * @return
     */
    public Map<String,Object> getItem(int position){
        return this.msgs == null?null:(position >= this.msgs.size()?null:this.msgs.get(position));
    }

    /**移除消息
     * @param position
     */
    public void remove(int position){
        msgs.remove(position);
        notifyDataSetChanged();
    }

    public Map<String, Object> getFirstMessage() {
        if (null != msgs && msgs.size() > 0) {
            return msgs.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND_TXT) {
            return new SendTextHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_sent_message, parent, false));
        } else if (viewType == TYPE_RECEIVER_TXT) {
            return new ReceiveTextHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_received_message, parent, false));
        }else{//开发者自定义的其他类型，可自行处理
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReceiveTextHolder) {
            //((ReceiveTextHolder)holder).showTime(shouldShowTime(position));
            ((ReceiveTextHolder) holder).tv_message.setText(msgs.get(position).get("text")+"");
            if (UtilTools.empty(msgs.get(position).get("img"))){
                if ((msgs.get(position).get("kind")+"").equals("0")){
                    ((ReceiveTextHolder) holder).iv_avatar.setImageResource(R.drawable.man);
                }else {
                    ((ReceiveTextHolder) holder).iv_avatar.setImageResource(R.drawable.woman);
                }
            }else {
                GlideUtils.loadImage2(context, msgs.get(position).get("img") + "",((ReceiveTextHolder) holder).iv_avatar , R.drawable.default_headimg);
            }

        } else {
            //((SendTextHolder)holder).showTime(shouldShowTime(position));
           /* if (UtilTools.empty(msgs.get(position).get("img"))){
                if (UtilTools.empty(MbsConstans.USER_MAP)) {
                    String s = SPUtils.get(context, MbsConstans.SharedInfoConstans.LOGIN_INFO, "").toString();
                    MbsConstans.USER_MAP = JSONUtil.getInstance().jsonMap(s);
                }
                GlideUtils.loadImage2(context, MbsConstans.USER_MAP.get("portrait") + "",((SendTextHolder) holder).iv_avatar , R.drawable.default_headimg);
            }else {
                GlideUtils.loadImage2(context, msgs.get(position).get("img") + "",((SendTextHolder) holder).iv_avatar , R.drawable.default_headimg);
            }*/
            //用户头像
            if (UtilTools.empty(MbsConstans.USER_MAP)) {
                String s = SPUtils.get(context, MbsConstans.SharedInfoConstans.LOGIN_INFO, "").toString();
                MbsConstans.USER_MAP = JSONUtil.getInstance().jsonMap(s);
            }
            GlideUtils.loadImage2(context, MbsConstans.USER_MAP.get("portrait") + "",((SendTextHolder) holder).iv_avatar ,R.drawable.default_headimg);
            ((SendTextHolder) holder).tv_message.setText(msgs.get(position).get("text")+"");
        }
    }

    @Override
    public int getItemViewType(int position) {
        Map<String,Object> message = msgs.get(position);
        if((message.get("status")+"").equals("0")){
           return TYPE_RECEIVER_TXT;
        }else {
            return TYPE_SEND_TXT;
        }
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }




    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }else {
            return false;
        }
        //long lastTime = msgs.get(position - 1).getCreateTime();
        //long curTime = msgs.get(position).getCreateTime();
        //return curTime - lastTime > TIME_INTERVAL;
    }


   class SendTextHolder  extends RecyclerView.ViewHolder {

       @BindView(R.id.iv_avatar)
       protected CircleImageView iv_avatar;

       @BindView(R.id.iv_fail_resend)
       protected ImageView iv_fail_resend;

       @BindView(R.id.tv_time)
       protected TextView tv_time;

       @BindView(R.id.tv_message)
       protected TextView tv_message;
       @BindView(R.id.tv_send_status)
       protected TextView tv_send_status;

       @BindView(R.id.progress_load)
       protected ProgressBar progress_load;


       private Context mContext;

       public SendTextHolder(View itemView) {
           super(itemView);
           ButterKnife.bind(this, itemView);
       }


   }

    class ReceiveTextHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        protected CircleImageView iv_avatar;

        @BindView(R.id.tv_time)
        protected TextView tv_time;

        @BindView(R.id.tv_message)
        protected TextView tv_message;

        public ReceiveTextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
