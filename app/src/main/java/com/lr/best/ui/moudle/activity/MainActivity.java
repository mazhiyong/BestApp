package com.lr.best.ui.moudle.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;
import com.lr.best.R;
import com.lr.best.api.MethodUrl;
import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.MbsConstans;
import com.lr.best.bean.MessageEvent;
import com.lr.best.db.IndexData;
import com.lr.best.mvp.view.RequestView;
import com.lr.best.mywidget.dialog.AppDialog;
import com.lr.best.mywidget.dialog.UpdateDialog;
import com.lr.best.service.DownloadService;
import com.lr.best.ui.moudle1.fragment.HomeFragment;
import com.lr.best.ui.moudle2.fragment.TradeFragment;
import com.lr.best.ui.moudle3.fragment.ShopFragment;
import com.lr.best.ui.moudle4.fragment.ChatViewFragment;
import com.lr.best.ui.moudle5.fragment.ZiChanFragment;
import com.lr.best.utils.permission.PermissionsUtils;
import com.lr.best.utils.permission.RePermissionResultBack;
import com.lr.best.utils.tool.JSONUtil;
import com.lr.best.utils.tool.LogUtilDebug;
import com.lr.best.utils.tool.SPUtils;
import com.lr.best.utils.tool.UtilTools;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfire.chat.kit.ChatManagerHolder;
import cn.wildfire.chat.kit.contact.ContactViewModel;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModel;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModelFactory;
import cn.wildfirechat.client.ConnectionStatus;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.remote.OnConnectionStatusChangeListener;


public class MainActivity extends BasicActivity implements RequestView {
    @BindView(R.id.btn_cart)
    ImageView btnCart;
    @BindView(R.id.btn_cart2)
    ImageView btnCart2;
    @BindView(R.id.btn_container_index)
    RelativeLayout rlay1;
    @BindView(R.id.btn_container_get)
    RelativeLayout rlay2;
    @BindView(R.id.btn_container_return)
    RelativeLayout rlay3;
    @BindView(R.id.btn_container_person)
    RelativeLayout rlay4;
    @BindView(R.id.btn_container_zichan)
    RelativeLayout rlay5;


    private TextView unreadLabel;
    // textview for unread event message
    private TextView unreadNewLable;
    private ImageView[] mTabs;
    private TextView[] mTextViews;
    private HomeFragment mHomeFrament;
    private ChatViewFragment mChatFragment;
    private ShopFragment mShopFragment;
    private TradeFragment mTradeFragment;
    private ZiChanFragment mPersonFragment;
    private Fragment[] fragments;

    private TextView mAutoScrollTextView;

    private int index;
    private int currentTabIndex;
    public static MainActivity mInstance;

    private RelativeLayout mIndexBottomLay;
    private Snackbar snackbar;

    private String mRequestTag = "";

    private Intent mDownIntent;

    private IndexData mIndexData;


    public static final int REQUEST_START_GROUP = 1;

    private Conversation.ConversationType[] conversationTypes;

    private int newsUnReadCount = 0;
    private int requestUnReadCount = 0;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    /**
     * 主界面不需要支持滑动返回，重写该方法永久禁用当前界面的滑动返回功能
     *
     * @return
     */

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void init() {

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MbsConstans.BroadcastReceiverAction.USER_INFO_UPDATE);
        registerReceiver(mBroadcastReceiver, intentFilter);

        Intent intent = new Intent();
        intent.setAction(MbsConstans.BroadcastReceiverAction.MAIN_ACTIVITY);
        sendBroadcast(intent);

        SPUtils.put(this, MbsConstans.SharedInfoConstans.LOGIN_OUT, false);

        StatusBarUtil.setTranslucentForImageView(this, MbsConstans.ALPHA, null);
        initView();

        //mDownIntent = new Intent(this, DownloadService.class);
        //startService(mDownIntent);

        mIndexData = IndexData.getInstance();

        mHandler = new Handler(Looper.getMainLooper());
        // initPush();
        mInstance = this;

        //启动SocketService
        //Intent msgIntent=new Intent(this, SocketService.class);
        //startService(msgIntent);

        // getAppVersion();
        getUserInfoAction();
        // getNameCodeInfo();


        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList.size() > 0){
            for (Fragment fragment:fragmentList){
                getSupportFragmentManager().beginTransaction().remove(fragment);
            }
        }

      /*  //首次安装启动
        String code = SPUtils.get(MainActivity.this, MbsConstans.SharedInfoConstans.IS_FIRST_START, "") + "";
        Log.i("show","code:"+code+"   APP_CODE:"+MbsConstans.UpdateAppConstans.VERSION_APP_CODE);
        if (!code.equals(MbsConstans.UpdateAppConstans.VERSION_APP_CODE + "")) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(R.string.title_dialog)
                    .setMessage("开启系统悬浮窗,当应用置于后台时,可在第一时间收到消息通知")
                    .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                            PermissionUtils.requestPermission(MainActivity.this, new OnPermissionResult() {
                                @Override
                                public void permissionResult(boolean b) {
                                    if (b){
                                        ToastUtils.showToast("悬浮窗开启成功");
                                    }else {
                                        ToastUtils.showToast("悬浮窗开启失败");
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("暂不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    })
                    .show();

            SPUtils.put(MainActivity.this, MbsConstans.SharedInfoConstans.IS_FIRST_START, MbsConstans.UpdateAppConstans.VERSION_APP_CODE+"");
        }*/

         //首次安装启动
        String code = SPUtils.get(MainActivity.this, MbsConstans.SharedInfoConstans.IS_FIRST_START, "") + "";
        if (!code.equals(MbsConstans.UpdateAppConstans.VERSION_APP_CODE + "")) {
            AppDialog dialog = new AppDialog(this);
            dialog.initValue("温馨提示","亲爱的玩家，欢迎您来到BEST数字资产现货交易所！\n\n"+"风险提示：数字货币是一种高风险的投资方式，请投资者谨慎购买，并注意投资风险。BEST会遴选优质币种，但不对您投资行为承担担保、赔偿等责任。您是否同意","不同意","同意");
            dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.cancel:
                            dialog.dismiss();
                            closeAllActivity();
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                            break;
                        case R.id.confirm:
                            dialog.dismiss();
                            SPUtils.put(MainActivity.this, MbsConstans.SharedInfoConstans.IS_FIRST_START, MbsConstans.UpdateAppConstans.VERSION_APP_CODE+"");
                            break;
                    }
                }
            });
            dialog.show();
        }



        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String id = sp.getString("id", null);
        String token = sp.getString("token", null);
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(token)) {
            if (UtilTools.empty(MbsConstans.RONGYUN_MAP)) {
                String s = SPUtils.get(MainActivity.this, MbsConstans.SharedInfoConstans.RONGYUN_DATA, "").toString();
                MbsConstans.RONGYUN_MAP = JSONUtil.getInstance().jsonMap(s);
            }
            ChatManagerHolder.gChatManager.connect(MbsConstans.RONGYUN_MAP.get("id") + "", MbsConstans.RONGYUN_MAP.get("token") + "");
            sp.edit().putString("id", MbsConstans.RONGYUN_MAP.get("id") + "")
                    .putString("token", MbsConstans.RONGYUN_MAP.get("token") + "")
                    .apply();
        }

        ChatManagerHolder.gChatManager.addConnectionChangeListener(new OnConnectionStatusChangeListener() {
            @Override
            public void onConnectionStatusChange(int status) {
                switch (status) {
                    case ConnectionStatus.ConnectionStatusTokenIncorrect:
                    case ConnectionStatus.ConnectionStatusLogout:
                    case ConnectionStatus.ConnectionStatusUnconnected:
                        Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }

            }
        });


        //0 红跌绿涨   1红涨绿跌
        String colorType = SPUtils.get(MainActivity.this, MbsConstans.SharedInfoConstans.COLOR_TYPE, "0").toString();
        if (colorType.equals("0")) {
            MbsConstans.COLOR_LOW = MbsConstans.COLOR_RED;
            MbsConstans.COLOR_TOP = MbsConstans.COLOR_GREEN;
        } else {
            MbsConstans.COLOR_LOW = MbsConstans.COLOR_GREEN;
            MbsConstans.COLOR_TOP = MbsConstans.COLOR_RED;
        }



        //会话列表ViewModel
        ConversationListViewModel conversationListViewModel = ViewModelProviders
                .of(this, new ConversationListViewModelFactory(Arrays.asList(cn.wildfirechat.model.Conversation.ConversationType.Single, cn.wildfirechat.model.Conversation.ConversationType.Group, cn.wildfirechat.model.Conversation.ConversationType.Channel), Arrays.asList(0)))
                .get(ConversationListViewModel.class);
        conversationListViewModel.unreadCountLiveData().observe(this, unreadCount -> {

            if (unreadCount == null) {
                if (requestUnReadCount == 0) {
                    unreadNewLable.setVisibility(View.GONE);
                } else {
                    unreadNewLable.setVisibility(View.VISIBLE);
                    if (requestUnReadCount < 99) {
                        unreadNewLable.setText(requestUnReadCount + "");
                    } else {
                        unreadNewLable.setText("99+");
                    }
                }

            } else {
                newsUnReadCount = unreadCount.unread;
                if (newsUnReadCount == 0 && requestUnReadCount == 0) {
                    unreadNewLable.setVisibility(View.GONE);
                } else {
                    unreadNewLable.setVisibility(View.VISIBLE);
                    if ((newsUnReadCount + requestUnReadCount) < 100) {
                        unreadNewLable.setText(newsUnReadCount + requestUnReadCount + "");
                    } else {
                        unreadNewLable.setText("99+");
                    }
                }


            }
        });
        //联系人ViewModel
        ContactViewModel contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.friendRequestUpdatedLiveData().observe(this, count -> {
            if (count == null) {
                unreadNewLable.setVisibility(View.GONE);
                if (newsUnReadCount == 0) {
                    unreadNewLable.setVisibility(View.GONE);
                } else {
                    unreadNewLable.setVisibility(View.VISIBLE);
                    if (newsUnReadCount < 99) {
                        unreadNewLable.setText(newsUnReadCount + "");
                    } else {
                        unreadNewLable.setText("99+");
                    }
                }

            } else {
                requestUnReadCount = count;
                if (newsUnReadCount == 0 && requestUnReadCount == 0) {
                    unreadNewLable.setVisibility(View.GONE);
                } else {
                    unreadNewLable.setVisibility(View.VISIBLE);
                    if ((newsUnReadCount + requestUnReadCount) < 100) {
                        unreadNewLable.setText(newsUnReadCount + requestUnReadCount + "");
                    } else {
                        unreadNewLable.setText("99+");
                    }
                }


            }
        });

    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MbsConstans.BroadcastReceiverAction.USER_INFO_UPDATE)) {
                getUserInfoAction();
            }
        }
    };

    private void initView() {
        mIndexBottomLay = findViewById(R.id.btn_container_index);

        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadNewLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new ImageView[5];
        mTabs[0] = (ImageView) findViewById(R.id.btn_conversation);
        mTabs[1] = (ImageView) findViewById(R.id.btn_address_list);
        mTabs[2] = (ImageView) findViewById(R.id.btn_cart);
        mTabs[3] = (ImageView) findViewById(R.id.btn_setting);
        mTabs[4] = (ImageView) findViewById(R.id.btn_zichan);

        mTextViews = new TextView[5];
        mTextViews[0] = (TextView) findViewById(R.id.one_tv);
        mTextViews[1] = (TextView) findViewById(R.id.two_tv);
        mTextViews[2] = (TextView) findViewById(R.id.three_tv);
        mTextViews[3] = (TextView) findViewById(R.id.four_tv);
        mTextViews[4] = (TextView) findViewById(R.id.five_tv);
        // select first tab
        mTabs[0].setSelected(true);
        mTextViews[0].setSelected(true);

        mHomeFrament = new HomeFragment();
        mChatFragment = new ChatViewFragment();
        mShopFragment = new ShopFragment();
        mTradeFragment = new TradeFragment();
        mPersonFragment = new ZiChanFragment();
        fragments = new Fragment[]{mHomeFrament,  mTradeFragment, mShopFragment,  mChatFragment,mPersonFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mHomeFrament)
                .show(mHomeFrament)
                .commitAllowingStateLoss();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取用户基本信息
     */
    public void getUserInfoAction() {
        mRequestTag = MethodUrl.USER_INFO;
        Map<String, Object> map = new HashMap<>();
        if (UtilTools.empty(MbsConstans.ACCESS_TOKEN)) {
            MbsConstans.ACCESS_TOKEN = SPUtils.get(MainActivity.this, MbsConstans.SharedInfoConstans.ACCESS_TOKEN, "").toString();
        }
        map.put("token", MbsConstans.ACCESS_TOKEN);
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestPostToMap(mHeaderMap, MethodUrl.USER_INFO, map);
    }

    /**
     * 获取全局字典配置信息
     */
    public void getNameCodeInfo() {
        mRequestTag = MethodUrl.nameCode;
        Map<String, String> map = new HashMap<>();
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestGetToRes(mHeaderMap, MethodUrl.nameCode, map);
    }

    /**
     * 获取app更新信息
     */
    public void getAppVersion() {
        mRequestTag = MethodUrl.appVersion;

        Map<String, String> map = new HashMap<>();
        map.put("appCode", MbsConstans.UPDATE_CODE);
        map.put("osType", "android");
        Map<String, String> mHeaderMap = new HashMap<String, String>();
        mRequestPresenterImp.requestGetToMap(mHeaderMap, MethodUrl.appVersion, map);
    }

    /**
     * on tab clicked
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_container_index:
                index = 0;
                btnCart2.setSelected(false);
                break;
            case R.id.btn_container_get:
                btnCart2.setSelected(false);
                index = 1;
                break;
            case R.id.btn_container_return:
                index = 2;
                btnCart2.setSelected(true);
                break;
            case R.id.btn_container_person:
                btnCart2.setSelected(false);
                index = 3;
                break;
            case R.id.btn_container_zichan:
                btnCart2.setSelected(false);
                index = 4;
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            } else {
                switch (index) {// mChatFragment, mBorrowFragment, mTradeFragment,mPersonFragment
                    case 0:
                        ((HomeFragment) fragments[index]).setBarTextColor();
                        break;
                    case 1:
                        ((TradeFragment) fragments[index]).setBarTextColor();
                        break;
                    case 2:
                        ((ShopFragment) fragments[index]).setBarTextColor();
                        break;
                    case 3:
                        ((ChatViewFragment) fragments[index]).setBarTextColor();
                        break;
                    case 4:
                        ((ZiChanFragment) fragments[index]).setBarTextColor();
                        break;

                }
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);

        mTextViews[currentTabIndex].setSelected(false);
        mTextViews[index].setSelected(true);
        currentTabIndex = index;


    }


    @OnClick({R.id.btn_cart, R.id.btn_cart2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cart:
            case R.id.btn_cart2:
                btnCart2.setSelected(true);
                index = 2;
                if (currentTabIndex != index) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.fragment_container, fragments[index]);
                    } else {
                        switch (index) {// mChatFragment, mBorrowFragment, mTradeFragment,mPersonFragment
                            case 0:
                                ((HomeFragment) fragments[index]).setBarTextColor();
                                break;
                            case 1:
                                ((TradeFragment) fragments[index]).setBarTextColor();
                                break;
                            case 2:
                                ((ShopFragment) fragments[index]).setBarTextColor();
                                break;
                            case 3:
                                ((ChatViewFragment) fragments[index]).setBarTextColor();
                                break;
                            case 4:
                                ((ZiChanFragment) fragments[index]).setBarTextColor();
                                break;

                        }
                    }
                    trx.show(fragments[index]).commitAllowingStateLoss();
                }
                mTabs[currentTabIndex].setSelected(false);
                // set current tab selected
                mTabs[index].setSelected(true);

                mTextViews[currentTabIndex].setSelected(false);
                mTextViews[index].setSelected(true);
                currentTabIndex = index;
                break;
        }
    }


    private Handler mHandler;
    private boolean isOnKeyBacking;

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentTabIndex != 0) {
                mIndexBottomLay.performClick();
            } else {
                if (isOnKeyBacking) {
                    mHandler.removeCallbacks(onBackTimeRunnable);
                    if (snackbar != null) {
                        snackbar.dismiss();
                    }
                    closeAllActivity();
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                    return true;
                } else {
                    isOnKeyBacking = true;
                    if (snackbar == null) {
                        snackbar = Snackbar.make(findViewById(R.id.fragment_container), "再次点击退出应用", Snackbar.LENGTH_SHORT);
                        snackbar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
                    }
                    snackbar.show();
                    mHandler.postDelayed(onBackTimeRunnable, 2000);
                    return true;
                }
            }
            return true;
        }
        //拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_START_GROUP:

                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void disimissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void loadDataSuccess(Map<String, Object> tData, String mType) {

        // {"id":1,"appCode":"phb","downUrl":"http://ys.51zhir.cn/app_api/apk/dr20190514.apk","fileName":"dr20190514.apk",
        // "fileSize":null,"isMust":"0","md5Code":"722f70f68c262e9c585f7dd800ae327c",
        // "memo":null,"osType":"android","verCode":"1","verName":"V1.0.1 Beta","verUpdateMsg":"版本更新内容"}
        switch (mType) {
            case MethodUrl.appVersion:
                if (tData != null && !tData.isEmpty()) {
                    //网络版本号
                    MbsConstans.UpdateAppConstans.VERSION_NET_CODE = UtilTools.getIntFromStr(tData.get("verCode") + "");
                    //MbsConstans.UpdateAppConstans.VERSION_NET_CODE = 3;
                    //网络下载url
                    MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL = tData.get("downUrl") + "";
                    //MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                    //网络版本名称
                    MbsConstans.UpdateAppConstans.VERSION_NET_APK_NAME = tData.get("verName") + "";
                    //网络MD5值
                    MbsConstans.UpdateAppConstans.VERSION_MD5_CODE = tData.get("md5Code") + "";
                    //本次更新内容
                    MbsConstans.UpdateAppConstans.VERSION_NET_UPDATE_MSG = tData.get("verUpdateMsg") + "";

                    String mustUpdate = tData.get("isMust") + "";
                    if (mustUpdate.equals("0")) {
                        MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE = false;
                    } else {
                        MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE = true;
                    }
                    showUpdateDialog();
                }
                break;
            case MethodUrl.nameCode:
                String result = tData.get("result") + "";
                SPUtils.put(MainActivity.this, MbsConstans.SharedInfoConstans.NAME_CODE_DATA, result);
                break;
            case MethodUrl.USER_INFO://用户信息 //{auth=1, firm_kind=0, head_pic=default, name=刘英超, tel=151****3298, idno=4107****3616, cmpl_info=0}
                switch (tData.get("code") + "") {
                    case "0": //请求成功
                        MbsConstans.USER_MAP = (Map<String, Object>) tData.get("data");
                        if (!UtilTools.empty(MbsConstans.USER_MAP)) {
                            SPUtils.put(MainActivity.this, MbsConstans.SharedInfoConstans.LOGIN_INFO, JSONUtil.getInstance().objectToJson(MbsConstans.USER_MAP));
                        }
                        break;
                    case "-1": //请求失败
                        showToastMsg(tData.get("msg") + "");
                        break;

                    case "1": //token过期
                        closeAllActivity();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;

                }

                //showUpdateDialog();
                break;
            case MethodUrl.REFRESH_TOKEN://获取refreshToken返回结果
                MbsConstans.REFRESH_TOKEN = tData.get("refresh_token") + "";
                mIsRefreshToken = false;
                for (String stag : mRequestTagList) {
                    switch (stag) {
                        case MethodUrl.USER_INFO:
                            getUserInfoAction();
                            break;
                        case MethodUrl.nameCode://{
                            getNameCodeInfo();
                            break;
                    }
                    mRequestTagList = new ArrayList<>();
                    break;
                }
        }
    }

    @Override
    public void loadDataError(Map<String, Object> map, String mType) {
        switch (mType) {
            case MethodUrl.appVersion:
                break;
        }
        dealFailInfo(map, mType);
    }


    private UpdateDialog mUpdateDialog;

    private void showUpdateDialog() {
        if (MbsConstans.UpdateAppConstans.VERSION_NET_CODE > MbsConstans.UpdateAppConstans.VERSION_APP_CODE) {
            mUpdateDialog = new UpdateDialog(this, true);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.cancel:
                            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                                showToastMsg("本次升级为必须更新，请您更新！");
                            } else {
                                mUpdateDialog.dismiss();
                            }
                            break;
                        case R.id.confirm:
                            PermissionsUtils.requsetRunPermission(MainActivity.this, new RePermissionResultBack() {
                                @Override
                                public void requestSuccess() {
                                    mUpdateDialog.getProgressLay().setVisibility(View.VISIBLE);
                                    DownloadService.downNewFile(MbsConstans.UpdateAppConstans.VERSION_NET_APK_URL, 1003,
                                            MbsConstans.UpdateAppConstans.VERSION_NET_APK_NAME, MbsConstans.UpdateAppConstans.VERSION_MD5_CODE, MainActivity.this);
                                }

                                @Override
                                public void requestFailer() {

                                }
                            }, Permission.Group.STORAGE);

                            if (!MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                                mUpdateDialog.dismiss();
                            }
                            break;
                    }
                }
            };
            mUpdateDialog.setCanceledOnTouchOutside(false);
            mUpdateDialog.setCancelable(false);
            String ss = "";
            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                ss = "必须更新";
            } else {
                ss = "建议更新";
            }
            mUpdateDialog.setOnClickListener(onClickListener);
            mUpdateDialog.initValue("检查新版本" + "(" + ss + ")", "更新内容:\n" + MbsConstans.UpdateAppConstans.VERSION_NET_UPDATE_MSG);
            mUpdateDialog.show();

            if (MbsConstans.UpdateAppConstans.VERSION_UPDATE_FORCE) {
                mUpdateDialog.setCancelable(false);
                mUpdateDialog.tv_cancel.setEnabled(false);
                mUpdateDialog.tv_cancel.setVisibility(View.GONE);
            } else {
                mUpdateDialog.setCancelable(true);
                mUpdateDialog.tv_cancel.setEnabled(true);
                mUpdateDialog.tv_cancel.setVisibility(View.VISIBLE);
            }
            mUpdateDialog.getProgressLay().setVisibility(View.GONE);
            DownloadService.mProgressBar = mUpdateDialog.getProgressBar();
            DownloadService.mTextView = mUpdateDialog.getPrgText();


        }
    }


    /**
     * DownLoadManager 下载时EventBus更新UI
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(MessageEvent event) {
        switch (event.getType()) {
            case 0:
                Map<Object, Object> map = event.getMessage();
                LogUtilDebug.i("show", "eventBus:" + map.get("size"));
                mUpdateDialog.update(map.get("max") + "", map.get("size") + "", "下载进度: " + map.get("progress") + "");
                break;

            case 1:
                LogUtilDebug.i("show", "eventBus: update UI");
                getUserInfoAction();
                break;

        }
    }


    public void toHeYueFragment() {
        rlay3.performClick();
    }

    public void toBBFragment(String selectArea, String slectSymbol, String type) {
        rlay2.performClick();
        mTradeFragment.TYPE = 0;
      /*  mTradeFragment.selectSymbol = slectSymbol;
        mTradeFragment.selectArea = selectArea;*/
        mTradeFragment.buySell = type;
    }

    public void toFBFragment() {
        rlay2.performClick();
        mTradeFragment.TYPE = 1;
    }


    /**
     * activity销毁前触发的方法
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * activity恢复时触发的方法
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsRefresh = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver);
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }


}
