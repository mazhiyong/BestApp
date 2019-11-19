package com.lr.best.ui.moudle4.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.listener.SoftKeyBoardListener;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.ui.moudle4.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BasicActivity implements RequestView {


    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.back_text)
    TextView backText;
    @BindView(R.id.left_back_lay)
    LinearLayout leftBackLay;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.right_text_tv)
    TextView rightTextTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_lay)
    LinearLayout rightLay;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.rc_view)
    RecyclerView rcView;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    @BindView(R.id.edit_msg)
    EditText editMsg;
    @BindView(R.id.btn_chat_send)
    Button btnChatSend;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;

    private ChatAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Map<String, Object>> mapList = new ArrayList<>();


    @Override
    public int getContentView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        return R.layout.activity_chat;
    }

    @Override
    public void init() {
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);
        titleText.setText("联系客服");
        titleText.setCompoundDrawables(null, null, null, null);
        divideLine.setVisibility(View.GONE);
        initView();

    }

    private void initView() {
        swRefresh.setEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        rcView.setLayoutManager(layoutManager);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            if (i % 2 == 0) {
                map.put("type", "0");
                map.put("content", "你好吗");
            } else {
                map.put("type", "1");
                map.put("content", "好你妹");
            }
            mapList.add(map);

        }
        adapter = new ChatAdapter(ChatActivity.this, mapList);
        rcView.setAdapter(adapter);
        llChat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llChat.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(false);
                //自动刷新
                //queryMessages(null);
            }
        });

        //下拉刷新
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swRefresh.setRefreshing(false);
            }
        });

        SoftKeyBoardListener.setListener(ChatActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //键盘显示
                scrollToBottom();
            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏
            }
        });
       /* editMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });*/



        editMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {

                    btnChatSend.setEnabled(true);
                } else {
                    btnChatSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }



    @OnClick({R.id.left_back_lay, R.id.btn_chat_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back_lay:
                finish();
                break;
            case R.id.btn_chat_send:
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .showSoftInput(editMsg, 0);
                String text = editMsg.getText().toString();
                Map<String, Object> map = new HashMap<>();
                map.put("type", "1");
                map.put("content", text);
                adapter.addMessage(map);
                scrollToBottom();
                adapter.notifyDataSetChanged();
                editMsg.setText("");
                editMsg.requestFocus();

                break;
        }
    }
    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
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
}
