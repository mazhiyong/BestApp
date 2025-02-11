package cn.wildfire.chat.kit.conversation;

import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.MbsConstans;

import butterknife.BindView;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfirechat.model.ConversationInfo;

public class ConversationInfoActivity extends WfcBaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ConversationInfo conversationInfo;

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }

    @Override
    protected void afterViews() {
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);
        tvTitle.setText("会话详情");
        conversationInfo = getIntent().getParcelableExtra("conversationInfo");
        Fragment fragment = null;
        switch (conversationInfo.conversation.type) {
            case Single: //单聊
                fragment = SingleConversationInfoFragment.newInstance(conversationInfo);
                break;
            case Group: //群聊
                fragment = GroupConversationInfoFragment.newInstance(conversationInfo);
                break;
            case ChatRoom: //聊天室
                // TODO
                break;
           /* case Channel:
                //频道
                fragment = ChannelConversationInfoFragment.newInstance(conversationInfo);
                break;*/
            default:
                break;
        }
        if (fragment == null) {
            //Toast.makeText(this, "todo", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
