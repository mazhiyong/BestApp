package cn.wildfire.chat.kit.group.manage;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.basic.MbsConstans;

import butterknife.BindView;
import cn.wildfire.chat.kit.WfcBaseActivity;
import cn.wildfirechat.model.GroupInfo;

public class GroupMemberPermissionActivity extends WfcBaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    protected void afterViews() {
        tvTitle.setText("成员权限设置");
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(this, MbsConstans.TOP_BAR_COLOR), MbsConstans.ALPHA);
        GroupInfo groupInfo = getIntent().getParcelableExtra("groupInfo");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, GroupMemberPermissionFragment.newInstance(groupInfo))
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}
